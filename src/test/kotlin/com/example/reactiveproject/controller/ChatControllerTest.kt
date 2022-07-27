package com.example.reactiveproject.controller


import com.example.reactiveproject.model.Chat
import com.example.reactiveproject.model.FullChat
import com.example.reactiveproject.model.User
import com.example.reactiveproject.repository.ChatRepository
import com.example.reactiveproject.repository.MessageRepository
import com.example.reactiveproject.repository.UserRepository
import com.example.reactiveproject.service.ChatService
import org.bson.types.ObjectId
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit.jupiter.SpringExtension

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class ChatControllerTest{

    @Autowired
    private lateinit var chatService: ChatService
    @Autowired
    private lateinit var chatRepository: ChatRepository
    @Autowired
    private lateinit var userRepository: UserRepository
    @Autowired
    private lateinit var messageRepository: MessageRepository
    @Autowired
    private lateinit var restTemplate: TestRestTemplate


    @LocalServerPort
    protected var port: Int = 0



    fun randomName(): String = List(10) {
        (('a'..'z') + ('A'..'Z')).random()
    }.joinToString("")
    private fun randomPhone(): String = List(12) {
        (('0'..'9')).random()
    }.joinToString("")
    private fun randomBio(): String = List(80) {
        (('a'..'z')).random()
    }.joinToString("")

    fun prepareChatData(): Chat {
        val chat = Chat(
            name = randomName(),
            userIds = setOf(ObjectId.get().toString()),
            messageIds = listOf(ObjectId.get().toString())
        )
        return chat
    }
    
    fun prepareUserData(): User {
        val user = User(
            name = randomName(),
            phoneNumber = "+" + randomPhone(),
            bio = randomBio(),
            chat = emptyList(),
            message = emptyList()
        )
        return user
    }

    @Test
    fun `should add user in chat`(){

        var prepareChat = chatRepository.save(prepareChatData())
        var prepareUser = userRepository.save(prepareUserData())

       // val user = userRepository.findByName("Anton")

        var newChat = chatRepository.findByName(prepareChat.name!!)
//        var newUserIds: HashSet<String>? = newChat?.userIds as HashSet<String>
//        newUserIds!!.add(user!!.id!!)
//        newChat.userIds = newUserIds

        val updateResponse = restTemplate.exchange(
            "http://localhost:$port/chat/chats-add/${prepareUser.name}",
            HttpMethod.PUT,
            HttpEntity(newChat, HttpHeaders()),
            Chat::class.java
        )

        val chatAfterAddingUser = chatRepository.findByName(newChat.name!!)
        assertEquals(newChat, chatAfterAddingUser)

    }

    @Test
    fun `should delete user from chat`(){

//        val user = userRepository.findByName("Anton")
//
//
//        var newUserIds: HashSet<String>? = newChat.userIds as HashSet<String>
//        newUserIds!!.remove(user!!.id!!)
//        newChat.userIds = newUserIds

        var newChat = chatRepository.findByName("Oleksandr-Maxym")

        val updateResponse = restTemplate.exchange(
            "http://localhost:$port/chat/chats-remove/62d68eaf0e586068f86005d3",
            HttpMethod.PUT,
            HttpEntity(newChat, HttpHeaders()),
            Chat::class.java
        )

        val chatAfterDeleteUser = chatRepository.findByName("Oleksandr-Maxym")
        assertEquals(newChat, chatAfterDeleteUser)

    }

    @Test
    fun `should get users and messages from lists of ids`(){
        val chat = chatRepository.findChatById("62dd3b0dd9a83900cfd51dcb")


        val updateResponse = restTemplate.getForEntity(
            "http://localhost:$port/chat/get-chat-id/62dd3b0dd9a83900cfd51dcb",
            FullChat::class.java
        )


//        val userList = chat!!.userIds!!.map {
//            userRepository!!.findById(it).get()
//        }.toList()
//        val messageList = chat.messageIds!!.map {
//            messageRepository!!.findById(it).get()
//        }.toList()
//
//        var fullChat = FullChat(chat, userList, messageList)



    }


}