import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "3.2.1"
	id("io.spring.dependency-management") version "1.1.4"
	kotlin("jvm") version "1.9.21"
	kotlin("plugin.spring") version "1.9.21"
}

group = "catchweak"
version = "0.0.1-SNAPSHOT"

java {
	sourceCompatibility = JavaVersion.VERSION_21
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")

	// spring boot jpa
	implementation("org.mariadb.jdbc:mariadb-java-client:2.7.2")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs += "-Xjsr305=strict"
		jvmTarget = "21"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}

// react build setting
//def frontendDir = "$projectDir/src/main/frontend"
//
//sourceSets {
//	main {
//		resources { srcDirs = ["$projectDir/src/main/resources"]
//		}
//	}
//}
//
//processResources { dependsOn "copyReactBuildFiles" }
//
//task installReact(type: Exec) {
//	workingDir "$frontendDir"
//	inputs.dir "$frontendDir"
//	group = BasePlugin.BUILD_GROUP
//	if (System.getProperty('os.name').toLowerCase(Locale.ROOT).contains('windows')) {
//		commandLine "npm.cmd", "audit", "fix"
//		commandLine 'npm.cmd', 'install' }
//	else {
//		commandLine "npm", "audit", "fix" commandLine 'npm', 'install'
//	}
//}
//
//task buildReact(type: Exec) {
//	dependsOn "installReact"
//	workingDir "$frontendDir"
//	inputs.dir "$frontendDir"
//	group = BasePlugin.BUILD_GROUP
//	if (System.getProperty('os.name').toLowerCase(Locale.ROOT).contains('windows')) {
//		commandLine "npm.cmd", "run-script", "build"
//	} else {
//		commandLine "npm", "run-script", "build"
//	}
//}
//
//task copyReactBuildFiles(type: Copy) {
//	dependsOn "buildReact"
//	from "$frontendDir/build"
//	into "$projectDir/src/main/resources/static"
//}