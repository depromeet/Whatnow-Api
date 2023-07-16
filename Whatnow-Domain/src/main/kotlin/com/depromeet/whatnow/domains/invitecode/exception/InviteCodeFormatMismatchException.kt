package com.depromeet.whatnow.domains.invitecode.exception

import com.depromeet.whatnow.domains.progresshistory.exception.PromiseHistoryErrorCode
import com.depromeet.whatnow.exception.WhatnowCodeException

class InviteCodeFormatMismatchException : WhatnowCodeException(
    InviteCodeErrorCode.INVITE_CODE_FORMAT_MISMATCH,
) {
    companion object {
        val EXCEPTION: WhatnowCodeException = InviteCodeFormatMismatchException()
    }
}
