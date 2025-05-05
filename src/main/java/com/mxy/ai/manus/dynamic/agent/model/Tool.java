package com.mxy.ai.manus.dynamic.agent.model;

import lombok.Data;

/**
 * @Author：mxy
 * @Date：2025-05-04-15:55
 * @Version：1.0
 * @Description：
 */
@Data
public class Tool {
    private String key;

    private String name;

    private String description;

    private boolean enabled;

    private String serviceGroup;
}
