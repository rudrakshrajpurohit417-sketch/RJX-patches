package app.rjx.patches.exfilemanager.premium

import app.morphe.patcher.extensions.InstructionExtensions.addInstructions
import app.morphe.patcher.patch.bytecodePatch
import app.morphe.util.returnEarly

/**
 * Unlocks EX File Manager premium (tested on 1.6.2 / versionCode 51).
 *
 * 1) a37.i() always true (entitlement gate)
 * 2) t02.h() returns forced ACTIVE + LIFETIME hg6 state
 * 3) PremiumVerificationWorker.d() returns success (qm4) so background verify cannot revoke
 */
@Suppress("unused")
val enablePremiumPatch = bytecodePatch(
    name = "Enable Premium",
    description = "Unlocks all premium features in EX File Manager (lifetime entitlement)."
) {
    compatibleWith("com.ace.ex.file.manager")

    execute {
        // 1. Force entitlement check
        IsPremiumEntitlementFingerprint.method.returnEarly(true)

        // 2. Force lifetime PremiumState
        PremiumStateBuilderFingerprint.method.addInstructions(
            0,
            """
            new-instance v0, Lace/hg6;
            sget-object v1, Lcom/ace/fileexplorer/billing/verification/PremiumEntitlementState;->ACTIVE:Lcom/ace/fileexplorer/billing/verification/PremiumEntitlementState;
            sget-object v2, Lcom/ace/fileexplorer/billing/verification/PremiumPlanType;->LIFETIME:Lcom/ace/fileexplorer/billing/verification/PremiumPlanType;
            sget-object v3, Lcom/ace/fileexplorer/billing/verification/PremiumLifecycleState;->ACTIVE:Lcom/ace/fileexplorer/billing/verification/PremiumLifecycleState;
            sget-object v4, Lcom/ace/fileexplorer/billing/verification/PremiumVerificationState;->SERVER_VERIFIED:Lcom/ace/fileexplorer/billing/verification/PremiumVerificationState;
            sget-object v5, Lcom/ace/fileexplorer/billing/verification/PremiumHistoryState;->CURRENT:Lcom/ace/fileexplorer/billing/verification/PremiumHistoryState;
            const-string v6, "lifetime_mod"
            const/4 v7, 0x0
            invoke-direct/range {v0 .. v7}, Lace/hg6;-><init>(Lcom/ace/fileexplorer/billing/verification/PremiumEntitlementState;Lcom/ace/fileexplorer/billing/verification/PremiumPlanType;Lcom/ace/fileexplorer/billing/verification/PremiumLifecycleState;Lcom/ace/fileexplorer/billing/verification/PremiumVerificationState;Lcom/ace/fileexplorer/billing/verification/PremiumHistoryState;Ljava/lang/String;Ljava/lang/String;)V
            return-object v0
            """.trimIndent()
        )

        // 3. Neutralize verification worker
        PremiumVerificationWorkerFingerprint.method.addInstructions(
            0,
            """
            new-instance v0, Lace/qm4;
            invoke-direct {v0}, Lace/qm4;-><init>()V
            return-object v0
            """.trimIndent()
        )
    }
}
