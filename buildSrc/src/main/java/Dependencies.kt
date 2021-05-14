/**
 * Gradle dependencies used within this library
 *
 */
object Dependencies {
    /**
     * Dependency Version
     */
    const val androidBuildToolsVersion = "4.1.0"
    const val kotlinVersion = "1.5.0"
    const val kotlinCoroutinesVersion = "1.5.0"
    const val kotlinxSerializationVersion = "1.1.0"
    const val androidNavigationVersion = "2.3.0"
    const val junitVersion = "4.12"
    const val moshiVersion = "1.12.0"

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

    /**
     * Kotlin
     */
    val kotlinStdlib = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlinVersion"
    val kotlinReflect = "org.jetbrains.kotlin:kotlin-reflect:$kotlinVersion"
    val kotlinCoroutines =
        "org.jetbrains.kotlinx:kotlinx-coroutines-android:$kotlinCoroutinesVersion"

    /**
     * Android
     */
    val androidMaterial = "com.google.android.material:material:1.2.0"
    val androidxAppcompat = "androidx.appcompat:appcompat:1.2.0"
    val androidxConstraintLayout = "androidx.constraintlayout:constraintlayout:2.0.0-rc1"
    val androidxCoreKtx = "androidx.core:core-ktx:1.3.2"
    val androidNavFragment = "androidx.navigation:navigation-fragment-ktx:$androidNavigationVersion"
    val androidNavUi = "androidx.navigation:navigation-ui-ktx:$androidNavigationVersion"

    /**
     * Serialization
     */
    val moshiKotlin = "com.squareup.moshi:moshi-kotlin:$moshiVersion"
    val moshiAdapters = "com.squareup.moshi:moshi-adapters:$moshiVersion"
    val moshiCodeGen = "com.squareup.moshi:moshi-kotlin-codegen:$moshiVersion"
    val kotlinxSerialization = "org.jetbrains.kotlinx:kotlinx-serialization-json:$kotlinxSerializationVersion"
    val kotlinxSerializationPlugin = "org.jetbrains.kotlin:kotlin-serialization:$kotlinVersion"

    /**
     * Testing
     */
    val junit = "junit:junit:$junitVersion"

    /**
     * App Libs
     */
    val lottie = "com.airbnb.android:lottie:$lottieVersion"
}
