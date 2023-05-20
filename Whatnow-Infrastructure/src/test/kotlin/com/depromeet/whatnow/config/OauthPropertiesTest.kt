package com.depromeet.whatnow.config

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

@InfraIntegrateSpringBootTest
class OauthPropertiesTest {
    @Autowired lateinit var oauthProperties: OauthProperties

    @Test
    fun `oauth 프로퍼티가 제대로 init 되어야한다`() {
        assertEquals(oauthProperties.kakao.appId, "default")
    }
}
