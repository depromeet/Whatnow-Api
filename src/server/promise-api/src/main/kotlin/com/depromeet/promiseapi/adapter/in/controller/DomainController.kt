package com.depromeet.promiseapi.adapter.`in`.controller

import com.depromeet.promiseapi.application.port.`in`.PromiseUseCase
import org.springframework.web.bind.annotation.RestController

@RestController
class DomainController {
    var promiseUseCase: PromiseUseCase? = null
}