package com.example.reactiveproject.impl


import com.example.reactiveproject.model.User
import com.example.reactiveproject.repository.UserRepository
import com.example.reactiveproject.service.impl.UserServiceImpl
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
import java.util.Optional

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class UserServiceImplTest(
){
    @Mock
    private lateinit var userRepository: UserRepository
    @InjectMocks
    private lateinit var userService: UserServiceImpl

    lateinit var user: User
    @BeforeAll
    fun prepareTestData(){
        user = User(
            id = "62d6bf4cd3053219b8852911",
            name = "Oleksandr",
            phoneNumber = "+380985690454",
            bio = "Test user bio",
            chat = emptyList(),
            message = emptyList()
        )

    }

    @Test
    fun `should create user`(){
//        userService.createUser(user)
//        Mockito.verify(userRepository, Mockito.times(1)).save(user)


    }

    @Test
    fun `should delete user`(){
//        Mockito.`when`(userRepository.findById("62d6bf4cd3053219b8852911")).thenReturn(Optional.of(user))
//        userService.deleteUser("62d6bf4cd3053219b8852911")
//        Mockito.verify(userRepository, Mockito.times(1)).delete(user)
    }

    @Test
    fun `should find user by phone number`(){
//        Mockito.`when`(userRepository.findByPhoneNumber("+380985690454")).thenReturn(user)
//        userService.findUserByPhoneNumber("+380985690454")
//        Mockito.verify(userRepository, Mockito.times(1)).findByPhoneNumber(phoneNumber = "+380985690454")
    }

    @Test
    fun `should find user by user name`(){
//        Mockito.`when`(userRepository.findByName("Oleksandr")).thenReturn(user)
//        userService.findUserByUserName("Oleksandr")
//        Mockito.verify(userRepository, Mockito.times(1)).findByName("Oleksandr")
    }

    @Test
    fun `should find user by user id`(){
//        Mockito.`when`(userRepository.findById("62d6bf4cd3053219b8852911")).thenReturn(Optional.of(user))
//        userService.findByUserId("62d6bf4cd3053219b8852911")
//        Mockito.verify(userRepository, Mockito.times(2)).findById("62d6bf4cd3053219b8852911")
    }

    //update user

    /*
    @Test
    fun `should update user`(){
        //створ юзера передано в параметр юзертуапдейт
        val userUpdate = User(
            id = "62d6bf4cd3053219b8852911",
            name = "Artem",
            phoneNumber = "+389754376804",
            bio = "Test user update",
            chat = emptyList(),
            message = emptyList())
        // створений юзер
        val foundUser = userRepository.findById("62d6bf4cd3053219b8852911")
        val actualUser = userService.updateUser(userUpdate)

        //actUs = update user
    }


     */
}