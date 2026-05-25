import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.plugin.mpp.apple.XCFramework
import java.util.Properties

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    id("com.google.gms.google-services")
}

val localProperties = Properties()
val localPropertiesFile: File = rootProject.file("local.properties")
if (localPropertiesFile.exists()) {
    localProperties.load(localPropertiesFile.inputStream())
}

val appVersionName =
    project.property("VERSION_MAJOR").toString() + "." + project.property("VERSION_MINOR")
        .toString()

kotlin {
    androidTarget {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
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
        androidMain.dependencies {
            implementation(libs.compose.preview)
            implementation(libs.androidx.activity.compose)
        }
        commonMain.dependencies {
            implementation(libs.compose.runtime)
            implementation(libs.compose.foundation)
            implementation(libs.compose.material3)
            implementation(libs.compose.ui)
            implementation(libs.compose.resources)
            implementation(libs.compose.preview)
            implementation(libs.androidx.lifecycle.viewmodelCompose)
            implementation(libs.androidx.lifecycle.runtimeCompose)
            implementation(libs.bundles.koin)

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
    }
}

android {
    namespace = "com.spendoo"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "com.spendoo"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = appVersionName

        val baseUrl = localProperties.getProperty("BASE_URL", "")
        buildConfigField("String", "BASE_URL", "\"${baseUrl}\"")
    }
    signingConfigs {
        if (project.hasProperty("KEYSTORE_STORE_FILE") || System.getenv("KEYSTORE_STORE_FILE") != null) {
            create("release") {
                val keystorePath = project.loadProperty(
                    path = "local.properties",
                    propertyName = "KEYSTORE_STORE_FILE",
                )

                storeFile = file(keystorePath)
                storePassword = project.loadProperty("local.properties", "KEYSTORE_STORE_PASSWORD")
                keyAlias = project.loadProperty("local.properties", "KEYSTORE_KEY_ALIAS")
                keyPassword = project.loadProperty("local.properties", "KEYSTORE_KEY_PASSWORD")
            }
        }
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            if (signingConfigs.findByName("release") != null) {
                signingConfig = signingConfigs.getByName("release")
            }

            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    debugImplementation(libs.compose.ui.tooling)
    debugImplementation(libs.androidx.poolingcontainer)
}

fun Project.loadProperty(
    path: String,
    propertyName: String,
): String {
    val properties = Properties()
    val propertiesFile = project.rootProject.file(path)

    if (propertiesFile.exists()) {
        properties.load(propertiesFile.inputStream())
        return properties.getProperty(propertyName)
            ?: System.getenv(propertyName)
            ?: throw GradleException("Property '$propertyName' not found in $path or environment")
    } else {
        // Fallback to environment variable for CI/CD
        return System.getenv(propertyName)
            ?: throw GradleException("Property file '$path' not found and '$propertyName' not in environment")
    }
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