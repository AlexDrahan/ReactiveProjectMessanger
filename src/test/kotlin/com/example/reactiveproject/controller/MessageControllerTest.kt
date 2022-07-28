package com.example.reactiveproject.controller


import com.example.reactiveproject.model.Message
import com.example.reactiveproject.repository.MessageRepository
import com.example.reactiveproject.service.MessageService
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
import java.time.LocalDateTime

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class MessageControllerTest{

    @Autowired
    private lateinit var messageService: MessageService
    @Autowired
    private lateinit var messageRepository: MessageRepository
    @Autowired
    private lateinit var restTemplate: TestRestTemplate


    @LocalServerPort
    protected var port: Int = 0


    private fun randomText(): String = List(80) {
        (('a'..'z')).random()
    }.joinToString("")

    fun prepareData(): Message {
        val message = Message(
            datetime = LocalDateTime.now().toString(),
            text = randomText(),
            messageChatId = ObjectId.get().toString(),
            messageUserId = ObjectId.get().toString()
        )
        return message
    }

    @Test
    fun `should update message`(){

//        val messageToUpdate = messageRepository.save(prepareData())
//
//
//        val updatedMessage = messageToUpdate.copy(
//            text = randomText(),
//            datetime = LocalDateTime.now().toString()
//        )
//        val updateResponse = restTemplate.exchange(
//            "http://localhost:$port/message/messages-edit",
//            HttpMethod.PUT,
//            HttpEntity(updatedMessage, HttpHeaders()),
//            Message::class.java
//        )
//        val messageAfterUpdate = messageService.findMessage(updatedMessage.text)
//
//        assertEquals(listOf(updatedMessage), messageAfterUpdate)

    }



}