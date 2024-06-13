plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.dagger.hilt)
    alias(libs.plugins.google.services)
}

android {
    namespace = "com.group2.bookstoreproject"
    compileSdk = 34

    buildFeatures {
        dataBinding = true
        viewBinding = true
    }

    defaultConfig {
        applicationId = "com.group2.bookstoreproject"
        minSdk = 26
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
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.legacy.support.v4)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)


    // Retrofit
    implementation(libs.retrofit)
    implementation(libs.retrofit.moshi)

    // Glide
    implementation(libs.glide)
    annotationProcessor(libs.glide.compiler)

    // Dagger Hilt
    implementation(libs.dagger.hilt)
    annotationProcessor(libs.dagger.hilt.compiler)

    // LiveData and ViewModel
    implementation(libs.lifecycle.livedata)
    implementation(libs.lifecycle.viewmodel)

    // RxJava
    implementation(libs.rxjava)
    implementation(libs.rxandroid)

    // Lottie
    implementation(libs.lottie)

    // Moshi
    implementation(libs.moshi)

    //firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.firestore)

    //viewpager
    implementation(libs.viewpager2)

    // Circle Image
    implementation(libs.circleimageview)

    //flexbox - automatic wrapping for dynamically in view
    implementation(libs.flexbox)

    //navigation
    implementation(libs.navigation.fragment)
    implementation(libs.navigation.ui)
}



