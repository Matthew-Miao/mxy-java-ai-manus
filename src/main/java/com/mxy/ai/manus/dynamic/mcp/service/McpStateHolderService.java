package com.mxy.ai.manus.dynamic.mcp.service;

import com.mxy.ai.manus.dynamic.mcp.model.vo.McpState;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author：mxy
 * @Date：2025-05-04-16:40
 * @Version：1.0
 * @Description：
 */
@Service
public class McpStateHolderService {
    private Map<String, McpState> mcpStateMap = new ConcurrentHashMap<>();

    public McpState getMcpState(String key) {
        return mcpStateMap.get(key);
    }

    public void setMcpState(String key, McpState state) {
        mcpStateMap.put(key, state);
    }

    public void removeMcpState(String key) {
        mcpStateMap.remove(key);
    }
}
