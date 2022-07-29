package com.example.reactiveproject.controller


import com.example.reactiveproject.model.Message
import com.example.reactiveproject.model.User
import com.example.reactiveproject.repository.MessageRepository
import com.example.reactiveproject.service.MessageService
import com.example.reactiveproject.service.impl.MessageServiceImpl
import com.example.reactiveproject.service.impl.UserServiceImpl
import org.bson.types.ObjectId
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.context.annotation.Import
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.reactive.server.WebTestClient
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.time.LocalDateTime

@ExtendWith(SpringExtension::class)
@WebFluxTest(controllers = [MessageController::class])
@Import(MessageServiceImpl::class)
internal class MessageControllerTest{

    @MockBean
    private lateinit var messageRepository: MessageRepository
    @Autowired
    private lateinit var webClient: WebTestClient



    private fun randomText(): String = List(80) {
        (('a'..'z')).random()
    }.joinToString("")

    fun prepareData(): Message {
        val message = Message(
            id = ObjectId.get().toString(),
            datetime = LocalDateTime.now().toString(),
            text = randomText(),
            messageChatId = ObjectId.get().toString(),
            messageUserId = ObjectId.get().toString()
        )
        return message
    }

    @Test
    fun `should delete message`(){
        val message = prepareData()
        val empty: Mono<Void> = Mono.empty()

        Mockito.`when`(messageRepository.deleteById(message.id!!)).thenReturn(empty)

        webClient.delete().uri("http://localhost:8081/message/messages-delete/{id}", message.id)
            .header(HttpHeaders.ACCEPT, "application/json")
            .exchange()
            .expectStatus().isOk

        Mockito.verify(messageRepository, Mockito.times(1)).deleteById(message.id!!)
    }
    @Test
    fun `should find message by text`(){

        val message = prepareData()

        Mockito.`when`(messageRepository.findMessageByText(message.text)).thenReturn(Flux.just(message))

        webClient.get().uri("http://localhost:8081/message/messages/by-text/{text}", message.text)
            .header(HttpHeaders.ACCEPT, "application/json")
            .exchange()
            .expectStatus().isOk
            .expectBodyList(Message::class.java)

        Mockito.verify(messageRepository, Mockito.times(1)).findMessageByText(message.text)

    }



}