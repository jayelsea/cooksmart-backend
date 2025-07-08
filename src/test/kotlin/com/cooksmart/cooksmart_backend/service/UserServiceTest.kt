package com.cooksmart.cooksmart_backend.service

import com.cooksmart.model.User
import com.cooksmart.repository.UserRepository
import com.cooksmart.service.UserService
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import java.util.*

class UserServiceTest {

    @Mock
    private lateinit var userRepository: UserRepository

    @InjectMocks
    private lateinit var userService: UserService

    @BeforeEach
    fun setup() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun `getAllUsers should return users`() {
        val users = listOf(User(1, "john", "john@email.com", "pass"))
        `when`(userRepository.findAll()).thenReturn(users)

        val result = userService.getAllUsers()
        assertEquals(1, result.size)
    }

    @Test
    fun `getUserById should return user`() {
        val user = User(1, "john", "john@email.com", "pass")
        `when`(userRepository.findById(1)).thenReturn(Optional.of(user))

        val result = userService.getUserById(1)
        assertEquals("john", result.username)
    }

    @Test
    fun `getUserById should throw exception when not found`() {
        `when`(userRepository.findById(1)).thenReturn(Optional.empty())
        assertThrows(RuntimeException::class.java) {
            userService.getUserById(1)
        }
    }

    @Test
    fun `createUser should save and return user`() {
        val user = User(1, "john", "john@email.com", "pass")
        `when`(userRepository.save(user)).thenReturn(user)

        val result = userService.createUser(user)
        assertEquals("john", result.username)
    }

    @Test
    fun `updateUser should update and return user`() {
        val user = User(1, "john", "john@email.com", "pass")
        val updated = User(1, "jane", "jane@email.com", "123")
        `when`(userRepository.findById(1)).thenReturn(Optional.of(user))
        `when`(userRepository.save(any(User::class.java))).thenReturn(updated)

        val result = userService.updateUser(1, updated)
        assertEquals("jane", result.username)
    }

    @Test
    fun `deleteUser should call deleteById`() {
        userService.deleteUser(1)
        verify(userRepository, times(1)).deleteById(1)
    }
}
