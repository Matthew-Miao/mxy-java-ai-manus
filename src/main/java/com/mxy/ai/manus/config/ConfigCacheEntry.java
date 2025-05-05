package com.mxy.ai.manus.config;

/**
 * @Author：mxy
 * @Date：2025-05-04-10:39
 * @Version：1.0
 * @Description：
 */
public class ConfigCacheEntry <T>{
    private T value;

    private long lastUpdateTime;

    private static final long EXPIRATION_TIME = 30000; // 30秒过期

    public ConfigCacheEntry(T value) {
        this.value = value;
        this.lastUpdateTime = System.currentTimeMillis();
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
        this.lastUpdateTime = System.currentTimeMillis();
    }

    public boolean isExpired() {
        return System.currentTimeMillis() - lastUpdateTime > EXPIRATION_TIME;
    }
}
