package com.depromeet.whatnow.api.auth.usecase

import com.depromeet.whatnow.api.auth.helper.KakaoOauthHelper
import com.depromeet.whatnow.config.security.SecurityUtils
import com.depromeet.whatnow.domains.user.adapter.RefreshTokenAdapter
import com.depromeet.whatnow.domains.user.adapter.UserAdapter
import com.depromeet.whatnow.domains.user.domain.User
import com.depromeet.whatnow.domains.user.service.UserDomainService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class WithDrawUseCase(
    val refreshTokenAdapter: RefreshTokenAdapter,
    val userAdapter: UserAdapter,
    val userDomainService: UserDomainService,
    val kakaoOauthHelper: KakaoOauthHelper
) {

   @Transactional
    fun execute() {
        val currentUserId: Long = SecurityUtils.currentUserId
        refreshTokenAdapter.deleteByUserId(currentUserId)
        val user = userAdapter.queryUser(currentUserId)
        val oid = user.oauthInfo.oauthId
        userDomainService.withDrawUser(currentUserId)
        kakaoOauthHelper.unlink(oid)
   }
}