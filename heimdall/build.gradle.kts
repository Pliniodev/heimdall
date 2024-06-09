
import org.jlleitschuh.gradle.ktlint.reporter.ReporterType

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.ktlint)
    kotlin("kapt")
    alias(libs.plugins.mavenPublish)
}

kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64(),
    ).forEach {
        it.binaries.framework {
            baseName = "permissions"
            isStatic = true
        }
    }

    sourceSets {
        androidMain.dependencies {
            implementation(libs.androidx.core.ktx)
        }
        commonMain.dependencies {
            implementation(libs.koin.core)
            implementation(libs.kotlinx.coroutines)
            implementation(compose.foundation)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}

android {
    namespace = "io.github.pliniodev"
    publishing {
        singleVariant("release") {
            withSourcesJar()
        }
    }
    compileSdk = 34
    defaultConfig {
        minSdk = 24
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

kapt {
    correctErrorTypes = true
}

ktlint {
    android = true
    ignoreFailures = false
    reporters {
        reporter(ReporterType.JSON)
    }
    filter {
        exclude("**/generated/**")
    }
}

afterEvaluate {
    publishing {
        publications {
            create<MavenPublication>("release") {
                from(components["release"])
                groupId = "io.github.pliniodev"
                artifactId = "heimdall"
                version = "0.1.0"

                pom {
                    packaging = "aar"
                    name.set("heimdall")
                    description.set("heimdall: CMP library for permissions")
                    url.set("https://github.com/Pliniodev/heimdall")
                    inceptionYear.set("2024")

                    licenses {
                        license {
                            name.set("MIT License")
                            url.set("https://opensource.org/licenses/MIT")
                        }
                    }

                    developers {
                        developer {
                            id.set("Pliniodev")
                            name.set("Pliniodev")
                            email.set("pliniodevprojetos@gmail.com")
                        }
                    }

                    scm {
                        connection.set("scm:git@github.com:pliniodev/heimdall.git")
                        developerConnection.set("scm:git@github.com:pliniodev/heimdall.git")
                        url.set("https://github.com/pliniodev/heimdall.git")
                    }
                }
            }
        }
    }
}