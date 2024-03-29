package com.example.reactiveproject.model

import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.FieldType
import org.springframework.data.mongodb.core.mapping.MongoId

@Document("chats")
data class Chat(
    @MongoId(value = FieldType.OBJECT_ID)
    var id: String? = null,
    var name: String? = null,
    var messageIds: List<String>? = null,
    var userIds: Set<String>? = null
) {



}
