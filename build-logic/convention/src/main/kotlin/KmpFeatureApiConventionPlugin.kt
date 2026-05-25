import com.android.build.api.dsl.LibraryExtension
import com.spendoo.convention.configureAndroidLibrary
import com.spendoo.convention.configureAndroidTarget
import com.spendoo.convention.configureKotlinMultiplatform
import com.spendoo.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class KmpFeatureApiConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply(libs.findPlugin("kotlinMultiplatform").get().get().pluginId)
                apply(libs.findPlugin("androidLibrary").get().get().pluginId)
                apply(libs.findPlugin("composeMultiplatform").get().get().pluginId)
                apply(libs.findPlugin("composeCompiler").get().get().pluginId)
            }

            configureKotlinMultiplatform()
            configureAndroidTarget()

            extensions.configure<KotlinMultiplatformExtension> {
                jvm()
            }

            extensions.configure<LibraryExtension> {
                configureAndroidLibrary(this)
            }

            dependencies {
                "commonMainImplementation"(libs.findLibrary("compose-runtime").get())
                "commonMainImplementation"(libs.findLibrary("compose-ui").get())
            }
        }
    }
}
