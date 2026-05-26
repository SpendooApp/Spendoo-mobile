import com.android.build.api.dsl.KotlinMultiplatformAndroidLibraryTarget
import com.spendoo.convention.configureKotlinMultiplatform
import com.spendoo.convention.configureIosAppTargets
import com.spendoo.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class KmpApplicationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply(libs.findPlugin("kotlinMultiplatform").get().get().pluginId)
                apply(libs.findPlugin("androidKotlinMultiplatformLibrary").get().get().pluginId)
                apply(libs.findPlugin("composeMultiplatform").get().get().pluginId)
                apply(libs.findPlugin("composeCompiler").get().get().pluginId)
            }

            configureIosAppTargets(
                xcFrameworkName = "SpendooApp",
                frameworkBaseName = "SpendooApp",
                isStaticFramework = true
            )
            configureKotlinMultiplatform(includeIosTargets = false)

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
                "commonMainImplementation"(libs.findLibrary("compose-runtime").get())
                "commonMainImplementation"(libs.findLibrary("compose-foundation").get())
                "commonMainImplementation"(libs.findLibrary("compose-material3").get())
                "commonMainImplementation"(libs.findLibrary("compose-ui").get())
                "commonMainImplementation"(libs.findLibrary("compose-resources").get())
                "commonMainImplementation"(libs.findLibrary("compose-preview").get())
                "commonMainImplementation"(libs.findLibrary("androidx-lifecycle-viewmodelCompose").get())
                "commonMainImplementation"(libs.findLibrary("androidx-lifecycle-runtimeCompose").get())
                "commonMainImplementation"(libs.findBundle("koin").get())

                "androidMainImplementation"(libs.findLibrary("compose-preview").get())
                "androidMainImplementation"(libs.findLibrary("androidx-activity-compose").get())
            }
        }
    }
}
