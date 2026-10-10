plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.example.reciepebox"
    compileSdk {
        version = release(37)
    }

    defaultConfig {
        applicationId = "com.example.reciepebox"
        minSdk = 28
        targetSdk = 37
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            optimization {
                enable = false
            }
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.junit)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
    debugImplementation(libs.androidx.compose.ui.tooling)

    implementation("androidx.activity:activity-compose:1.10.1")

    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.compose.ui:ui-tooling-preview")

    implementation(
        "androidx.navigation:navigation-compose:2.9.4"
    )

    implementation(
        "androidx.lifecycle:lifecycle-viewmodel-compose:2.9.2"
    )

    implementation(
        "com.squareup.retrofit2:retrofit:3.0.0"
    )

    implementation(
        "com.squareup.retrofit2:converter-gson:3.0.0"
    )

    implementation(
        "io.coil-kt:coil-compose:2.7.0"
    )

    implementation(
        "org.jetbrains.kotlinx:kotlinx-coroutines-android:1.10.2"
    )
    implementation("androidx.navigation:navigation-compose:2.9.4")
    implementation("androidx.compose.material:material-icons-extended")
    dependencies {

        implementation("androidx.core:core-ktx:1.17.0")
        implementation("androidx.activity:activity-compose:1.11.0")

        implementation(platform("androidx.compose:compose-bom:2026.09.00"))

        implementation("androidx.compose.ui:ui")
        implementation("androidx.compose.ui:ui-tooling-preview")

        implementation("androidx.compose.material3:material3")

        // IMPORTANT: Material icons
        implementation("androidx.compose.material:material-icons-extended")

        // Navigation
        implementation("androidx.navigation:navigation-compose:2.9.4")

        // Images
        implementation("io.coil-kt:coil-compose:2.7.0")

        debugImplementation("androidx.compose.ui:ui-tooling")
        implementation("io.coil-kt:coil-compose:2.7.0")

    }
}