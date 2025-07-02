package com.cooksmart.service

import com.cooksmart.model.User
import com.cooksmart.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class UserService(private val userRepository: UserRepository) {

    fun getAllUsers(): List<User> = userRepository.findAll()

    fun getUserById(id: Long): User =
        userRepository.findById(id).orElseThrow { RuntimeException("User not found") }

    fun createUser(user: User): User = userRepository.save(user)

    fun updateUser(id: Long, updatedUser: User): User {
        val user = getUserById(id)
        val userToSave = user.copy(
            username = updatedUser.username,
            email = updatedUser.email,
            password = updatedUser.password
        )
        return userRepository.save(userToSave)
    }

    fun deleteUser(id: Long) = userRepository.deleteById(id)
}
