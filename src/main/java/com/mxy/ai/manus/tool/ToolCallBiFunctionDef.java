package com.mxy.ai.manus.tool;

import com.mxy.ai.manus.tool.code.ToolExecuteResult;
import org.springframework.ai.chat.model.ToolContext;

import java.util.function.BiFunction;

/**
 * @Author：mxy
 * @Date：2025-05-04-21:12
 * @Version：1.0
 * @Description：Tool 定义的接口，提供统一的工具定义方法
 */
public interface ToolCallBiFunctionDef  extends BiFunction<String, ToolContext, ToolExecuteResult> {
    /**
     * 获取工具组的名称
     * @return 返回工具的唯一标识名称
     */
    String getServiceGroup();

    /**
     * 获取工具的名称
     * @return 返回工具的唯一标识名称
     */
    String getName();

    /**
     * 获取工具的描述信息
     * @return 返回工具的功能描述
     */
    String getDescription();

    /**
     * 获取工具的参数定义 schema
     * @return 返回JSON格式的参数定义架构
     */
    String getParameters();

    /**
     * 获取工具的输入类型
     * @return 返回工具接受的输入参数类型Class
     */
    Class<?> getInputType();

    /**
     * 判断工具是否直接返回结果
     * @return 如果工具直接返回结果返回true，否则返回false
     */
    boolean isReturnDirect();

    /**
     * 设置关联的Agent实例
     * @param agent 要关联的BaseAgent实例
     */
    public void setPlanId(String planId);

    /**
     * 获取工具当前的状态字符串
     * @return 返回描述工具当前状态的字符串
     */
    String getCurrentToolStateString();

    /**
     * 清理指定 planId 的所有相关资源
     * @param planId 计划ID
     */
    void cleanup(String planId);
}
