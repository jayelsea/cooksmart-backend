package com.cooksmart.controller

import com.cooksmart.model.LoginRequest
import com.cooksmart.model.LoginResponse
import com.cooksmart.model.RegisterRequest
import com.cooksmart.model.RegisterResponse
import com.cooksmart.model.User
import com.cooksmart.service.UserService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api")
class UserController(private val userService: UserService) {

    @GetMapping("/users")
    fun getAllUsers(): List<User> = userService.getAllUsers()

    @GetMapping("/users/{id}")
    fun getUserById(@PathVariable id: Long): User = userService.getUserById(id)

    @PostMapping("/users")
    fun createUser(@RequestBody user: User): User = userService.createUser(user)

    @PutMapping("/users/{id}")
    fun updateUser(@PathVariable id: Long, @RequestBody user: User): User =
        userService.updateUser(id, user)

    @DeleteMapping("/users/{id}")
    fun deleteUser(@PathVariable id: Long) = userService.deleteUser(id)

    @PostMapping("/login")
    fun login(@RequestBody request: LoginRequest): LoginResponse {
        val user = userService.findByEmail(request.email)
        return if (user != null && user.password == request.password) {
            LoginResponse(success = true, message = "Login exitoso", token = null)
        } else {
            LoginResponse(success = false, message = "Credenciales inválidas", token = null)
        }
    }

    @PostMapping("/register")
    fun register(@RequestBody request: RegisterRequest): RegisterResponse {
        val exists = userService.findByEmail(request.email) != null
        return if (exists) {
            RegisterResponse(success = false, message = "El usuario ya existe")
        } else {
            val user = User(username = request.name, email = request.email, password = request.password)
            userService.createUser(user)
            RegisterResponse(success = true, message = "Registro exitoso")
        }
    }
}
