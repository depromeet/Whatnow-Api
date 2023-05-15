package com.depromeet.whatnow.config.swagger

import com.depromeet.whatnow.annotation.ApiErrorCodeExample
import com.depromeet.whatnow.annotation.ApiErrorExceptionsExample
import com.depromeet.whatnow.annotation.DisableSwaggerSecurity
import com.depromeet.whatnow.annotation.ExplainError
import com.depromeet.whatnow.dto.ErrorReason
import com.depromeet.whatnow.dto.ErrorResponse
import com.depromeet.whatnow.example.dto.ExampleHolder
import com.depromeet.whatnow.exception.BaseErrorCode
import com.depromeet.whatnow.exception.WhatnowCodeException
import com.depromeet.whatnow.interfaces.SwaggerExampleExceptions
import com.fasterxml.jackson.databind.ObjectMapper
import io.swagger.v3.core.jackson.ModelResolver
import io.swagger.v3.oas.annotations.tags.Tag
import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.Operation
import io.swagger.v3.oas.models.examples.Example
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.info.License
import io.swagger.v3.oas.models.media.Content
import io.swagger.v3.oas.models.media.MediaType
import io.swagger.v3.oas.models.responses.ApiResponse
import io.swagger.v3.oas.models.responses.ApiResponses
import io.swagger.v3.oas.models.security.SecurityScheme
import io.swagger.v3.oas.models.servers.Server
import org.springdoc.core.customizers.OperationCustomizer
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.method.HandlerMethod
import java.util.function.Consumer
import javax.servlet.ServletContext
import kotlin.reflect.KClass
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.full.findAnnotation

/** Swagger 사용 환경을 위한 설정 파일  */
@Configuration
class SwaggerConfig {
    private val applicationContext: ApplicationContext? = null

    @Bean
    fun openAPI(servletContext: ServletContext): OpenAPI {
        val contextPath = servletContext.contextPath
        val server = Server().url(contextPath)
        return OpenAPI().apply {
            servers = listOf(server)
            components = authSetting()
            info = swaggerInfo()
        }
    }

    private fun swaggerInfo(): Info {
        val license = License()
        license.url = "https://github.com/depromeet/Whatnow-Api"
        license.name = "Whatnow"
        return Info()
            .version("v0.0.1")
            .title("\"Whatnow 서버 API문서\"")
            .description("Whatnow 서버의 API 문서 입니다.")
            .license(license)
    }

    private fun authSetting(): Components {
        return Components()
            .addSecuritySchemes(
                "access-token",
                SecurityScheme()
                    .type(SecurityScheme.Type.HTTP)
                    .scheme("bearer")
                    .bearerFormat("JWT")
                    .`in`(SecurityScheme.In.HEADER)
                    .name("Authorization"),
            )
    }

    @Bean
    fun modelResolver(objectMapper: ObjectMapper?): ModelResolver {
        return ModelResolver(objectMapper)
    }

    @Bean
    fun customize(): OperationCustomizer {
        return OperationCustomizer { operation: Operation, handlerMethod: HandlerMethod ->
            val methodAnnotation: DisableSwaggerSecurity? = handlerMethod.getMethodAnnotation(
                DisableSwaggerSecurity::class.java,
            )
            val apiErrorExceptionsExample: ApiErrorExceptionsExample? = handlerMethod.getMethodAnnotation(
                ApiErrorExceptionsExample::class.java,
            )
            val apiErrorCodeExample: ApiErrorCodeExample? =
                handlerMethod.getMethodAnnotation(ApiErrorCodeExample::class.java)
            val tags = getTags(handlerMethod)
            // DisableSecurity 어노테이션있을시 스웨거 시큐리티 설정 삭제
            if (methodAnnotation != null) {
                operation.security = emptyList()
            }
            // 태그 중복 설정시 제일 구체적인 값만 태그로 설정
            if (!tags.isEmpty()) {
                operation.tags = listOf(tags[0])
            }
            // ApiErrorExceptionsExample 어노테이션 단 메소드 적용
            if (apiErrorExceptionsExample != null) {
                generateExceptionResponseExample(operation, apiErrorExceptionsExample.value.java)
            }
            // ApiErrorCodeExample 어노테이션 단 메소드 적용
            if (apiErrorCodeExample != null) {
                generateErrorCodeResponseExample(operation, apiErrorCodeExample.value)
            }
            operation
        }
    }

    /**
     * BaseErrorCode 타입의 이넘값들을 문서화 시킵니다. ExplainError 어노테이션으로 부가설명을 붙일수있습니다. 필드들을 가져와서 예시 에러 객체를
     * 동적으로 생성해서 예시값으로 붙입니다.
     */
    private fun generateErrorCodeResponseExample(operation: Operation, type: KClass<out BaseErrorCode>) {
        val responses = operation.responses
        val errorCodes = type.java.enumConstants as? Array<out BaseErrorCode> ?: return
        val statusWithExampleHolders: Map<Int?, List<ExampleHolder>> = errorCodes
            .mapNotNull { baseErrorCode ->
                baseErrorCode?.errorReason?.let { errorReason ->
                    try {
                        ExampleHolder.builder()
                            .holder(
                                getSwaggerExample(
                                    baseErrorCode.explainError,
                                    errorReason,
                                ),
                            )
                            .code(errorReason.status)
                            .name(errorReason.code)
                            .build()
                    } catch (e: NoSuchFieldException) {
                        throw RuntimeException(e)
                    }
                }
            }.groupBy(ExampleHolder::code)

        addExamplesToResponses(responses, statusWithExampleHolders)
    }

    /**
     * SwaggerExampleExceptions 타입의 클래스를 문서화 시킵니다.
     * SwaggerExampleExceptions 타입의 클래스는 필드로
     * CodeException 타입을 가지며, CodeException 의 errorReason 와,ExplainError 의 설명을
     * 문서화시킵니다.
     */
    private fun generateExceptionResponseExample(operation: Operation, type: Class<out SwaggerExampleExceptions>) {
        val responses = operation.responses

        // ----------------생성
        val bean = applicationContext!!.getBean(type.javaClass)
        val statusWithExampleHolders: Map<Int?, List<ExampleHolder>> = type.kotlin.declaredMemberProperties
            .filter { it.findAnnotation<ExplainError>() != null }
            .filter { it.returnType.classifier == WhatnowCodeException::class }
            .mapNotNull { field ->
                try {
                    val exception = field.getter.call(bean) as WhatnowCodeException
                    val annotation = field.findAnnotation<ExplainError>()!!
                    val value = annotation.value
                    val errorReason = exception.errorReason
                    ExampleHolder.builder()
                        .holder(getSwaggerExample(value, errorReason))
                        .code(errorReason?.status)
                        .name(field.name)
                        .build()
                } catch (e: IllegalAccessException) {
                    throw RuntimeException(e)
                }
            }
            .groupBy(ExampleHolder::code)

        // -------------------------- 콘텐츠 세팅 코드별로 진행
        addExamplesToResponses(responses, statusWithExampleHolders)
    }

    private fun getSwaggerExample(value: String?, errorReason: ErrorReason?): Example {
        val errorResponse = ErrorResponse.of(errorReason, "요청시 패스정보입니다.")
        val example = Example()
        example.description(value)
        example.value = errorResponse
        return example
    }

    private fun addExamplesToResponses(
        responses: ApiResponses,
        statusWithExampleHolders: Map<Int?, List<ExampleHolder>>,
    ) {
        statusWithExampleHolders.forEach { (status: Int?, v: List<ExampleHolder>) ->
            val content = Content()
            val mediaType = MediaType()
            val apiResponse =
                ApiResponse()
            v.forEach(
                Consumer { exampleHolder: ExampleHolder ->
                    mediaType.addExamples(
                        exampleHolder.name,
                        exampleHolder.holder,
                    )
                },
            )
            content.addMediaType("application/json", mediaType)
            apiResponse.content = content
            responses.addApiResponse(status.toString(), apiResponse)
        }
    }

    companion object {
        private fun getTags(handlerMethod: HandlerMethod): List<String> {
            val tags = mutableListOf<String>()
            val methodTags = handlerMethod.method.annotations.filterIsInstance<Tag>()
            val methodTagStrings = methodTags.map { it.name }
            val classTags = handlerMethod.javaClass.annotations.filterIsInstance<Tag>()
            val classTagStrings = classTags.map { it.name }
            tags.addAll(methodTagStrings)
            tags.addAll(classTagStrings)
            return tags
        }
    }
}
