import org.jetbrains.compose.resources.ResourcesExtension

plugins {
    id("spendoo.kmp.feature.presentation")
}

compose {
    resources {
        generateResClass = ResourcesExtension.ResourceClassGeneration.Never
    }
}

kotlin {
    android {
        namespace = "com.spendoo.statistics.presentation"
    }

    sourceSets {
        commonMain {
            dependencies {
                implementation(projects.designSystem)
                implementation(projects.statisticsApi)
                implementation(projects.categoriesDomain)
                implementation(projects.identityDomain)
                implementation(projects.categoriesApi)
                implementation(projects.charts)
            }
        }
        androidMain {
            dependencies {
                implementation(libs.androidx.poolingcontainer)
            }
        }
    }
}

dependencies {
    "androidRuntimeClasspath"(libs.compose.ui.tooling)
}
