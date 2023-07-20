package com.depromeet.whatnow.events.handler

import ch.hsr.geohash.GeoHash
import com.depromeet.whatnow.common.vo.CoordinateVo
import com.depromeet.whatnow.config.fcm.FcmService
import com.depromeet.whatnow.consts.RADIUS_CONVERT_METER
import com.depromeet.whatnow.domains.district.repository.DistrictRepository
import com.depromeet.whatnow.domains.notification.domain.NotificationType
import com.depromeet.whatnow.domains.notification.service.NotificationDomainService
import com.depromeet.whatnow.domains.promise.adaptor.PromiseAdaptor
import com.depromeet.whatnow.domains.promiseuser.adaptor.PromiseUserAdaptor
import com.depromeet.whatnow.domains.promiseuser.service.PromiseUserDomainService
import com.depromeet.whatnow.domains.user.adapter.UserAdapter
import com.depromeet.whatnow.events.domainEvent.PromiseUserUpdateLocationEvent
import com.mongodb.client.model.geojson.Point
import com.mongodb.client.model.geojson.Position
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener
@Component
class PromiseUserUpdateLocationEventHandler(
    val promiseAdaptor: PromiseAdaptor,
    val promiseUserAdaptor: PromiseUserAdaptor,
    val promiseUserDomainService: PromiseUserDomainService,
    val userAdapter: UserAdapter,
    val fcmService: FcmService,
    val notificationDomainService: NotificationDomainService,
    val districtRepository: DistrictRepository,
) {
    // logger

    @Async
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @TransactionalEventListener(classes = [PromiseUserUpdateLocationEvent::class], phase = TransactionPhase.AFTER_COMMIT)
    fun handlerPromiseUserUpdateLocationEventHandler(promiseUserUpdateLocationEvent: PromiseUserUpdateLocationEvent) {
        val promiseId = promiseUserUpdateLocationEvent.promiseId
        val userId = promiseUserUpdateLocationEvent.userId
        val promiseUserId = promiseUserUpdateLocationEvent.promiseUserId

        val promise = promiseAdaptor.queryPromise(promiseId)
        val promiseUser = promiseUserAdaptor.findByPromiseIdAndUserId(promiseId, userId)
//        if (promiseUserDomainService.isArrived(promiseUser, promise.meetPlace!!.coordinate)) {
        if (isArrived(promiseUser.userLocation, promise.meetPlace!!.coordinate)) {
            // 활성화된 약속이 종료되기 전일 때
            if (promise.isBeforePromiseEndTime() && promise.isActive()) {
                // 약속유저 상태 도착 상태(WAIT)로 변경
                promiseUser.updatePromiseUserTypeToWait()

                val promiseUsers = promiseUserAdaptor.findByPromiseId(promiseId)

                // 약속에 참여한 유저들 조회 (자기 자신 제외)
                val users = promiseUsers
                    .map { promiseUser -> userAdapter.queryUser(promiseUser.userId) }
                    .filter { user -> user.id != userId }

                // 도착한 유저
                val arrivalUser = userAdapter.queryUser(userId)

                // 앱 알람 허용한 유저
                val appAlarmPermitUsers = users
                    .filter { user -> user.fcmNotification.fcmToken != "" && user.fcmNotification.appAlarm }

                val data: MutableMap<String, String> = mutableMapOf()
                data["notificationType"] = NotificationType.ARRIVAL.name
                data["promiseId"] = promiseId.toString()
                data["senderUserId"] = promiseUserUpdateLocationEvent.userId.toString()

                // 행정동 조회
                val intersects = districtRepository.findByLocationIntersects(
                    Point(
                        Position(promise.meetPlace!!.coordinate.longitude, promise.meetPlace!!.coordinate.latitude),
                    ),
                )
                val district = intersects.properties.adm_nm

                // 앱 알람 허용한 유저에게 알람 보내기
                if (appAlarmPermitUsers.isNotEmpty()) {
                    fcmService.sendGroupMessageAsync(
                        tokenList = appAlarmPermitUsers.map { user -> user.fcmNotification.fcmToken!! },
                        title = "${arrivalUser.nickname} 도착완료",
                        content = "\uD83C\uDFC1 $district",
                        data = data,
                    )
                }

                // notification 저장
                users.forEach { user ->
                    notificationDomainService.saveForArrival(promiseId, arrivalUser.id!!, user.id!!)
                }
            }
            // 활성화된 약속이 종료되고 나서 도착 시 사전에 LATE처리를 해서 별도의 처리가 필요 없음
        }
    }

    fun isArrived(promiseUser: CoordinateVo, coordinate: CoordinateVo): Boolean {
        val start = GeoHash.withCharacterPrecision(promiseUser.latitude, promiseUser.longitude, 10).toBase32()
        val end = GeoHash.withCharacterPrecision(coordinate.latitude, coordinate.longitude, 10).toBase32()
        val distance = calculateDistanceUsingGeoHash(start, end)
        return distance <= 20
    }

    fun calculateDistanceUsingGeoHash(geoHash1: String, geoHash2: String): Double {
        val commonPrefixLength = getCommonPrefixLength(geoHash1, geoHash2)
        val cellSize = getCellSize(commonPrefixLength)
        return cellSize * RADIUS_CONVERT_METER // Convert to meters
    }

    fun getCommonPrefixLength(geoHash1: String, geoHash2: String): Int {
        var commonPrefixLength = 0
        val length = minOf(geoHash1.length, geoHash2.length)

        while (commonPrefixLength < length && geoHash1[commonPrefixLength] == geoHash2[commonPrefixLength]) {
            commonPrefixLength++
        }

        return commonPrefixLength
    }

    fun getCellSize(commonPrefixLength: Int): Double {
        return 180.0 / (1 shl (commonPrefixLength * 2))
    }
}
