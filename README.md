# Ascendant Spawners

Ascendant Spawners lets you silk-touch mob spawners and modify their stats (delay, range, spawn count, redstone control, entity type and more) through crafting recipes, for Minecraft 1.21.1 (NeoForge). It is the spawner module of [Ascendant Equipment](https://gitlab.com/stalking-dragons/minecraft/ascendant-equipment) (our port of Apotheosis).

> This mod is a fork of [ApothicSpawners](https://www.curseforge.com/minecraft/mc-mods/apothic-spawners) by Shadows_of_Fire. Not affiliated with or endorsed by the original author.

## Status

Beta (`0.0.0-beta.1`). Initial port to Minecraft 1.21.1 / NeoForge 21.1.249 — a **re-fork** from the upstream Apothic Spawners 1.21 sources (1.4.0), rebranded to the Ascendant Spawners identity, not a back-port of the 26.2 fork. Feature parity with the 26.2 line: Capturing enchantment, spawner stat-modification recipes, live stat tooltip, advancements, JEI/Jade compat. `./gradlew build` and `./gradlew runServer` verified.

## Requirements

| Component | Version |
|---|---|
| Minecraft | 1.21.1 |
| NeoForge | 21.1.249+ |
| Java | 21+ |
| [Common Toolkit](https://gitlab.com/stalking-dragons/minecraft/common-toolkit) | Required (fork of Placebo) |
| [Ascendant Equipment](https://gitlab.com/stalking-dragons/minecraft/ascendant-equipment) | Optional (spawner module of) |
| JEI / Jade | Optional (compat only) |

## Installation

1. Install [NeoForge](https://neoforge.net/) for Minecraft 1.21.1.
2. Install Common Toolkit.
3. Download the mod jar and place it in your `mods/` folder.

## License

MIT — see [LICENSE](LICENSE). Upstream ApothicSpawners copyright is retained; all in-game assets are original to this project. The required dependency Common Toolkit is LGPL-2.1-or-later (depended on, not bundled).
