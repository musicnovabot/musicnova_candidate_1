plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization") version "1.4.10"
    id("org.jetbrains.dokka")
}

group = "eu.musicnova"
version = "0.0.1-SNAPSHOT"

repositories {
    mavenCentral()
    jcenter()
}

kotlin {
    /* Targets configuration omitted. 
    *  To find out how to configure the targets, please follow the link:
    *  https://kotlinlang.org/docs/reference/building-mpp-with-gradle.html#setting-up-targets */
    jvm()
    js(){
        useCommonJs()
        browser() {}
        nodejs()
    }
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(kotlin("stdlib-common"))
                //implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.0.1")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-protobuf:1.0.1")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))


            }
        }
        val jsMain by getting {
            dependencies {
                implementation(kotlin("stdlib-js"))
            }
        }
        val jvmMain by getting {
            dependencies {
                implementation(kotlin("stdlib-jdk8"))
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-protobuf:1.0.1")
            }
        }
    }

}