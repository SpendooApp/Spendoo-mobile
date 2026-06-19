import com.android.build.api.dsl.KotlinMultiplatformAndroidLibraryTarget
import com.spendoo.convention.configureKotlinMultiplatform
import com.spendoo.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class KmpFeaturePresentationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply(libs.findPlugin("kotlinMultiplatform").get().get().pluginId)
                apply(libs.findPlugin("androidKotlinMultiplatformLibrary").get().get().pluginId)
                apply(libs.findPlugin("composeMultiplatform").get().get().pluginId)
                apply(libs.findPlugin("composeCompiler").get().get().pluginId)
                apply(libs.findPlugin("kotlinx-serialization").get().get().pluginId)
            }

            configureKotlinMultiplatform()

            extensions.configure<KotlinMultiplatformExtension> {
                targets.withType(KotlinMultiplatformAndroidLibraryTarget::class.java).configureEach {
                    compileSdk = libs.findVersion("android-compileSdk").get().requiredVersion.toInt()
                    minSdk = libs.findVersion("android-minSdk").get().requiredVersion.toInt()

                    androidResources {
                        enable = true
                    }
                }
            }

            dependencies {
                "commonMainImplementation"(libs.findLibrary("kotlin-stdlib").get())
                "commonMainImplementation"(libs.findLibrary("compose-runtime").get())
                "commonMainImplementation"(libs.findLibrary("compose-foundation").get())
                "commonMainImplementation"(libs.findLibrary("compose-ui").get())
                "commonMainImplementation"(libs.findLibrary("compose-material3").get())
                "commonMainImplementation"(libs.findLibrary("compose-resources").get())
                "commonMainImplementation"(libs.findLibrary("compose-preview").get())
                "commonMainImplementation"(libs.findLibrary("kotlinx-datetime").get())
                "commonMainImplementation"(libs.findLibrary("coil-compose").get())
                "commonMainImplementation"(libs.findBundle("koin").get())
                "commonMainImplementation"(libs.findLibrary("koin-compose-navigation3").get())
                "commonMainImplementation"(libs.findLibrary("androidx-navigation-compose").get())
                "commonMainImplementation"(libs.findLibrary("androidx-navigation3-ui").get())
                "commonMainImplementation"(libs.findLibrary("androidx-lifecycle-viewmodel-navigation3").get())

                "androidMainImplementation"(libs.findLibrary("compose-preview").get())
                "androidMainImplementation"(libs.findLibrary("androidx-activity-compose").get())
            }
        }
    }
}
