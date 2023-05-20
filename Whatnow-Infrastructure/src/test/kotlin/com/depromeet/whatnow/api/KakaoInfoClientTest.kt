package com.depromeet.whatnow.api

import com.depromeet.whatnow.config.InfraIntegrateProfileResolver
import com.depromeet.whatnow.config.InfraIntegrateTestConfig
import com.github.tomakehurst.wiremock.client.WireMock
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.TestPropertySource
import org.springframework.util.ResourceUtils
import java.nio.file.Files

@ContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = [InfraIntegrateTestConfig::class])
@AutoConfigureWireMock(port = 0)
@ActiveProfiles(resolver = InfraIntegrateProfileResolver::class)
@TestPropertySource(properties = ["feign.kakao.info=http://localhost:\${wiremock.server.port}"])
class KakaoInfoClientTest {
    @Autowired lateinit var kakaoInfoClient: KakaoInfoClient

    @Test
    fun `카카오 유저 정보 요청이 올바르게 파싱되어야한다`() {
        val file = ResourceUtils.getFile("classpath:payload/oauth-user-info-response.json").toPath()
        WireMock.stubFor(
            WireMock.get(WireMock.urlPathEqualTo("/v2/user/me"))
                .willReturn(
                    WireMock.aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader(
                            "Content-Type",
                            MediaType.APPLICATION_JSON_VALUE,
                        )
                        .withBody(Files.readAllBytes(file)),
                ),
        )
        var response = kakaoInfoClient.kakaoUserInfo("accessToken")
        assertEquals(response.kakaoAccount.email, "sample@sample.com")
        assertEquals(response.kakaoAccount.profile.profileImageUrl, "http://yyy.kakao.com/dn/.../img_640x640.jpg")
        assertEquals(response.kakaoAccount.profile.isDefaultImage, false)
    }
}
