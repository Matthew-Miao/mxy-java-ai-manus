package com.mxy.ai.manus.config.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Author：mxy
 * @Date：2025-05-02-22:36
 * @Version：1.0
 * @Description：
 */
@Entity
@Table(name = "system_config")
@Data
public class ConfigEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 配置组
     */
    @Column(nullable = false)
    private String configGroup;

    /**
     * 配置子组
     */
    @Column(nullable = false)
    private String configSubGroup;

    /**
     * 配置键
     */
    @Column(nullable = false)
    private String configKey;

    /**
     * 配置项完整路径
     */
    @Column(nullable = false, unique = true)
    private String configPath;

    /**
     * 配置值
     */
    @Column(columnDefinition = "TEXT")
    private String configValue;

    /**
     * 默认值
     */
    @Column(columnDefinition = "TEXT")
    private String defaultValue;

    /**
     * 配置描述
     */
    @Column(columnDefinition = "TEXT")
    private String description;

    /**
     * 输入类型
     */
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ConfigInputType inputType;

    /**
     * 选项JSON字符串 用于存储SELECT类型的选项数据
     */
    @Column(columnDefinition = "TEXT")
    private String optionsJson;

    /**
     * 最后更新时间
     */
    @Column(nullable = false)
    private LocalDateTime updateTime;

    /**
     * 创建时间
     */
    @Column(nullable = false)
    private LocalDateTime createTime;

    @PrePersist
    protected void onCreate() {
        createTime = LocalDateTime.now();
        updateTime = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updateTime = LocalDateTime.now();
    }
}
