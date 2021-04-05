/**
 * Gradle dependencies used within this library
 *
 */
object Dependencies {
    /**
     * Dependency Version
     */
    const val androidBuildToolsVersion = "4.1.0"
    const val kotlinVersion = "1.3.72"
    const val kotlinCoroutinesVersion = "1.4.2"
    const val androidNavigationVersion = "2.3.0"
    const val detektVersion = "1.8.0"
    const val ktlintVersion = "0.36.0"
    const val androidLintVersion = "26.6.0"
    const val junitVersion = "4.12"
    const val moshiVersion = "1.9.3"

    /**
     * App Dependency Version
     */
    const val lottieVersion = "3.5.0"

    /**
     * Internal
     */
    val metaLottie = ":meta-lottie"

    /**
     * Gradle Plugins
     */
    val androidBuildTools = "com.android.tools.build:gradle:$androidBuildToolsVersion"
    val kotlinGradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion"
    val detektGradlePlugin = "io.gitlab.arturbosch.detekt:detekt-gradle-plugin:$detektVersion"

    /**
     * Kotlin
     */
    val kotlinStdlib = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlinVersion"
    val kotlinReflect = "org.jetbrains.kotlin:kotlin-reflect:$kotlinVersion"
    val kotlinCoroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-android:$kotlinCoroutinesVersion"

    /**
     * Android
     */
    val androidMaterial = "com.google.android.material:material:1.2.0"
    val androidxAppcompat = "androidx.appcompat:appcompat:1.2.0"
    val androidxConstraintLayout = "androidx.constraintlayout:constraintlayout:2.0.0-rc1"
    val androidxCoreKtx = "androidx.core:core-ktx:1.1.0"
    val androidNavFragment = "androidx.navigation:navigation-fragment-ktx:$androidNavigationVersion"
    val androidNavUi = "androidx.navigation:navigation-ui-ktx:$androidNavigationVersion"
    val androidLintApi = "com.android.tools.lint:lint-api:$androidLintVersion"
    val androidLint = "com.android.tools.lint:lint:$androidLintVersion"
    val androidLintTests = "com.android.tools.lint:lint-tests:$androidLintVersion"
    val detektRules = ":static-analysis:detekt:rules"

    /**
     * Serialization
     */
    val moshiKotlin = "com.squareup.moshi:moshi-kotlin:$moshiVersion"
    val moshiAdapters = "com.squareup.moshi:moshi-adapters:$moshiVersion"
    val moshiCodeGen = "com.squareup.moshi:moshi-kotlin-codegen:$moshiVersion"

    /**
     * Testing
     */
    val junit = "junit:junit:$junitVersion"

    /**
     * App Libs
     */
    val lottie = "com.airbnb.android:lottie:$lottieVersion"
}
