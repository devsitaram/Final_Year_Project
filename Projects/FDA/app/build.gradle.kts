plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")

    id("kotlin-android")
    id("kotlin-kapt")
//    id("dagger.hilt.android.plugin")
    id("kotlin-parcelize") // data transfer
}

android {
    namespace = "com.record.fda"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.record.fda"
        minSdk = 33
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
        viewBinding = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
    implementation("androidx.activity:activity-compose:1.8.1")
    implementation(platform("androidx.compose:compose-bom:2023.03.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.03.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    // navigation compose
    implementation("androidx.compose.material:material:1.5.3")
    implementation ("androidx.navigation:navigation-compose:2.7.4")
    implementation ("androidx.hilt:hilt-navigation-compose:1.1.0-beta01")
    implementation ("com.google.dagger:hilt-android:2.48")
    implementation ("androidx.hilt:hilt-lifecycle-viewmodel:1.0.0-alpha03")
    implementation("androidx.hilt:hilt-navigation-fragment:1.0.0")
    implementation ("androidx.navigation:navigation-compose:2.7.4") // floating action button bar

    //compose
    implementation("androidx.compose.ui:ui:1.6.0-alpha07")
    implementation("androidx.compose.material:material:1.6.0-alpha07")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2")
    debugImplementation("androidx.compose.ui:ui-tooling:1.6.0-alpha07")
    implementation("androidx.compose.ui:ui-tooling-preview:1.6.0-alpha07")
    implementation("androidx.compose.runtime:runtime-livedata:1.6.0-alpha07")

    implementation("androidx.navigation:navigation-fragment-ktx:2.7.4")
    implementation("androidx.navigation:navigation-ui-ktx:2.7.4")

    // activity
    implementation("androidx.activity:activity-compose:1.8.0")

    // Lifecycle
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.6.2")
    implementation("androidx.lifecycle:lifecycle-common:2.6.2")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
    implementation("androidx.lifecycle:lifecycle-extensions:2.2.0")

    // ViewModel and LiveData for Compose
    implementation ("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.2")

    // tab layout
    implementation ("com.google.accompanist:accompanist-pager:0.28.0")
    implementation ("com.google.accompanist:accompanist-pager-indicators:0.28.0")// for indicators

    // coroutines threading
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.1")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.1")

    // defaults icons
    implementation ("androidx.compose.material:material-icons-extended-android:1.6.0-alpha07")

    // splash screen
    implementation("androidx.core:core-splashscreen:1.0.1")

    // retrofit and Api
    implementation ("com.github.bumptech.glide:glide:4.15.1")
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation ("com.squareup.okhttp3:okhttp:5.0.0-alpha.10")
    implementation ("com.squareup.okhttp3:logging-interceptor:5.0.0-alpha.10")

//    // youtube video play
//    implementation ("com.pierfrancescosoffritti.androidyoutubeplayer:chromecast-sender:0.28")
//    implementation ("com.pierfrancescosoffritti.androidyoutubeplayer:core:12.0.0")

    // api image extract
    implementation("io.coil-kt:coil-compose:2.4.0")

//    // lottie
//    implementation ("com.airbnb.android:lottie-compose:4.0.0")
//    implementation ("androidx.compose.foundation:foundation:1.5.3")
//    implementation ("androidx.viewpager2:viewpager2:1.0.0")

//    // location track
//    implementation("com.google.android.gms:play-services-location:21.0.1")
}