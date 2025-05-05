package com.mxy.ai.manus.dynamic.agent;

import com.mxy.ai.manus.planning.PlanningFactory;

import java.util.Map;

/**
 * @Author：mxy
 * @Date：2025-05-04-15:50
 * @Version：1.0
 * @Description：
 */
public interface ToolCallbackProvider{

    Map<String, PlanningFactory.ToolCallBackContext> getToolCallBackContext();
}
