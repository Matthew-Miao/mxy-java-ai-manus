package com.mxy.ai.manus.dynamic.agent.service;

import lombok.Data;

import java.util.List;

/**
 * @Author：mxy
 * @Date：2025-05-04-15:58
 * @Version：1.0
 * @Description：
 */
@Data
public class AgentConfig {
    private String id;

    private String name;

    private String description;

    private String systemPrompt;

    private String nextStepPrompt;

    private List<String> availableTools;

    private String className;
}
