plugins {
    id("spendoo.kmp.feature.data")
}

kotlin {
    androidLibrary {
        namespace = "com.spendoo.goals.data"
    }

    listOf(
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "goalsDataKit"
        }
    }

    sourceSets {
        commonMain {
            dependencies {
                implementation(projects.goalsDomain)
                implementation(projects.identityDomain)
            }
        }
    }
}
