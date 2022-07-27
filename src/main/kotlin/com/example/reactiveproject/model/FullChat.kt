package com.example.reactiveproject.model

import org.springframework.data.mongodb.core.mapping.Document


@Document("fullChats")
data class FullChat(
    val chat: Chat,
    val userList: List<User>,
    val messageList: List<Message>
)
