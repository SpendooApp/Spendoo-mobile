package com.spendoo.convention

import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.mpp.apple.XCFramework

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

internal fun Project.configureIosAppTargets(
    xcFrameworkName: String,
    frameworkBaseName: String,
    isStaticFramework: Boolean
) {
    if (!org.jetbrains.kotlin.konan.target.HostManager.hostIsMac) {
        return
    }

    val xcf = XCFramework(xcFrameworkName)
    extensions.configure<KotlinMultiplatformExtension> {
        listOf(
            iosX64(),
            iosArm64(),
            iosSimulatorArm64()
        ).forEach { iosTarget ->
            iosTarget.binaries.framework {
                baseName = frameworkBaseName
                isStatic = isStaticFramework
                xcf.add(this)
            }
        }
    }
}
