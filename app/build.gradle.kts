import java.util.Properties
import java.io.FileInputStream

plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("android.extensions")
    id("com.google.gms.google-services")
}

data class ProjectSigningProperties(
    val keyAlias: String,
    val keyPassword: String,
    val storeFilePath: String,
    val storePassword: String
)

val defaultProjectSigningProperties =
    ProjectSigningProperties(
        keyAlias = "androiddebugkey",
        keyPassword = "android",
        storeFilePath = "$rootDir/debug.keystore",
        storePassword = "android"
    )

/*
** ~/.gradle/gradle.properties - this is globally accessible list of properties,
** this is place where you can put production keys, as it's not accessible from vcs.
** by project - searches for properties in gradle.properties of project or global gradle.properties
*/

val keyAlias: String? by project
val keyPassword: String? by project
val storeFilePath: String? by project
val storePassword: String? by project

val prepertiesNotNull = keyAlias != null && keyPassword != null &&
        storeFilePath != null && storePassword != null

val gradlePropertiesPath: String? by project
val projectSigningProperties = if (gradlePropertiesPath != null) {
    logger.info("detected properties: $gradlePropertiesPath")
    val keystoreProperties = Properties()
    keystoreProperties.load(FileInputStream(gradlePropertiesPath!!))
    ProjectSigningProperties(
        keyAlias = keystoreProperties["keyAlias"] as String,
        keyPassword = keystoreProperties["keyPassword"] as String,
        storeFilePath = keystoreProperties["storeFilePath"] as String,
        storePassword = keystoreProperties["storePassword"] as String
    )
} else if (prepertiesNotNull) {
    ProjectSigningProperties(
            keyAlias = keyAlias as String,
            keyPassword = keyPassword as String,
            storeFilePath = storeFilePath as String,
            storePassword = storePassword as String
    )
} else {
    defaultProjectSigningProperties
}

// To build release build you need to use this command:
// ./gradlew :app:assembleRelease -PshouldEnableCrashlytics=true
val shouldEnableCrashlytics: String? by project

val enableCrashlytics = shouldEnableCrashlytics?.toBoolean() ?: false

if (enableCrashlytics) {
    apply(plugin = "com.google.firebase.crashlytics")
}
@Suppress("detekt:MagicNumber")
android {
    compileSdkVersion(apiLevel = 30)
    defaultConfig {
        applicationId = "com.selfformat.yourrandomquote"
        minSdkVersion(21)
        targetSdkVersion(30)
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        multiDexEnabled = true
    }

    signingConfigs {
        getByName("debug") {
            keyAlias = defaultProjectSigningProperties.keyAlias
            keyPassword = defaultProjectSigningProperties.keyPassword
            storeFile = file(defaultProjectSigningProperties.storeFilePath)
            storePassword = defaultProjectSigningProperties.storePassword
        }
        create("release") {
            keyAlias = projectSigningProperties.keyAlias
            keyPassword = projectSigningProperties.keyPassword
            storeFile = file(projectSigningProperties.storeFilePath)
            storePassword = projectSigningProperties.storePassword
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("release")
        }
        getByName("debug") {
            applicationIdSuffix = ".debug"
            signingConfig = signingConfigs.getByName("debug")
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
    implementation("com.jakewharton.timber:timber:$TIMBER_VERSION")

    // Tests
    testImplementation("junit:junit:$JUNIT_VERSION")
    androidTestImplementation("androidx.test.ext:junit:$ANDROIDX_JUNIT_VERSION")
    androidTestImplementation("androidx.test.espresso:espresso-core:$ESPRESSO_VERSION")

    // Firebase
    implementation("com.google.firebase:firebase-analytics:$FIREBASE_ANALYTICS_VERSION")
    implementation("com.google.firebase:firebase-auth:$FIREBASE_AUTH_VERSION")
    implementation("com.google.firebase:firebase-database:$FIREBASE_DATABASE_VERSION")
    if (enableCrashlytics) {
        releaseImplementation("com.google.firebase:firebase-crashlytics-ktx:$FIREBASE_CRASHLYTICS_VERSION")
    } else {
        releaseCompileOnly("com.google.firebase:firebase-crashlytics-ktx:$FIREBASE_CRASHLYTICS_VERSION")
    }
    implementation("com.firebaseui:firebase-ui-auth:$FIREBASE_UI_VERSION")

    // Koin for Android
    implementation("org.koin:koin-android:$KOIN_VERSION")
    // Koin Android ViewModel features
    implementation("org.koin:koin-android-viewmodel:$KOIN_VERSION")
    implementation("androidx.navigation:navigation-fragment-ktx:$NAV_VERSION")
    implementation("androidx.navigation:navigation-ui-ktx:$NAV_VERSION")
}
