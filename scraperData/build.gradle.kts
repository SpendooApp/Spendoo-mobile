plugins {
    id("spendoo.kmp.feature.data")
}

kotlin {
    android {
        namespace = "org.spendoo.scraper.data"
    }
    
    sourceSets {
        commonMain.dependencies {
            implementation(project(":scraperDomain"))
            implementation("io.ktor:ktor-client-core:3.4.0")
            implementation("com.fleeksoft.ksoup:ksoup:0.2.6")
        }
    }
}
