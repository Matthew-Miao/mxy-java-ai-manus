package com.mxy.ai.manus.dynamic.agent.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

/**
 * @Author：mxy
 * @Date：2025-05-04-15:54
 * @Version：1.0
 * @Description：
 */
@Entity
@Table(name = "dynamic_agents")
@Data
public class DynamicAgentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String agentName;

    @Column(nullable = false, length = 1000)
    private String agentDescription;

    @Column(nullable = false, length = 40000)
    private String systemPrompt;

    @Column(nullable = false, length = 40000)
    private String nextStepPrompt;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "dynamic_agent_tools", joinColumns = @JoinColumn(name = "agent_id"))
    @Column(name = "tool_key")
    private List<String> availableToolKeys;

    @Column(nullable = false)
    private String className;
}
