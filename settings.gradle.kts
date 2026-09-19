pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "MrD_Assessment"
include(":app")
include(":feature:home:home")
include(":feature:home:restaurantTab")
include(":feature:home:favouriteTab")
include(":feature:restaurant:detail")
include(":data:restaurant")
include(":data:menu")
include(":core:ui")
include(":core:network")
include(":core:datastore")
include(":core:navigation")
include(":core:string")
include(":model")
