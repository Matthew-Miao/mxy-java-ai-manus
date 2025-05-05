package com.mxy.ai.manus.dynamic.agent.service;

import com.mxy.ai.manus.agent.BaseAgent;
import com.mxy.ai.manus.config.ManusProperties;
import com.mxy.ai.manus.dynamic.agent.DynamicAgent;
import com.mxy.ai.manus.dynamic.agent.ToolCallbackProvider;
import com.mxy.ai.manus.dynamic.agent.entity.DynamicAgentEntity;
import com.mxy.ai.manus.dynamic.agent.model.Tool;
import com.mxy.ai.manus.dynamic.agent.respository.DynamicAgentRepository;
import com.mxy.ai.manus.llm.LlmService;
import com.mxy.ai.manus.planning.PlanningFactory;
import com.mxy.ai.manus.recorder.PlanExecutionRecorder;
import com.mxy.ai.manus.planning.PlanningFactory.ToolCallBackContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.model.tool.ToolCallingManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author：mxy
 * @Date：2025-05-04-15:59
 * @Version：1.0
 * @Description：
 */
@Service
public class AgentServiceImpl implements AgentService {
    private static final String DEFAULT_AGENT_NAME = "DEFAULT_AGENT";

    private static final Logger log = LoggerFactory.getLogger(AgentServiceImpl.class);

    private final DynamicAgentLoader dynamicAgentLoader;

    private final DynamicAgentRepository repository;

    private final PlanningFactory planningFactory;

    @Autowired
    @Lazy
    private LlmService llmService;

    @Autowired
    private PlanExecutionRecorder planExecutionRecorder;

    @Autowired
    private ManusProperties manusProperties;

    @Autowired
    @Lazy
    private ToolCallingManager toolCallingManager;

    @Autowired
    public AgentServiceImpl(@Lazy DynamicAgentLoader dynamicAgentLoader, DynamicAgentRepository repository,
                            @Lazy PlanningFactory planningFactory) {
        this.dynamicAgentLoader = dynamicAgentLoader;
        this.repository = repository;
        this.planningFactory = planningFactory;
    }

    @Override
    public List<AgentConfig> getAllAgents() {
        return repository.findAll().stream().map(this::mapToAgentConfig).collect(Collectors.toList());
    }

    @Override
    public AgentConfig getAgentById(String id) {
        DynamicAgentEntity entity = repository.findById(Long.parseLong(id))
                .orElseThrow(() -> new IllegalArgumentException("Agent not found: " + id));
        return mapToAgentConfig(entity);
    }

    @Override
    public AgentConfig createAgent(AgentConfig config) {
        try {
            // 检查是否已存在同名Agent
            DynamicAgentEntity existingAgent = repository.findByAgentName(config.getName());
            if (existingAgent != null) {
                log.info("发现同名Agent: {}，返回已存在的Agent", config.getName());
                return mapToAgentConfig(existingAgent);
            }

            DynamicAgentEntity entity = new DynamicAgentEntity();
            updateEntityFromConfig(entity, config);
            entity = repository.save(entity);
            log.info("成功创建新Agent: {}", config.getName());
            return mapToAgentConfig(entity);
        }
        catch (Exception e) {
            log.warn("创建Agent过程中发生异常: {}，错误信息: {}", config.getName(), e.getMessage());
            // 如果是唯一性约束违反异常，尝试返回已存在的Agent
            if (e.getMessage() != null && e.getMessage().contains("Unique")) {
                DynamicAgentEntity existingAgent = repository.findByAgentName(config.getName());
                if (existingAgent != null) {
                    log.info("返回已存在的Agent: {}", config.getName());
                    return mapToAgentConfig(existingAgent);
                }
            }
            throw e;
        }
    }

    @Override
    public AgentConfig updateAgent(AgentConfig config) {
        DynamicAgentEntity entity = repository.findById(Long.parseLong(config.getId()))
                .orElseThrow(() -> new IllegalArgumentException("Agent not found: " + config.getId()));
        updateEntityFromConfig(entity, config);
        entity = repository.save(entity);
        return mapToAgentConfig(entity);
    }

    @Override
    public void deleteAgent(String id) {
        DynamicAgentEntity entity = repository.findById(Long.parseLong(id))
                .orElseThrow(() -> new IllegalArgumentException("Agent not found: " + id));

        if (DEFAULT_AGENT_NAME.equals(entity.getAgentName())) {
            throw new IllegalArgumentException("不能删除默认 Agent");
        }

        repository.deleteById(Long.parseLong(id));
    }

    @Override
    public List<Tool> getAvailableTools() {

        Map<String, PlanningFactory.ToolCallBackContext> toolcallContext = planningFactory.toolCallbackMap(null);
        return toolcallContext.entrySet().stream().map(entry -> {
            Tool tool = new Tool();
            tool.setKey(entry.getKey());
            tool.setName(entry.getKey()); // You might want to provide a more friendly
            // name
            tool.setDescription(entry.getValue().getFunctionInstance().getDescription());
            tool.setEnabled(true);
            tool.setServiceGroup(entry.getValue().getFunctionInstance().getServiceGroup());
            return tool;
        }).collect(Collectors.toList());
    }

    private AgentConfig mapToAgentConfig(DynamicAgentEntity entity) {
        AgentConfig config = new AgentConfig();
        config.setId(entity.getId().toString());
        config.setName(entity.getAgentName());
        config.setDescription(entity.getAgentDescription());
        config.setSystemPrompt(entity.getSystemPrompt());
        config.setNextStepPrompt(entity.getNextStepPrompt());
        config.setAvailableTools(entity.getAvailableToolKeys());
        config.setClassName(entity.getClassName());
        return config;
    }

    private void updateEntityFromConfig(DynamicAgentEntity entity, AgentConfig config) {
        entity.setAgentName(config.getName());
        entity.setAgentDescription(config.getDescription());
        entity.setSystemPrompt(config.getSystemPrompt());
        entity.setNextStepPrompt(config.getNextStepPrompt());
        List<String> availableTools = config.getAvailableTools();

        // 确保工具列表中至少包含 TerminateTool
        if (availableTools == null
                || !availableTools.contains(com.mxy.ai.manus.tool.TerminateTool.name)) {
            if (availableTools == null) {
                availableTools = new java.util.ArrayList<>();
            }
            log.info("为Agent[{}]添加必要的工具: {}", config.getName(),
                    com.mxy.ai.manus.tool.TerminateTool.name);
            availableTools.add(com.mxy.ai.manus.tool.TerminateTool.name);
        }

        entity.setAvailableToolKeys(availableTools);
        entity.setClassName(config.getName());
    }

    @Override
    public BaseAgent createDynamicBaseAgent(String name, String planId) {

        log.info("创建新的BaseAgent: {}, planId: {}", name, planId);

        try {
            // 通过dynamicAgentLoader加载已存在的Agent
            DynamicAgent agent = dynamicAgentLoader.loadAgent(name);

            // 设置planId
            agent.setPlanId(planId);
            // 设置工具回调映射
            Map<String, ToolCallBackContext> toolCallbackMap = planningFactory.toolCallbackMap(planId);
            agent.setToolCallbackProvider(new ToolCallbackProvider() {

                @Override
                public Map<String, ToolCallBackContext> getToolCallBackContext() {
                    return toolCallbackMap;
                }

            });

            log.info("成功加载BaseAgent: {}, 可用工具数量: {}", name, agent.getToolCallList().size());

            return agent;
        }
        catch (Exception e) {
            log.error("加载BaseAgent过程中发生异常: {}, 错误信息: {}", name, e.getMessage(), e);
            throw new RuntimeException("加载BaseAgent失败: " + e.getMessage(), e);
        }
    }
}
