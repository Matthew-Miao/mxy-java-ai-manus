package com.mxy.ai.manus.planning.model.vo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author：mxy
 * @Date：2025-05-04-18:18
 * @Version：1.0
 * @Description：
 */
@Data
public class ExecutionResult {
    private String planId;

    private List<ExecutionStep> stepResults;

    private String executionDetails;

    private boolean success;

    public void addStepResult(ExecutionStep stepResult) {
        if (this.stepResults == null) {
            this.stepResults = new ArrayList<>();
        }
        this.stepResults.add(stepResult);
    }
}
