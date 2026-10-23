# Inspecting the actual compiled Morphe patch and target bytecode

A Morphe implementation may arrive as Java/Kotlin source, individual JVM `.class` files, a JAR, or compiled artifacts from the Morphe build. The Android app being patched may separately arrive as an APK, split APK set, DEX, or Smali. These are different artifacts and must not be confused.

## Identify each artifact

- **Patch class/JAR:** code that runs during patching. It may contain declarations, annotations, fingerprints, bytecode edit instructions, helper methods and metadata.
- **Runtime extension class/JAR/DEX:** code that may be injected into the modified Android app. It can execute later on the device and requires correct packaging, types and bindings.
- **Target APK/DEX/Smali:** the application code and resources the patch modifies.
- **Source repository/release:** readable source for a particular version; it may differ from the supplied compiled artifact.
- **Reseam bundle:** a signed distribution archive. It ordinarily contains compiled patch/extension payloads and metadata, not the editable project source.

Never infer that an input class is target-app bytecode just because it ends in `.class`. Establish its role from package paths, class dependencies, bytecode descriptors, annotations and the build layout.

## Inspect a JVM class or JAR

Inventory the archive and inspect the exact class using installed JDK tooling:

```bash
jar tf <actual-input.jar>
javap -classpath <actual-classpath> -p -c -s -v <actual.fully.qualified.ClassName>
```

Replace each angle-bracketed path or class name with a value discovered from the real artifact. These are inspection commands, not patch source. Never place these placeholders in an output patch.

- Access flags and private methods can reveal declarations and helper structure.
- Bytecode reveals execution order, calls, branches, field access and return paths.
- Descriptors establish the exact JVM type contract.
- Annotations and constants may expose patch metadata, option names, target patterns and compatibility declarations.
- Synthetic methods, Kotlin lambda classes, bridge methods and generated metadata may carry behavior omitted or obscured by decompilation.

If source is not present, use an available JVM decompiler as a reading aid. Cross-check each important transformation against bytecode. Inspect neighboring classes, superclass/interfaces, call sites and dependencies. Do not assume the old Morphe framework classes exist in Reseam or that the old patch class can simply be recompiled.

## Inspect the target app

Use JADX for navigation and call relationships, and apktool/Smali when exact DEX instructions or decoded manifest/resources are required. Identify package, version/build, split APKs and relevant classes from the actual artifact. Preserve an untouched copy.

For obfuscated apps, correlate multiple signals: string constants, call targets, parameter and return descriptors, field use, neighboring control flow, resource references and call relationships. Search the app versions relevant to the original patch; one version's obfuscated method names, offsets or registers must not be assumed stable in another.

## Reconstruct exact behavior

For every original target, determine:

- How the target is selected and which target variants are intended.
- The original matching predicate and any dependencies on prior patches.
- Every inserted, removed, replaced or redirected instruction and its exact position.
- Branch conditions, parameters, receiver, types, return semantics, field access, exceptions and side effects.
- Which code executes in the patching process and which code executes inside the patched app.
- Which helpers/extensions/options/resources/assets are required.
- Which target app version/revision the evidence describes.
- Which details remain uncertain because source, symbols, dependencies or an exact target APK are unavailable.

Use those observations to construct the Reseam behavior-coverage ledger. No generic patch is an acceptable substitute for missing bytecode or an unidentified source implementation.

## Integrity and handling

- Do not execute untrusted patch classes or decompiled code merely to discover what they do.
- Keep original input artifacts unchanged; work on copies and record identifiers/hashes when available.
- Do not commit target APKs, dumps, signing private keys, tokens, or credentials.
- Do not reconstruct behavior intended to forge paid entitlements, bypass payment/license controls, defeat DRM, steal credentials, or disable signature/trust checks.
- Verify low-level API signatures against the pinned Reseam docs before implementing bytecode edits.

Useful references: [Reseam bytecode](https://reseam.app/docs/authoring/bytecode/), [target finding](https://reseam.app/docs/authoring/finding_code/), [instruction points](https://reseam.app/docs/authoring/points/).
