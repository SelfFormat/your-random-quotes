buildscript {

    repositories {
        google()
        jcenter()
        maven(url = "https://oss.sonatype.org/content/repositories/snapshots")
    }

    dependencies {
        classpath("com.android.tools.build:gradle:$GRADLE_VERSION")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$KOTLIN_VERSION")
        classpath("com.google.gms:google-services:$GOOGLE_SERVICES_VERSION")
        classpath("com.google.firebase:firebase-crashlytics-gradle:$CRASHLYTICS_GRADLE_VERSION")
        classpath("org.koin:koin-gradle-plugin:$KOIN_VERSION")
        classpath("com.vanniktech:gradle-android-junit-jacoco-plugin:0.17.0-SNAPSHOT")
    }
}

allprojects {
    repositories {
        google()
        jcenter()
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}

apply(from = "$rootDir/jacoco_coverage.gradle")

apply(from = "$rootDir/jacoco_coverage.gradle")
