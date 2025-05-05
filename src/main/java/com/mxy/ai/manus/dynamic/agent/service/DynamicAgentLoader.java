package com.mxy.ai.manus.dynamic.agent.service;

import com.mxy.ai.manus.config.ManusProperties;
import com.mxy.ai.manus.dynamic.agent.DynamicAgent;
import com.mxy.ai.manus.dynamic.agent.entity.DynamicAgentEntity;
import com.mxy.ai.manus.dynamic.agent.respository.DynamicAgentRepository;
import com.mxy.ai.manus.llm.LlmService;
import com.mxy.ai.manus.recorder.PlanExecutionRecorder;
import org.springframework.ai.model.tool.ToolCallingManager;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author：mxy
 * @Date：2025-05-04-16:01
 * @Version：1.0
 * @Description：
 */
@Service
public class DynamicAgentLoader {
    private final DynamicAgentRepository repository;

    private final LlmService llmService;

    private final PlanExecutionRecorder recorder;

    private final ManusProperties properties;

    private final ToolCallingManager toolCallingManager;

    public DynamicAgentLoader(DynamicAgentRepository repository, @Lazy LlmService llmService,
                              PlanExecutionRecorder recorder, ManusProperties properties, @Lazy ToolCallingManager toolCallingManager) {
        this.repository = repository;
        this.llmService = llmService;
        this.recorder = recorder;
        this.properties = properties;
        this.toolCallingManager = toolCallingManager;
    }

    public DynamicAgent loadAgent(String agentName) {
        DynamicAgentEntity entity = repository.findByAgentName(agentName);
        if (entity == null) {
            throw new IllegalArgumentException("Agent not found: " + agentName);
        }

        return new DynamicAgent(llmService, recorder, properties, entity.getAgentName(), entity.getAgentDescription(),
                entity.getSystemPrompt(), entity.getNextStepPrompt(), entity.getAvailableToolKeys(),
                toolCallingManager);
    }

    public List<DynamicAgentEntity> getAllAgents() {
        return repository.findAll();
    }
}
