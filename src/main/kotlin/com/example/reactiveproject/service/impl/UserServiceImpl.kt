package com.example.reactiveproject.service.impl

import com.example.reactiveproject.model.User
import org.springframework.beans.factory.annotation.Autowired
import com.example.reactiveproject.repository.UserRepository
import com.example.reactiveproject.service.UserService
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono


@Service
class UserServiceImpl(
    @Autowired
    val userRepository: UserRepository
): UserService
{
    override fun createUser(user: User) {
        userRepository.save(user)
            .subscribe()
    }

    override fun deleteUser(id: String) {
        userRepository.deleteById(id).subscribe()
    }

    override fun updateUser(id: String, user: User): Mono<User> {
//        val userToUpdate: User = userRepository.findById(user.id!!).get()
//            .copy(name = user.name,
//                phoneNumber = user.phoneNumber,
//                bio = user.bio)
//        userRepository.save(userToUpdate)

        return userRepository.findById(id)
            .map {
                User(
                    id = id,
                    name = user.name,
                    phoneNumber = user.phoneNumber,
                    bio = user.bio,
                    chat = user.chat,
                    message = user.message
                    )
            }
            .flatMap(
                userRepository::save
            )

    }

    override fun findUserByPhoneNumber(phoneNumber: String): Mono<User?> {
        return userRepository.findByPhoneNumber(phoneNumber)
            .switchIfEmpty(
                Mono.error(NotFoundException())
            )
    }


    override fun findUserByUserName(name: String): Mono<User?> {
        return userRepository.findByName(name)
            .switchIfEmpty(
                Mono.error(NotFoundException())
            )
    }

    override fun findByUserId(id: String): Mono<User?> {
        return userRepository.findById(id)
            .switchIfEmpty(
                Mono.error(NotFoundException())
            )
    }
}