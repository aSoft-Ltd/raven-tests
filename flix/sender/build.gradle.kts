import docker.DockateExtension
import docker.DockatePlugin

plugins {
    kotlin("jvm")
    kotlin("plugin.serialization")
    id("tz.co.asoft.library")
    application
}

apply<DockatePlugin>()

application {
    mainClass.set("raven.MainKt")
}

kotlin {
    target { application() }

    sourceSets {
        val main by getting {
            dependencies {
                implementation(ktor.server.cio)
                implementation(ktor.server.cors)
                implementation(libs.raven.flix.sender)
            }
        }
    }
}

configure<DockateExtension> {
    environments("Testing")

    val app = image(name = "server") {
        from(OPEN_JDK_22_JDK_SLIM)
        expose(port = 8080)
        source(layout.buildDirectory.dir("install/${project.name}")) {
            dependsOn(tasks.named("installDist"))
        }
        copy("bin", "/app/bin")
        copy("lib", "/app/lib")
        cmd("/app/bin/$name")
    }

    compose("raven") {
        version(3.8)

        service(name = "app", image = app) {
            restart("always")
            port(8080, 8080)
        }
    }
}