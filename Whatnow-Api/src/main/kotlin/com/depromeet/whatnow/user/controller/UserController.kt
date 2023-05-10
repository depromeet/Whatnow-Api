package com.depromeet.whatnow.user.controller

import com.depromeet.whatnow.domains.user.domain.User
import com.depromeet.whatnow.user.usecase.ReadUserUseCase
import com.depromeet.whatnow.user.usecase.RegisterUserUseCase
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/users")
class UserController(
    val registerUserUseCase: RegisterUserUseCase,
    val readUserUseCase: ReadUserUseCase,
) {

    @GetMapping
    fun getUser(): User {
        return readUserUseCase.findUser()
    }

    @PostMapping
    fun registerUser(): User {
        return registerUserUseCase.execute()
    }
}
