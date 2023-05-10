package com.depromeet.whatnow.config

import org.springframework.test.context.ActiveProfilesResolver

class ApiIntegrateProfileResovler : ActiveProfilesResolver {

    override fun resolve(testClass: Class<*>): Array<String> {
        return arrayOf("infrastructure", "domain")
    }
}
