plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("android.extensions")
    id("com.google.gms.google-services")
}
@Suppress("detekt:MagicNumber")
android {
    compileSdkVersion(apiLevel = 29)
    defaultConfig {
        applicationId = "com.selfformat.yourrandomquote"
        minSdkVersion(21)
        targetSdkVersion(29)
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        multiDexEnabled = true
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        getByName("debug") {
            applicationIdSuffix = ".debug"
        }
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk7:$KOTLIN_VERSION")
    implementation("androidx.appcompat:appcompat:$APP_COMPAT_VERSION")
    implementation("androidx.core:core-ktx:$KTX_VERSION")
    implementation("androidx.constraintlayout:constraintlayout:$CONSTRAINT_LAYOUT_VERSION")
    implementation("com.google.android.material:material:$MATERIAL_VERSION")
    implementation("androidx.legacy:legacy-support-v4:$ANDROIDX_LEGACY_VERSION")
    implementation("androidx.lifecycle:lifecycle-extensions:$LIFECYCLE_VERSION")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$LIFECYCLE_VERSION")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:$LIFECYCLE_VERSION")

    // Tests
    testImplementation("junit:junit:$JUNIT_VERSION")
    androidTestImplementation("androidx.test.ext:junit:$ANDROIDX_JUNIT_VERSION")
    androidTestImplementation("androidx.test.espresso:espresso-core:$ESPRESSO_VERSION")

    // Firebase
    implementation("com.google.firebase:firebase-analytics:$FIREBASE_ANALYTICS_VERSION")
    implementation("com.google.firebase:firebase-auth:$FIREBASE_AUTH_VERSION")
    implementation("com.google.firebase:firebase-database:$FIREBASE_DATABASE_VERSION")
    implementation("com.firebaseui:firebase-ui-auth:$FIREBASE_UI_VERSION")

    // Koin for Android
    implementation("org.koin:koin-android:$KOIN_VERSION")
    // Koin Android ViewModel features
    implementation("org.koin:koin-android-viewmodel:$KOIN_VERSION")
    implementation("androidx.navigation:navigation-fragment-ktx:$NAV_VERSION")
    implementation("androidx.navigation:navigation-ui-ktx:$NAV_VERSION")
}
