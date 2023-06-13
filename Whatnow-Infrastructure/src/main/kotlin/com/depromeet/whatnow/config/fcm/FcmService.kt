package com.depromeet.whatnow.config.fcm

import com.google.api.core.ApiFuture
import com.google.firebase.messaging.*
import org.springframework.stereotype.Service


@Service
class FcmService {
    fun sendGroupMessageAsync(
        tokenList: List<String>, title: String, content: String, imageUrl: String
    ): ApiFuture<BatchResponse> {
        val multicast = MulticastMessage.builder()
            .addAllTokens(tokenList)
            .setNotification(
                Notification.builder()
                    .setTitle(title)
                    .setBody(content)
                    .setImage(imageUrl)
                    .build()
            ).build()
        return FirebaseMessaging.getInstance().sendMulticastAsync(multicast)
    }

    fun sendMessageSync(token: String, content: String) {
        val message = Message.builder()
            .setToken(token)
            .setNotification(Notification.builder().setBody(content).build())
            .build()
        FirebaseMessaging.getInstance().send(message)
    }
}