package com.stash

object ProjectInfo {
    /**
     * Android
     */
    const val minSdkVersion = 24
    const val compileSdkVersion = 33
    const val targetSdkVersion = 33

    /**
     * Library Project
     */
    const val versionMajor = 0
    const val versionMinor = 3
    const val versionBuild = 0

    const val versionCode = versionMajor * 1000000 + versionMinor * 10000 + versionBuild
    const val versionName = "$versionMajor.$versionMinor.$versionBuild"

    const val metaLottieMavenVersion = versionName

    const val metaLottieMavenGroup = "com.stash"
    const val metaLottiePluginMavenName = "meta-lottie-plugin"
    const val metaLottieMavenName = "meta-lottie"
    const val metaLottieComposeMavenName = "meta-lottie-compose"
}
