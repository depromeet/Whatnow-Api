package com.depromeet.whatnow.api.promiseprogress.dto.response

import com.depromeet.whatnow.domains.progresshistory.domain.PromiseProgress
import com.depromeet.whatnow.domains.user.domain.User

data class UserProgressResponse(
    val user: User, // 유저 dto 로 치환예정입니당
    val currentProgress: PromiseProgress,
    val beforeProgress: PromiseProgress?,
)
