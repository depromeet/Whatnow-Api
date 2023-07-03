import org.springframework.boot.gradle.tasks.bundling.BootJar


dependencies{
    api ("io.github.openfeign:feign-httpclient:12.1")
    api ("org.springframework.cloud:spring-cloud-starter-openfeign:3.1.4")
    api ("io.github.openfeign:feign-jackson:12.1")
    api ("org.springframework.boot:spring-boot-starter-data-redis")
    api ("org.redisson:redisson:3.19.0")
    api ("com.amazonaws:aws-java-sdk-s3:1.12.476")
    api ("com.google.firebase:firebase-admin:9.1.1")
    // slack
    api ("com.slack.api:slack-api-client:1.27.2")

    // bucket
    api ("com.bucket4j:bucket4j-core:8.1.1")
    api ("com.bucket4j:bucket4j-jcache:8.1.1")

    implementation(project(":Whatnow-Common"))

    // mongoDB
    api ("org.springframework.boot:spring-boot-starter-data-mongodb-reactive")

    testImplementation ("org.springframework.cloud:spring-cloud-starter-contract-stub-runner:3.1.5")
    testImplementation ("org.springframework.cloud:spring-cloud-contract-wiremock:3.1.5")
}


tasks.getByName<BootJar>("bootJar") {
    enabled = false
}

tasks.getByName<Jar>("jar") {
    enabled = true
}