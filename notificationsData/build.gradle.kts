plugins {
    id("spendoo.kmp.feature.data")
}

kotlin {
    android {
        namespace = "com.spendoo.notifications.data"
    }

    sourceSets {
        commonMain {
            dependencies {
                implementation(projects.notificationsDomain)
                implementation(projects.sharedData)
            }
        }
    }
}
