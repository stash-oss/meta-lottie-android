plugins {
    kotlin("jvm")
    `java-library`
}

dependencies {
    compileOnly(Dependencies.kotlinStdlib)
    compileOnly(Dependencies.androidLintApi)
    testImplementation(Dependencies.androidLint)
    testImplementation(Dependencies.androidLintTests)
}

val jar by tasks.getting(Jar::class) {
    manifest {
    }
}