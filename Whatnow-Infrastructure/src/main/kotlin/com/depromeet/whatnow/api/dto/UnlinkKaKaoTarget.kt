package com.depromeet.whatnow.api.dto

import feign.form.FormProperty

data class UnlinkKaKaoTarget(@FormProperty("target_id") val aud: String) {
    @FormProperty("target_id_type")
     val targetIdType = "user_id"
}