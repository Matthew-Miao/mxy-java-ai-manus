package com.mxy.ai.manus.dynamic.agent.respository;

import com.mxy.ai.manus.dynamic.agent.entity.DynamicAgentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @Author：mxy
 * @Date：2025-05-04-15:55
 * @Version：1.0
 * @Description：
 */
@Repository
public interface DynamicAgentRepository extends JpaRepository<DynamicAgentEntity, Long> {

    DynamicAgentEntity findByAgentName(String agentName);
}
