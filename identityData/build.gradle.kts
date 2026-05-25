plugins {
    id("spendoo.kmp.feature.data")
}

kotlin {
    androidLibrary {
        namespace = "com.spendoo.identity.data"
    }

    listOf(
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "identityDataKit"
        }
    }

    sourceSets {
        commonMain {
            dependencies {
                implementation(projects.identityDomain)
            }
        }
    }
}
