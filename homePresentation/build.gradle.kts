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
        namespace = "com.spendoo.home.presentation"
    }

    sourceSets {
        commonMain {
            dependencies {
                implementation(projects.designSystem)
                implementation(projects.homeApi)
                implementation(projects.offersDomain)

                implementation(libs.coil.network.ktor)
                implementation(libs.coil.svg)

                implementation(projects.categoriesDomain)
                implementation(projects.identityDomain)
                implementation(projects.goalsDomain)
                implementation(projects.goalsApi)
                implementation(projects.sharedDomain)
            }
        }
        androidMain {
            dependencies {
                implementation(libs.androidx.poolingcontainer)
                // runtimeClasspath is used for compose tooling
                // However, since it's a library, we might use androidRuntimeClasspath
            }
        }
    }
}

dependencies {
    "androidRuntimeClasspath"(libs.compose.ui.tooling)
}
