package com.depromeet.whatnow.api.user.controller

import com.depromeet.whatnow.api.auth.usecase.RegisterUserUseCase
import com.depromeet.whatnow.api.user.usecase.ReadUserUseCase
import com.depromeet.whatnow.domains.user.domain.User
import org.springframework.web.bind.annotation.GetMapping
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
}
