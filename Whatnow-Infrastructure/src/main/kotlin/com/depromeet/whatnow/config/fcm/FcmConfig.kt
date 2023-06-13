package com.depromeet.whatnow.config.fcm

import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import java.io.ByteArrayInputStream
import java.nio.charset.StandardCharsets
import javax.annotation.PostConstruct

@Configuration
class FcmConfig(
    @Value("\${fcm.certification}")
    private val fcmCertification: String,
) {
    @PostConstruct
    fun init() {
        try {
            if (FirebaseApp.getApps().isEmpty()) {
                val options = FirebaseOptions.builder()
                    .setCredentials(
                        GoogleCredentials.fromStream(
                            ByteArrayInputStream(
                                fcmCertification.toByteArray(StandardCharsets.UTF_8),
                            ),
                        ),
                    )
                    .build()
                FirebaseApp.initializeApp(options)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            throw RuntimeException(e)
        }
    }
}
