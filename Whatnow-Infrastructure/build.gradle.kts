import org.springframework.boot.gradle.tasks.bundling.BootJar


dependencies{
    api ("io.github.openfeign:feign-httpclient:12.1")
    api ("org.springframework.cloud:spring-cloud-starter-openfeign:3.1.4")
    api ("io.github.openfeign:feign-jackson:12.1")
    api ("org.springframework.boot:spring-boot-starter-data-redis")
    api ("org.redisson:redisson:3.19.0")


    testImplementation ("org.springframework.cloud:spring-cloud-starter-contract-stub-runner:3.1.5")
    testImplementation ("org.springframework.cloud:spring-cloud-contract-wiremock:3.1.5")
}


tasks.getByName<BootJar>("bootJar") {
    enabled = false
}

tasks.getByName<Jar>("jar") {
    enabled = true
}