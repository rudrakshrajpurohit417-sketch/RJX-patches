# RJX Patches

Custom patches for use with [Morphe](https://morphe.software).

## How to use

Add this source in Morphe Manager:

https://morphe.software/add-source?github=rudrakshrajpurohit417-sketch/RJX-patches

Or paste the GitHub repo URL as a patch source.

## Patches

| App | Package | Patches |
| --- | --- | --- |
| EX File Manager | `com.ace.ex.file.manager` | Enable Premium |

### EX File Manager — Enable Premium

Forces `ace.a37.m138i()` (the entitlement gate) to always return `true`.  
Tested against decompiled **1.6.2** (versionCode **51**).

## Build locally

1. Create a GitHub PAT with `read:packages`.
2. Put credentials in `~/.gradle/gradle.properties` (never commit them):

```
gpr.user=YOUR_GITHUB_USERNAME
gpr.key=ghp_xxxxxxxx
```

3. Also copy the Gradle wrapper jar from the [Morphe patches template](https://github.com/MorpheApp/morphe-patches-template) into `gradle/wrapper/gradle-wrapper.jar` if missing.

4. Build:

```bash
./gradlew buildAndroid
```

5. Output: `patches/build/libs/patches-*.mpp`

Apply the `.mpp` with Morphe Manager or Morphe Desktop on the official APK.

## License

GPLv3 — see [LICENSE](LICENSE). Name "RJX Patches" is intentionally distinct from the Morphe project (see [NOTICE](NOTICE)).
