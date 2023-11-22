import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask
import org.jetbrains.dokka.DokkaConfiguration
import org.jetbrains.dokka.gradle.DokkaTask
import java.net.URL

// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {
    repositories {
        google()
        jcenter()

    }
    dependencies {
        classpath(Classpaths.gradleClasspath)
        classpath(Classpaths.kotlinClasspath)
        classpath(Classpaths.dokkaClasspath)
    }
}

plugins {
    id("com.github.ben-manes.versions") version ("0.50.0")
}

allprojects {
    repositories {
        google()
        jcenter()

    }
}

tasks.register("clean", Delete::class) {
    delete = setOf(rootProject.buildDir)
}

tasks.named<DependencyUpdatesTask>("dependencyUpdates") {
    isCheckForGradleUpdate = true
    outputFormatter = "json"
    outputDir = "build/dependencyUpdates"
    reportfileName = "report"
}


subprojects {
    apply(from = "$rootDir/versions.gradle.kts")
    buildscript.repositories {
        jcenter()
        mavenCentral()
    }
}

tasks.withType<DokkaTask> {
    outputFormat = "html"
    outputDirectory = "$buildDir/docs/dokka"
    externalDocumentationLink(delegateClosureOf<DokkaConfiguration.ExternalDocumentationLink.Builder> {
        url = URL("https://docs.spring.io/spring-framework/docs/5.1.0.BUILD-SNAPSHOT/javadoc-api/")
    })
    jdkVersion = 8
    // Do not output deprecated members
    skipDeprecated = true
    // Emit warnings about not documented members.
    reportUndocumented = true
}

defaultTasks("clean", "tasks", "--all")
