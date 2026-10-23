package app.rjx.adguard

import app.reseam.patch.patch

/**
 * A safe first Reseam smoke test for AdGuard.
 * This only changes the manifest and does not modify license checks.
 */
val enableDebugging = patch("Enable debugging") {
    description(
        "Marks AdGuard as debuggable for local adb/run-as debugging. " +
            "Intended for test builds only."
    )
    execute {
        manifest.edit {
            val application = findByTag("application").firstOrNull()
                ?: error("manifest has no <application> element")
            application["android:debuggable"] = "true"
        }
    }
}
