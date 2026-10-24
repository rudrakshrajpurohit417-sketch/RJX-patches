package app.rjx.adguard.patches

import app.reseam.patch.patch

/**
 * Safe smoke-test patch for checking the Reseam build pipeline.
 * It only sets android:debuggable in the manifest.
 */
val enableDebugging = patch("Enable debugging") {
    description("Marks a test build as debuggable for local adb/run-as debugging.")

    execute {
        manifest.edit {
            val application = findByTag("application").firstOrNull()
                ?: error("manifest has no <application> element")
            application["android:debuggable"] = "true"
        }
    }
}
