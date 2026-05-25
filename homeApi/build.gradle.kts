plugins {
    id("spendoo.kmp.feature.api")
}

kotlin {
    listOf(
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "HomeApi"
            isStatic = true
        }
    }
}

android {
    namespace = "com.spendoo.home.api"
}
