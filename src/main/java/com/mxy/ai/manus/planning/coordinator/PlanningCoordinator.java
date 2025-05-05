package com.mxy.ai.manus.planning.coordinator;

import com.mxy.ai.manus.planning.creator.PlanCreator;
import com.mxy.ai.manus.planning.executor.PlanExecutor;
import com.mxy.ai.manus.planning.finalizer.PlanFinalizer;
import com.mxy.ai.manus.planning.model.vo.ExecutionContext;

/**
 * @Author：mxy
 * @Date：2025-05-04-16:50
 * @Version：1.0
 * @Description：
 */
public class PlanningCoordinator {
    private final PlanCreator planCreator;

    private final PlanExecutor planExecutor;

    private final PlanFinalizer planFinalizer;

    public PlanningCoordinator(PlanCreator planCreator, PlanExecutor planExecutor, PlanFinalizer planFinalizer) {
        this.planCreator = planCreator;
        this.planExecutor = planExecutor;
        this.planFinalizer = planFinalizer;
    }

    /**
     * 仅创建计划，不执行
     * @param context 执行上下文
     * @return 执行上下文
     */
    public ExecutionContext createPlan(ExecutionContext context) {
        // 只执行创建计划步骤
        planCreator.createPlan(context);
        return context;
    }

    /**
     * 执行完整的计划流程
     * @param context 执行上下文
     * @return 执行总结
     */
    public ExecutionContext executePlan(ExecutionContext context) {
        // 1. 创建计划
        planCreator.createPlan(context);

        // 2. 执行计划
        planExecutor.executeAllSteps(context);

        // 3. 生成总结
        planFinalizer.generateSummary(context);

        return context;
    }

    /**
     * 执行已有计划（跳过创建计划步骤）
     * @param context 包含现有计划的执行上下文
     * @return 执行总结
     */
    public ExecutionContext executeExistingPlan(ExecutionContext context) {
        // 1. 执行计划
        planExecutor.executeAllSteps(context);

        // 2. 生成总结
        planFinalizer.generateSummary(context);

        return context;
    }
}
