import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("com.android.application")
    id("org.jetbrains.dokka-android")
    kotlin("android.extensions")
    kotlin("android")
    kotlin("kapt")
}

val javaVersion: JavaVersion by extra { JavaVersion.VERSION_1_8 }

val BASE_URL: String by project
val BASE_PLAYER_URL: String by project
val ACCESS_TOKEN: String by project

android {
    compileSdkVersion(extra["compileSdk"] as Int)
    defaultConfig {
        applicationId = "com.nuhkoca.myapplication"
        minSdkVersion(extra["minSdk"] as Int)
        targetSdkVersion(extra["targetSdk"] as Int)
        versionCode = 1
        versionName = getSemanticVersionName()
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    sourceSets {
        getByName("main").java.srcDirs("src/kotlin/main")
    }

    testOptions {
        unitTests.isReturnDefaultValues = true
    }

    compileOptions {
        targetCompatibility = javaVersion
        sourceCompatibility = javaVersion
    }

    tasks.withType<JavaCompile> {
        options.isIncremental = true
        allprojects {
            options.compilerArgs.addAll(arrayOf("-Xlint:-unchecked", "-Xlint:deprecation", "-Xdiags:verbose"))
        }
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            jvmTarget = javaVersion.toString()
        }
    }

    dataBinding {
        isEnabled = true
    }

    lintOptions {
        textReport = true
        textOutput("stdout")
        isCheckAllWarnings = true
        isWarningsAsErrors = true
        setLintConfig(file("lint.xml"))
        isCheckReleaseBuilds = false
        isCheckTestSources = true
    }

    buildTypes.forEach {
        it.buildConfigField("String", "BASE_URL", BASE_URL)
        it.buildConfigField("String", "BASE_PLAYER_URL", BASE_PLAYER_URL)
        it.buildConfigField("String", "ACCESS_TOKEN", ACCESS_TOKEN)
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    implementation(Libs.kotlin)
    implementation(Libs.x)
    implementation(Libs.material)
    implementation(Libs.constraint_layout)
    implementation(Libs.exo_player)
    implementation(Libs.paging)
    implementation(Libs.paging_runtime)
    implementation(Libs.lifecycle)
    implementation(Libs.dagger)
    implementation(Libs.dagger_android)
    implementation(Libs.dagger_support)
    kapt(Libs.dagger_processor)
    kapt(Libs.dagger_compiler)
    implementation(Libs.rxandroid)
    implementation(Libs.rxjava)
    implementation(Libs.jetbrains)
    implementation(Libs.rxjava_adapter)
    implementation(Libs.retrofit)
    implementation(Libs.gson)
    implementation(Libs.logging)
    implementation(Libs.glide)
    kapt(Libs.glide_compiler)
    implementation(Libs.glide_okhttp) {
        exclude(mapOf("group" to "glide-parent"))
    }

    testImplementation(TestLibs.test_core)
    testImplementation(TestLibs.runner)
    testImplementation(TestLibs.rules)
    testImplementation(TestLibs.junit)
    testImplementation(TestLibs.truth_ext)
    testImplementation(TestLibs.truth)
    testImplementation(TestLibs.espresso_core)
    testImplementation(TestLibs.mockito)
    testImplementation(TestLibs.arch_core)
}

fun getSemanticVersionName(): String {
    val majorCode = 1
    val minorCode = 0
    val patchCode = 0
    return "$majorCode.$minorCode.$patchCode"
}
repositories {
    mavenCentral()
}