plugins {
    id("spendoo.kmp.feature.data")
}

kotlin {
    android {
        namespace = "com.spendoo.offers.data"
    }

    listOf(
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "offersDataKit"
        }
    }

    sourceSets {
        commonMain {
            dependencies {
                implementation(projects.offersDomain)
            }
        }
    }
}
