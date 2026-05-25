plugins {
    id("spendoo.kmp.feature.api")
}

kotlin {
    androidTarget {
        // compilerOptions inherited if common, but namespace is specific
    }

    listOf(
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "IdentityApi"
            isStatic = true
        }
    }
}

android {
    namespace = "com.spendoo.identity.api"
}
