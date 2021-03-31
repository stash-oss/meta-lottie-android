import Dependencies.detektVersion
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

buildscript {
    repositories {
        mavenCentral()
        google()
        jcenter()
    }
}

repositories {
    mavenCentral()
    google()
    jcenter()
}

plugins {
    kotlin("jvm")
    `java-library`
}

dependencies {
    implementation(Dependencies.kotlinStdlib)
    implementation(Dependencies.kotlinReflect)

    implementation("io.gitlab.arturbosch.detekt:detekt-api:$detektVersion")

    testImplementation("io.gitlab.arturbosch.detekt:detekt-test:$detektVersion")
    testImplementation(Dependencies.junit)
}

val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions {
    jvmTarget = "1.8"
}
val compileTestKotlin: KotlinCompile by tasks
compileTestKotlin.kotlinOptions {
    jvmTarget = "1.8"
}
