# Morphe → Reseam port validation checklist

Use this checklist for every patch. Mark a test as passed only after seeing its actual result.

## Before implementation

- [ ] Identify target app/package and exact version from the supplied APK or reliable metadata.
- [ ] Determine whether the input is source code, a compiled patch `.class`/`.jar`, app DEX/Smali, a decompiled APK, or a `.reseam` bundle.
- [ ] Read the full patch and helper/dependency chain.
- [ ] Summarize intent, targets, operations, settings/options, runtime extensions, and version assumptions.
- [ ] Confirm the requested behavior does not defeat paid licenses/subscriptions, payment controls, DRM, credential checks, or signature/trust verification.

## Implementation

- [ ] Public top-level Kotlin patch `val` using the Reseam DSL.
- [ ] Correct `apps/<app>/patch/` location and package path.
- [ ] App compatibility metadata is evidence-based.
- [ ] All method/class/field targets have enough constraints to resolve uniquely.
- [ ] No copied Morphe fingerprint indexes, raw offsets, registers, or helper APIs assumed compatible.
- [ ] Branching is built with Reseam runtime branch builders, not Kotlin `if` that only runs during patch construction.
- [ ] Receiver, parameter, return, field, static/instance and access semantics are correct.
- [ ] Extensions and their generated bindings/exports are used only where required.
- [ ] Manifest/resource updates handle missing nodes and correct Android namespaces.
- [ ] No unrelated code, local secrets, APKs, decompiler dumps, or signing keys committed.

## Build and testing

- [ ] Gradle bundle build succeeds against the pinned plugin.
- [ ] `reseam bundle list` succeeds for the generated `.reseam` file.
- [ ] Patch metadata/ID/compatibility/dependencies are present as expected.
- [ ] Dry-run succeeds against the exact test APK, when provided.
- [ ] Behavior tested in a disposable patched APK, when runtime testing is actually available.
- [ ] Negative case checked: a different/incompatible app version fails clearly rather than silently patching a wrong method.
- [ ] Logs checked for unresolved targets, multiple matches, skipped dependencies, register allocation, or signature/trust problems.

## Report

Use explicit statuses:

- `Source inspected`: yes/no, with file/artifact names.
- `Compiled`: yes/no.
- `Bundle validated`: yes/no.
- `Dry-run tested`: yes/no, exact APK/version.
- `Runtime tested`: yes/no, tested environment.
- `Known limitations`: exact unresolved errors/assumptions.

A clean build is not proof of runtime correctness. A target-resolution dry run is not proof that the changed feature behaves correctly.