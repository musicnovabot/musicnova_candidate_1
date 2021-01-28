import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import com.moowork.gradle.node.npm.NpmTask

plugins {
    idea
    java
    id("org.springframework.boot") version "2.4.2"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"

    id("com.github.node-gradle.node") version "2.2.4"
    kotlin("jvm") version "1.4.21"
    id("org.jetbrains.dokka") version "1.4.20"
    kotlin("plugin.spring") version "1.4.21"
    kotlin("plugin.jpa") version "1.4.21"

    kotlin("kapt") version "1.4.21"
}


group = "eu.musicnova"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

val webpack_minimize: String by project
val webpack_minimize_bool by lazy { webpack_minimize.toBoolean() }
configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
    jcenter()
    maven("https://bintray.com/kotlin/")
}

dependencies {

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-websocket")
    implementation("org.springframework.boot:spring-boot-starter-jetty")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
    //implementation("org.springframework.shell:spring-shell-starter:2.0.0.RELEASE")
    implementation("org.springframework.session:spring-session-jdbc")
    implementation("org.springframework.boot:spring-boot-starter-security")
    //TODO("remove on release")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation(project(":musicnova-web-shared"))
    implementation("com.google.guava:guava:30.1-jre")
    implementation("org.jboss.aerogear:aerogear-otp-java:1.0.0")
    implementation("org.jetbrains.kotlinx:kotlinx-html:0.7.2")

    implementation("org.springframework.boot:spring-boot-starter-data-jdbc")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-jdbc")
    implementation("org.springframework.boot:spring-boot-starter-mail")
    implementation("org.springframework.boot:spring-boot-starter-quartz")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    compileOnly("org.projectlombok:lombok")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    runtimeOnly("com.h2database:h2")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
    annotationProcessor("org.projectlombok:lombok")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "11"
    }
}
/* copy resources */
tasks {
    /* hook */
    val copyResName = "copy-resource-files"
    val preCopyResName = "pre-copy-resource-files"


    /* task names*/
    val copyWebpackTaskName = "copy-webpack"

    /* tasks */
    create<Copy>(copyWebpackTaskName) {
        dependsOn(preCopyResName, "build-webpack")
        from("${buildDir.path}/webpack/")
        into("${buildDir.path}/resources/main/files/web/")
    }
    create<NpmTask>("build-webpack") {
        outputs.dir(file("$buildDir/webpack"))
        inputs.dir(file("src/web"))
        outputs.cacheIf { true }
        dependsOn("npmInstall", preCopyResName,":musicnova-web-shared:jsBrowserWebpack")

        if (webpack_minimize_bool)
            setArgs(listOf("run", "build-prod"))
        else
            setArgs(listOf("run", "build-dev"))




    }

    /* hook tasks */
    classes {
        dependsOn("copy-resource-files")
    }
    create(copyResName) {
        dependsOn(preCopyResName, copyWebpackTaskName)
    }
    create(preCopyResName) {
        dependsOn(JavaPlugin.PROCESS_RESOURCES_TASK_NAME)
    }

}
@org.gradle.api.tasks.CacheableTask
abstract class CachedNPMTask : NpmTask()

tasks.withType<Test> {
    useJUnitPlatform()
}

configurations.all {
    exclude("org.springframework.boot", "spring-boot-starter-tomcat")
}
node {
    version = "14.9.0"

    download = true

    // Set the work directory for unpacking node
    workDir = file("${project.buildDir}/nodejs")

    nodeModulesDir = file("src/web")
}
idea {
    module {
        sourceDirs.add(file("src/web"))

        isDownloadJavadoc = true
        isDownloadSources = true
        iml {
            withXml {
                asNode().appendNode("iLoveGradle", "true")
            }
        }

    }
}