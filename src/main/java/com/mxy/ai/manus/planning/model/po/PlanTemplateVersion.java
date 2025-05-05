package com.mxy.ai.manus.planning.model.po;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Author：mxy
 * @Date：2025-05-04-17:23
 * @Version：1.0
 * @Description：
 */
@Entity
@Table(name = "plan_template_version")
@Data
public class PlanTemplateVersion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "plan_template_id", nullable = false, length = 50)
    private String planTemplateId;

    @Column(name = "version_index", nullable = false)
    private Integer versionIndex;

    @Column(name = "plan_json", columnDefinition = "TEXT", nullable = false)
    private String planJson;

    @Column(name = "create_time", nullable = false)
    private LocalDateTime createTime;

    // 构造函数
    public PlanTemplateVersion() {
    }

    public PlanTemplateVersion(String planTemplateId, Integer versionIndex, String planJson) {
        this.planTemplateId = planTemplateId;
        this.versionIndex = versionIndex;
        this.planJson = planJson;
        this.createTime = LocalDateTime.now();
    }
}
