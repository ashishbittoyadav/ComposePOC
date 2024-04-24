import java.net.URI

plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    `maven-publish`
}

android {
    namespace = "com.ashish.network"
    compileSdk = 34

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

//afterEvaluate {
//    publishing {
//        publications {
//            create<MavenPublication>("maven") {
//                groupId = "com.ashish.network"
//                artifactId = "networking"
//                version = "1.0.0"
//
//                from(components["java"])
//            }
//        }
//
//        repositories {
//            maven {
//                name = "GitHubPackages"
//                url = URI("https://maven.pkg.github.com/ashish/network")
//            }
//        }
//    }
//}

publishing {
    publications {
        create<MavenPublication>("ReleaseAar") {
            groupId = "com.ashish.network"
            artifactId = "networking"
            version = "1.0.0"
            afterEvaluate {
                artifact(tasks.getByName("bundleReleaseAar"))
            }
        }
    }
    repositories{
        maven{
            name = "GithubPackages"
            url = URI("https://maven.pkg.github.com/ashish/network")
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation(libs.ktor.client.core)
    implementation(libs.ktor.client.okhttp)
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)
//    implementation(libs.ktor.client.serialization)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.ktor.client.cio)
    implementation(libs.ktor.client.android)
    implementation(libs.ktor.client.serialization)
    implementation(libs.ktor.client.json)
    implementation(libs.ktor.client.logging)
    implementation(libs.ktor.client.content.negotiation)
    implementation(libs.ktor.serialization.kotlinx.json)

}