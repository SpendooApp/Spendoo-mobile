import com.android.build.api.dsl.ApplicationExtension
import com.spendoo.convention.configureAndroidApplication
import com.spendoo.convention.configureAndroidTarget
import com.spendoo.convention.configureKotlinMultiplatform
import com.spendoo.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

class KmpApplicationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply(libs.findPlugin("kotlinMultiplatform").get().get().pluginId)
                apply(libs.findPlugin("androidApplication").get().get().pluginId)
                apply(libs.findPlugin("composeMultiplatform").get().get().pluginId)
                apply(libs.findPlugin("composeCompiler").get().get().pluginId)
            }

            configureKotlinMultiplatform()
            configureAndroidTarget()

            extensions.configure<ApplicationExtension> {
                configureAndroidApplication(this)
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
