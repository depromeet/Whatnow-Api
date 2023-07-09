package com.depromeet.whatnow.events.handler

import com.depromeet.whatnow.annotation.Handler
import com.depromeet.whatnow.common.aop.event.Events
import com.depromeet.whatnow.consts.MEET_RADIUS
import com.depromeet.whatnow.domains.promiseuser.adaptor.PromiseUserAdaptor
import com.depromeet.whatnow.events.domainEvent.PromiseUserMeetEvent
import com.depromeet.whatnow.events.domainEvent.PromiseUserUpdateLocationEvent
import com.google.common.geometry.S2CellId
import com.google.common.geometry.S2LatLng
import com.google.common.geometry.S2LatLngRect
import com.google.common.geometry.S2RegionCoverer
import org.springframework.scheduling.annotation.Async
import org.springframework.transaction.annotation.Transactional
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener

@Handler
class PromiseUserUpdateLocationEventHandler(
    val promiseUserAdaptor: PromiseUserAdaptor,
) {
    @Async
    @Transactional
    @TransactionalEventListener(classes = [PromiseUserUpdateLocationEvent::class], phase = TransactionPhase.AFTER_COMMIT)
    fun handlePromiseUserUpdateLocationEvent(promiseUserUpdateLocationEvent: PromiseUserUpdateLocationEvent) {
        val promiseId = promiseUserUpdateLocationEvent.promiseId
        val referencePoint = S2LatLng.fromDegrees(promiseUserUpdateLocationEvent.coordinateVo.latitude, promiseUserUpdateLocationEvent.coordinateVo.longitude)
        // 주변 점들의 목록을 생성
        val findByPromiseId = promiseUserAdaptor.findByPromiseId(promiseId)
        val indexedPoints = mutableMapOf<S2CellId, S2LatLng>()

        // S2 셀 ID를 사용하여 점들을 인덱싱합니다.
        findByPromiseId.forEach { promiseUser ->
            if (promiseUser.userLocation == null) return@forEach
            val cellId = S2CellId.fromLatLng(S2LatLng.fromDegrees(promiseUser.userLocation!!.latitude, promiseUser.userLocation!!.longitude))
            indexedPoints[cellId] = S2LatLng.fromDegrees(promiseUser.userLocation!!.latitude, promiseUser.userLocation!!.longitude)
        }
        // 증가하는 거리에 따라 S2 셀 레벨을 탐색하면서 인접한 점들을 참조할 수 있는 상자를 확장합니다.
        var currentLevel = 24 // S2 셀 레벨을 초기화

        // 24Level average area : 0.3 m^2
        // 19Level average area : 309.27 m^2
        // intersect 를 넓혀가면서 찾는다
        while (currentLevel >= 19) {
            var neighbors = arrayListOf<S2CellId>()
            // 상자 크기에 따라 현재 레벨에서 이웃하는 S2 셀 핸들링
            S2RegionCoverer.getSimpleCovering(
                S2LatLngRect.fromCenterSize(
                    referencePoint,
                    S2LatLng.fromDegrees(0.01, 0.01),
                ),
                referencePoint.toPoint(),
                currentLevel,
                neighbors,
            )
            // 찾을 때까지 근접한 S2 셀에 대한 점들을 확인합니다.
            var nearestPoint: S2LatLng? = null
            var minDistanceMeters = Double.MAX_VALUE
            for (neighbor in neighbors) {
                indexedPoints[neighbor]?.let { namedPoint ->
                    val distanceMeters = referencePoint.getDistance(namedPoint).radians() * 6371000.0
                    if (distanceMeters < minDistanceMeters) {
                        nearestPoint = namedPoint
                        minDistanceMeters = distanceMeters
                    }
                }
                currentLevel -= 1
                // 10m 이내인 사람이 있으면 (계산식 상 무조건 1명만 만난다) 이벤트 발행
                if (minDistanceMeters < MEET_RADIUS) {
                    Events.raise(PromiseUserMeetEvent(promiseId, promiseUserUpdateLocationEvent.coordinateVo))
                }
            }
        }
    }
}
