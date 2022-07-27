package com.example.reactiveproject.controller

import com.example.reactiveproject.model.Chat
import com.example.reactiveproject.service.ChatService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux

@RestController
@RequestMapping("/chat")
class ChatController (
    private val chatService: ChatService
){

    @PostMapping("/chats-create")
    @ResponseStatus(HttpStatus.CREATED)
    fun createChat(@RequestBody chat: Chat) {
        chatService.createChat(chat)
    }

    @PutMapping("/chats-add/{chatId}/{id}")
    fun addUserToTheChat(@PathVariable("chatId") chatId: String, @PathVariable("id") userId: String) {
        chatService.addUserToTheChat(chatId, userId)
    }

    @DeleteMapping("/chats/{name}")
    fun delete(@PathVariable("name") name: String) {
        chatService.deleteChat(name)
    }

    @GetMapping("/chats-all")
    @ResponseStatus(HttpStatus.FOUND)
    fun findAllChats(): Flux<Chat> {
        return chatService.findAllChats()
    }

    @DeleteMapping("/chats-remove/{chatId}/{id}")
    fun deleteUserFromChat(@PathVariable("chatId") chatId: String, @PathVariable("id") userId: String){
        chatService.deleteUserFromChat(chatId, userId)
    }

//    @GetMapping("/get-chat-id/{id}")
//    fun getChatById(@PathVariable(value = "id") chatId: String){
//        chatService.getChatById(chatId)
//    }

}
