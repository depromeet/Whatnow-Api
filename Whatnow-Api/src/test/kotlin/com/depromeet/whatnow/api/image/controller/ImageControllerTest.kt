package com.depromeet.whatnow.api.image.controller

import com.depromeet.whatnow.api.image.usecase.GetPresignedUrlUseCase
import com.depromeet.whatnow.api.image.usecase.ImageUploadSuccessUseCase
import com.depromeet.whatnow.config.s3.ImageFileExtension
import com.depromeet.whatnow.domains.image.domain.ImageCommentType
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(ImageController::class)
@ContextConfiguration(classes = [ImageController::class])
@AutoConfigureMockMvc(addFilters = false)
class ImageControllerTest {
    @MockBean
    lateinit var getPresignedUrlUseCase: GetPresignedUrlUseCase

    @MockBean
    lateinit var successUseCase: ImageUploadSuccessUseCase

    @Autowired
    lateinit var mockMvc: MockMvc

    @Test
    fun `약속 이미지 presignedUrl 요청에 성공하면 200을 응답한다`() {
        // given
        val promiseId = 1L
        val fileExtension = ImageFileExtension.JPEG.name

        // when, then
        mockMvc.perform(
            get("/v1/promises/{promiseId}/images", promiseId)
                .param("fileExtension", fileExtension),
        )
            .andExpect(status().isOk)
            .andDo { print(it) }
    }

    @Test
    fun `유저 프로필 presignedUrl 요청에 성공하면 200을 응답한다`() {
        // given
        val fileExtension = ImageFileExtension.JPEG.name

        // when, then
        mockMvc.perform(
            get("/v1/users/me/images")
                .param("fileExtension", fileExtension),
        )
            .andExpect(status().isOk)
            .andDo { print(it) }
    }

    @Test
    fun `약속 이미지 업로드 성공 요청에 정상적으로 200을 반환한다`() {
        // given
        val promiseId = 1
        val imageKey = "imageKey"
        val imageCommentType = ImageCommentType.SORRY_LATE

        // when, then
        mockMvc.perform(
            MockMvcRequestBuilders.post("/v1/promises/{promiseId}/images/success/{imageKey}", promiseId, imageKey)
                .param("imageCommentType", imageCommentType.name),
        )
            .andExpect(status().isOk)
            .andDo { print(it) }
    }

    @Test
    fun `유저 프로필 업로드 성공 요청에 정상적으로 200을 반환한다`() {
        // given
        val imageKey = "imageKey"

        // when, then
        mockMvc.perform(
            MockMvcRequestBuilders.post("/v1/users/me/images/success/{imageKey}", imageKey),
        )
            .andExpect(status().isOk)
            .andDo { print(it) }
    }
}
