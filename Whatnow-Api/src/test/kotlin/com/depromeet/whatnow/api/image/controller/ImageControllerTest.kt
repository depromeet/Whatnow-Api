package com.depromeet.whatnow.api.image.controller

import com.depromeet.whatnow.api.image.usecase.GetPresignedUrlUseCase
import com.depromeet.whatnow.api.image.usecase.ImageCommentReadUseCase
import com.depromeet.whatnow.api.image.usecase.ImageUploadSuccessUseCase
import com.depromeet.whatnow.api.image.usecase.PromiseImageDeleteUseCase
import com.depromeet.whatnow.api.image.usecase.PromiseImageReadUseCase
import com.depromeet.whatnow.config.s3.ImageFileExtension
import com.depromeet.whatnow.domains.image.domain.PromiseImageCommentType
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

    @MockBean
    lateinit var imageCommentReadUseCase: ImageCommentReadUseCase

    @MockBean
    lateinit var promiseImageReadUseCase: PromiseImageReadUseCase

    @MockBean
    lateinit var promiseImageDeleteUseCase: PromiseImageDeleteUseCase

    @Autowired
    lateinit var mockMvc: MockMvc

    @Test
    fun `약속 이미지 presignedUrl 요청에 성공하면 200을 응답한다`() {
        // given
        val promiseId = 1L
        val fileExtension = ImageFileExtension.JPEG.name

        // when, then
        mockMvc.perform(
            get("/v1/images/promises/{promiseId}/presigned-url", promiseId)
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
            get("/v1/images/users/me/presigned-url")
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
        val fileExtension = ImageFileExtension.JPEG.name
        val promiseImageCommentType = PromiseImageCommentType.SORRY_LATE
        // when, then
        mockMvc.perform(
            MockMvcRequestBuilders.post("/v1/images/{imageKey}/promises/{promiseId}", imageKey, promiseId)
                .param("fileExtension", fileExtension)
                .param("promiseImageCommentType", promiseImageCommentType.name),
        )
            .andExpect(status().isOk)
            .andDo { print(it) }
    }

    @Test
    fun `유저 프로필 업로드 성공 요청에 정상적으로 200을 반환한다`() {
        // given
        val imageKey = "imageKey"
        val fileExtension = ImageFileExtension.JPEG.name

        // when, then
        mockMvc.perform(
            MockMvcRequestBuilders.post("/v1/images/{imageKey}/users/me", imageKey)
                .param("fileExtension", fileExtension),
        )
            .andExpect(status().isOk)
            .andDo { print(it) }
    }
}
