package com.depromeet.whatnow.domains.promiseuser.service

import com.depromeet.whatnow.domains.promiseuser.adaptor.PromiseUserAdaptor
import com.depromeet.whatnow.domains.promiseuser.domain.PromiseUser
import com.depromeet.whatnow.domains.promiseuser.domain.PromiseUserType
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class PromiseUserDomainService(
    val promiseUserAdaptor: PromiseUserAdaptor,
) {
    @Transactional
    fun queryPromiseUser(promiseId: Long): PromiseUser {
        return promiseUserAdaptor.queryPromise(promiseId)
    }

    /**
     * 참여한 유저가 약속 참여를 취소합니다.
     * */
    @Transactional
    fun withDraw(promiseId: Long, userId: Long) {
        val promiseUser = promiseUserAdaptor.queryPromise(promiseId)
        promiseUser.cancelPromiseUser()
    }

    @Transactional
    fun updatePromiseUserType(promiseId: Long, userId: Long, promiseUserType: PromiseUserType): PromiseUser {
        val promiseUser = promiseUserAdaptor.findByPromiseIdAndUserId(promiseId, userId)
        promiseUser.updatePromiseUserType(promiseUserType)
        return promiseUser
    }

    /**
     * 초기 약속 생성
     * */
    @Transactional
    fun createPromiseUser(promiseUser: PromiseUser): PromiseUser {
        return promiseUserAdaptor.save(promiseUser)
    }

    fun findByPromiseId(promiseId: Long): List<PromiseUser> {
        return promiseUserAdaptor.findByPromiseId(promiseId)
    }

    fun findByUserId(userId: Long): List<PromiseUser> {
        return promiseUserAdaptor.findByUserId(userId)
    }

    fun findByPromiseIdAndUserId(promiseId: Long, userId: Long): PromiseUser {
        return promiseUserAdaptor.findByPromiseIdAndUserId(promiseId, userId)
    }
}
