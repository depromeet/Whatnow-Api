package com.depromeet.promiseapi.application.service

import com.depromeet.promiseapi.application.port.`in`.PromiseUseCase
import com.depromeet.promiseapi.application.port.out.LoadDomainPort
import com.depromeet.promiseapi.application.port.out.RecordDomainPort
import org.springframework.stereotype.Service

@Service
class DomainService : PromiseUseCase {
    val loadDomainPort : LoadDomainPort?= null
    val recordDomainPort : RecordDomainPort?= null

}