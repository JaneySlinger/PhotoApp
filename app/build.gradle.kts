
import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.janey.photo"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.janey.photo"
        minSdk = 28
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "com.janey.photo.HiltTestRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

        // Get the API key from apiKey.properties
        val properties = Properties().apply {
            file("../apiKey.properties").inputStream().use { fis ->
                load(fis)
            }
        }

        buildConfigField(type = "String", name="FLICKR_API_KEY", value = properties["FLICKR_API_KEY"] as String)
        buildConfigField(type = "String", name="FLICKR_BASE_URL", value = "\"www.flickr.com/services/rest/\"")
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
    buildFeatures {
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.lifecycle.compose.viewmodel)
    implementation(libs.androidx.lifecycle.runtime.compose)
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

    implementation(libs.kotlinx.coroutines.android)
    testImplementation(libs.kotlinx.coroutines.test)

    // network
    implementation(libs.retrofit2.retrofit)
    implementation(libs.moshi.kotlin)
    implementation(libs.converter.moshi)

    // hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)
    implementation(libs.androidx.hilt.navigation.compose)
    testImplementation(libs.hilt.android.testing)
    kaptTest(libs.hilt.android.compiler)
    androidTestImplementation(libs.hilt.android.testing)
    kaptAndroidTest(libs.hilt.android.compiler)

    // coil image loading
    implementation(libs.coil.compose)

    //mockito
    testImplementation(libs.mockito.core)
    testImplementation(libs.mockito.kotlin)

    // jetpack paging library
    implementation(libs.androidx.paging.runtime)
    testImplementation(libs.androidx.paging.common)
    testImplementation(libs.androidx.paging.testing)
    androidTestImplementation(libs.androidx.paging.testing)
    implementation(libs.androidx.paging.compose)

}

kapt {
    correctErrorTypes = true
}