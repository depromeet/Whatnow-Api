package com.depromeet.whatnow.domains.user.domain

import javax.persistence.Embeddable
import javax.persistence.EnumType
import javax.persistence.Enumerated

@Embeddable
class OauthProfile(
    var oauthId: String,

    @Enumerated(EnumType.STRING)
    var oauthProvider: OauthProvider,
)
