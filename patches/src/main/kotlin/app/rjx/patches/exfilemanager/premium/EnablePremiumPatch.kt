package app.rjx.patches.exfilemanager.premium

import app.morphe.patcher.patch.bytecodePatch
import app.morphe.util.returnEarly

/**
 * Unlocks EX File Manager (com.ace.ex.file.manager) premium features.
 *
 * Root cause (v1.6.2 / versionCode 51):
 * - SharedPreferences "prefs_runtime" holds entitlement state
 * - ace.a37.m138i() is the single boolean gate used by t02.m13584h() -> hg6 PremiumState
 * - Forcing m138i to true makes every premium feature check pass
 */
@Suppress("unused")
val enablePremiumPatch = bytecodePatch(
    name = "Enable Premium",
    description = "Unlocks all premium features in EX File Manager by forcing the entitlement check to always return true."
) {
    compatibleWith("com.ace.ex.file.manager")

    execute {
        IsPremiumEntitlementFingerprint.method.returnEarly(true)
    }
}
