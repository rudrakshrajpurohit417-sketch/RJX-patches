package app.rjx.patches.exfilemanager.premium

import app.morphe.patcher.Fingerprint

/**
 * ace.a37.i()Z — premium entitlement gate.
 * Unique prefs key only used in this boolean method.
 */
object IsPremiumEntitlementFingerprint : Fingerprint(
    strings = listOf("key_p_entitlement_active_v2"),
    returnType = "Z"
)

/**
 * ace.t02.h() — builds Lace/hg6 PremiumState.
 */
object PremiumStateBuilderFingerprint : Fingerprint(
    strings = listOf("key_p_expires_at_v2", "key_p_encrypt_st"),
    returnType = "Lace/hg6;"
)

/**
 * PremiumVerificationWorker.d() — renamed doWork, returns Lace/rm4.
 * Success type in this app is Lace/qm4.
 */
object PremiumVerificationWorkerFingerprint : Fingerprint(
    definingClass = "Lcom/ace/fileexplorer/billing/verification/PremiumVerificationWorker;",
    returnType = "Lace/rm4;"
)
