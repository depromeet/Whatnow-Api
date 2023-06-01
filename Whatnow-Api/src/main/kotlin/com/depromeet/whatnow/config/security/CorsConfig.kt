package band.gosrock.api.config.security

import com.depromeet.whatnow.helper.SpringEnvironmentHelper
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class CorsConfig: WebMvcConfigurer{
    val springEnvironmentHelper: SpringEnvironmentHelper? = null
    override fun addCorsMappings(registry: CorsRegistry) {
        val allowedOriginPatterns = ArrayList<String>()
        allowedOriginPatterns.add("https://dev.whatnow.kr")
//        if (!springEnvironmentHelper!!.isProdProfile) {
//            allowedOriginPatterns.add("http://localhost:3000")
//            allowedOriginPatterns.add("http://localhost:5173")
//        }
        val patterns = allowedOriginPatterns.toTypedArray()
        registry.addMapping("/**")
            .allowedMethods("*")
            .allowedOriginPatterns(*patterns)
            .exposedHeaders("Set-Cookie")
            .allowCredentials(true)
    }
}