package com.mxy.ai.manus.planning.model.po;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Author：mxy
 * @Date：2025-05-04-17:22
 * @Version：1.0
 * @Description：
 */
@Entity
@Table(name = "plan_template")
@Data
public class PlanTemplate {
    @Id
    @Column(name = "plan_template_id", length = 50)
    private String planTemplateId;

    @Column(name = "title", length = 255)
    private String title;

    @Column(name = "user_request", length = 4000)
    private String userRequest;

    @Column(name = "create_time", nullable = false)
    private LocalDateTime createTime;

    @Column(name = "update_time", nullable = false)
    private LocalDateTime updateTime;

    // 构造函数
    public PlanTemplate() {
    }

    public PlanTemplate(String planTemplateId, String title, String userRequest) {
        this.planTemplateId = planTemplateId;
        this.title = title;
        this.userRequest = userRequest;
        this.createTime = LocalDateTime.now();
        this.updateTime = LocalDateTime.now();
    }
}
