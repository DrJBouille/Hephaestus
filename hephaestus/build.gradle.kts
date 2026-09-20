plugins {
    kotlin("jvm") version "2.3.0"
    id("com.gradleup.shadow") version "9.6.1"
    id("xyz.jpenilla.run-paper") version "3.1.0"
}

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
    implementation("com.jeff-media:MorePersistentDataTypes:2.4.0")
    compileOnly("io.papermc.paper:paper-api:26.2.build.126-stable")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
}

kotlin {
    jvmToolchain(25)
}


tasks {
    build {
        dependsOn(shadowJar)
    }

    runServer {
        // Configure the Minecraft version for our task.
        // This is the only required configuration besides applying the plugin.
        // Your plugin's jar (or shadowJar if present) will be used automatically.
        minecraftVersion("26.2")
        jvmArgs("-Xms2G", "-Xmx2G")
    }

    processResources {
        val props = mapOf("version" to version)
        filesMatching("plugin.yml") {
            expand(props)
        }
    }
}

tasks.jar {
    archiveClassifier.set("plain")
}

tasks.shadowJar {
    archiveFileName.set("hephaestus.jar")
}

tasks {
    register<Copy>("deployPlugin") {
        dependsOn(shadowJar)
        from(shadowJar)
        into("D:/Minecraft/Hephaestus/plugins")
    }

    build {
        finalizedBy("deployPlugin")
    }
}