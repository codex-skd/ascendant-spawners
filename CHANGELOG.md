# Changelog — Ascendant Spawners

## 0.0.0-beta.2

- Port Fase 1: núcleo funcional completo portado 1:1 desde ApothicSpawners (26.1.2) a 26.2.
- Root: `AscendantSpawners` (entrypoint/setup/regs/data), `AscSpEvents`, `AscSpConfig` (+ `ConfigPayload`), `AscSpClient`, `AscSpObjects`.
- `stats` (7): `SpawnerStats`, `CustomStat`, `EfficiencyStat`, `NoAiStat`, `SilkTouchStat`, `SpawnCountStat`, `SpawnerStat`.
- `modifiers` (3): `SpawnerModifier`, `StatModifier`, `Mode`.
- `block` (4): `SpawnerBlock`, `SpawnerItem`, `SpawnerTile`, `LyingLevel` (SpawnerLogic se integra en `SpawnerTile`).
- `data` (3): `ASEnchantmentProvider`, `ASLootProvider`, `ASRecipeProvider`.
- `advancements` (2): `ModifierTrigger` (+ `TriggerInstance`), `SpawnEggItemPredicate`.
- `compat` (1): `SpawnerRecipeCache` (dependencia interna de `SpawnerModifier`).
- Adaptaciones 26.2: `ObfuscationReflectionHelper` → reflexión estándar; `BlockEntityType.MOB_SPAWNER`/`EntityType.PIG` → lookup por `BuiltInRegistries.*.getValue(...)`; paquetes de advancements reubicados a `net.minecraft.advancements.predicates[.entity]` / `.triggers`; `LootContextParams`/`LootContextParamSets` → `net.minecraft.world.level.storage.loot.parameters`.
- `./gradlew.bat compileJava` en verde.

## 0.0.0-beta.1

- Scaffold inicial desde el esqueleto `codex-docs/mod_template/neoforge/26.2-26.2.0.32-beta` (NeoForge 26.2 / NeoForge 26.2.0.32-beta), adaptado desde `ascendant_equipment`.
- Repo creado en `stalking-dragons/minecraft/ascendant-spawners`.
- Declarado como port de [ApothicSpawners](https://www.curseforge.com/minecraft/mc-mods/apothic-spawners) por Shadows_of_Fire, módulo de spawners de Ascendant Equipment.
- Dependencia de compilación añadida: `libs/common_toolkit-26.2-neoforge-0.0.0-beta.1.jar` (port de Placebo).
- `lib_ext/ApothicSpawners-26.1.2-2.0.1.jar` añadido como referencia de decompilación para la Fase 0 del roadmap.
