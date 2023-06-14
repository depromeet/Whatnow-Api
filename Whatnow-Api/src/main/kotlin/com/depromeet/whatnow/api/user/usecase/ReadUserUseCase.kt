package com.depromeet.whatnow.api.user.usecase

import com.depromeet.whatnow.annotation.UseCase
import com.depromeet.whatnow.common.vo.UserDetailVo
import com.depromeet.whatnow.common.vo.UserInfoVo
import com.depromeet.whatnow.config.security.SecurityUtils
import com.depromeet.whatnow.domains.user.adapter.UserAdapter

@UseCase
class ReadUserUseCase(
    val userAdapter: UserAdapter,
) {
    fun findMyInfo(): UserDetailVo {
        val currentUserId: Long = SecurityUtils.currentUserId
        return userAdapter.queryUser(currentUserId).toUserDetailVo()
    }

    fun findUserById(userId: Long): UserInfoVo {
        val currentUserId: Long = SecurityUtils.currentUserId
        return userAdapter.queryUser(currentUserId).toUserInfoVo()
    }
}
