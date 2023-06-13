package com.depromeet.whatnow.api.picture.controller

import com.depromeet.whatnow.api.picture.usecase.PictureUploadSuccessUseCase
import com.depromeet.whatnow.domains.picture.domain.PictureCommentType
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(PictureController::class)
@ContextConfiguration(classes = [PictureController::class])
@AutoConfigureMockMvc(addFilters = false)
class PictureControllerTest {
    @MockBean
    lateinit var successUseCase: PictureUploadSuccessUseCase

    @Autowired
    lateinit var mockMvc: MockMvc

    @Test
    fun `이미지 업로드 성공 요청에 정상적으로 200을 반환한다`() {
        // given
        val promiseId = 1
        val imageKey = "imageKey"
        val pictureCommentType = PictureCommentType.SORRY_LATE

        // when, then
        mockMvc.perform(
            post("/v1/promises/{promiseId}/images/success/{imageKey}", promiseId, imageKey)
                .param("pictureCommentType", pictureCommentType.name),
        )
            .andExpect(status().isOk)
            .andDo { print(it) }
    }
}
