---
name: morphe-to-reseam-porting
description: Port Morphe/ReVanced-style Android app patches to Reseam Kotlin bundles. Use for Java/Kotlin patch sources, compiled .class or .jar files, APK/Dex/Smali analysis, fingerprints, bytecode edits, manifest/resource edits, and Reseam build failures.
compatibility: Requires a checkout of the target patch repository. Building usually requires JDK 17, Android SDK, Gradle wrapper, and a Reseam CLI version matching the project's pinned plugin.
metadata:
  author: RJX Mods
  version: "1.0.0"
---

# Morphe → Reseam Porting Skill

Convert the *behavior* of a Morphe patch into a maintainable Reseam patch. Do not mechanically rename imports or APIs. Inspect the actual implementation, discover the relevant target-app code, write idiomatic Reseam Kotlin, build it, and report exactly what was and was not tested.

## Broad technical scope

- There is no fixed app whitelist or patch-category whitelist. Attempt any legitimate Morphe-to-Reseam port, including UI, playback, media, privacy, accessibility, ad handling, performance, networking, resources, manifest changes, general feature changes, and compatibility fixes. These examples are illustrative, not exhaustive.
- Do not arbitrarily limit a port to one file, method, module, extension, package, or target version. Add helper code, runtime extensions, resources, or multiple patch declarations when the original behavior requires them.
- Do not stop merely because source is incomplete or an input is compiled. Inspect available `.class`/`.jar`, APK, DEX/Smali, decompiler output, dependency archives, upstream history, and nearby implementations; reconstruct the best-supported behavior and explicitly record uncertainty.
- If a crucial artifact is unavailable, first complete all useful analysis possible with the current inputs. Then identify the precise missing item and why it matters instead of imposing a broad refusal or asking generic questions.
- Use examples and preferred approaches as starting points, not a hardcoded list of allowed implementation techniques. When higher-level Reseam APIs cannot express the required legitimate behavior, investigate documented lower-level APIs and extensions.

## Non-negotiable rules

- Work from the current repository and provided artifacts first. Inspect `README`, `settings.gradle.kts`, `manifest.toml`, module layout, existing patches, workflow files, and the pinned Reseam plugin/CLI before editing.
- Use the newest authoritative docs compatible with the project's pinned version. Do not assume that a remembered API still exists.
- Do not invent method signatures, package names, fingerprints, compatibility versions, dependencies, CLI flags, or build results. Verify them against source, decompiler output, docs, or a real build.
- Port the patch's intended behavior—not its Morphe implementation mechanics. Rebuild the target selection and bytecode edits using Reseam primitives.
- Make all changes required to preserve the original patch's intended behavior, including related modules, extensions, resources, and multiple target methods when needed. Avoid unrelated churn and follow the repo's naming and formatting style.
- If source and binary disagree, trust neither blindly: inspect both, explain the mismatch, and use the version that matches the intended target app.
- Respect upstream licenses and notices. Prefer a link to upstream source over copying large portions into this repository.
- A required boundary applies regardless of app, framework, or implementation technique: do not implement patches whose purpose is to defeat paid-license/subscription checks, forge entitlements, evade payment, defeat DRM, steal credentials/tokens, or bypass security/signature verification. If a patch combines such behavior with separable legitimate changes, port the separable changes and explain precisely what was omitted.

## End-to-end workflow

### 1. Inventory the repository and inputs

1. Inspect the full relevant tree and existing branch state. Identify the app module, patch module, extension modules, manifest, pinned Reseam plugin version, CLI version, and current CI workflow.
2. Identify each input type separately: source `.java`/`.kt`, compiled patch `.class`/`.jar`, target `.apk`/`.apkm`/split APKs, DEX/Smali, patch metadata, or a prior build log.
3. Read the whole candidate patch and its direct dependencies before coding. Search for helper classes, shared extensions, fingerprints, options, settings, compatibility declarations, strings/resources, and other patches that must execute first.
4. Determine the exact package name and target app version from reliable metadata when available. Do not guess a version from the newest version number found online.

Read [class and bytecode inputs](references/class-and-bytecode-inputs.md) when the source is binary or decompiled.

### 2. Recover behavior before translation

Write a short behavior summary with:
- trigger/target method or resource;
- original preconditions and relevant control flow;
- what the patch inserts, removes, replaces, or changes;
- values/registers/parameters/return values it depends on;
- dependencies, options, hooks, extensions, and app-version assumptions;
- risks or unknowns that require validation.

Separate the patch definition (the code that runs during patching) from code that is injected into the target app at runtime. A class in the Morphe patch JAR may implement patch-time logic; an extension class may be code added to the patched app. Do not confuse either with the target app's own class.

### 3. Choose the Reseam mechanism

Prefer the simplest valid Reseam feature that matches the source behavior:

- Manifest attributes, XML/resources, or files → Reseam manifest/resource/file editing APIs.
- A behavior in a method → declare a semantic `method(...)` target and edit it via `before`, `after`, or `replace`.
- A single instruction/call/string/literal → use a narrow point/instruction target, then edit that point.
- Existing app class or field → use `klass(...)`, `fieldOfType(...)`, or documented class/field targets.
- Logic too long or awkward for a bytecode block → put app-runtime Kotlin in an extension and expose only the methods needed by the patch, following current Reseam extension docs.
- A transformation not expressible through a high-level API → investigate documented raw-bytecode APIs, extension code, or a combination of approaches. Inspect the exact bytecode and confirm API behavior against the project's Reseam version; do not exclude a technique merely because it is lower-level.

Do not copy Morphe instruction indexes or assume an old fingerprint maps one-to-one. Reseam searches the target APK independently; choose constraints that uniquely identify the intended code.

### 4. Implement as a Reseam patch

1. Place source under `apps/<app>/patch/src/main/kotlin/<package path>/` using the repository's established layout.
2. Declare a **public top-level** Kotlin `val` with `patch("Display name") { ... }`. Private properties or patches nested inside an object are not discovered as patch declarations.
3. Set an accurate description, known app compatibility, options, and dependencies where needed. Never overstate compatibility.
4. Identify the intended target set from the original patch. If it targets one method, make the match unique; if it intentionally targets several methods/classes/resources, support and verify that complete set rather than arbitrarily forcing a single match. Prefer stable strings, calls, types, class relationships, and behavior over obfuscated names or brittle offsets.
5. Use documented Reseam bytecode builders for values and branches. Remember that Kotlin inside the patch DSL executes at patch time to *construct* instructions; use Reseam branch builders for runtime branches.
6. Give each patch a stable Kotlin property/package path. Renaming them may change patch IDs used by users or dependencies.
7. Fail clearly when a required target is missing. For optional or multi-target patches, report which targets matched, which were skipped, and why; do not silently drop intended behavior or force all patches into a single-target design.

Use [the migration map](references/migration-map.md) for framework-specific translation pitfalls.

### 5. Build, validate, and test

Run the repo's existing checks first. Normally:

```bash
./gradlew --no-daemon bundle
reseam bundle list build/reseam/<bundle-name>.reseam
```

Use the actual generated path from the current template and manifest; do not assume the placeholder path above. If the repository has a CI workflow that builds the bundle, inspect the job result and logs too.

Where an exact test APK and trusted bundle key are available, follow the current CLI docs for a dry run, then apply the bundle to a disposable copy of that APK. For example, current Reseam CLI docs show `reseam patch ... --dry-run`; verify the required `--bundle`, `--trust`, and output options for the installed CLI. Do not patch the user's only copy in place.

After a successful build:
- verify the bundle lists the intended patch and compatibility metadata;
- run a dry-run against the exact target app build if one is available;
- test actual behavior only when you can genuinely run the patched app or receive a test result;
- inspect build output for skipped patches, duplicate target matches, register allocation failures, missing dependencies, or manifest/resource errors;
- leave no signing private key in Git. Keep persistent release keys in a secure secret store; an ephemeral CI key is suitable only for smoke-test artifacts, not a stable published signing identity.

Report results precisely using separate statuses: `compiled`, `bundle validated`, `dry-run tested`, and `runtime tested`. Do not call a patch "working" merely because the source compiles.

## Error-driven debugging

- **Patch not discovered:** confirm the declaration is a public top-level `val`, package files are inside the configured module tree, and the app module was included by the workspace plugin.
- **No method match:** inspect the rejection details; check app version, strings, parameter/return descriptors, calls, class constraints, and whether the APK's code actually contains the expected behavior.
- **Too many matches:** add evidence-based constraints; do not solve ambiguity with `first()` unless matches are genuinely interchangeable.
- **API/import/compile error:** compare the installed Reseam plugin version with the current official reference and local template examples; do not guess replacement imports.
- **Patch runtime failure:** verify parameter/return types, static versus instance calls, access restrictions, branch paths, object/register types, and whether an extension method is exported/bound correctly.
- **Register allocation error:** move nontrivial logic into an app extension rather than trying random register changes.
- **Bundle signature or trust error:** distinguish bundle-signing keys from APK-signing keys. Never disable trust verification to make a test pass.
- **Version-specific breakage:** narrow or update compatibility metadata and test a known target version. Do not claim support for every app version without evidence.

## Deliverables for every port

Return the actual edited source files and a concise report stating:
1. original patch and intended behavior;
2. Reseam implementation and files changed;
3. target app/package/version assumptions;
4. build/validation/runtime results, including errors still unresolved;
5. exact artifact path or link if a `.reseam` bundle was actually produced;
6. source/API references used.

Do not stop after writing a plan or placeholder when enough evidence exists to implement the port. Proceed as far as possible using the available inputs, document assumptions, build and validate the implementation, and ask for a specific missing artifact only when it materially blocks the next step. Do not claim unsupported compatibility or testing.

## Reference files

- [Migration map](references/migration-map.md)
- [Class/JAR/DEX inspection](references/class-and-bytecode-inputs.md)
- [Port validation checklist](references/validation.md)
- [Reseam official docs and Morphe source](references/upstream-docs.md)