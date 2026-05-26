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
        namespace = "com.spendoo.categories.presentation"
    }

    sourceSets {
        commonMain {
            dependencies {
                implementation(projects.designSystem)
                implementation(projects.categoriesApi)
                implementation(projects.categoriesDomain)
                implementation(projects.goalsDomain)

                implementation(libs.filekit.compose)
                implementation(libs.filekit.core)
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
