package com.mxy.ai.manus.tool.bash;

import java.util.List;

/**
 * @Author：mxy
 * @Date：2025-05-04-21:18
 * @Version：1.0
 * @Description：
 * Shell命令执行器接口 提供跨平台(Windows/Linux/Mac)的shell命令执行能力
 */
public interface ShellCommandExecutor {

    /**
     * 执行shell命令
     * @param commands 要执行的命令列表
     * @param workingDir 工作目录
     * @return 命令执行结果列表
     */
    List<String> execute(List<String> commands, String workingDir);

    /**
     * 终止当前正在执行的进程
     */
    void terminate();

    /**
     * 获取当前系统类型
     * @return 系统类型(windows/linux/mac)
     */
    default String getOsType() {
        String os = System.getProperty("os.name").toLowerCase();
        if (os.contains("win")) {
            return "windows";
        }
        else if (os.contains("mac")) {
            return "mac";
        }
        else {
            return "linux";
        }
    }

}
