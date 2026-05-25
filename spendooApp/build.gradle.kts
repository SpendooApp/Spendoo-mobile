import org.jetbrains.kotlin.gradle.plugin.mpp.apple.XCFramework
import java.util.Properties

plugins {
    id("spendoo.kmp.application")
}

val localProperties = Properties()
val localPropertiesFile: File = rootProject.file("local.properties")
if (localPropertiesFile.exists()) {
    localProperties.load(localPropertiesFile.inputStream())
}

kotlin {
    // android {} block for the new KMP library plugin
    android {
        namespace = "com.spendoo.library" // Different from androidApp
    }

    val xcf = XCFramework("SpendooApp")
    listOf(
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "SpendooApp"
            isStatic = true
            xcf.add(this)
        }
    }

    sourceSets {
        commonMain.dependencies {
            implementation(projects.designSystem)
            implementation(projects.identityData)
            implementation(projects.identityDomain)
            implementation(projects.identityPresentation)
            implementation(projects.identityApi)
            implementation(projects.homeApi)
            implementation(projects.homePresentation)
            implementation(projects.offersData)
            implementation(projects.goalsData)
            implementation(projects.categoriesDomain)
            implementation(projects.categoriesData)
            implementation(projects.categoriesApi)
            implementation(projects.categoriesPresentation)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
        androidMain.dependencies {
            implementation(libs.androidx.poolingcontainer)
        }
    }
}

dependencies {
    "androidRuntimeClasspath"(libs.compose.ui.tooling)
}

tasks.register("syncIosConfig") {
    group = "ios"
    doLast {
        val baseUrl = localProperties.getProperty("BASE_URL") ?: ""
        val escapedUrl = baseUrl.replace("//", "/$()/")
        val configFile = file("../iosApp/Configuration/Config.xcconfig")

        if (configFile.exists()) {
            val lines = configFile.readLines().toMutableList()
            val index = lines.indexOfFirst { it.startsWith("BASE_URL") }
            val newLine = "BASE_URL = $escapedUrl"

            if (index != -1) {
                lines[index] = newLine
            } else {
                lines.add(newLine)
            }
            configFile.writeText(lines.joinToString("\n"))
        }
    }
}

tasks.matching { it.name.contains("Framework") }.configureEach {
    dependsOn("syncIosConfig")
}
