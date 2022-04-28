plugins {
    application
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":lib"))
}

application {
    mainClass.set("com.alexsobiek.dash.example.Server")
}

