plugins {
    id("com.gradle.enterprise") version("3.10.3")
}

include("depPom")
include(
    ":core:base",
    ":core:api",
    ":core:storage",
    ":core:ui",
    ":core:feature",
    ":app"
)
