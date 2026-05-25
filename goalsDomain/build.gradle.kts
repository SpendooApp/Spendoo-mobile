plugins {
    id("spendoo.kmp.feature.domain")
}

kotlin {
    androidLibrary {
        namespace = "com.spendoo.goals.domain"
    }

    listOf(
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "goalsDomainKit"
        }
    }

    sourceSets {
        commonMain {
            dependencies {
                api(libs.kotlinx.datetime)
                api(projects.categoriesDomain)
            }
        }
    }
}
