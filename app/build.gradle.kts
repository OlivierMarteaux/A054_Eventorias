import com.android.build.gradle.BaseExtension
import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp)
    id("jacoco")  // for JaCoCo test coverage
    // Add the dependency for the Google services Gradle plugin for Firebase authentication
    alias(libs.plugins.googleservices)
    alias(libs.plugins.crashlytics) // firebase crashlytics
}

// Specific for JaCoCo
tasks.withType<Test> {
    extensions.configure(JacocoTaskExtension::class) {
        isIncludeNoLocationClasses = true
        excludes = listOf("jdk.internal.*")
    }
}

android {
    namespace = "com.oliviermarteaux.a054_eventorias"
    compileSdk {
        version = release(36)
    }

    // specific for JaCoCo
    testCoverage { version = "0.8.8" }

    defaultConfig {
        applicationId = "com.oliviermarteaux.a054_eventorias"
        minSdk = 26
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        // For GoogleMaps API
        val localProperties = Properties()
        val localPropertiesFile = rootProject.file("local.properties")
        if (localPropertiesFile.exists()) {
            localPropertiesFile.inputStream().use { localProperties.load(it) }
        }
        val mapsApiKey: String = localProperties.getProperty("MAPS_API_KEY") ?: ""
        manifestPlaceholders["MAPS_API_KEY"] = mapsApiKey

        // choose test runner
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        // testInstrumentationRunner = "com.kirabium.relayance.runner.MyCucumberTestRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        // to enable JaCoCo test coverage reports
        debug {
            enableAndroidTestCoverage = true
            enableUnitTestCoverage = true
            // Optional: only if you want to test minification earlier
//            isMinifyEnabled = true
        }
    }

    // Add JVM toolchain to define global java version
    kotlin { jvmToolchain(17) }

    buildFeatures {
        compose = true
    }
}

// Needed variable for jacocoTestReport and jacocoTestCoverage
val androidExtension = extensions.getByType<BaseExtension>()

//_ setup a jacoco test report task including unit tests, android tests and global coverage:
val jacocoTestReport by tasks.registering(JacocoReport::class) {
    dependsOn("clean" , "ensureEmulator", "testDebugUnitTest", "createDebugCoverageReport")
    group = "Reporting"
    description = "Generate Jacoco coverage reports"

    reports {
        csv.required.set(false)
        xml.required.set(true)
        html.required.set(true)
    }


//    val debugTree = fileTree("${buildDir}/tmp/kotlin-classes/debug")
    val debugTree = fileTree("${buildDir}/tmp/kotlin-classes/debug") {
        exclude("**/di/**")        // exclude DI packages
        exclude("**/*Module*.*")   // exclude Module classes
    }
    val mainSrc = androidExtension.sourceSets.getByName("main").java.srcDirs

    classDirectories.setFrom(debugTree)
    sourceDirectories.setFrom(files(mainSrc))
    executionData.setFrom(fileTree(buildDir) {
        include("**/*.exec", "**/*.ec")
    })
}

//_ The JacocoCoverageVerification task can be used to verify if code coverage metrics are met
val jacocoTestCoverageCheck by tasks.registering(JacocoCoverageVerification::class) {
    dependsOn("clean" , "ensureEmulator","testDebugUnitTest", "createDebugCoverageReport")
    group = "Verification"
    description = "Verifies code coverage metrics"

    val debugTree = fileTree("${buildDir}/tmp/kotlin-classes/debug")
    val mainSrc = androidExtension.sourceSets.getByName("main").java.srcDirs

    classDirectories.setFrom(debugTree)
    sourceDirectories.setFrom(files(mainSrc))
    executionData.setFrom(fileTree(buildDir) {
        include("**/*.exec", "**/*.ec")
    })

    violationRules {
        rule {
            isEnabled = true
            limit {
                minimum = "0.5".toBigDecimal() // 50% minimum coverage
            }
        }

        rule {
            isEnabled = true
            element = "CLASS"
            limit {
                counter = "LINE"
                value = "COVEREDRATIO"
                minimum = "0.5".toBigDecimal() // each class must have ≥ 50% line coverage
            }
        }
    }
}

dependencies {
    // Personal shared library
    implementation(libs.oliviermarteaux.compose)
    implementation(libs.oliviermarteaux.core)

    // Base dependencies for compose app
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.activity.compose)

    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.navigation.compose) // Navigation
    implementation(libs.material.icons.extended) // Icons (full material library)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx) // usage of lifecycleScope

    //_ Preferences DataStore
    implementation(libs.datastore.preferences)

    //_ Firebase
    implementation(platform(libs.firebase.bom)) // Bom
    implementation(libs.firebase.analytics)  // Google Analytics
    implementation(libs.firebase.crashlytics.ndk)  // Crashlytics
    implementation(libs.firebase.auth) // Authentication
    implementation(libs.firebase.firestore) // Database
    implementation(libs.firebase.messaging) // Cloud notifications
    implementation(libs.firebase.storage) // Media files storage
    // For google account authentication
    implementation(libs.play.services.credentials)
    implementation(libs.androidx.credentials)
    implementation(libs.googleid)

    //_ hilt for DI
    implementation(libs.hilt)

    ksp(libs.hilt.compiler)
    implementation(libs.hilt.navigation.compose)

    //_ Coil for image loading
    implementation(libs.coil.compose)
    implementation(libs.coil.network.okhttp) // to load images from internet

    //_ cameraX for camera capture
    implementation(libs.androidx.camera.core)
    implementation(libs.androidx.camera.lifecycle)
    implementation(libs.androidx.camera.view)
    implementation(libs.androidx.camera.camera2)

    // _ GoogleMaps
    // Google Maps Compose
    implementation(libs.googlemaps.android)
    // Google Maps SDK
    implementation(libs.googlemaps.sdk)
    // Accompanist
    implementation(libs.google.accompanist)

    // Unit Tests
    testImplementation(libs.junit)

    //_ cucumber for UnitTests
    testImplementation(libs.cucumber.java)
    testImplementation(libs.cucumber.junit)
    testImplementation(libs.cucumber.picocontainer)

    // Android Tests
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)

    //_ cucumber for AndroidTests
    androidTestImplementation(libs.cucumber.android)
    androidTestImplementation(libs.cucumber.junit)
    androidTestImplementation(libs.cucumber.picocontainer)
    androidTestImplementation(libs.cucumber.messages)

    // Debug conf
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
}

/**
 * Helper function to force application run to be performed on physical device.
 */
val targetDevice = "adb-cb4d0d70-D3BuA7._adb-tls-connect._tcp" // ← Replace with your actual device ID
val checkPhysicalDevice = tasks.register("checkPhysicalDevice") {
    doFirst {
        val adbOutput = ProcessBuilder("adb", "devices")
            .redirectErrorStream(true)
            .start()
            .inputStream
            .bufferedReader()
            .readText()

        val connectedDevices = adbOutput.lines().filter { line ->
            line.isNotBlank() &&
                    !line.startsWith("List") &&
                    line.contains("device") &&
                    !line.startsWith("emulator-")
        }

        if (connectedDevices.none { it.startsWith(targetDevice) }) {
            throw GradleException("ERROR: Required physical device ($targetDevice) is not connected.")
        } else {
            println("✅ Physical device ($targetDevice) is connected. Proceeding with build.")
        }
    }
}