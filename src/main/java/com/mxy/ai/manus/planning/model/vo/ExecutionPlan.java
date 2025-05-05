package com.mxy.ai.manus.planning.model.vo;

import com.mxy.ai.manus.agent.AgentState;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 计划实体类，用于管理执行计划的相关信息
 */
@Data
public class ExecutionPlan {

    private String planId;

    private String title;

    private String planningThinking;

    // 使用简单字符串存储执行参数
    private String executionParams;

    private List<ExecutionStep> steps;

    public ExecutionPlan(String planId, String title) {
        this.planId = planId;
        this.title = title;
        this.steps = new ArrayList<>();
        this.executionParams = "";
    }

    public void addStep(ExecutionStep step) {
        this.steps.add(step);
    }

    public String getPlanExecutionStateStringFormat(boolean onlyCompletedAndFirstInProgress) {
        StringBuilder state = new StringBuilder();
        state.append("全局目标 : ").append("\n").append(title).append(")\n");

        state.append("\n- 执行参数: ").append("\n");
        if (executionParams != null && !executionParams.isEmpty()) {
            state.append(executionParams).append("\n\n");
        }
        else {
            state.append("未提供执行参数。\n\n");
        }

        state.append("- 全局步骤计划:\n");
        state.append(getStepsExecutionStateStringFormat(onlyCompletedAndFirstInProgress));

        return state.toString();
    }

    /**
     * 获取步骤执行状态的字符串格式
     * @param onlyCompletedAndFirstInProgress 当为true时，只输出所有已完成的步骤和第一个进行中的步骤
     * @return 格式化的步骤执行状态字符串
     */
    public String getStepsExecutionStateStringFormat(boolean onlyCompletedAndFirstInProgress) {
        StringBuilder state = new StringBuilder();
        boolean foundInProgress = false;

        for (int i = 0; i < steps.size(); i++) {
            ExecutionStep step = steps.get(i);

            // 如果onlyCompletedAndFirstInProgress为true，则只显示COMPLETED状态的步骤和第一个IN_PROGRESS状态的步骤
            if (onlyCompletedAndFirstInProgress) {
                // 如果是COMPLETED状态，始终显示
                if (step.getStatus() == AgentState.COMPLETED) {
                    // 什么都不做，继续显示
                }
                // 如果是IN_PROGRESS状态，且还没找到其他IN_PROGRESS的步骤
                else if (step.getStatus() == AgentState.IN_PROGRESS && !foundInProgress) {
                    foundInProgress = true; // 标记已找到IN_PROGRESS步骤
                }
                // 其他所有情况（不是COMPLETED且不是第一个IN_PROGRESS）
                else {
                    continue; // 跳过不符合条件的步骤
                }
            }

            String symbol = switch (step.getStatus()) {
                case COMPLETED -> "[completed]";
                case IN_PROGRESS -> "[in_progress]";
                case BLOCKED -> "[blocked]";
                case NOT_STARTED -> "[not_started]";
                default -> "[ ]";
            };
            state.append("步骤 ")
                    .append(i)
                    .append(": ")
                    .append(symbol)
                    .append(" ")
                    .append(step.getStepRequirement())
                    .append("\n")
                    .append("\n");
            String result = step.getResult();
            if (result != null && !result.isEmpty()) {
                state.append("该步骤的执行结果: ").append("\n").append(result).append("\n");
            }

        }
        return state.toString();
    }

    /**
     * 获取所有步骤执行状态的字符串格式（兼容旧版本）
     * @return 格式化的步骤执行状态字符串
     */
    public String getStepsExecutionStateStringFormat() {
        return getStepsExecutionStateStringFormat(false);
    }

    /**
     * 将计划转换为JSON字符串
     * @return 计划的JSON字符串表示
     */
    public String toJson() {
        StringBuilder json = new StringBuilder();
        json.append("{\n");
        json.append("  \"planId\": \"").append(planId).append("\",\n");
        json.append("  \"title\": \"").append(title).append("\",\n");

        // 添加步骤数组
        json.append("  \"steps\": [\n");
        for (int i = 0; i < steps.size(); i++) {
            json.append(steps.get(i).toJson());
            if (i < steps.size() - 1) {
                json.append(",");
            }
            json.append("\n");
        }
        json.append("  ]\n");

        json.append("}");
        return json.toString();
    }

    /**
     * 从JSON字符串解析并创建ExecutionPlan对象
     * @param planJson JSON字符串
     * @param newPlanId 新的计划ID（可选，如果提供将覆盖JSON中的planId）
     * @return 解析后的ExecutionPlan对象
     * @throws Exception 如果解析失败则抛出异常
     */
    public static ExecutionPlan fromJson(String planJson, String newPlanId) throws Exception {
        com.fasterxml.jackson.databind.ObjectMapper objectMapper = new com.fasterxml.jackson.databind.ObjectMapper();
        com.fasterxml.jackson.databind.JsonNode rootNode = objectMapper.readTree(planJson);

        // 获取计划标题
        String title = rootNode.has("title") ? rootNode.get("title").asText() : "来自模板的计划";

        // 使用新的计划ID或从JSON中获取
        String planId = (newPlanId != null && !newPlanId.isEmpty()) ? newPlanId
                : (rootNode.has("planId") ? rootNode.get("planId").asText() : "unknown-plan");

        // 创建新的ExecutionPlan对象
        ExecutionPlan plan = new ExecutionPlan(planId, title);

        // 如果有计划步骤，添加到计划中
        if (rootNode.has("steps") && rootNode.get("steps").isArray()) {
            com.fasterxml.jackson.databind.JsonNode stepsNode = rootNode.get("steps");
            int stepIndex = 0;
            for (com.fasterxml.jackson.databind.JsonNode stepNode : stepsNode) {
                if (stepNode.has("stepRequirement")) {
                    // 调用ExecutionStep的fromJson方法创建步骤
                    ExecutionStep step = ExecutionStep.fromJson(stepNode);
                    Integer stepIndexValFromJson = step.getStepIndex();
                    if (stepIndexValFromJson != null) {
                        stepIndex = stepIndexValFromJson;
                    }
                    else {
                        step.setStepIndex(stepIndex);
                    }
                    plan.addStep(step);
                    stepIndex++;
                }
            }
        }

        return plan;
    }
}
