package com.depromeet.whatnow.domains.invitecode.exception

import com.depromeet.whatnow.exception.WhatnowCodeException

class InviteCodeNotFoundException : WhatnowCodeException(
    InviteCodeErrorCode.INVITE_CODE_NOT_FOUND,
) {
    companion object {
        val EXCEPTION: WhatnowCodeException = InviteCodeNotFoundException()
    }
}
