# Original Morphe → Reseam port validation

This checklist validates a real port of an identified upstream patch. It is not a list of optional niceties and it is not a patch-category whitelist.

## Source identity and scope

- [ ] The original patch is identified by source path/class and immutable source revision or by the exact supplied binary/artifact identity.
- [ ] The target package and the APK/app version under test are known, or any unavailable target identity is explicitly recorded as a blocker to APK-specific verification.
- [ ] The full original patch implementation has been read or its compiled bytecode has been inspected.
- [ ] Every direct and transitive dependency, fingerprint, helper, extension, option, resource and related patch has been found or recorded as unresolved.
- [ ] Patch-time code is distinguished from runtime extension code.
- [ ] The source revision used matches the intended original patch; a similarly named or newer implementation has not been silently substituted.

## Original-behavior coverage

- [ ] A behavior-coverage ledger maps every original declaration and mutation to the concrete Reseam implementation.
- [ ] Every fingerprint/matcher and the full intended target set are represented.
- [ ] Every instruction insertion, replacement/removal, branch, call, field access, argument, return path and side effect is accounted for.
- [ ] Options, preferences, dependencies, ordering, helper classes, runtime extensions, manifest/resource/file changes and compatibility metadata are preserved where applicable.
- [ ] Any change in behavior is explained and evidence-backed.
- [ ] No original behavior is silently omitted because it was difficult to translate.
- [ ] No demo, sample, placeholder or smoke-test patch has been added in place of the requested original implementation.
- [ ] Any unresolved item is identified precisely; unresolved original behavior means the port is partial, not complete.

## Implementation correctness

- [ ] All patch declarations are discoverable using the pinned Reseam API.
- [ ] Target predicates are based on inspected app code rather than copied indexes, instruction offsets or register numbers.
- [ ] Single-target patches resolve to the intended target; multi-target patches preserve the original intended target set.
- [ ] JVM/Dex types, receiver semantics, parameter order, return types, static/instance invocation, branches, exceptions and access restrictions are correct.
- [ ] Required extension code and bindings/exports are included and referenced correctly.
- [ ] Resource/manifest mutations use names and structures verified against the actual APK.
- [ ] No unrelated project changes, unneeded demo sources, secrets, credentials, or private signing keys are included.

## Build and target validation

- [ ] The project builds with its pinned Reseam/Gradle/JDK/Android SDK toolchain.
- [ ] The actual generated bundle's metadata and patch inventory have been inspected.
- [ ] The current Reseam CLI validates the actual generated bundle.
- [ ] A dry run is executed against the exact target APK/split set when available; every original patch outcome is inspected.
- [ ] A disposable patched build is exercised for relevant behavior when runtime testing is available.
- [ ] Important options and branches are tested where practical.
- [ ] Negative/incompatible target versions fail transparently instead of silently matching the wrong code.
- [ ] Logs have been reviewed for unresolved or duplicate matches, skipped dependencies, type/register problems, resource failures and signature/trust failures.
- [ ] Each validation result is supported by a real log or observed result, not inferred from source code.

## Report the evidence

Report these separately:

- **Original source inspected:** exact source path/revision or binary identity.
- **Behavior coverage:** complete, partial, or blocked, with unresolved ledger entries.
- **Compiled:** exact toolchain result.
- **Bundle validated:** exact artifact and CLI result.
- **Target dry-run:** exact APK/app version and patch-by-patch output.
- **Runtime tested:** exact build/environment and observed behavior.
- **Residual gaps:** anything still missing, ambiguous, or untested.

Never call a conversion complete based solely on compilation or a successful bundle listing. “Complete” requires the source-to-port ledger to account for every original behavior and a clear statement of which validation stages actually ran.
