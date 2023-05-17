package com.depromeet.whatnow.common.vo

import javax.persistence.Embeddable

@Embeddable
class CoordinateVo(
    var latitude: Double, // 위도
    var longitude: Double, // 경도
)
