plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    // añadimos el plugin  que habilita el uso de anotaciones
    id("kotlin-kapt")
}

android {
    namespace = "com.example.ejercicioroom"
    compileSdk {
        version = release(36)
    }

    defaultConfig {
        applicationId = "com.example.ejercicioroom"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.recyclerview)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    // ROOM
    implementation(libs.androidx.room.runtime)
    // Annotation processor
    kapt(libs.androidx.room.compiler)
    // Kotin extensions
    implementation(libs.androidx.room.ktx)
}