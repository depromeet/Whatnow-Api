import org.springframework.boot.gradle.tasks.bundling.BootJar

dependencies {
    api("org.springframework.boot:spring-boot-starter-data-jpa")
    api("org.springframework.boot:spring-boot-starter-data-redis")
    api("com.mysql:mysql-connector-j")
    implementation(project(":Whatnow-Infrastructure"))

    // for test profile
    runtimeOnly("com.h2database:h2")
}

tasks.getByName<BootJar>("bootJar") {
    enabled = false
}

tasks.getByName<Jar>("jar") {
    enabled = true
}