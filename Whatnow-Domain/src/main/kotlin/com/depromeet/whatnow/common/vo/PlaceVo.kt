package com.depromeet.whatnow.common.vo

import javax.persistence.Embeddable
import javax.persistence.Embedded

@Embeddable
class PlaceVo(
    @Embedded
    var coordinate: CoordinateVo,
    var address: String,
)
