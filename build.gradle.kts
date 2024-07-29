import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  id("org.springframework.boot") version "3.2.1"
  id("io.spring.dependency-management") version "1.1.4"
  id("org.jlleitschuh.gradle.ktlint") version "12.1.1"
  kotlin("jvm") version "1.9.21"
  kotlin("plugin.spring") version "1.9.21"
}

group = "catchweak"
version = "0.0.1-SNAPSHOT"

val jwtVersion = "0.11.5"

java {
  sourceCompatibility = JavaVersion.VERSION_21
}

repositories {
  mavenCentral()
}

dependencies {
  // spring
  implementation("org.springframework.boot:spring-boot-starter-web")
  implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
  implementation("org.jetbrains.kotlin:kotlin-reflect")
  developmentOnly("org.springframework.boot:spring-boot-devtools")
  implementation("org.springframework.boot:spring-boot-starter-security")

  // batch
  implementation("org.springframework.boot:spring-boot-starter-batch")

  // morpheme analyzer
  // https://mvnrepository.com/artifact/org.openkoreantext/open-korean-text
  implementation("org.openkoreantext:open-korean-text:2.3.1")
   // logger
  // SLF4J and Logback dependencies
  implementation("org.slf4j:slf4j-api:2.0.7")
  testImplementation("ch.qos.logback:logback-classic:1.4.14")

  // jpa
  implementation("org.mariadb.jdbc:mariadb-java-client:2.7.2")
  implementation("org.springframework.boot:spring-boot-starter-data-jpa")

   // db
  runtimeOnly("org.mariadb.jdbc:mariadb-java-client")

  // redis
  implementation("org.springframework.boot:spring-boot-starter-data-redis")
// 	implementation("org.redisson:redisson-spring-boot-starter:3.17.4")

  // elasticsearch
  implementation("org.springframework.boot:spring-boot-starter-data-elasticsearch")

  // jwt
  implementation("io.jsonwebtoken:jjwt-api:$jwtVersion")
  runtimeOnly("io.jsonwebtoken:jjwt-impl:$jwtVersion")
  runtimeOnly("io.jsonwebtoken:jjwt-jackson:$jwtVersion")

  // test
  runtimeOnly("com.h2database:h2")
  testImplementation("org.springframework.security:spring-security-test")
  testImplementation("org.springframework.boot:spring-boot-starter-test")
  testRuntimeOnly("org.junit.platform:junit-platform-launcher")

  // ktlint
  implementation("org.jetbrains.kotlin:kotlin-stdlib")
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

configure<org.jlleitschuh.gradle.ktlint.KtlintExtension> {
  filter {
    exclude {
      it.file.path.startsWith(project.layout.buildDirectory.get().dir("generated").toString())
    }
    exclude("**/*.xml")
  }
}

ktlint {
  outputToConsole.set(true)
}
// react build setting
// def frontendDir = "$projectDir/src/main/frontend"
//
// sourceSets {
// 	main {
// 		resources { srcDirs = ["$projectDir/src/main/resources"]
// 		}
// 	}
// }
//
// processResources { dependsOn "copyReactBuildFiles" }
//
// task installReact(type: Exec) {
// 	workingDir "$frontendDir"
// 	inputs.dir "$frontendDir"
// 	group = BasePlugin.BUILD_GROUP
// 	if (System.getProperty('os.name').toLowerCase(Locale.ROOT).contains('windows')) {
// 		commandLine "npm.cmd", "audit", "fix"
// 		commandLine 'npm.cmd', 'install' }
// 	else {
// 		commandLine "npm", "audit", "fix" commandLine 'npm', 'install'
// 	}
// }
//
// task buildReact(type: Exec) {
// 	dependsOn "installReact"
// 	workingDir "$frontendDir"
// 	inputs.dir "$frontendDir"
// 	group = BasePlugin.BUILD_GROUP
// 	if (System.getProperty('os.name').toLowerCase(Locale.ROOT).contains('windows')) {
// 		commandLine "npm.cmd", "run-script", "build"
// 	} else {
// 		commandLine "npm", "run-script", "build"
// 	}
// }
//
// task copyReactBuildFiles(type: Copy) {
// 	dependsOn "buildReact"
// 	from "$frontendDir/build"
// 	into "$projectDir/src/main/resources/static"
// }
