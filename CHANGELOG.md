# Ascendant Spawners (1.21.1) — Changelog

Branch `minecraft/1.21.1/neoforge-21.1.249/production`. History independent of the 26.2 branch.

## [0.0.0-beta.1] - 2026-08-31

### Added

- **Initial port to Minecraft 1.21.1 / NeoForge 21.1.249** (Java 21). Strategy: **re-fork**
  from the upstream Apothic Spawners 1.21 sources (1.4.0, NeoForge 21.1.187), rebranded to
  the Ascendant Spawners identity — not a back-port of the 26.2 fork.
- Feature parity with the 26.2 line: the Capturing enchantment (silk-touch spawners), the
  spawner stat-modification recipe system (min/max delay, spawn count, ranges, redstone
  control, no-AI, silent, youthful, initial health, burning, ignore light/players/conditions,
  each with an `_inverse` recipe), the Shift/Jade stat tooltip, and the advancement tab.
  JEI recipe category and Jade tooltip compat.

### Technical

- Package `dev.shadowsoffire.apothic_spawners` → `com.skd.ascendantspawners`; modid
  `apothic_spawners` → `ascendant_spawners`; classes `ApothicSpawners` → `AscendantSpawners`,
  `AS*` → `AscSp*`, `ApothSpawnerBlock/Item/Tile` → `AscendantSpawnerBlock/Item/Tile`
  (kept the `Ascendant` prefix — vanilla `net.minecraft…block.SpawnerBlock` still exists on
  1.21.1, unlike 26.2 where the fork could use the bare name), `SpawnerHwylaPlugin` →
  `SpawnerJadePlugin`.
- Dependency **Placebo → Common Toolkit** (`com.skd.commontoolkit`), consumed from `libs/`
  as `compileOnly` + `localRuntime`. Ascendant Equipment stays optional (no 1.21.1 build yet).
- The 26.2 fork's 8 extra classes (datagen providers, Jade provider split, client recipe
  cache, `ItemStackMixin`) were reviewed and **not carried over** — they are 26.2-API
  adaptations, not new features; the datapack JSON they generated ships hand-authored from
  upstream instead.
- `BlocksMixin` `@Redirect` handler keeps the vanilla `SpawnerBlock` return type (returns an
  `AscendantSpawnerBlock` instance) — mixin requires the factory descriptor to match the
  `NEW` target type.
- Build: workspace `net.neoforged.moddev` template retargeted to NeoForge 21.1.249 / Java 21;
  `modLoader`/`loaderVersion` added to `neoforge.mods.toml`; mixins `JAVA_21`; clean 14-line
  access transformer from upstream (the 26.2 fork's AT carried a large workspace-template dump).
- Verified: `./gradlew build` OK; `./gradlew runServer` reaches `Done`, `ascendant_spawners`
  + `common_toolkit` load, both mixins apply, no errors.
