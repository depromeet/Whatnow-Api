package com.depromeet.whatnow.config.fcm

import com.google.api.core.ApiFuture
import com.google.firebase.messaging.BatchResponse
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.Message
import com.google.firebase.messaging.MulticastMessage
import com.google.firebase.messaging.Notification
import org.springframework.stereotype.Service

@Service
class FcmService {
    fun sendGroupMessageAsync(
        tokenList: List<String>,
        title: String,
        content: String,
        data: MutableMap<String, String>,
    ): ApiFuture<BatchResponse> {
        val multicast = MulticastMessage.builder()
            .addAllTokens(tokenList)
            .setNotification(
                Notification.builder()
                    .setTitle(title)
                    .setBody(content)
                    .build(),
            )
            .putAllData(data)
            .build()
        return FirebaseMessaging.getInstance().sendMulticastAsync(multicast)
    }

    fun sendMessageAsync(
        token: String,
        title: String,
        content: String,
        data: MutableMap<String, String>,
    ): ApiFuture<String> {
        val message = Message.builder()
            .setToken(token)
            .setNotification(
                Notification.builder()
                    .setTitle(title)
                    .setBody(content)
                    .build(),
            )
            .putAllData(data)
            .build()
        return FirebaseMessaging.getInstance().sendAsync(message)
    }
}
