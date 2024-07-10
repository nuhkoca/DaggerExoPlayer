object Versions {
    const val kotlin_gradle_version = "1.9.23"
    const val gradle_version = "8.3.1"
    const val dokka_version = "0.9.18"

    const val x = "1.7.0"
    const val material = "1.11.0"
    const val constraint_layout = "2.1.4"
    const val exo_player = "2.19.1"
    const val dagger = "2.51"
    const val paging = "3.2.1"
    const val livedata = "2.2.0"
    const val rxjava = "2.2.21"
    const val rxandroid = "2.1.1"
    const val jetbrains = "24.1.0"
    const val retrofit = "2.10.0"
    const val okhttp = "4.12.0"
    const val glide = "4.16.0"
    const val kotlin_version = "1.9.23"

    const val test_core = "1.5.0"
    const val runner = "1.5.2"
    const val rules = "1.5.0"
    const val junit = "1.1.5"
    const val truth_ext = "1.5.0"
    const val truth = "1.4.2"
    const val espresso_core = "3.5.1"
    const val mockito = "1.6.0"
    const val arch_core = "1.1.1"
}

object Libs {
    const val x = "androidx.appcompat:appcompat:${Versions.x}"
    const val material = "com.google.android.material:material:${Versions.material}"
    const val constraint_layout = "androidx.constraintlayout:constraintlayout:${Versions.constraint_layout}"
    const val exo_player = "com.google.android.exoplayer:exoplayer:${Versions.exo_player}"
    const val paging = "androidx.paging:paging-common:${Versions.paging}"
    const val paging_runtime = "androidx.paging:paging-runtime:${Versions.paging}"
    const val lifecycle = "androidx.lifecycle:lifecycle-extensions:${Versions.livedata}"
    const val dagger = "com.google.dagger:dagger:${Versions.dagger}"
    const val dagger_android = "com.google.dagger:dagger-android:${Versions.dagger}"
    const val dagger_support = "com.google.dagger:dagger-android-support:${Versions.dagger}"
    const val dagger_processor = "com.google.dagger:dagger-android-processor:${Versions.dagger}"
    const val dagger_compiler = "com.google.dagger:dagger-compiler:${Versions.dagger}"
    const val rxandroid = "io.reactivex.rxjava2:rxandroid:${Versions.rxandroid}"
    const val rxjava = "io.reactivex.rxjava2:rxjava:${Versions.rxjava}"
    const val jetbrains = "org.jetbrains:annotations:${Versions.jetbrains}"
    const val rxjava_adapter = "com.squareup.retrofit2:adapter-rxjava2:${Versions.retrofit}"
    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    const val gson = "com.squareup.retrofit2:converter-gson:${Versions.retrofit}"
    const val logging = "com.squareup.okhttp3:logging-interceptor:${Versions.okhttp}"
    const val glide = "com.github.bumptech.glide:glide:${Versions.glide}"
    const val glide_compiler = "com.github.bumptech.glide:compiler:${Versions.glide}"
    const val glide_okhttp = "com.github.bumptech.glide:okhttp3-integration:${Versions.glide}"
    const val kotlin = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin_version}"
}

object TestLibs {
    // Core library
    const val test_core = "androidx.test:core:${Versions.test_core}"

    // AndroidJUnitRunner and JUnit Rules
    const val runner = "androidx.test:runner:${Versions.runner}"
    const val rules = "androidx.test:rules:${Versions.rules}"

    // Assertions
    const val junit = "androidx.test.ext:junit:${Versions.junit}"
    const val truth_ext = "androidx.test.ext:truth:${Versions.truth_ext}"
    const val truth = "com.google.truth:truth:${Versions.truth}"

    // Espresso dependencies
    const val espresso_core = "androidx.test.espresso:espresso-core:${Versions.espresso_core}"

    const val mockito = "com.nhaarman:mockito-kotlin-kt1.1:${Versions.mockito}"

    const val arch_core = "android.arch.core:core-testing:${Versions.arch_core}"
}