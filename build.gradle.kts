import com.vanniktech.maven.publish.SonatypeHost

plugins {
    kotlin("jvm") version "2.1.20"
    id("com.vanniktech.maven.publish") version "0.30.0"
}

group = "io.github"
version = "1.0-SNAPSHOT"

mavenPublishing {
    publishToMavenCentral(SonatypeHost.CENTRAL_PORTAL)

    signAllPublications()

    coordinates(group.toString(), "fibonacci", version.toString())

    pom {
        name = "Fibonacci library"
        description = "A mathematics calculation library."
        inceptionYear = "2024"
        url = "https://github.com/kotlin-hands-on/fibonacci/"
        licenses {
            license {
                name = "The Apache License, Version 2.0"
                url = "https://www.apache.org/licenses/LICENSE-2.0.txt"
                distribution = "https://www.apache.org/licenses/LICENSE-2.0.txt"
            }
        }
        developers {
            developer {
                id = "kotlin-hands-on"
                name = "Kotlin Developer Advocate"
                url = "https://github.com/kotlin-hands-on/"
            }
        }
        scm {
            url = "https://github.com/kotlin-hands-on/fibonacci/"
            connection = "scm:git:git://github.com/kotlin-hands-on/fibonacci.git"
            developerConnection = "scm:git:ssh://git@github.com/kotlin-hands-on/fibonacci.git"
        }
    }
}


repositories {
    mavenCentral()
    maven("https://jitpack.io")
}

dependencies {
    testImplementation(kotlin("test"))
//    implementation("com.github.vpa-research:libsl-parser:c2db6b6213b417f562492a9d00ab56c175d836b3")
    implementation("com.github.artem-goldenberg:libsl-parser:4bf7479ba3d8a5a8d69ade43351bb39d0eaaa192")
//    implementation("com.github.vpa-research:libsl-parser:main")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}