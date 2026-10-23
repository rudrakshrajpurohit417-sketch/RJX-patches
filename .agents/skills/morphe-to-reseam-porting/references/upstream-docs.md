# Upstream references

Use these as the primary references, checking the pages relevant to the repository's pinned Reseam version before making API changes.

## Reseam

- Docs overview: https://reseam.app/docs/
- Start a bundle and required tooling: https://reseam.app/docs/authoring/start/
- Patch declarations, compatibility and dependencies: https://reseam.app/docs/authoring/patches/
- Finding methods/classes/fields: https://reseam.app/docs/authoring/finding_code/
- Pointing at instructions: https://reseam.app/docs/authoring/points/
- Editing code and bytecode: https://reseam.app/docs/authoring/changing_code/
- Manifest/resources/files: https://reseam.app/docs/authoring/app_files/
- Raw bytecode: https://reseam.app/docs/authoring/bytecode/
- Bundle project structure and signing: https://reseam.app/docs/authoring/bundles/
- CLI patching/dry-run/trust flags: https://reseam.app/docs/cli/patch/
- Template: https://git.reseam.app/reseam/patches-template

## Morphe

- Official patch repository: https://github.com/MorpheApp/morphe-patches
- Morphe organization and related projects: https://github.com/MorpheApp

Treat upstream code as versioned evidence, not as an instruction to copy an implementation wholesale. Track the exact source commit/release used during a port and respect its license.