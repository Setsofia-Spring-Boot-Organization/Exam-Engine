plugins {
	id("org.springframework.boot") version "3.3.0"
	id("io.spring.dependency-management") version "1.1.5"
	kotlin("plugin.jpa") version "1.9.24"
	kotlin("jvm") version "1.9.24" // Update to match plugin versions
	kotlin("plugin.spring") version "1.9.24"
	kotlin("plugin.serialization") version "1.9.24" // Update to match plugin versions
}

group = "com.examengine"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion.set(JavaLanguageVersion.of(21))
	}
}

tasks.withType<Jar> {
	archiveBaseName.set("Exam_engine")
	archiveVersion.set("0.0.1-SNAPSHOT")
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-oauth2-client:3.3.1")
	implementation("org.springframework.boot:spring-boot-starter-mail")
	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-websocket")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.springframework.boot:spring-boot-starter-data-mongodb:3.3.0")
	compileOnly("org.projectlombok:lombok")
	developmentOnly("org.springframework.boot:spring-boot-devtools")
	annotationProcessor("org.projectlombok:lombok")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
	testImplementation("org.springframework.security:spring-security-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")

	implementation("io.jsonwebtoken:jjwt-api:0.12.5")
	runtimeOnly("io.jsonwebtoken:jjwt-impl:0.12.5")
	runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.12.5")
	implementation("io.jsonwebtoken:jjwt:0.12.5")

	implementation(platform("io.github.jan-tennert.supabase:bom:2.4.2"))
	implementation("io.github.jan-tennert.supabase:realtime-kt")
	implementation("io.github.jan-tennert.supabase:postgrest-kt:2.4.2")
	implementation("io.github.jan-tennert.supabase:storage-kt:2.4.2")
	implementation("io.github.jan-tennert.supabase:gotrue-kt:2.4.2")

	implementation(kotlin("stdlib"))
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.1") // Updated version
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor:1.7.1") // Updated version
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactive:1.7.1") // Updated version
	implementation("org.reactivestreams:reactive-streams:1.0.3")

	// Ktor HTTP client dependencies
	implementation("io.ktor:ktor-client-core:2.3.1")
	implementation("io.ktor:ktor-client-cio:2.3.1")

	// https://mvnrepository.com/artifact/org.thymeleaf/thymeleaf
	implementation("org.thymeleaf:thymeleaf:3.1.2.RELEASE")
	implementation("org.springframework.boot:spring-boot-starter-thymeleaf")

	// https://mvnrepository.com/artifact/org.springframework/spring-messaging
	implementation("org.springframework:spring-messaging:6.1.11")

}

kotlin {
	compilerOptions {
		freeCompilerArgs.addAll("-Xjsr305=strict")
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
