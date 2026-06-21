rootProject.name = "Spendoo"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    includeBuild("build-logic")
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

include(":spendooApp")
include(":androidApp")
include(":designSystem")
include(":identityData")
include(":identityDomain")
include(":identityPresentation")
include(":homePresentation")
include(":identityApi")
include(":homeApi")
include(":offersDomain")
include(":offersData")
include(":goalsDomain")
include(":goalsData")
include(":categoriesDomain")
include(":categoriesData")
include(":categoriesApi")
include(":categoriesPresentation")
include(":statisticsApi")
include(":statisticsPresentation")
include(":goalsApi")
include(":goalsPresentation")
include(":chatbotApi")
include(":chatbotPresentation")
include(":sharedDomain")
include(":charts")
