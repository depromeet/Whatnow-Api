package com.depromeet.whatnow.api.promise.controller

import com.depromeet.whatnow.api.image.controller.ImageController
import com.depromeet.whatnow.api.promise.usecase.PromiseReadUseCase
import com.depromeet.whatnow.api.promise.usecase.PromiseRegisterUseCase
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@WebMvcTest(ImageController::class)
@ContextConfiguration(classes = [PromiseController::class])
@AutoConfigureMockMvc(addFilters = false)
class PromiseControllerTest {
    @MockBean
    lateinit var promiseReadUseCase: PromiseReadUseCase

    @MockBean
    lateinit var promiseRegisterUseCase: PromiseRegisterUseCase

    @Autowired
    lateinit var mockMvc: MockMvc

    @Test
    fun queryStringEnum() {
        val request = MockMvcRequestBuilders
            .get("/v1/promises/users/status/BEFORE")
        mockMvc
            .perform(request)
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andDo { print(it) }
    }
}
