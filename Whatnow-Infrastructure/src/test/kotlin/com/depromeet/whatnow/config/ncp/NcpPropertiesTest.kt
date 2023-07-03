package com.depromeet.whatnow.config.ncp

import com.depromeet.whatnow.config.InfraIntegrateSpringBootTest
import com.depromeet.whatnow.config.NcpProperties
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

@InfraIntegrateSpringBootTest
class NcpPropertiesTest {
    @Autowired
    lateinit var ncpProperties: NcpProperties

    @Test
    fun `ncp 프로퍼티가 제대로 init 되어야한다`() {
        Assertions.assertEquals(ncpProperties.local.searchUrl, "https://openapi.naver.com/v1/search")
    }
}
