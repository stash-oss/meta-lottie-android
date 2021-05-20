/**
 * Gradle dependencies used within this library
 *
 */
object Dependencies {
    /**
     * Dependency Version
     */
    const val androidBuildToolsVersion = "4.2.1"
    const val androidxAnnotationVersion = "1.2.0"
    const val androidxAppcompatVersion ="1.3.0"
    const val androidxCollectionVersion = "1.1.0"
    const val androidxCoreKtxVersion = "1.5.0"
    const val junitVersion = "4.12"
    const val kotlinCoroutinesVersion = "1.4.2"
    const val kotlinVersion = "1.5.0"
    const val kotlinxSerializationVersion = "1.1.0"
    const val moshiVersion = "1.12.0"

    /**
     * App Dependency Version
     */
    const val lottieVersion = "3.7.0"

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
    val kotlinCoroutines =
        "org.jetbrains.kotlinx:kotlinx-coroutines-android:$kotlinCoroutinesVersion"

    /**
     * Android
     */
    val androidxAnnotation = "androidx.annotation:annotation:$androidxAnnotationVersion"
    val androidxAppcompat = "androidx.appcompat:appcompat:$androidxAppcompatVersion"
    val androidxCollection = "androidx.collection:collection:$androidxCollectionVersion"
    val androidxCoreKtx = "androidx.core:core-ktx:$androidxCoreKtxVersion"

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
