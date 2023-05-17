package com.depromeet.whatnow.common.vo

import javax.persistence.Embeddable

@Embeddable
class CoordinateVo(
    var latitude: Long, // 위도
    var longitude: Long, // 경도
)
