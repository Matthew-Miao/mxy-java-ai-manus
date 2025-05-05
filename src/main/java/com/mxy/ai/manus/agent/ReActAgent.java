package com.mxy.ai.manus.agent;

import com.mxy.ai.manus.config.ManusProperties;
import com.mxy.ai.manus.llm.LlmService;
import com.mxy.ai.manus.recorder.PlanExecutionRecorder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author：mxy
 * @Date：2025-05-02-22:31
 * @Version：1.0
 * @Description：
 */
public abstract class ReActAgent extends BaseAgent{
    private static final Logger logger = LoggerFactory.getLogger(ReActAgent.class);


    /**
     * 构造函数
     * @param llmService LLM服务实例，用于处理自然语言交互
     * @param planExecutionRecorder 计划执行记录器，用于记录执行过程
     * @param manusProperties Manus配置属性
     */
    public ReActAgent(LlmService llmService, PlanExecutionRecorder planExecutionRecorder,
                      ManusProperties manusProperties) {
        super(llmService, planExecutionRecorder, manusProperties);
    }

    /**
     * 执行思考过程，判断是否需要采取行动
     *
     * 子类实现要求： 1. 分析当前状态和上下文 2. 进行逻辑推理，得出下一步行动的决策 3. 返回是否需要执行行动
     *
     * 示例实现： - 如果需要调用工具，返回true - 如果当前步骤已完成，返回false
     * @return true表示需要执行行动，false表示当前不需要行动
     */
    protected abstract boolean think();

    /**
     * 执行具体的行动
     *
     * 子类实现要求： 1. 基于think()的决策执行具体操作 2. 可以是工具调用、状态更新等具体行为 3. 返回执行结果的描述信息
     *
     * 示例实现： - ToolCallAgent：执行选定的工具调用 - BrowserAgent：执行浏览器操作
     * @return 行动执行的结果描述
     */
    protected abstract AgentExecResult act();

    /**
     * 执行一个完整的思考-行动步骤
     * @return 如果不需要行动则返回思考完成的消息，否则返回行动的执行结果
     */
    @Override
    public AgentExecResult step() {

        boolean shouldAct = think();
        if (!shouldAct) {
            AgentExecResult result = new AgentExecResult("Thinking complete - no action needed",
                    AgentState.IN_PROGRESS);

            return result;
        }
        return act();
    }
}
