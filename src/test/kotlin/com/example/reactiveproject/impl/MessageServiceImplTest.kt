package com.example.reactiveproject.impl


import com.example.reactiveproject.model.Message
import com.example.reactiveproject.repository.MessageRepository
import com.example.reactiveproject.service.impl.MessageServiceImpl
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers.any
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.time.LocalDateTime
import java.util.*


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class MessageServiceImplTest{

    @Mock
    private lateinit var messageRepository: MessageRepository
    @InjectMocks
    private lateinit var messageService: MessageServiceImpl

    lateinit var message: Message


    @BeforeAll
    fun prepareTestData(){
        message = Message(
            id = "1",
            text = "Some text for text",
            datetime = "2022-07-21T16:56:27.769371",
            messageUserId = "62d68eaf0e586068f86005d3", //Anton
            messageChatId = "1"
        )
    }

    @Test
    fun `should create a message`(){
        messageService.sendMessage(message)
        Mockito.verify(messageRepository, Mockito.times(1)).save(any(Message::class.java))
    }

    @Test
    fun `should delete a message`(){
        Mockito.`when`(messageRepository.findMessageById("1")).thenReturn(message)
        messageService.deleteMessage("1")
        Mockito.verify(messageRepository, Mockito.times(1)).delete(message)
    }

    @Test
    fun `should find message by user name`(){
        Mockito.`when`(messageRepository.findMessageByText("Some text for text")).thenReturn(message)
        messageService.findMessage("Some text for text")
        Mockito.verify(messageRepository, Mockito.times(1)).findMessageByText("Some text for text")
    }

    //update message

}