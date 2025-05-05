package com.mxy.ai.manus.flow;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.ai.chat.messages.Message;

import java.util.List;

/**
 * @Author：mxy
 * @Date：2025-05-04-11:26
 * @Version：1.0
 * @Description：
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ToolCallbackContext {
    private String planId;

    private String toolName;

    private List<Message> messages;

    private String lastResult;

    public ToolCallbackContext(String planId, String toolName) {
        this.planId = planId;
        this.toolName = toolName;
    }
}
