import com.android.build.api.dsl.KotlinMultiplatformAndroidLibraryTarget
import com.spendoo.convention.configureKotlinMultiplatform
import com.spendoo.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class KmpFeatureDataConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply(libs.findPlugin("kotlinMultiplatform").get().get().pluginId)
                apply(libs.findPlugin("androidKotlinMultiplatformLibrary").get().get().pluginId)
                apply(libs.findPlugin("androidLint").get().get().pluginId)
                apply(libs.findPlugin("kotlinx-serialization").get().get().pluginId)
            }

            configureKotlinMultiplatform()

            extensions.configure<KotlinMultiplatformExtension> {
                targets.withType(KotlinMultiplatformAndroidLibraryTarget::class.java).configureEach {
                    compileSdk = libs.findVersion("android-compileSdk").get().requiredVersion.toInt()
                    minSdk = libs.findVersion("android-minSdk").get().requiredVersion.toInt()

                    withDeviceTestBuilder {
                        sourceSetTreeName = "test"
                    }.configure {
                        instrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
                    }
                }
            }

            dependencies {
                "commonMainImplementation"(libs.findLibrary("kotlin-stdlib").get())
                "commonMainImplementation"(libs.findLibrary("kotlinx-coroutines-core").get())
                "commonMainImplementation"(libs.findLibrary("kotlinx-serialization-json").get())
                "commonMainImplementation"(libs.findBundle("ktor").get())
                "commonMainImplementation"(libs.findLibrary("koin-core").get())
                "commonMainImplementation"(libs.findLibrary("multiplatform-settings").get())
                "commonMainImplementation"(libs.findLibrary("kotlinx-datetime").get())

                "androidMainImplementation"(libs.findLibrary("koin-android").get())
                "androidMainImplementation"(libs.findLibrary("ktor-client-okhttp").get())

                libs.findLibrary("ktor-client-darwin").ifPresent {
                    "iosMainImplementation"(it)
                }
            }
        }
    }
}
