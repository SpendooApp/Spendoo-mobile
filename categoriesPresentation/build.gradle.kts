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
    listOf(
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "CategoriesPresentationKit"
        }
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
    }
}

android {
    namespace = "com.spendoo.categories.presentation"
}

dependencies {
    debugImplementation(libs.compose.ui.tooling)
    debugImplementation(libs.androidx.poolingcontainer)
}
