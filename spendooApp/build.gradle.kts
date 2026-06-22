import java.util.Properties

plugins {
    id("spendoo.kmp.application")
}

kotlin {
    android {
        namespace = "com.spendoo.library"
    }


    sourceSets {
        commonMain.dependencies {
            implementation(libs.androidx.navigation3.ui)
            implementation(libs.androidx.lifecycle.viewmodel.navigation3)
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
            implementation(projects.statisticsApi)
            implementation(projects.statisticsPresentation)
            implementation(projects.statisticsDomain)
            implementation(projects.statisticsData)
            implementation(projects.goalsApi)
            implementation(projects.goalsPresentation)
            implementation(projects.chatbotApi)
            implementation(projects.chatbotPresentation)
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
    val localPropsFile = rootProject.layout.projectDirectory.file("local.properties")
    val configFile = layout.projectDirectory.file("../iosApp/Configuration/Config.xcconfig")
    inputs.file(localPropsFile).optional()
    outputs.file(configFile)

    doLast {
        val properties = Properties()
        val localProps = localPropsFile.asFile
        if (localProps.exists()) {
            localProps.inputStream().use { properties.load(it) }
        }

        val baseUrl = properties.getProperty("BASE_URL").orEmpty()
        val escapedUrl = baseUrl.replace("//", "/$()/")
        val configFileOnDisk = configFile.asFile

        if (configFileOnDisk.exists()) {
            val lines = configFileOnDisk.readLines().toMutableList()
            val index = lines.indexOfFirst { it.startsWith("BASE_URL") }
            val newLine = "BASE_URL = $escapedUrl"

            if (index != -1) {
                lines[index] = newLine
            } else {
                lines.add(newLine)
            }
            configFileOnDisk.writeText(lines.joinToString("\n"))
        }
    }
}

tasks.matching { it.name.contains("Framework") }.configureEach {
    dependsOn("syncIosConfig")
}
