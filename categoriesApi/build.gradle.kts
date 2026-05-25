plugins {
    id("spendoo.kmp.feature.api")
}

kotlin {
    android {
        namespace = "com.spendoo.categories.api"
    }

    listOf(
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "CategoriesApi"
            isStatic = true
        }
    }
}
