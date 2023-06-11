package com.depromeet.whatnow.api.user.usecase

import com.depromeet.whatnow.annotation.UseCase
import com.depromeet.whatnow.config.security.SecurityUtils
import com.depromeet.whatnow.domains.user.adapter.UserAdapter
import com.depromeet.whatnow.domains.user.domain.User

@UseCase
class ReadUserUseCase(
    val userAdapter: UserAdapter,
) {
    fun findUser(): User {
        val currentUserId: Long = SecurityUtils.currentUserId
        return userAdapter.queryUser(currentUserId)
    }
}
