package com.mxy.ai.manus.js;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.microsoft.playwright.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class PlaywrightDomTreeDemo {
    public static void main(String[] args) {
        try (Playwright playwright = Playwright.create()) {
            // 启动浏览器
            Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions()
                    .setHeadless(false)); // 设置为有头模式，方便查看效果

            // 创建上下文和页面
            BrowserContext context = browser.newContext();
            Page page = context.newPage();

            // 导航到百度
            page.navigate("https://www.baidu.com");
            System.out.println("页面已加载: " + page.title());

            // 准备传递给JS的参数
            Map<String, Object> params = new HashMap<>();
            params.put("doHighlightElements", true);
            params.put("focusHighlightIndex", -1);
            params.put("viewportExpansion", 0);
            params.put("debugMode", true);

            // 读取JavaScript代码
            String jsCode = readJsFile();

            // 执行JavaScript代码并获取返回结果
            Object result = page.evaluate(jsCode, params);

            // 将结果转换为JSON并打印出来
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String jsonResult = gson.toJson(result);

            // 只打印前1000个字符，避免输出过多
            System.out.println("DOM树构建结果 (部分):");
            System.out.println(jsonResult.substring(0, Math.min(jsonResult.length(), 1000)) + "...");

            // 将完整结果保存到文件
            Files.writeString(Paths.get("dom_tree_result.json"), jsonResult);
            System.out.println("完整结果已保存到 dom_tree_result.json 文件");

            // 将结果也转换为Java对象进行处理
            processDomTree(result);

            // 保持浏览器打开一段时间，以便查看高亮效果
            Thread.sleep(5000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 读取JavaScript文件
     */
    private static String readJsFile() throws Exception {
        return Files.readString(Paths.get("D:\\mxy\\qisheng\\workspace\\mxy-git-workspace\\mxyManus\\mxy-java-ai-manus\\src\\main\\java\\com\\mxy\\ai\\manus\\js\\buildDomTree.js"));
    }

    /**
     * 处理DOM树结果
     */
    @SuppressWarnings("unchecked")
    private static void processDomTree(Object domTreeResult) {
        try {
            // 将原始结果转换为Map
            Map<String, Object> resultMap = (Map<String, Object>) domTreeResult;

            // 获取节点映射和根节点ID
            Map<String, Object> nodeMap = (Map<String, Object>) resultMap.get("map");
            String rootId = (String) resultMap.get("rootId");

            System.out.println("DOM树节点数量: " + nodeMap.size());
            System.out.println("根节点ID: " + rootId);

            // 简单统计可交互元素数量
            int interactiveElements = 0;
            for (Object node : nodeMap.values()) {
                Map<String, Object> nodeData = (Map<String, Object>) node;
                if (nodeData.containsKey("isInteractive") && (Boolean) nodeData.get("isInteractive")) {
                    interactiveElements++;
                }
            }

            System.out.println("可交互元素数量: " + interactiveElements);

            // 如果存在性能指标，打印出来
            if (resultMap.containsKey("perfMetrics")) {
                Map<String, Object> perfMetrics = (Map<String, Object>) resultMap.get("perfMetrics");
                System.out.println("性能指标:");
                System.out.println("- 总处理节点: " +
                        ((Map<String, Object>)perfMetrics.get("nodeMetrics")).get("processedNodes"));
                System.out.println("- 总跳过节点: " +
                        ((Map<String, Object>)perfMetrics.get("nodeMetrics")).get("skippedNodes"));
            }

        } catch (Exception e) {
            System.err.println("处理DOM树时出错: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
