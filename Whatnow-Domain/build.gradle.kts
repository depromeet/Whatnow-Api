import org.springframework.boot.gradle.tasks.bundling.BootJar

dependencies {
    api("org.springframework.boot:spring-boot-starter-data-jpa")
    api("org.springframework.boot:spring-boot-starter-data-redis")
    api ("org.springframework.boot:spring-boot-starter-data-mongodb-reactive")
    api("com.mysql:mysql-connector-j")
    implementation(project(":Whatnow-Infrastructure"))
    implementation(project(":Whatnow-Common"))
    api("com.h2database:h2")
}

tasks.getByName<BootJar>("bootJar") {
    enabled = false
}

tasks.getByName<Jar>("jar") {
    enabled = true
}