package com.example.reactiveproject.impl


import com.example.reactiveproject.model.Chat
import com.example.reactiveproject.repository.ChatRepository
import com.example.reactiveproject.repository.MessageRepository
import com.example.reactiveproject.repository.UserRepository
import com.example.reactiveproject.service.impl.ChatServiceImpl
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.util.*

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class ChatServiceImplTest{

    @Mock
    private lateinit var chatRepository: ChatRepository
    @Mock
    private lateinit var  userRepository: UserRepository
    @Mock
    private lateinit var messageRepository: MessageRepository


    @InjectMocks
    private lateinit var chatService: ChatServiceImpl
    lateinit var chat: Chat

    @BeforeAll
    fun prepareTestData(){
        chat = Chat(
        id = "1",
        name = "Anton/Oleksandr",
        messageIds = listOf("1", "2", "3"),
        userIds = setOf("62d68eaf0e586068f86005d3", "62d7e83144b291390f7d5439")
        )
    }

    @Test
    fun `should create chat`(){
        Mockito.`when`(chatRepository.save(chat)).thenReturn(chat)
        chatService.createChat(chat)
        Mockito.verify(chatRepository, Mockito.times(1)).save(chat)
    }

    @Test
    fun `should delete chat`(){
        Mockito.`when`(chat.name?.let { chatRepository.findByName(it) }).thenReturn(chat)
        chatService.deleteChat(chat.name!!)
        Mockito.verify(chatRepository, Mockito.times(1)).delete(chat)
    }

}