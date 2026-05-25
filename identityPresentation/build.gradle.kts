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
            baseName = "IdentityPresentationKit"
        }
    }

    sourceSets {
        commonMain {
            dependencies {
                implementation(projects.designSystem)
                implementation(projects.identityDomain)
                implementation(projects.identityApi)
                implementation(projects.homeApi)
            }
        }
    }
}

android {
    namespace = "com.spendoo.identity.presentation"
}

dependencies {
    debugImplementation(libs.compose.ui.tooling)
    debugImplementation(libs.androidx.poolingcontainer)
}
