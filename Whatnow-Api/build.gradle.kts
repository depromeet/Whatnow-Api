dependencies {
    implementation(project(":Whatnow-Domain"))
    implementation(project(":Whatnow-Infrastructure"))
    implementation(project(":Whatnow-Common"))

    implementation ("io.jsonwebtoken:jjwt-api:0.11.5")
    runtimeOnly ( "io.jsonwebtoken:jjwt-impl:0.11.5")
    runtimeOnly ( "io.jsonwebtoken:jjwt-jackson:0.11.5")

    implementation("org.springdoc:springdoc-openapi-ui:1.6.12")
    implementation("org.springframework.boot:spring-boot-starter-security")
    testImplementation("org.springframework.security:spring-security-test")
}