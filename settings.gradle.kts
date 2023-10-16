import java.io.File

pluginManagement {
    includeBuild("../build-logic")
}

plugins {
    id("multimodule")
}

fun includeSubs(base: String, path: String = base, vararg subs: String) {
    subs.forEach {
        include(":$base-$it")
        project(":$base-$it").projectDir = File("$path/$it")
    }
}

listOf(
    "lexi", "neat", "geo-api", "kronecker",
    "epsilon-api", "krono-core", "hormone", "identifier-api", "kommerce",
    "kollections", "koncurrent", "kommander", "cabinet-api",
    "raven-core", "raven-server", "raven-client"
).forEach { includeBuild("../$it") }

rootProject.name = "raven-tests"

includeSubs(base = "raven-flix-test", path = "flix", "sender", "receiver")
