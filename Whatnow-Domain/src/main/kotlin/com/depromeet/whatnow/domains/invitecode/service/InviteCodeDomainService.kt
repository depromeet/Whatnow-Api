package com.depromeet.whatnow.domains.invitecode.service

import com.depromeet.whatnow.annotation.DomainService
import com.depromeet.whatnow.consts.INVITE_CODE_EXPIRED_TIME
import com.depromeet.whatnow.consts.INVITE_CODE_LENGTH
import com.depromeet.whatnow.domains.invitecode.adapter.InviteCodeAdapter
import com.depromeet.whatnow.domains.invitecode.domain.InviteCodeRedisEntity
import java.nio.charset.StandardCharsets
import java.security.MessageDigest
import kotlin.math.min

@DomainService
class InviteCodeDomainService(
    val inviteCodeAdapter: InviteCodeAdapter,
) {
    fun createInviteCode(promiseId: Long): String {
        val promiseIdString = promiseId.toString()
        val hashedPromiseId = hashStringWithSHA256(promiseIdString)
        val inviteCode = generateInviteCodeFromHash(hashedPromiseId)

        // redis에 초대 코드 저장 및 만료 시간 지정 / 24 hour
        saveInviteCodeToRedis(promiseId, inviteCode, INVITE_CODE_EXPIRED_TIME)

        return inviteCode
    }

    private fun hashStringWithSHA256(input: String): ByteArray {
        val md = MessageDigest.getInstance("SHA-256")
        return md.digest(input.toByteArray(StandardCharsets.UTF_8))
    }

    private fun generateInviteCodeFromHash(hashBytes: ByteArray): String {
        val sb = StringBuilder()
        for (b in hashBytes) {
            // Convert hash bytes -> hexadecimal string
            sb.append(String.format("%02x", b))
        }
        return sb.substring(0, min(INVITE_CODE_LENGTH, sb.length))
    }

    private fun saveInviteCodeToRedis(promiseId: Long, inviteCode: String, expirationHours: Long) {
        val redisEntity = InviteCodeRedisEntity(promiseId, inviteCode, expirationHours)
        inviteCodeAdapter.save(redisEntity)
    }
}
