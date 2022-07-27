package com.example.reactiveproject.controller


import com.example.reactiveproject.model.User
import com.example.reactiveproject.repository.UserRepository
import com.example.reactiveproject.service.UserService
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
internal class UserControllerTest{

    @Autowired
    private lateinit var userService: UserService
    @Autowired
    private lateinit var userRepository: UserRepository
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


    fun prepareData(): User {
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
    fun `should update user`(){
       // val userToUpdate = userService.findByUserId("62d7e83144b291390f7d5439")
        //Given
//        val user1 = userRepository.save(User(
//            name = "Maxym",
//            phoneNumber = "+380993847294",
//            bio = "New bio",
//            chat = emptyList(),
//            message = emptyList()
//        ))

        val userToSave = userRepository.save(prepareData())



        val newUser = userToSave.copy(name = randomName())
        val updateResponse = restTemplate.exchange(
            "http://localhost:$port/user/users-update",
            HttpMethod.PUT,
            HttpEntity(newUser, HttpHeaders()),
            User::class.java
        )
        val userAfterUpdate = userService.findByUserId(userToSave.id!!)

        assertEquals(newUser, userAfterUpdate)

    }

    @Test
    fun `should return user by id`() {
        //Given
        val userToSave = userRepository.save(prepareData())

        val user = userService.findByUserId(userToSave.id!!)
        val userId = user!!.id

        val response = restTemplate.getForEntity(
            "http://localhost:$port/user/users-find/${userId}",
            User::class.java
        )

        assertEquals(200, response.statusCode.value())
        assertNotNull(response.body)
        assertEquals(userId, response.body?.id)
    }

    @Test
    fun `should return user by user name`() {
        val userToSave = userRepository.save(prepareData())

        val user = userService.findUserByUserName(userToSave.name)
        val userName = user?.name

        val response = restTemplate.getForEntity(
            "http://localhost:$port/user/users/by-name/$userName",
            User::class.java
        )

        assertEquals(200, response.statusCode.value())
        assertNotNull(response.body)
        assertEquals(userName, response.body?.name)
    }

    @Test
    fun `should return user by phone number`() {
        val userToSave = userRepository.save(prepareData())

        val user = userService.findUserByPhoneNumber(userToSave.phoneNumber)

        val response = restTemplate.getForEntity(
            "http://localhost:$port/user/users/by-phone/${user!!.phoneNumber}",
            User::class.java
        )

        assertEquals(200, response.statusCode.value())
        assertNotNull(response.body)
        assertEquals(user!!.phoneNumber, response.body?.phoneNumber)
    }
}