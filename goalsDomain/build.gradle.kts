plugins {
    id("spendoo.kmp.feature.domain")
}

kotlin {
    android {
        namespace = "com.spendoo.goals.domain"
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
