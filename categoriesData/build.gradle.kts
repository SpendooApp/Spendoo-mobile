plugins {
    id("spendoo.kmp.feature.data")
}

kotlin {
    androidLibrary {
        namespace = "com.spendoo.categories.data"
    }

    listOf(
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "categoriesDataKit"
        }
    }

    sourceSets {
        commonMain {
            dependencies {
                implementation(projects.categoriesDomain)
                implementation(projects.identityDomain)
            }
        }
    }
}
