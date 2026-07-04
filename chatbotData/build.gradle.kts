plugins {
    id("spendoo.kmp.feature.data")
}

kotlin {
    android {
        namespace = "com.spendoo.chatbot.data"
    }

    sourceSets {
        commonMain {
            dependencies {
                implementation(projects.chatbotDomain)
                implementation(projects.sharedData)
            }
        }
    }
}
