# Framework migration map: Morphe/ReVanced-style patches → Reseam

This is a conceptual mapping, not an API replacement table or an exhaustive whitelist. Port legitimate behavior across app categories and patch types; verify the exact Reseam DSL against the project's pinned version, and combine high-level targets, lower-level bytecode operations, extensions, resources, and manifest edits as required.

| Morphe/ReVanced-side concept | Reseam-side approach | Important caveat |
|---|---|---|
| `@Patch`, `@Name`, `@Description`, metadata on a patch class | Public top-level Kotlin `val x = patch("Name") { description(...); ...; execute { ... } }` | A private or nested patch value is not discovered. |
| App/package/version compatibility metadata | `compatibleWith(...)` in the patch declaration | Only declare versions supported by evidence/tests. |
| Patch dependencies/patch ordering | Reseam `dependsOn(otherPatch)` | Dependencies must exist in the bundle or be declared from another bundle per docs. |
| Options/preferences in the old patch manager | Reseam patch options or in-app settings, as appropriate | Recreate user-facing semantics; don't map names alone. |
| `MethodFingerprint`, `Fingerprint`, opcode/string scans | Reseam semantic targets such as `method("label") { strings(...); returns(...); params(...); calls(...) }` | A fingerprint is evidence, not a transferable identity. Match a unique method in the actual target APK. |
| `BytecodePatch` or patch-class `execute(context)` | Reseam `execute { ... }` block | Old context/helper APIs do not exist just because the names look similar. |
| Insert at method entry | Reseam method target `.before { ... }` | Use only after proving the target method and expected receiver/parameters. |
| Insert before each return / after method behavior | Reseam `.after { ... }` or a narrow instruction point, depending on desired semantics | Reseam method `after` executes before returns; branches can affect point-level hooks differently. |
| Replace entire target method | Reseam `.replace { ... }` | Must preserve correct return type, side effects, and all necessary paths. |
| Insert/replace at a specific instruction | Reseam point target plus `.before { ... }` / `.after { ... }`; inspect current point docs | A text match may occur more than once; make the point unambiguous. |
| Smali strings/opcodes/register manipulation in the source patch | Prefer Reseam's higher-level target/value DSL; use raw-bytecode APIs only if necessary | Do not transplant register indexes from a different DEX build. |
| Runtime helper/extension classes | Reseam app `extensions/<name>/` Kotlin code with documented exports/bindings | Patch-time code and injected app-runtime code execute in different environments. |
| Manifest patch | Reseam manifest editing inside `execute` | Check namespace, existing attributes, and missing-node behavior on the actual app manifest. |
| Resource/XML/file patch | Reseam resource/file APIs from current docs | Confirm resource names/IDs after resource shrinking or obfuscation. |
| Patch IDs and references | Reseam IDs derive from Kotlin package + top-level property; bundle name comes from `manifest.toml` | Renaming the property/package may break downstream references. |
| Old tool's patch bundle/install/signing workflow | Reseam signed `.reseam` bundle and current Reseam CLI/Manager workflow | Bundle signature/trust and APK signing are separate concerns. |

## Safe conversion procedure

1. Record the original patch's target, trigger, edits, conditions, dependencies, and side effects.
2. Find the same method/field/resource in the exact target app version. Use semantic features rather than names alone where obfuscation applies.
3. Translate each edit separately. Explain any dropped or changed behavior instead of silently approximating it.
4. Validate the intended target set: require a unique match when the original patch targets one item, and support the full verified set when it intentionally targets multiple items. Report missing or ambiguous matches clearly; do not arbitrarily limit a port to one target.
5. Build and validate. If the current DSL differs from any example here, the installed project's Reseam reference/template wins.

## Minimal Reseam shape

```kotlin
package app.rjx.example

import app.reseam.patch.patch

val exampleManifestChange = patch("Example manifest change") {
    description("Demonstrates the declaration structure; change only an attribute that is valid for the target app.")

    execute {
        manifest.edit {
            val application = findByTag("application").firstOrNull()
                ?: error("manifest has no <application> element")
            application["android:debuggable"] = "true"
        }
    }
}
```

This example illustrates declaration structure and a manifest edit only. `android:debuggable` is for local testing, not a substitute for the functional behavior of an arbitrary Morphe patch.