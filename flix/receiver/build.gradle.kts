plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization")
    id("tz.co.asoft.library")
}

kotlin {
    jvm { library() }
    js(IR) { library(testTimeout = 60000) }

    sourceSets {
        val commonMain by getting {
            dependencies {
                api(libs.raven.flix.receiver)
                api(libs.koncurrent.later.coroutines)
                api(ktor.client.core)
            }
        }

        val commonTest by getting {
            dependencies {
                implementation(libs.kommander.coroutines)
            }
        }

        val jvmTest by getting {
            dependencies {
                implementation(ktor.client.cio)
            }
        }
    }
}
