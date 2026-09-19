import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.hilt.android)
}

android {
    namespace = "com.example.mrd_assessment.data.restaurant"
    compileSdk {
        version = release(37)
    }

    defaultConfig {
        minSdk = 24
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

tasks.withType<KotlinCompile>().configureEach {
    compilerOptions {
        jvmTarget.set(JvmTarget.JVM_11)
    }
}

dependencies {
    implementation(project(":model"))
    implementation(project(":core:network"))
    implementation(project(":core:datastore"))
    implementation(project(":core:string"))

    implementation(libs.hilt.android)
    add("kapt", libs.hilt.compiler)

    implementation(libs.retrofit.core)
    implementation(libs.retrofit.kotlinx.serialization)

    implementation(libs.deferred.resources)
    implementation(libs.deferred.resources.view.extensions)
    implementation(libs.deferred.resources.compose.adapter)

    implementation(libs.androidx.core.ktx)
}
