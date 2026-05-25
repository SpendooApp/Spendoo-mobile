package com.spendoo.convention

import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

internal fun Project.configureKotlinMultiplatform() {
    configureIosTargets()
    extensions.configure<KotlinMultiplatformExtension> {
        applyHierarchyTemplate()
    }
}

