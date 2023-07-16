package com.depromeet.whatnow.domains.invitecode.exception

import com.depromeet.whatnow.exception.WhatnowCodeException

class InviteCodeDuplicateCreatedException : WhatnowCodeException(
    InviteCodeErrorCode.INVITE_CODE_DUPLICATE_CREATED,
) {
    companion object {
        val EXCEPTION: WhatnowCodeException = InviteCodeDuplicateCreatedException()
    }
}
