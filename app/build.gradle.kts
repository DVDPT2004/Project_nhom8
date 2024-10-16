plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.project_nhom8"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.project_nhom8"
        minSdk = 24
        targetSdk = 34
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
//    Phuoc
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
//    Tuệ
    implementation("com.sun.mail:android-activation:1.6.2")
    implementation("com.sun.mail:android-mail:1.6.2")
//Viet
    implementation ("androidx.appcompat:appcompat:1.6.1")  // Phiên bản có thể thay đổi
    implementation ("androidx.core:core-ktx:1.10.1")
    implementation ("com.google.android.material:material:1.10.0")
    implementation ("me.relex:circleindicator:2.1.6")
    implementation ("com.github.bumptech.glide:glide:4.16.0")
    implementation ("com.google.android.material:material:1.9.0")
//Doanh
    implementation("com.github.PhilJay:MPAndroidChart:v3.0.2")

}