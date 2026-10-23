# RJX Patch Porting Skill

An Agent Skill for turning Morphe/ReVanced-style Android patch implementations into maintainable Reseam Kotlin patches.

## Agent Skill

Skill path: `.agents/skills/morphe-to-reseam-porting/SKILL.md`

It guides an AI coding agent through repository inspection, behavior reconstruction, `.class`/`.jar` and APK/DEX analysis, Reseam target design, Kotlin implementation, bundle building, dry-run validation, and honest reporting of test results.

Supporting guides:

- [Framework migration map](.agents/skills/morphe-to-reseam-porting/references/migration-map.md)
- [Class, JAR, APK, and bytecode inspection](.agents/skills/morphe-to-reseam-porting/references/class-and-bytecode-inputs.md)
- [Validation checklist](.agents/skills/morphe-to-reseam-porting/references/validation.md)
- [Upstream docs and source links](.agents/skills/morphe-to-reseam-porting/references/upstream-docs.md)

The skill uses the open Agent Skills folder format and lives in `.agents/skills/` for discovery by compatible coding agents. Open your AI coding tool in this repository and ask it to port a specific Morphe patch to Reseam; provide the patch source or artifact and the target app version when available.

## Existing Reseam smoke-test patch

`apps/adguard/patch/` still contains an **Enable debugging** example only. It marks the target application's manifest as debuggable for local testing; it is not an AdGuard Premium patch and does not alter subscription/license checks.

## Build the sample bundle

The `Build Reseam bundle` GitHub Actions workflow builds the smoke-test patch against the official Reseam template and uploads a `.reseam` bundle as a workflow artifact.

1. Open [GitHub Actions](https://github.com/rudrakshrajpurohit417-sketch/RJX-patches/actions).
2. Open the latest **Build Reseam bundle** run.
3. Download `rjx-patches-reseam-bundle` from its Artifacts section.

A successful build verifies compilation and bundle validation only. Runtime behavior must be tested separately against the exact target APK version.

## References

- [Reseam authoring docs](https://reseam.app/docs/)
- [Reseam patch bundle template](https://git.reseam.app/reseam/patches-template)
- [Official Morphe patch repository](https://github.com/MorpheApp/morphe-patches)

**Safety boundary:** this skill is for legitimate feature changes and compatibility ports. It will not implement patches that bypass paid licenses/subscriptions, payment controls, DRM, credentials, or bundle/signature trust checks.