import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.7.11" apply false
    id("io.spring.dependency-management") version "1.0.15.RELEASE" apply false
    kotlin("jvm") version "1.6.21"
    kotlin("plugin.spring") version "1.6.21" apply false
    kotlin("plugin.jpa") version "1.6.21" apply false
    id("com.diffplug.spotless") version  "6.18.0" // spotless
    id("org.sonarqube") version "3.5.0.2730" // 소나 클라우드
    id("jacoco")
}

java.sourceCompatibility = JavaVersion.VERSION_11

sonarqube {
    properties {
        property ("sonar.projectKey", "depromeet_Whatnow-Api")
        property ("sonar.organization", "depromeet-1")
        property ("sonar.host.url", "https://sonarcloud.io")
        property ("sonar.sources", "src")
        property ("sonar.language", "Kotlin")
        property ("sonar.sourceEncoding", "UTF-8")
        property ("sonar.test.inclusions", "**/*Test.java")
        property ("sonar.exclusions", "**/test/**, **/Q*.kt, **/*Doc*.kt, **/resources/** ,**/*Application*.kt , **/*Config*.kt, **/*Dto*.kt, **/*Request*.kt, **/*Response*.kt ,**/*Exception*.kt ,**/*ErrorCode*.kt")
        property ("sonar.java.coveragePlugin", "jacoco")
    }
}


allprojects {
    group = "com.depromeet"
    version = "0.0.1-SNAPSHOT"

    repositories {
        mavenCentral()
    }
    tasks.withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
            jvmTarget = "11"
        }
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }

}

configure<com.diffplug.gradle.spotless.SpotlessExtension> {

    kotlin {
        // version, setUseExperimental, userData and editorConfigOverride are all optional
        target ("**/*.kt")
        ktlint("0.48.0")
    }
}



subprojects{
    apply(plugin = "org.jetbrains.kotlin.jvm")
    apply(plugin = "org.jetbrains.kotlin.plugin.spring")
    apply(plugin = "org.springframework.boot")
    apply(plugin = "io.spring.dependency-management")
    apply(plugin = "jacoco" )


    dependencies {
        implementation("org.springframework.boot:spring-boot-starter-web")
        implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
        implementation("org.jetbrains.kotlin:kotlin-reflect")
        implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
        testImplementation("org.springframework.boot:spring-boot-starter-test")
    }
    tasks.getByName<Jar>("jar") {
        enabled = false
    }


    tasks.test {
        finalizedBy(tasks.jacocoTestReport) // report is always generated after tests run
    }



    tasks.jacocoTestReport {
        dependsOn(tasks.test) // tests are required to run before generating the report
        reports {
            html.required.set(true) // html 설정
            csv.required.set(true) // csv 설정
            xml.required.set(true)
            xml.outputLocation.set(File("${buildDir}/reports/jacoco.xml"))
        }

        classDirectories.setFrom(
                files(classDirectories.files.map {
                    fileTree(it) { // jacoco file 테스트 커버리지 측정안할 목록
                        exclude("**/*Application*",
                                "**/*Config*",
                                "**/*Dto*",
                                "**/*Request*",
                                "**/*Response*",
                                "**/*Interceptor*",
                                "**/*Exception*" ,
                                "**/Q*") // Query Dsl 용이긴하나 Q로 시작하는 다른 클래스를 뺄 위험이 있음.
                    }
                })
        )
    }

    sonarqube {
        properties {
            property ("sonar.java.binaries", "${buildDir}/classes")
            property ("sonar.coverage.jacoco.xmlReportPaths", "${buildDir}/reports/jacoco.xml")
        }
    }

}