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
@TestPropertySource(properties = ["feign.kakao.oauth=http://localhost:\${wiremock.server.port}"])
class KakaoOauthClientTest {
    @Autowired lateinit var kakaoOauthClient: KakaoOauthClient

    @Test
    fun `카카오 Oauth 토큰 요청이 올바르게 파싱되어야한다`() {
        val file = ResourceUtils.getFile("classpath:payload/oauth-token-response.json").toPath()
        WireMock.stubFor(
            WireMock.post(WireMock.urlPathEqualTo("/oauth/token"))
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
        var response = kakaoOauthClient.kakaoAuth("CLIENT_ID", "REDIRECT_URI", "CODE", "CLIENT_SECRET")
        assertEquals(response.idToken, "idToken")
        assertEquals(response.accessToken, "accessToken")
        assertEquals(response.refreshToken, "refreshToken")
    }

    @Test
    fun `카카오 OIDC 공개키 요청이 올바르게 파싱되어야한다`() {
        val file = ResourceUtils.getFile("classpath:payload/oauth-oidc-public-key-response.json").toPath()
        WireMock.stubFor(
            WireMock.get(WireMock.urlPathEqualTo("/.well-known/jwks.json"))
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
        var response = kakaoOauthClient.kakaoOIDCOpenKeys()
        assertEquals(response.keys[0].kid, "kid1")
        assertEquals(response.keys[1].kid, "kid2")
    }
}
