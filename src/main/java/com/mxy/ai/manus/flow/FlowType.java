package com.mxy.ai.manus.flow;

/**
 * @Author：mxy
 * @Date：2025-05-04-11:26
 * @Version：1.0
 * @Description：
 */
public enum FlowType {
    PLANNING("planning");

    private final String value;

    FlowType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }
}
