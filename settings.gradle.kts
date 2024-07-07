import java.util.Properties

// Đọc local.properties
val localProperties = Properties()
val localPropertiesFile = rootDir.resolve("local.properties")
if (localPropertiesFile.exists()) {
    localPropertiesFile.inputStream().use { localProperties.load(it) }
}

// Lấy MAPBOX_DOWNLOADS_TOKEN từ local.properties
val mapboxDownloadsToken: String? = localProperties.getProperty("MAPBOX_DOWNLOADS_TOKEN")

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
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        // Mapbox Maven repository
        maven {
            url = uri("https://api.mapbox.com/downloads/v2/releases/maven")
            // Do not change the username below. It should always be "mapbox" (not your username).
            credentials.username = "mapbox"
            // Use the secret token stored in gradle.properties as the password
            credentials.password = mapboxDownloadsToken ?: error("MAPBOX_DOWNLOADS_TOKEN not found in local.properties")
            authentication.create<BasicAuthentication>("basic")
        }
    }
}

rootProject.name = "BookStoreProject"
include(":app")
 