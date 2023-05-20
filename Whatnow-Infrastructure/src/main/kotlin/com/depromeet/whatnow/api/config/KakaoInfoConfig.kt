package com.depromeet.whatnow.api.config

import feign.codec.Encoder
import feign.form.spring.SpringFormEncoder
import org.springframework.beans.factory.ObjectFactory
import org.springframework.boot.autoconfigure.http.HttpMessageConverters
import org.springframework.cloud.openfeign.support.SpringEncoder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Import

@Import(KakaoInfoErrorDecoder::class)
class KakaoInfoConfig {
//    @Bean
//    @ConditionalOnMissingBean(value = [ErrorDecoder::class])
//    fun commonFeignErrorDecoder(): KakaoInfoErrorDecoder {
//        return KakaoInfoErrorDecoder()
//    }

    @Bean
    fun encoder(converters: ObjectFactory<HttpMessageConverters>): Encoder {
        return SpringFormEncoder(SpringEncoder(converters))
    }
}
