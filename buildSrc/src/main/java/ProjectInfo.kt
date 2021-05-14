object ProjectInfo {
    /**
     * Android
     */
    const val minSdkVersion = 24
    const val compileSdkVersion = 29
    const val targetSdkVersion = 29

    /**
     * Library Project
     */
    const val versionMajor = 0
    const val versionMinor = 1
    const val versionBuild = 0

    const val versionCode = versionMajor * 1000000 + versionMinor * 10000 + versionBuild
    const val versionName = "$versionMajor.$versionMinor.$versionBuild"

    const val metaLottieMavenName = "meta-lottie"
    const val metaLottieMavenGroup = "com.stash"
    const val metaLottieMavenVersion = versionName
}
