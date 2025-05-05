package com.mxy.ai.manus.agent;

/**
 * @Author：mxy
 * @Date：2025-05-02-22:25
 * @Version：1.0
 * @Description：
 */
public enum AgentState {
    NOT_STARTED("not_started"),
    IN_PROGRESS("in_progress"),
    COMPLETED("completed"),
    BLOCKED("blocked"),
    FAILED("failed");

    private final String state;

    AgentState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return state;
    }
}
