package com.depromeet.whatnow.api.user.controller

import com.depromeet.whatnow.api.auth.usecase.RegisterUserUseCase
import com.depromeet.whatnow.api.user.usecase.ReadUserUseCase
import com.depromeet.whatnow.domains.user.domain.User
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/users")
@Tag(name = "2. [유저]")
@SecurityRequirement(name = "access-token")
class UserController(
    val registerUserUseCase: RegisterUserUseCase,
    val readUserUseCase: ReadUserUseCase,
) {

    @GetMapping("/me")
    fun getMyUserInfo(): User {
        return readUserUseCase.findUser()
    }
}
