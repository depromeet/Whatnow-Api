package com.depromeet.whatnow.config.ncp

import com.depromeet.whatnow.api.NcpLocalSearchClient
import com.depromeet.whatnow.api.config.ncp.NcpConfig
import com.depromeet.whatnow.config.InfraIntegrateProfileResolver
import com.depromeet.whatnow.config.InfraIntegrateTestConfig
import com.github.tomakehurst.wiremock.client.WireMock
import com.github.tomakehurst.wiremock.client.WireMock.aResponse
import com.github.tomakehurst.wiremock.client.WireMock.equalTo
import com.github.tomakehurst.wiremock.client.WireMock.get
import com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock
import org.springframework.cloud.openfeign.FeignClientsConfiguration
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.TestPropertySource
import org.springframework.util.ResourceUtils

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = [InfraIntegrateTestConfig::class])
@AutoConfigureWireMock(port = 0)
@ActiveProfiles(resolver = InfraIntegrateProfileResolver::class)
@TestPropertySource(properties = ["ncp.local.search-url=http://localhost:\${wiremock.server.port}"])
@ContextConfiguration(classes = [FeignClientsConfiguration::class, NcpConfig::class])
class NcpLocalSearchClientTest() {
    @Autowired lateinit var client: NcpLocalSearchClient

    @Test
    fun `testSearchByKeyword`() {
//        application-
        val file = ResourceUtils.getFile("classpath:payload/ncp-local-search-response.json").readText()
        val responseJson = file

        val accessKey = "your-access-key"
        val secretKey = "your-secret-key"
        val query = "강남"

        WireMock.stubFor(
            get(urlPathEqualTo("/local.json"))
                .withHeader("X-Naver-Client-Id", equalTo(accessKey))
                .withHeader("X-Naver-Client-Secret", equalTo(secretKey))
                .withQueryParam("query", equalTo(query))
                .willReturn(
                    aResponse()
                        .withStatus(200)
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .withBody(responseJson),
                ),
        )
        val response = client.searchByKeyword(accessKey, secretKey, query)
        assertEquals(response.items?.size, 1)
    }
}
