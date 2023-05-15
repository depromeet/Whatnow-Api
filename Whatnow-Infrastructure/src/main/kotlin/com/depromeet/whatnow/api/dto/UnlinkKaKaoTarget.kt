package com.depromeet.whatnow.api.dto

import feign.form.FormProperty

// https://github.com/OpenFeign/feign-form/issues/79
// val private final이라서 안되는 이슈가 있었음 var 로 바꿔야함.
data class UnlinkKaKaoTarget(@FormProperty("target_id") var aud: String) {
    @FormProperty("target_id_type")
    var targetIdType = "user_id"
}
