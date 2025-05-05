package com.mxy.ai.manus.dynamic.agent.controller;

import com.mxy.ai.manus.dynamic.agent.model.Tool;
import com.mxy.ai.manus.dynamic.agent.service.AgentConfig;
import com.mxy.ai.manus.dynamic.agent.service.AgentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author：mxy
 * @Date：2025-05-04-15:53
 * @Version：1.0
 * @Description：
 */
@RestController
@RequestMapping("/api/agents")
@CrossOrigin(origins = "*") // 添加跨域支持
public class AgentController {
    @Autowired
    private AgentService agentService;

    @GetMapping
    public ResponseEntity<List<AgentConfig>> getAllAgents() {
        return ResponseEntity.ok(agentService.getAllAgents());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AgentConfig> getAgentById(@PathVariable String id) {
        return ResponseEntity.ok(agentService.getAgentById(id));
    }

    @PostMapping
    public ResponseEntity<AgentConfig> createAgent(@RequestBody AgentConfig agentConfig) {
        return ResponseEntity.ok(agentService.createAgent(agentConfig));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AgentConfig> updateAgent(@PathVariable String id, @RequestBody AgentConfig agentConfig) {
        agentConfig.setId(id);
        return ResponseEntity.ok(agentService.updateAgent(agentConfig));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAgent(@PathVariable String id) {
        try {
            agentService.deleteAgent(id);
            return ResponseEntity.ok().build();
        }
        catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/tools")
    public ResponseEntity<List<Tool>> getAvailableTools() {
        return ResponseEntity.ok(agentService.getAvailableTools());
    }
}
