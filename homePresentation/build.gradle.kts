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
            baseName = "HomePresentationKit"
        }
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
            }
        }
    }
}

android {
    namespace = "com.spendoo.home.presentation"
}

dependencies {
    debugImplementation(libs.compose.ui.tooling)
    debugImplementation(libs.androidx.poolingcontainer)
}
