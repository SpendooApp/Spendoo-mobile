plugins {
    id("spendoo.kmp.feature.domain")
}

kotlin {
    android {
        namespace = "com.spendoo.identity.domain"
    }

    listOf(
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "identityDomainKit"
        }
    }
}
