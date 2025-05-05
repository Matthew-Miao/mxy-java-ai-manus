package com.mxy.ai.manus.flow;

import com.mxy.ai.manus.agent.BaseAgent;
import com.mxy.ai.manus.recorder.PlanExecutionRecorder;
import org.springframework.ai.tool.ToolCallback;

import java.util.List;
import java.util.Map;

/**
 * @Author：mxy
 * @Date：2025-05-04-11:22
 * @Version：1.0
 * @Description：
 */
public  abstract class BaseFlow {
    protected List<BaseAgent> agents;

    protected PlanExecutionRecorder recorder;

    public BaseFlow(List<BaseAgent> agents, Map<String, Object> data, PlanExecutionRecorder recorder) {
        this.recorder = recorder;
        this.agents = agents;
        data.put("agents", agents);
    }

    public abstract String execute(String inputText);

    public abstract List<ToolCallback> getToolCallList();

    protected PlanExecutionRecorder getRecorder() {
        return recorder;
    }
}
