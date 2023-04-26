package com.depromeet.promiseapi.domain.dto

import com.fasterxml.jackson.annotation.JsonIgnore
import java.time.LocalDate

data class DomainRequestDto(
    @set: JsonIgnore
    var id: Long = 0,
    var title: String,
    var date: LocalDate
    ){

}
