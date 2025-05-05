package com.mxy.ai.manus.dynamic.mcp.repository;

import com.mxy.ai.manus.dynamic.mcp.model.po.McpConfigEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @Author：mxy
 * @Date：2025-05-04-16:23
 * @Version：1.0
 * @Description：
 */
@Repository
public interface McpConfigRepository extends JpaRepository<McpConfigEntity, Long> {
    McpConfigEntity findByMcpServerName(String mcpServerName);
}
