package com.example.reactiveproject.service

import com.example.reactiveproject.model.Chat
import com.example.reactiveproject.model.FullChat
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono


interface ChatService {

    fun createChat(chat: Chat)
    fun deleteChat(name: String)

    fun addUserToTheChat(chatId: String, userId: String)

    fun deleteUserFromChat(chatId: String, userId: String)

    fun findAllChats(): Flux<Chat>

//    fun getChatById(chatId: String): FullChat

}