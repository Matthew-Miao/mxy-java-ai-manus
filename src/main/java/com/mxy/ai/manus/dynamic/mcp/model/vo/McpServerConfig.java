package com.mxy.ai.manus.dynamic.mcp.model.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author：mxy
 * @Date：2025-05-04-16:14
 * @Version：1.0
 * @Description：内部服务器配置类
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class McpServerConfig {
    private String url;

    @JsonProperty("command")
    private String command;

    @JsonProperty("args")
    private List<String> args;

    @JsonProperty("env")
    private Map<String, String> env;

    public McpServerConfig() {
        this.env = new HashMap<>();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public List<String> getArgs() {
        return args;
    }

    public void setArgs(List<String> args) {
        this.args = args;
    }

    public Map<String, String> getEnv() {
        return env;
    }
    public void setEnv(Map<String, String> env) {
        this.env = env;
    }

    /**
     * 将ServerConfig转换为JSON字符串
     * @return 转换后的JSON字符串
     */
    public String toJson() {
        try {
            return new ObjectMapper().writeValueAsString(this);
        }
        catch (Exception e) {
            // 如果序列化失败，则手动构建简化版JSON
            StringBuilder sb = new StringBuilder();
            sb.append("{");

            // 添加URL（如果存在）
            if (url != null && !url.isEmpty()) {
                sb.append("\"url\":\"").append(url).append("\"");
            }

            // 添加命令（如果存在）
            if (command != null && !command.isEmpty()) {
                if (sb.length() > 1)
                    sb.append(",");
                sb.append("\"command\":\"").append(command).append("\"");
            }

            // 添加参数（如果存在）
            if (args != null && !args.isEmpty()) {
                if (sb.length() > 1)
                    sb.append(",");
                sb.append("\"args\":[");
                boolean first = true;
                for (String arg : args) {
                    if (!first)
                        sb.append(",");
                    sb.append("\"").append(arg).append("\"");
                    first = false;
                }
                sb.append("]");
            }

            // 添加环境变量（如果存在）
            if (env != null && !env.isEmpty()) {
                if (sb.length() > 1)
                    sb.append(",");
                sb.append("\"env\":{");
                boolean first = true;
                for (Map.Entry<String, String> entry : env.entrySet()) {
                    if (!first)
                        sb.append(",");
                    sb.append("\"").append(entry.getKey()).append("\":\"").append(entry.getValue()).append("\"");
                    first = false;
                }
                sb.append("}");
            }

            sb.append("}");
            return sb.toString();
        }
    }

}
