import org.springframework.boot.gradle.tasks.bundling.BootJar

dependencies {
    api("org.springframework.boot:spring-boot-starter-data-jpa")
    api("org.springframework.boot:spring-boot-starter-data-redis")
//    api("mysql:mysql-connector-java") 이후 정리되면 추가 예정
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