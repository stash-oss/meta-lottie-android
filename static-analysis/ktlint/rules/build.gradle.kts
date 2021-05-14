import Dependencies.ktlintVersion

plugins {
    kotlin("jvm")
    `java-library`
}

dependencies {
    implementation(Dependencies.kotlinStdlib)
    implementation("com.pinterest:ktlint:$ktlintVersion")
    implementation("com.pinterest.ktlint:ktlint-core:$ktlintVersion")
    // For access to the NoUnusedImportsRule to format the file after removing injects
    implementation("com.pinterest.ktlint:ktlint-ruleset-standard:$ktlintVersion")
    implementation("com.pinterest.ktlint:ktlint-test:$ktlintVersion")

    testImplementation(Dependencies.junit)
}
