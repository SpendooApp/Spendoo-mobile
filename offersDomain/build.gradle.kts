plugins {
    id("spendoo.kmp.feature.domain")
}

kotlin {
    androidLibrary {
        namespace = "com.spendoo.home.domain"
    }

    listOf(
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "offersDomainKit"
        }
    }

    sourceSets {
        commonMain {
            dependencies {
                api(libs.kotlinx.datetime)
            }
        }
    }
}
