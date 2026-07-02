package com.saumya.projects.lovable_clone.entity;

import com.saumya.projects.lovable_clone.enums.MessageRole;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.Instant;

@Getter
@Setter
@FieldDefaults(level =  AccessLevel.PRIVATE)
public class ChatMessage {
    Long id;
    Long chatSessionId; // FK: chat_session_id
    String content;

    String toolCalls; // JSON array of tools called
    Integer tokensUsed;

    Instant createdAt;
    Instant deletedAt;

    MessageRole role;
}
