import java.io.FileInputStream
import java.net.URI
import java.util.Properties

include(":networking")


pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    val properties = Properties()
    properties.load(FileInputStream(File("local.properties")))

    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven("https://jitpack.io")
        maven { url = URI("https://kotlin.bintray.com/kotlinx")  }

        maven {
            url = uri("https://maven.pkg.github.com/ashishbittoyadav/ComposePOC")
            credentials {
                username = properties.getProperty("username", "")
                password = properties.getProperty("token", "")
            }
        }
    }
}

rootProject.name = "Melz"
include(":app")
 