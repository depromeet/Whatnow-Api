package com.depromeet.whatnow.domains.user.domain

import com.depromeet.whatnow.consts.WITHDRAW_PREFIX
import java.time.LocalDateTime
import javax.persistence.Embeddable
import javax.persistence.EnumType
import javax.persistence.Enumerated

@Embeddable
class OauthInfo(
    var oauthId: String,

    @Enumerated(EnumType.STRING)
    var oauthProvider: OauthProvider,
) {

    fun withDrawOauthInfo(): OauthInfo {
        val withDrawOid = WITHDRAW_PREFIX + LocalDateTime.now().toString() + ":" + oauthId
        return OauthInfo(withDrawOid, oauthProvider)
    }
}
