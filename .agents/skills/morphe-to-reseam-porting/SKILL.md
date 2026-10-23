---
name: morphe-to-reseam-porting
description: Faithfully port identified original Morphe patches to Reseam, including compiled patch classes, fingerprints, dependencies, extensions, bytecode, resources, and manifest edits. Never substitute demo or placeholder patches for the original implementation.
compatibility: Requires a checkout of the target patch repository and compatible JDK/Android SDK/Gradle/Reseam tooling for build validation.
metadata:
  author: RJX Mods
  version: "1.1.0"
---

# Original Morphe → Reseam Porting Skill

This skill exists to convert an **identified, original Morphe patch** into a functionally equivalent Reseam implementation. The deliverable is the real port of the requested patch—not a tutorial, a toy patch, a smoke test, a plausible-looking substitute, or a demonstration of Reseam syntax.

## Mandatory source-fidelity contract

1. **Identify the original patch first.** Use the artifact supplied by the user when one is supplied. If only a patch name or feature is given, locate the corresponding original patch in the official Morphe source repository and identify the exact source revision. Search the real patch tree, its metadata, fingerprints, helper classes, shared code, dependencies, extensions, and history. Do not guess the implementation from a display name or a feature description.
2. **Never fabricate a replacement.** Do not create a sample patch, demo patch, placeholder patch, smoke-test patch, generic manifest edit, or made-up implementation as a stand-in for the requested original. Do not invent target methods, fingerprints, class names, signatures, resource IDs, option behavior, dependencies, or test results.
3. **No fake completion.** A partially ported patch must be marked partial. A compile-only result is not a behaviorally complete port. Never say that a port is complete if original operations, dependencies, target cases, options, or runtime effects remain unimplemented or unverified.
4. **Build a behavior-coverage ledger.** For every original patch class/declaration, list each target/fingerprint, matching constraint, transformation, condition/branch, injected call, option, dependency, extension, resource/manifest change, and version assumption. Map each item to its Reseam implementation and the corresponding source evidence. Account for every item as ported, intentionally unnecessary with evidence, or unresolved. Do not silently drop anything.
5. **Preserve the original behavior, not the old framework syntax.** Morphe APIs and execution models may not exist in Reseam. Reconstruct the semantics in the Reseam model, including helper code, runtime extensions, resources and changes across multiple classes/methods where required. Do not merely rename imports, and do not force a multi-target original patch into a single-target rewrite.
6. **Compiled input is real source evidence.** If the original implementation is in `.class` files, a JAR, or compiled Morphe artifacts, inspect class metadata, annotations, constant pools, descriptors, method bodies, call sites, and neighboring classes. Decompile as an aid and cross-check ambiguous control flow against bytecode. Search for the matching upstream source and dependencies, but do not replace a supplied binary with an unrelated upstream revision. When source and binary differ, record which artifact/version is the target and port that behavior.
7. **Missing input does not authorize fabrication.** Perform all useful analysis with the available artifact, then identify the exact unresolved item and why it blocks faithful porting. Ask only for that specific item. Do not create a stand-in patch while waiting for it.
8. **Use authoritative, version-matched information.** Inspect the target project's Reseam template, plugin version, CLI version, official docs, and local conventions before implementing. Confirm API names and call semantics from the docs/source for that version instead of guessing.
9. **Keep changes focused on the actual port.** Edit the real patch files and any required related extensions/resources/build configuration. Do not leave unrelated demo patches or generic scaffolding in the deliverable. Preserve unrelated user changes.
10. Respect source licenses and notices. Do not run untrusted decompiled code. Do not commit APKs, private keys, tokens, or credentials.

## Scope

There is no fixed app whitelist or patch-category whitelist. Attempt the complete, legitimate original behavior across app categories and implementation layers. The technical approach can include Kotlin patch declarations, semantic method/class/field targets, instruction points, low-level bytecode, runtime extensions, shared code, resources, manifest edits, and file changes as required by the source. Preferred APIs are guidance, not a whitelist.

The required safety boundary remains: do not implement behavior intended to forge paid entitlements, bypass paid license/subscription checks or payment, defeat DRM, steal credentials/tokens, or bypass signature/trust verification. If a source combines such behavior with separable legitimate changes, explain the blocked portion and only port the separable behavior; do not claim a full port of the original.

## Required workflow

### 1. Identify and pin the source

- Inspect the working repository, branch state, build layout, bundle manifest, Reseam plugin, CLI, and available test infrastructure.
- Determine which original patch is requested from the exact supplied file/class/path/name. If it is not supplied, locate it in the official Morphe repository. Do not assume that a similarly named patch, a ReVanced predecessor, or a different release is identical.
- Record the original source URL and immutable commit/release identifier where available. For uploaded binaries, record the exact filename and hash when the tooling permits; retain the original input unchanged.
- Locate related original declarations, base classes, fingerprints, annotations, helpers, shared patch modules, injected extensions, resources, settings/options, dependencies and patch-order requirements.
- Establish the target app package and version from the real APK/artifact or trustworthy metadata. If the exact APK is unavailable, continue source analysis, but label APK-specific target matches and runtime compatibility unverified.

### 2. Recover the complete original behavior

Inspect the full original patch implementation and its direct and transitive dependencies. Separate patch-time code from runtime code that gets injected into the Android app. For compiled inputs, follow the procedure in [class-and-bytecode-inputs.md](references/class-and-bytecode-inputs.md).

Create a behavior-coverage ledger before finalizing implementation. For each source operation, capture:

- original patch declaration and metadata, target package/version and patch ID/name;
- every fingerprint, selector, match constraint and matched target (including multiple targets);
- instruction-level insertion/replacement/removal and control-flow semantics;
- receiver, parameter, return, field, local/register, type and access semantics;
- conditions, branches, null behavior, exceptions, side effects and ordering;
- patch options, settings, user-visible descriptions/defaults and dependencies;
- all helper classes, runtime extension code, resource/manifest/file edits and generated assets;
- compatibility constraints and known version-specific changes.

When behavior depends on code in a superclass, helper, extension or dependency, inspect that code too. Do not infer complete behavior from a patch class name, description, annotation or fingerprint alone.

### 3. Translate the actual patch into Reseam

- Use the real Reseam module structure and declarations discovered from the repository's template and docs. Reseam discovers public top-level patch values; ensure the original patch is represented by actual discoverable declarations.
- Recreate all original targets from evidence in the target APK, using Reseam's supported target and point model. Rebuild fingerprint logic semantically; old fingerprints, obfuscated names, instruction positions, indexes, or register numbers are not automatically portable.
- Translate each edit in execution order and preserve the meaning of every branch, return, call, field access, argument, exception path and side effect. Confirm static versus instance invocation and descriptors.
- Implement the original options, metadata, compatibility, dependencies and ordering. Do not replace option-controlled behavior with a hard-coded value.
- Port required runtime extensions, shared code, resources, manifest changes and assets. Preserve inter-module dependencies and patch-to-extension bindings.
- When high-level targets cannot express the required behavior, inspect the current low-level Reseam API and use the appropriate bytecode or extension mechanism. Do not abandon functionality simply because the first API tried is insufficient.
- Where the original intentionally handles multiple methods/classes/instructions, port and verify the full intended target set. A missing match must not silently turn a multi-target patch into a partial conversion.
- If a source behavior cannot yet be represented, keep it explicitly unresolved in the coverage ledger. Do not replace it with a merely similar behavior and describe it as equivalent.

Use the [migration map](references/migration-map.md) as a conceptual index only. It is not a code template and is not exhaustive.

### 4. Validate against the real target

1. Build the bundle using the repository's actual pinned toolchain and correct bundle configuration.
2. Inspect the build output and the generated bundle metadata. Confirm the actual original patch declarations, IDs, options, compatibility and dependencies are present.
3. Run Reseam's bundle validation/listing command against the actual generated artifact using the current CLI's required trust flags.
4. If the exact target APK (including required splits) is available, run the current CLI dry-run against it. Inspect the per-patch outcomes, target-resolution details and errors. Resolve failures rather than treating them as warnings to ignore.
5. Apply to a disposable copy of the target APK only when appropriate, then test the modified behavior against the original behavior contract. Do not overwrite the sole original APK.
6. Test relevant branches, option settings and target variants when practical. Record exactly which versions and cases were tested. Never claim runtime testing unless it actually occurred.
7. Keep bundle signing separate from APK signing. Do not disable trust verification or commit private signing material to make a test pass.

A compile or bundle-validation pass proves only that the bundle builds and its metadata can be read. A dry run proves only the validations it actually executes. Functional equivalence requires evidence for the original behavior and target version.

### 5. Handle failures without faking success

- **Original source not found:** search official source, history and available artifacts; if still missing, request the precise original patch file or class. Do not synthesize a generic replacement.
- **Binary-only patch:** inspect bytecode and dependencies, use decompilation only as an aid, and list any uncertainty caused by unavailable symbols or runtime dependencies.
- **No target match:** compare package/version and each original fingerprint clue against the actual APK; fix the port or report the exact unresolved target.
- **Multiple matches:** reproduce the original intended target set, or narrow a single-target selector with evidence. Never pick the first match merely to force a pass.
- **Compile/API failure:** read the pinned Reseam API/docs and repository sources; correct the implementation instead of inserting a dummy method or bypassing the check.
- **Runtime failure:** inspect types, stack/register behavior, branches, exceptions, extension bindings, resources and side effects; fix the real port.
- **Incomplete dependencies:** port the required legitimate dependencies or record the exact blocker. Do not remove dependencies and claim equivalence.
- **Signing/trust failure:** diagnose the actual key/configuration problem. Never turn off trust verification.
- **Target-version drift:** compare the source revision and app version, update the target evidence, and report each tested version separately.

### 6. Required deliverables

Return only the actual files for the original port, its necessary dependencies, and relevant build/test configuration. Do not add a demo or placeholder patch to illustrate a conversion.

For every port, include:

- **Source identity:** original path/repository, source revision or supplied artifact identity.
- **Target identity:** package, app version/build and APK or split set inspected.
- **Coverage ledger:** every original behavior mapped to the actual Reseam code, or explicitly unresolved with a reason.
- **Implementation:** files changed and how runtime extensions/resources/dependencies are connected.
- **Verification:** separate factual results for source inspection, compilation, bundle validation, target dry-run, and runtime tests.
- **Residual gaps:** any unported behavior, unmatched fingerprints, unknown dependencies, unsupported bytecode semantics or untested branches.
- **Artifacts:** exact bundle location/link only if that bundle was actually produced.

Use the word **complete** only when the coverage ledger has no unexplained omissions and the stated level of validation supports that claim. Otherwise label the port partial and say exactly what remains.

## Reference files

- [Migration map](references/migration-map.md)
- [Class/JAR/DEX inspection](references/class-and-bytecode-inputs.md)
- [Port validation checklist](references/validation.md)
- [Official documentation and source repositories](references/upstream-docs.md)
