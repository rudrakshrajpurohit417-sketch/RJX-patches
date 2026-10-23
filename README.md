# RJX Patches

Reseam patch source maintained in this repository.

## AdGuard test patch

The first target is a non-premium-related AdGuard debugging patch. It marks the app's manifest as debuggable for local testing; it does not change license or subscription checks.

## Build

Open **Actions → Build Reseam bundle → Run workflow**. The workflow checks out the official Reseam template, overlays the patch source from this repository, and runs the Gradle bundle task. The resulting bundle is uploaded as a workflow artifact.

This is an initial smoke test. A successful build confirms the source compiles against the current Reseam template; runtime compatibility still needs testing against the exact AdGuard APK version.
