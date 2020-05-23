plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("android.extensions")
    id("com.google.gms.google-services")
}
android {
    compileSdkVersion(29)
    defaultConfig {
        applicationId = "com.selfformat.yourrandomquote"
        minSdkVersion(17)
        targetSdkVersion(29)
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk7:$kotlin_version")
    implementation("androidx.appcompat:appcompat:$app_compat_version")
    implementation("androidx.core:core-ktx:$ktx_version")
    implementation("androidx.constraintlayout:constraintlayout:$constraint_layout_version")
    implementation("androidx.legacy:legacy-support-v4:$androidx_legacy_version")
    implementation("androidx.lifecycle:lifecycle-extensions:$lifecycle_version")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycle_version")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:$lifecycle_version")

    //Tests
    testImplementation("junit:junit:$junit_version")
    androidTestImplementation("androidx.test.ext:junit:$androidx_junit_version")
    androidTestImplementation("androidx.test.espresso:espresso-core:$espresso_version")

    //Firebase
    implementation("com.google.firebase:firebase-analytics:$firebase_analytics_version")
    implementation("com.google.firebase:firebase-auth:$firebase_auth_version")
    implementation("com.google.firebase:firebase-database:$firebase_database_version")
    implementation("com.firebaseui:firebase-ui-auth:$firebase_ui_version")

    // Koin for Android
    implementation("org.koin:koin-android:$koin_version")
    // Koin Android ViewModel features
    implementation("org.koin:koin-android-viewmodel:$koin_version")
    implementation("androidx.navigation:navigation-fragment-ktx:$nav_version")
    implementation("androidx.navigation:navigation-ui-ktx:$nav_version")
}