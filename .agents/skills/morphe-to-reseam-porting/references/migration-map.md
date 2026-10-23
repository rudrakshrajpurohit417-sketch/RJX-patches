# Porting semantics from Morphe to Reseam

This reference maps framework concepts, not source code. It contains **no sample patch implementation**. Never copy a row, invented method, hypothetical fingerprint or generic manifest edit into the target repository as a substitute for the original patch.

## Translate the real source operation

| Original Morphe-side material | Required Reseam-side work | Evidence required |
|---|---|---|
| Patch declaration, annotations and metadata | Recreate an actual discoverable Reseam top-level patch declaration with the matching user-facing intent and supported metadata | The original declaration, compiled annotation/constants, exact patch ID/name and current Reseam docs |
| Target application, package name and compatibility range | Declare compatibility for the actual package and verified target versions | Original patch metadata and target APK/version metadata |
| Fingerprints and matchers | Rebuild the predicate from actual strings, calls, descriptors, class/field relations and instruction context | Every original fingerprint matcher plus inspection of the target APK |
| Multiple fingerprints, target methods or classes | Preserve the entire intended target set and each target's distinct edit | All original target declarations and results from resolution against the target APK |
| Bytecode patch execution | Reconstruct every original mutation using the Reseam APIs supported by the pinned version | The original patch implementation, not just its name or documentation |
| Entry/exit/instruction insertions | Preserve execution position, return behavior, control flow and side effects using the appropriate method or instruction-point APIs | Original instruction/branch behavior and the target method's actual bytecode |
| Method replacement | Preserve the complete method contract, parameters, types, exceptions, branches, side effects and return values | Full original replacement logic and target signature |
| Helper classes and utility code | Port each required dependency to patch-time code, shared code or app-runtime extension code according to where it executes | Class references, dependency metadata, build outputs and call graph |
| Extension code injected into the patched app | Rebuild the actual runtime code and required bindings/exports; connect calls from patch code | Original extension sources or compiled artifacts, manifest declarations and runtime call sites |
| Patch options and user preferences | Recreate option names, types, defaults, conditions and observable effects | Original option declaration, all reads of the option and its effect on each code path |
| Dependencies and patch ordering | Preserve prerequisite relationships and initialization assumptions | Original dependency declarations and the code that each dependency establishes |
| Manifest, resources, assets and packaged files | Recreate each actual mutation and any code that references the changed resources/files | Original transformation plus names/IDs/layouts from the exact target app |
| Reflection, dynamic lookup or framework hooks | Identify the actual runtime resolution path and recreate its behavior with supported Reseam mechanisms | Call sites, lookup strings/types, initialization order and actual target code |
| Obfuscated names and version-specific behavior | Match semantically using evidence, and record version-specific variants rather than assuming names or offsets stay stable | APK/Dex inspection for each target version claimed to be supported |
| Patch bundle, metadata and signing | Build and inspect the actual bundle with the repository's pinned Reseam toolchain | Actual generated bundle, CLI validation output and trusted key configuration |

## Required source-to-port mapping

Build a ledger for every original patch declaration and dependency. Each behavior row must identify:

- Original source file/class/method or compiled-class method descriptor.
- The original target fingerprint/predicate and every target it is expected to match.
- Exact mutation and where it executes.
- Branch conditions, options/settings, parameters, return semantics and side effects.
- Supporting helper/extension/resource/dependency code.
- The concrete Reseam file/declaration that implements it.
- Validation evidence and residual uncertainty.

Each original behavior must be accounted for as **ported**, **proven unnecessary in the target architecture**, or **unresolved with a specific reason**. Unresolved behavior prevents declaring the port complete. Do not label behavior as unnecessary just because the implementation is difficult.

## No-template rule

Do not put an illustrative Kotlin patch, toy manifest edit, dummy target, fictional fingerprint, placeholder extension, or generic smoke-test patch into the output. Use the current official Reseam documentation to determine API syntax; use original Morphe source/artifacts to determine what code to write. If the original is unavailable, inspect all supplied evidence and request the exact missing source/artifact instead of manufacturing replacement behavior.

## Authoritative API documentation

Use the current Reseam docs for the project’s pinned version:

- [Patch declarations and metadata](https://reseam.app/docs/authoring/patches/)
- [Finding target code](https://reseam.app/docs/authoring/finding_code/)
- [Instruction points](https://reseam.app/docs/authoring/points/)
- [Changing code and bytecode](https://reseam.app/docs/authoring/changing_code/)
- [Manifest, resources and files](https://reseam.app/docs/authoring/app_files/)
- [Low-level bytecode](https://reseam.app/docs/authoring/bytecode/)
- [Bundle structure and build process](https://reseam.app/docs/authoring/bundles/)
- [CLI target validation](https://reseam.app/docs/cli/patch/)

If a remembered signature conflicts with the pinned toolchain’s source/docs, the pinned toolchain wins. Do not write a plausible but unverified substitute.
