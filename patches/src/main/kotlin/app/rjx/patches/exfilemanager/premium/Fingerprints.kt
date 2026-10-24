package app.rjx.patches.exfilemanager.premium

import app.morphe.patcher.Fingerprint

/**
 * ace.a37.m138i() — boolean entitlement check.
 * Unique SharedPreferences key used only in this gate.
 */
object IsPremiumEntitlementFingerprint : Fingerprint(
    strings = listOf("key_p_entitlement_active_v2"),
    returnType = "Z"
)

/**
 * Optional: PremiumVerificationWorker background job that can rewrite entitlement.
 * Neutralize if premium resets after idle.
 */
object PremiumVerificationWorkerFingerprint : Fingerprint(
    strings = listOf(
        "key_p_entitlement_active_v2",
        "key_p_next_verification_time_v2"
    )
)
