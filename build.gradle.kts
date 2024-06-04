val kotlin_version: String by project
val logback_version: String by project

plugins {
    kotlin("jvm") version "2.0.0"
    id("io.ktor.plugin") version "2.3.11"
    id("org.graalvm.buildtools.native") version "0.9.8"
}

group = "org.jetbrains"
version = "0.0.1"

application {
    mainClass.set("org.jetbrains.ApplicationKt")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

repositories {
    mavenCentral()
}

graalvmNative {
    binaries {

        named("main") {

            javaLauncher.set(javaToolchains.launcherFor {
                languageVersion.set(JavaLanguageVersion.of(21))
                vendor.set(JvmVendorSpec.matching("GraalVM Community"))
            })


            fallback.set(false)
            verbose.set(true)

            with(buildArgs) {
                add("--initialize-at-build-time=ch.qos.logback")
                add("--initialize-at-build-time=io.ktor,kotlin")
                add("--initialize-at-build-time=org.slf4j.LoggerFactory")

                add("--initialize-at-run-time=io.netty.handler.ssl.BouncyCastleAlpnSslUtils")
                add("--initialize-at-run-time=io.netty.channel.epoll.Epoll")
                add("--initialize-at-run-time=io.netty.channel.epoll.Native")
                add("--initialize-at-run-time=io.netty.channel.epoll.EpollEventLoop")
                add("--initialize-at-run-time=io.netty.channel.epoll.EpollEventArray")
                add("--initialize-at-run-time=io.netty.channel.DefaultFileRegion")
                add("--initialize-at-run-time=io.netty.channel.kqueue.KQueueEventArray")
                add("--initialize-at-run-time=io.netty.channel.kqueue.KQueueEventLoop")
                add("--initialize-at-run-time=io.netty.channel.kqueue.KQueue")
                add("--initialize-at-run-time=io.netty.channel.kqueue.Native")
                add("--initialize-at-run-time=io.netty.channel.unix.Errors")
                add("--initialize-at-run-time=io.netty.channel.unix.IovArray")
                add("--initialize-at-run-time=io.netty.channel.unix.Limits")
                add("--initialize-at-run-time=io.netty.util.internal.logging.Log4JLogger")

//                add("--trace-class-initialization=io.netty.util.AbstractReferenceCounted")
//                add("--trace-class-initialization=io.netty.channel.DefaultFileRegion")

                add("-H:+InstallExitHandlers")
                add("-H:+ReportUnsupportedElementsAtRuntime")
                add("-H:+ReportExceptionStackTraces")
            }
            imageName.set("graalvm-server")
        }
    }
}



dependencies {
    implementation("io.ktor:ktor-server-core-jvm")
    implementation("io.ktor:ktor-server-netty-jvm")
    implementation("ch.qos.logback:logback-classic:$logback_version")
    testImplementation("io.ktor:ktor-server-tests-jvm")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")
}
