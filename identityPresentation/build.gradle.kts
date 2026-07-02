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
        namespace = "com.spendoo.identity.presentation"
    }

    sourceSets {
        commonMain {
            dependencies {
                implementation(projects.designSystem)
                implementation(projects.identityDomain)
                implementation(projects.identityApi)
                implementation(projects.homeApi)
                implementation(libs.kmpnotifier)
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
