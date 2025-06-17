import com.vanniktech.maven.publish.SonatypeHost

plugins {
    kotlin("jvm") version "2.1.20"
    id("com.vanniktech.maven.publish") version "0.30.0"
}

group = "io.github.artem-goldenberg"
version = "1.0.0"

mavenPublishing {
    publishToMavenCentral(SonatypeHost.CENTRAL_PORTAL)

    signAllPublications()

    coordinates(group.toString(), "libsl-gen", version.toString())

    pom {
        name = "LibSLGen"
        description = "Test generation library for the LibSL language specifications."
        inceptionYear = "2025"
        url = "https://github.com/Artem-Goldenberg/LibSLGen"
        licenses {
            license {
                name = "MIT License"
                url = "https://spdx.org/licenses/MIT.html"
                distribution = "https://spdx.org/licenses/MIT.html"
            }
        }
        developers {
            developer {
                id = "artem"
                name = "Artem Ibragimov"
                url = "https://github.com/Artem-Goldenberg"
            }
        }
        scm {
            url = "https://github.com/Artem-Goldenberg/LibSLGen"
            connection = "scm:git:9git://github.com/Artem-Goldenberg/LibSLGen.git"
            developerConnection = "scm:git:ssh://git@github.com/Artem-Goldenberg/LibSLGen.git"
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