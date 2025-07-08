package com.cooksmart.cooksmart_backend.controller

import com.cooksmart.controller.UserController
import com.cooksmart.model.User
import com.cooksmart.service.UserService
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.given
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@WebMvcTest(UserController::class)
class UserControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var userService: UserService

    @Test
    fun `GET all users`() {
        val users = listOf(User(1, "john", "john@email.com", "pass"))
        given(userService.getAllUsers()).willReturn(users)

        mockMvc.perform(get("/api/users"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$[0].username").value("john"))
    }

    @Test
    fun `GET user by id`() {
        val user = User(1, "john", "john@email.com", "pass")
        given(userService.getUserById(1)).willReturn(user)

        mockMvc.perform(get("/api/users/1"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.username").value("john"))
    }
}
