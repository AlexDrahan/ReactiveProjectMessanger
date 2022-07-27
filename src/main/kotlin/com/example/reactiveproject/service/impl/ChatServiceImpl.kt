package com.example.reactiveproject.service.impl

import com.example.reactiveproject.model.Chat
import com.example.reactiveproject.model.FullChat
import org.springframework.beans.factory.annotation.Autowired
import com.example.reactiveproject.repository.ChatRepository
import com.example.reactiveproject.repository.FullChatRepository
import com.example.reactiveproject.repository.MessageRepository
import com.example.reactiveproject.repository.UserRepository
import com.example.reactiveproject.service.ChatService
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono


@Service
class ChatServiceImpl(
    @Autowired
    val chatRepository: ChatRepository,
    @Autowired
    val userRepository: UserRepository?,
    val fullChatRepository: FullChatRepository,
    val messageRepository: MessageRepository?
) : ChatService {

    override fun createChat(chat: Chat){
        chatRepository.save(chat)
            .subscribe()
    }

    override fun deleteChat(name: String) {
        chatRepository.findByName(name)
            .switchIfEmpty(
                Mono.error(NotFoundException())
            )
            .flatMap(
                chatRepository::delete
            )
            .subscribe()
    }

    override fun addUserToTheChat(chatId: String, userId: String) {


        userRepository!!.findById(userId)
            .switchIfEmpty(
                Mono.error(NotFoundException())
            )
            .then(
                chatRepository.findChatById(chatId)
            )
            .map {
                it!!.userIds
                Chat(
                    id = chatId,
                    name = it.name,
                    messageIds = it.messageIds,
                    userIds = it.userIds!!.plus(userId)
                )
            }
            .flatMap {
                chatRepository.save(it)
            }
            .subscribe()
    }


    override fun deleteUserFromChat(chatId: String, userId: String) {

        userRepository!!.findById(userId)
            .switchIfEmpty(
                Mono.error(NotFoundException())
            )
            .then(
                chatRepository.findChatById(chatId)
            )
            .map {
                it!!.userIds
                Chat(
                    id = chatId,
                    name = it.name,
                    messageIds = it.messageIds,
                    userIds = it.userIds!!.minus(userId)
                )
            }
            .flatMap {
                chatRepository.save(it)
            }
            .subscribe()
    }


    override fun findAllChats(): Flux<Chat> {
        return chatRepository.findAll()
    }

//    override fun getChatById(chatId: String): FullChat {
//        val chat = chatRepository.findChatById(chatId)
//        val userList = chat!!.userIds!!.map {
//            userRepository!!.findById(it).get()
//        }.toList()
//        val messageList = chat.messageIds!!.map {
//            messageRepository!!.findById(it).get()
//        }.toList()
//
//        val fullChat = FullChat(chat, userList, messageList)
//        fullChatRepository.save(fullChat)
//        return fullChat
//
//    }


}
