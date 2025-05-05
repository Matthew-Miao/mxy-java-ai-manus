/*
 * Copyright 2025 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.mxy.ai.manus.dynamic.mcp.model.vo;


import com.mxy.ai.manus.dynamic.mcp.model.po.McpConfigEntity;
import com.mxy.ai.manus.dynamic.mcp.model.po.McpConfigType;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * VO对象，用于McpConfig的前端展示
 */
@Data
public class McpConfigVO {

	private Long id;

	private String mcpServerName;

	private McpConfigType connectionType;

	private String connectionConfig;

	private List<String> toolNames; // 添加工具名称列表，用于前端展示

	public McpConfigVO() {
	}

	public McpConfigVO(McpConfigEntity entity) {
		this.id = entity.getId();
		this.mcpServerName = entity.getMcpServerName();
		this.connectionType = entity.getConnectionType();
		this.connectionConfig = entity.getConnectionConfig();
		this.toolNames = new ArrayList<>(); // 初始化为空列表，实际使用时可能需要从其他地方获取
	}

	// 将VO列表转换为实体列表的静态方法
	public static List<McpConfigVO> fromEntities(List<McpConfigEntity> entities) {
		List<McpConfigVO> vos = new ArrayList<>();
		if (entities != null) {
			for (McpConfigEntity entity : entities) {
				vos.add(new McpConfigVO(entity));
			}
		}
		return vos;
	}
}
