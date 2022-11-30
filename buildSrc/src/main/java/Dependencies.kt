/**
 * Gradle dependencies used within this library
 *
 */
object Dependencies {
    /**
     * Dependency Version
     */
    const val androidBuildToolsVersion = "7.2.2"
    const val androidxAnnotationVersion = "1.2.0"
    const val androidxAppcompatVersion = "1.5.1"
    const val androidxCollectionVersion = "1.1.0"
    const val androidxComposeActivityVersion = "1.6.1"
    const val androidxCoreKtxVersion = "1.5.0"
    const val androidxNavigationVersion = "2.5.3"
    const val composeBomVersion = "2022.10.00"
    const val junitVersion = "5.8.2"
    const val kotlinCoroutinesVersion = "1.4.2"
    const val kotlinPoetVersion = "1.12.0"
    const val kotlinxSerializationVersion = "1.1.0"
    const val kotlinVersion = "1.7.20"
    const val materialVersion = "1.7.0"
    const val moshiVersion = "1.14.0"

    /**
     * App Dependency Version
     */
    const val lottieVersion = "5.2.0"

    /**
     * Internal
     */
    val metaLottie = ":meta-lottie"
    val metaLottieCompose = ":meta-lottie-compose"

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
    val material = "com.google.android.material:material:$materialVersion"
    val androidxNavigationFragment =
        "androidx.navigation:navigation-fragment-ktx:$androidxNavigationVersion"
    val androidxNavigationUi = "androidx.navigation:navigation-ui-ktx:$androidxNavigationVersion"

    /**
     * Serialization
     */
    val moshiKotlin = "com.squareup.moshi:moshi-kotlin:$moshiVersion"
    val moshiAdapters = "com.squareup.moshi:moshi-adapters:$moshiVersion"
    val moshiCodeGen = "com.squareup.moshi:moshi-kotlin-codegen:$moshiVersion"
    val kotlinxSerialization =
        "org.jetbrains.kotlinx:kotlinx-serialization-json:$kotlinxSerializationVersion"
    val kotlinxSerializationPlugin = "org.jetbrains.kotlin:kotlin-serialization:$kotlinVersion"

    /**
     * Testing
     */
    val junit = "org.junit.jupiter:junit-jupiter:$junitVersion"

    /**
     * App Libs
     */
    val lottie = "com.airbnb.android:lottie:$lottieVersion"
    val lottieCompose = "com.airbnb.android:lottie-compose:$lottieVersion"

    /**
     * Compose
     */
    val composeBom = "androidx.compose:compose-bom:$composeBomVersion"
    val composeUi = "androidx.compose.ui:ui"
    val composeMaterial = "androidx.compose.material:material"
    val composePreview = "androidx.compose.ui:ui-tooling-preview"
    val composeActivity = "androidx.activity:activity-compose:$androidxComposeActivityVersion"

    /**
     * Codegen
     */
    val kotlinPoet = "com.squareup:kotlinpoet:$kotlinPoetVersion"
}
