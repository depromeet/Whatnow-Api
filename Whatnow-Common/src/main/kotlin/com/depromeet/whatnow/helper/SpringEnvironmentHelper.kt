package com.depromeet.whatnow.helper

import com.depromeet.whatnow.consts.DEV
import com.depromeet.whatnow.consts.LOCAL
import com.depromeet.whatnow.consts.PROD
import org.springframework.core.env.Environment
import org.springframework.stereotype.Component
import org.springframework.util.CollectionUtils
import java.util.*

@Component
class SpringEnvironmentHelper(
    val environment: Environment,
) {

    private val PROD_AND_DEV = listOf(DEV, PROD)
    val isProdProfile: Boolean
        get() {
            val activeProfiles = environment.activeProfiles.toList()
            return activeProfiles.contains(PROD)
        }
    val isLocalProfile: Boolean
        get() {
            return environment.activeProfiles.contains(LOCAL)
        }
    val isDevProfile: Boolean
        get() {
            return environment.activeProfiles.contains(DEV)
        }
    val isProdAndStagingProfile: Boolean
        get() {
            val activeProfiles = environment.activeProfiles.toList()
            return CollectionUtils.containsAny(PROD_AND_DEV, activeProfiles)
        }
}
