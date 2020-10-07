buildscript {

    repositories {
        google()
        jcenter()
    }

    dependencies {
        classpath("com.android.tools.build:gradle:$GRADLE_VERSION")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$KOTLIN_VERSION")
        classpath("com.google.gms:google-services:$GOOGLE_SERVICES_VERSION")
        classpath("com.google.firebase:firebase-crashlytics-gradle:$CRASHLYTICS_GRADLE_VERSION")
        classpath("org.koin:koin-gradle-plugin:$KOIN_VERSION")
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
