# RJX Patches

Custom patches for [Morphe](https://morphe.software).

## Add to Morphe Manager

https://morphe.software/add-source?github=rudrakshrajpurohit417-sketch/RJX-patches

After a release exists with a `.mpp` asset, enable the source and patch your APK.

## Patches

| App | Package | Patch |
| --- | --- | --- |
| EX File Manager | `com.ace.ex.file.manager` | **Enable Premium** |

### EX File Manager — Enable Premium

Tested on **1.6.2** (versionCode **51**).

- Forces `ace.a37.i()` entitlement gate → always true
- Forces `ace.t02.h()` → ACTIVE + LIFETIME `hg6` state
- Forces `PremiumVerificationWorker.d()` → success so background verify cannot revoke

## Build locally (required once)

### 1. GitHub PAT

Create a classic token with scope **`read:packages`**:
https://github.com/settings/tokens

Add to `~/.gradle/gradle.properties` (do **not** commit this file):

```
gpr.user=YOUR_GITHUB_USERNAME
gpr.key=ghp_YOUR_TOKEN
```

### 2. Gradle wrapper jar

```bash
mkdir -p gradle/wrapper
curl -L -o gradle/wrapper/gradle-wrapper.jar \
  https://github.com/MorpheApp/morphe-patches-template/raw/main/gradle/wrapper/gradle-wrapper.jar
chmod +x gradlew
```

Or clone template and copy the jar.

### 3. Build

```bash
git clone https://github.com/rudrakshrajpurohit417-sketch/RJX-patches.git
cd RJX-patches
# ensure gpr.user / gpr.key set (step 1)
./gradlew buildAndroid
```

Output: `patches/build/libs/patches-*.mpp`

Load that file in Morphe Manager / Desktop and patch the official APK.

## Release for Manager source

1. Repo Settings → Actions → General → allow Actions to create/approve PRs (if using template release flow)
2. Push to `main` triggers `.github/workflows/release.yml` (builds `.mpp`)
3. Create a GitHub Release and attach the `.mpp`, **or** let semantic-release do it if fully configured
4. Users add the source URL above

## License

GPLv3. Name **RJX Patches** is distinct from the Morphe project (see NOTICE).
