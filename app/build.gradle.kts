import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties
import org.gradle.kotlin.dsl.implementation

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)

    // add
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.longlegsdev.rhythm"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.longlegsdev.rhythm"
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        val baseUrl: String = getApiKey("BASE_URL")
        buildConfigField("String", "BASE_URL", "\"$baseUrl\"")
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    buildFeatures {
        buildConfig = true
        compose = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    /* add dependency */
    // hilt
    // how to use: https://developer.android.com/training/dependency-injection/hilt-android
    // latest version: https://github.com/google/dagger
    val hilt_version = "2.51.1"
    implementation("com.google.dagger:hilt-android:$hilt_version")
    kapt("com.google.dagger:hilt-android-compiler:$hilt_version")

    // hilt navigation compose
    // https://developer.android.com/jetpack/androidx/releases/hilt?hl=ko
    val hilt_navigation_version = "1.2.0"
    implementation("androidx.hilt:hilt-navigation-compose:$hilt_navigation_version")

    // room
    // https://developer.android.com/training/data-storage/room?hl=ko#kts
    val room_version = "2.7.0"
    implementation("androidx.room:room-runtime:$room_version")
    implementation("androidx.room:room-ktx:$room_version")
    kapt("androidx.room:room-compiler:$room_version")

    // coroutine
    // git: https://github.com/Kotlin/kotlinx.coroutines
    val coroutine_version = "1.10.2"
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutine_version")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutine_version")

    // lifecycle-extensions
    val lifecycle_version = "2.8.7"
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycle_version") // ViewModel
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:$lifecycle_version") // LiveData
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:$lifecycle_version") // Lifecycles only (without ViewModel or LiveDat

    // compose navigation
    // https://developer.android.com/develop/ui/compose/navigation?hl=ko#kts
    val nav_version = "2.8.9"
    implementation("androidx.navigation:navigation-compose:$nav_version")

    // compose base
    // https://developer.android.com/jetpack/androidx/releases/compose-foundation?hl=ko
//    val foundation_version = "1.7.8"
    val foundation_version = "1.8.0-rc03"
    implementation("androidx.compose.foundation:foundation:$foundation_version")

    // timber
    // git: https://github.com/JakeWharton/timber
    val timber_version = "5.0.1"
    implementation("com.jakewharton.timber:timber:$timber_version")

    val retrofit_version = "2.11.0"
    implementation("com.squareup.retrofit2:retrofit:$retrofit_version")
    implementation("com.squareup.retrofit2:converter-moshi:$retrofit_version")

    val okhttp_version = "4.12.0"
    implementation("com.squareup.okhttp3:okhttp:$okhttp_version")
    implementation("com.squareup.okhttp3:logging-interceptor:$okhttp_version")

    // moshi
    // git: https://github.com/square/moshi
    val moshi_version = "1.15.2"
    implementation("com.squareup.moshi:moshi-kotlin:$moshi_version")

    // media3
    // https://developer.android.com/media/implement/playback-app
    val media3_version = "1.6.0"
    implementation("androidx.media3:media3-exoplayer:$media3_version")
    implementation("androidx.media3:media3-ui:$media3_version")
    implementation("androidx.media3:media3-common:$media3_version")
    implementation("androidx.media3:media3-session:$media3_version")


    // landscapist of skydoves
    // git: https://github.com/skydoves/landscapist
    val landscapist_version = "2.4.7"
    implementation("com.github.skydoves:landscapist-coil:$landscapist_version")
    implementation("com.github.skydoves:landscapist-palette:$landscapist_version")
    // orbital
    implementation("com.github.skydoves:orbital:0.4.0")


    // 임시
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-guava:1.6.0")

}

configurations.all {
    resolutionStrategy {
        force("org.jetbrains.kotlinx:kotlinx-metadata-jvm:0.9.0")
    }
}

// Allow references to generated code
kapt {
    correctErrorTypes = true
}

fun getApiKey(propertyKey: String): String {
    return gradleLocalProperties(rootDir, providers).getProperty(propertyKey)
}