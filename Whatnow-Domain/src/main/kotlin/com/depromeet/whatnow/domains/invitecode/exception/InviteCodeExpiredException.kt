package com.depromeet.whatnow.domains.invitecode.exception

import com.depromeet.whatnow.exception.WhatnowCodeException

class InviteCodeExpiredException : WhatnowCodeException(
    InviteCodeErrorCode.INVITE_CODE_EXPIRED,
) {
    companion object {
        val EXCEPTION: WhatnowCodeException = InviteCodeExpiredException()
    }
}
