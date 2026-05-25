plugins {
    id("spendoo.kmp.feature.domain")
}

kotlin {
    androidLibrary {
        namespace = "com.spendoo.categories.domain"
    }

    listOf(
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "categoriesDomainKit"
        }
    }
}
