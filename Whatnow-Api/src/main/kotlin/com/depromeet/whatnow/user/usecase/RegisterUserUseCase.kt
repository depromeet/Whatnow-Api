package com.depromeet.whatnow.user.usecase

import com.depromeet.whatnow.domains.user.domain.User
import org.springframework.stereotype.Service

@Service // TODO : UseCase annotation 을 커스텀 하게 만들고싶은데 위치를 어디로 줘야할지? 커먼 레이어가없음
class RegisterUserUseCase {
    fun execute(): User {
    }
}
