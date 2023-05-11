package com.depromeet.whatnow.api

import org.springframework.cloud.openfeign.FeignClient

@FeignClient(name = "KakaoAuthClient", url = "https://kauth.kakao.com")
interface TestFeignClient
