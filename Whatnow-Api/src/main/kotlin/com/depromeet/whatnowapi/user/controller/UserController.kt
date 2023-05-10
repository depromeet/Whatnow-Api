package com.depromeet.whatnowapi.user.controller

import com.depromeet.whatnowapi.user.usecase.RegisterUserUseCase
import com.depromeet.whatnowdomain.domains.user.domain.User
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/v1/users")
class UserController (
        val registerUserUseCase: RegisterUserUseCase
        ){

    @GetMapping
    fun getUser() :User {

    }
}