group = "app.rjx"

patches {
    about {
        name = "RJX Patches"
        description = "Custom Morphe patches by RJ — EX File Manager premium unlock and more"
        source = "https://github.com/rudrakshrajpurohit417-sketch/RJX-patches"
        author = "RJ"
        contact = "na"
        website = "https://github.com/rudrakshrajpurohit417-sketch/RJX-patches"
        license = "GPLv3"
    }
}

val patchListGeneratorClasspath = configurations.create("patchListGeneratorClasspath")

dependencies {
    compileOnly(libs.gson)
    patchListGeneratorClasspath(libs.gson)
}

tasks {
    register<JavaExec>("generatePatchesList") {
        description = "Build patch with patch list"
        dependsOn(build)
        classpath = sourceSets["main"].runtimeClasspath + patchListGeneratorClasspath
        mainClass.set("util.PatchListGeneratorKt")
    }

    publish {
        dependsOn("generatePatchesList")
    }
}
