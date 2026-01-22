plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.binaryCompatibilityValidator)
    alias(libs.plugins.dokka)
    alias(libs.plugins.build.logic.library.kmp)
    alias(libs.plugins.build.logic.library.android)
    alias(libs.plugins.build.logic.library.publishing)
}

kotlin {
    androidLibrary {
        namespace = "dev.sdkforge.exif.data"
    }

    sourceSets {
        commonMain {
            dependencies {
                implementation("com.squareup.okio:okio:3.16.0")
            }
        }
        commonTest {
            dependencies {
                implementation(libs.kotlin.test)
            }
        }
        androidMain {
            dependencies {
                implementation(libs.androidx.exifinterface)
                implementation("androidx.core:core-ktx:1.17.0")
            }
        }
    }
}
