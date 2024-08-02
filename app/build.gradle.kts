plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    //id ("com.android.application")
    //id ("org.jetbrains.kotlin.android")
    id ("com.google.protobuf")
    id ("com.google.devtools.ksp")
    id("kotlin-kapt")
    //id ("androidx.room")
    //kotlin("kapt") version "2.0.0"
    //id("com.google.devtools.ksp") version "2.0.0-1.0.23"
}

android {
    namespace = "com.example.myapplication"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.myapplication"
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
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.play.services.tasks)
    implementation(libs.androidx.recyclerview)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    implementation("androidx.datastore:datastore-preferences:1.1.1")
    implementation("androidx.datastore:datastore:1.1.1")
    implementation("com.google.protobuf:protobuf-javalite:4.26.1")
    implementation ("com.squareup.retrofit2:retrofit:2.4.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.4.0")
    implementation ("com.google.code.gson:gson:2.8.2")
    implementation ("com.squareup.okhttp3:logging-interceptor:4.9.3")
    implementation ("com.squareup.picasso:picasso:2.71828")
    implementation ("com.github.bumptech.glide:glide:4.14.2")
    implementation ("androidx.recyclerview:recyclerview:1.2.1")
    //implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.0")
    //implementation ("com.github.bumptech.glide:compiler:4.14.2")  // If using Kotlin

    /*val room_version= "2.6.1"
    implementation("androidx.room:room-runtime:$room_version")
    annotationProcessor("androidx.room:room-compiler:$room_version")
    ksp("androidx.room:room-compiler:$room_version")
    //implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:2.9.0")


    val roomVersion = "2.5.0" // or the latest version
    implementation("androidx.room:room-runtime:$roomVersion")
    kapt("androidx.room:room-compiler:$roomVersion") // Use kapt for Kotlin projects
    implementation("androidx.room:room-ktx:$roomVersion") // Room Coroutines extensions*/

    val room_version = "2.6.1"

    implementation("androidx.room:room-runtime:$room_version")
    annotationProcessor("androidx.room:room-compiler:$room_version")

    // To use Kotlin annotation processing tool (kapt)
    //kapt("androidx.room:room-compiler:$room_version")
    // To use Kotlin Symbol Processing (KSP)
    ksp("androidx.room:room-compiler:$room_version")

    // optional - Kotlin Extensions and Coroutines support for Room
    implementation("androidx.room:room-ktx:$room_version")

    // optional - RxJava2 support for Room
    implementation("androidx.room:room-rxjava2:$room_version")

    // optional - RxJava3 support for Room
    implementation("androidx.room:room-rxjava3:$room_version")

    // optional - Guava support for Room, including Optional and ListenableFuture
    implementation("androidx.room:room-guava:$room_version")

    // optional - Test helpers
    testImplementation("androidx.room:room-testing:$room_version")

    // optional - Paging 3 Integration
    implementation("androidx.room:room-paging:$room_version")

    //implementation("androidx.core:core-ktx:1.13.1")


    val viewModel_version= "2.8.0"

    //workmanager dependency
    implementation ("androidx.work:work-runtime-ktx:2.9.0")

    implementation("com.android.volley:volley:1.2.1")
}

protobuf{
    protoc{
        artifact= "com.google.protobuf-javalite:4.26.1"
    }

    generateProtoTasks{
        all().forEach { task->
            task.builtins {
                create ("java"){
                    option("lite")
                }
            }
        }
    }
}