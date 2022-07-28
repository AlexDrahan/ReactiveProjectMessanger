package com.example.reactiveproject.controller

import com.example.reactiveproject.repository.UserRepository
import com.example.reactiveproject.service.impl.UserServiceImpl
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.context.annotation.Import
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@WebFluxTest(controllers = [UserController::class])
internal class UserControllerTest{

    @MockBean
    private lateinit var userRepository: UserRepository
    @InjectMocks
    private lateinit var userService: UserServiceImpl


    @Test
    fun `should find user by id`(){

    }


}