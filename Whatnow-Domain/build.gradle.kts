import org.springframework.boot.gradle.tasks.bundling.BootJar

dependencies {
    api("org.springframework.boot:spring-boot-starter-data-jpa")
    api("org.springframework.boot:spring-boot-starter-data-redis")
    api("com.mysql:mysql-connector-j")
    implementation("com.google.geometry:s2-geometry:2.0.0")
    implementation(project(":Whatnow-Infrastructure"))
    implementation(project(":Whatnow-Common"))
    api("com.h2database:h2")
    // https://mvnrepository.com/artifact/ch.hsr/geohash

}

tasks.getByName<BootJar>("bootJar") {
    enabled = false
}

tasks.getByName<Jar>("jar") {
    enabled = true
}