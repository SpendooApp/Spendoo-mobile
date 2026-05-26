package com.spendoo.convention

import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

internal fun Project.configureIosTargets() {
    if (!org.jetbrains.kotlin.konan.target.HostManager.hostIsMac) {
        return
    }

    extensions.configure<KotlinMultiplatformExtension> {
        listOf(
            iosX64(),
            iosArm64(),
            iosSimulatorArm64()
        ).forEach { iosTarget ->
            iosTarget.binaries.framework {
                baseName = this@configureIosTargets.pathToFrameworkName()
            }
        }
    }
}
