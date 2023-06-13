package com.depromeet.whatnow.config.s3

import com.depromeet.whatnow.config.InfraIntegrateSpringBootTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

@InfraIntegrateSpringBootTest
class S3PropertiesTest {
    @Autowired
    lateinit var s3Properties: S3Properties

    @Test
    fun `s3 프로퍼티가 제대로 init 되어야한다`() {
        assertEquals(s3Properties.s3.endpoint, "https://kr.object.ncloudstorage.com")
    }
}
