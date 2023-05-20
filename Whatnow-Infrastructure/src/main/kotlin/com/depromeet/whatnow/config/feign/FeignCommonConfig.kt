package com.depromeet.whatnow.config.feign

import com.depromeet.whatnow.api.BaseFeignClientPackage
import feign.Logger
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@EnableFeignClients(basePackageClasses = [BaseFeignClientPackage::class])
class FeignCommonConfig {

    // 코틀린으로 잭슨 설정이 잘 안먹힘... 일단 패스!
//    @Bean
//    fun feignDecoder(): Decoder {
//        return JacksonDecoder(customObjectMapper())
//    }
//
//    /**
//     * 타임관련 유닛 해석을 위한 디코더 추가
//     * @return
//     */
//    fun customObjectMapper(): ObjectMapper {
//        val objectMapper = ObjectMapper()
//        objectMapper.registerModule(JavaTimeModule())
//        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
//        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
//        objectMapper.configure(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE, false)
//        return objectMapper
//    }

    @Bean
    fun feignLoggerLevel(): Logger.Level {
        return Logger.Level.FULL
    }
}
