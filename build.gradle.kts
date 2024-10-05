import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  id("org.springframework.boot") version libs.versions.springBoot.get()
  id("io.spring.dependency-management") version libs.versions.dependencyManagement.get()
//  alias(libs.plugins.ktlint)
  alias(libs.plugins.kotlin.jvm)
  kotlin("plugin.spring") version libs.versions.kotlin.get()
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
  // kotlin
  implementation(libs.kotlin)
  implementation(libs.stdlib)

  // Spring
  implementation(libs.web)
  implementation(libs.webflux)
  developmentOnly(libs.devtools)
  implementation(libs.security)
  implementation(libs.batch)
  implementation(libs.jackson)

  // Morpheme Analyzer
  implementation(libs.openkoreantext)

  // Logger
  implementation(libs.slf4j.api)
  testImplementation(libs.logback.classic)

  // JPA
  implementation(libs.jpa)

  // Database
  runtimeOnly(libs.mariadb)

  // Redis
  implementation(libs.redis)

  // Elasticsearch
  implementation(libs.elasticsearch)
//  implementation(libs.elasticsearchJavaClient)

  // mongodb
  implementation(libs.mongodb)

  // jwt
  implementation(libs.jjwt.api)
  runtimeOnly(libs.jjwt.impl)
  runtimeOnly(libs.jjwt.jackson)

  // Test
  runtimeOnly(libs.h2)
  testImplementation(libs.spring.test)
  testImplementation(libs.security.test)
  testRuntimeOnly(libs.junit)

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

//configure<org.jlleitschuh.gradle.ktlint.KtlintExtension> {
//  filter {
//    exclude {
//      it.file.path.startsWith(project.layout.buildDirectory.get().dir("generated").toString())
//    }
//    exclude("**/*.xml")
//  }
//}
//
//ktlint {
//  outputToConsole.set(true)
//}
