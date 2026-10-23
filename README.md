# RJX Original Morphe → Reseam Porting Skill

This repository contains an AI Agent Skill for faithfully porting **identified original Morphe patches** into Reseam. It is designed to work from real source or compiled artifacts—not to create generic demonstrations.

## Use the skill

Open this repository as an AI coding-agent workspace and invoke the skill:

```text
/morphe-to-reseam-porting
```

Identify the exact original Morphe patch or provide its source/build artifact. The skill traces its actual declarations, compiled `.class`/JAR code, fingerprints, dependencies, extensions, options, resources and target-app behavior, then creates the corresponding Reseam implementation.

## No fake patches

The skill explicitly forbids demo, sample, placeholder, generic smoke-test or made-up patches in place of the requested original. Every original behavior must be mapped to concrete ported code, shown to be unnecessary in the target architecture with evidence, or reported as unresolved. Missing source does not justify fabricating a substitute; the agent must inspect the available artifacts and request only the exact item that blocks faithful conversion.

See the [main skill instructions](.agents/skills/morphe-to-reseam-porting/SKILL.md), [migration map](.agents/skills/morphe-to-reseam-porting/references/migration-map.md), [compiled-code inspection guide](.agents/skills/morphe-to-reseam-porting/references/class-and-bytecode-inputs.md), [validation checklist](.agents/skills/morphe-to-reseam-porting/references/validation.md), and [upstream documentation](.agents/skills/morphe-to-reseam-porting/references/upstream-docs.md).

The skill supports broad, legitimate patch categories and implementation techniques. It retains necessary boundaries against paid-license/subscription bypasses, payment evasion, DRM defeat, credential theft and signature/trust bypasses.

## Validation

The [GitHub Actions skill validator](.github/workflows/validate-agent-skills.yml) checks skill metadata, internal references and required no-fabrication rules.
