# RJX Patch Porting Skill

An Agent Skill for turning Morphe/ReVanced-style Android patch implementations into maintainable Reseam Kotlin patches.

## Use the skill

Skill path: `.agents/skills/morphe-to-reseam-porting/SKILL.md`

In Google Antigravity, open this repository as the workspace and invoke:

```text
/morphe-to-reseam-porting
```

You can also let a compatible coding agent discover the skill automatically. Provide the original Morphe patch source or compiled artifact (`.class`/`.jar`), plus the target APK/app version when available. The skill guides the agent to inspect the implementation, recover its behavior and dependencies, recreate the target selection in Reseam, write the Kotlin patch, build the bundle, and report what was actually tested.

## Reference guides

- [Framework migration map](.agents/skills/morphe-to-reseam-porting/references/migration-map.md)
- [Class, JAR, APK, DEX, and bytecode inspection](.agents/skills/morphe-to-reseam-porting/references/class-and-bytecode-inputs.md)
- [Validation checklist](.agents/skills/morphe-to-reseam-porting/references/validation.md)
- [Upstream docs and source links](.agents/skills/morphe-to-reseam-porting/references/upstream-docs.md)

## Validation

The GitHub Actions workflow at [Validate Agent Skills](.github/workflows/validate-agent-skills.yml) checks skill metadata and local references.

## Scope

This repository is focused on the Morphe-to-Reseam porting skill and its supporting reference material.

The skill supports legitimate feature ports and compatibility fixes; it does not implement paid-license/subscription bypasses, payment evasion, DRM defeat, credential theft, or signature/trust bypasses.

## Upstream projects

- [Reseam authoring documentation](https://reseam.app/docs/)
- [Reseam patch bundle template](https://git.reseam.app/reseam/patches-template)
- [Morphe patch repository](https://github.com/MorpheApp/morphe-patches)
