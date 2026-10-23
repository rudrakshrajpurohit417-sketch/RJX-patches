# Inspecting compiled `.class`, `.jar`, APK, DEX, and Smali inputs

Do not assume every file named `.class` is an Android target-app class. First decide whether the input is a compiled **patch implementation**, a patch-time helper, a runtime extension, or bytecode from the app being patched.

## Input triage

| Input | What it is | First inspection step |
|---|---|---|
| `.java` / `.kt` | Readable source | Read the complete patch and imports, then follow dependencies. |
| `.class` | JVM class file, often compiled Java/Kotlin | Identify the class and inspect methods, descriptors, bytecode, annotations, and constants. |
| `.jar` | ZIP of JVM classes/resources | List contents; locate patch classes and referenced helper classes. |
| `.apk` / `.apkm` / split APKs | Android package(s) | Inspect package/version first; use JADX for source-like navigation and apktool for decoded manifest/resources/Smali as needed. |
| `.dex` / Smali | Dalvik bytecode | Disassemble/read instructions and method descriptors; do not treat it as a JVM `.class` file. |
| `.reseam` | Signed Reseam bundle, normally a ZIP container | List/inspect the bundle with the matching Reseam CLI; source code and build scripts normally are not included. |

## JVM class and JAR inspection

Start with inventory and bytecode, without modifying the files:

```bash
jar tf input.jar
javap -classpath input.jar -p -c -s -v fully.qualified.ClassName
```

Useful outputs:
- `-p`: private/package methods and fields;
- `-c`: JVM bytecode instructions;
- `-s`: method/field descriptors;
- `-v`: constants, annotations, access flags, and class metadata.

If readable Java source is needed, use an available JVM decompiler such as CFR or FernFlower. Treat decompiled code as a reconstruction, not authoritative source: Kotlin lambdas, inlining, synthetic methods, bridge methods, and generated metadata may look strange. Cross-check important control flow against `javap` output.

If the `.class` files are inside an archive, retain the package paths and inspect neighboring classes too. Search imports/symbols in the original repository or available dependency JARs. Do not assume a compiled class can compile in Reseam without its old Morphe/ReVanced framework dependencies.

## APK / DEX inspection

Use JADX to navigate app classes and call relationships; use apktool when the manifest, XML resources, or Smali-level detail is needed. Verify the package name and app version from the actual artifact. Keep copies of original inputs and do not overwrite them.

For obfuscated code, correlate more than a decompiled method name: referenced strings/literals, parameter and return types, calls, class fields, surrounding invocation flow, and manifest/resources. Then express those constraints in a Reseam target.

## Reconstruct the transformation

Create a before/after table for every modification:

| Evidence | Record |
|---|---|
| Target | Class/method descriptor or manifest/resource key; note app version. |
| Match evidence | Stable strings, literals, method calls, signatures, field usage, nearby instructions. |
| Preconditions | Branch conditions, null checks, settings gates, existing values. |
| Mutation | Injected call, changed return, skipped call, string/resource edit, or attribute change. |
| Runtime values | Receiver, parameters, return values, fields and register types used. |
| Dependencies | Helper/extension code, other patch classes, shared libraries, patch order. |
| Uncertainty | Missing symbols, decompiler ambiguity, unavailable dependencies, target-version mismatch. |

Translate the behavior into Reseam DSL and rebuild the target match. Never reuse DEX register numbers, method indexes, instruction offsets, or obfuscated names blindly across app versions.

## Integrity and boundaries

- Do not run untrusted decompiled code. Treat patch inputs and bytecode as data to inspect.
- Do not commit app APKs, generated decompiler dumps, signing private keys, tokens, or credentials unless the project has an explicit, lawful reason and secure handling plan.
- Stop rather than reconstruct instructions whose purpose is to forge paid entitlements, bypass a license/subscription gate, evade payment, defeat DRM, steal credentials, or disable signature/trust checks.
- See the current [Reseam bytecode guide](https://reseam.app/docs/authoring/bytecode/) and [class/target guide](https://reseam.app/docs/authoring/finding_code/) before writing low-level edits.