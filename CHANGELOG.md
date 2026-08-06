# Changelog — Ascendant Spawners

## 0.0.0-beta.10

- **Endurecimiento de seguridad**: Agregados null-checks en `SpawnerJEIPlugin` para evitar potencial `NullPointerException` al acceder a contexto de nivel cliente. Todas las llamadas opcionales a Minecraft API ahora protegen correctamente contra valores null.
- **Corrección crítica del sistema de build**: Corregida lógica de detección de mixins en `build.gradle`. Versiones anteriores tenían un algoritmo de descubrimiento de archivos quebrado que impedía que los mixins se registraran correctamente en el JAR. Los tres mixins (`BlocksMixin`, `ItemsMixin`, `ItemStackMixin`) ahora se detectan y compilan correctamente. Técnico: se reescribió la construcción de rutas para usar la API `file()` de Gradle y se reemplazó el parsing frágil de strings con filtrado directo de `listFiles()`.
- **Documentación**: Agregados comentarios inline explicando ruta de migración futura para actualizaciones NeoForge 26.2+.
- **Calidad de código**: Compilación completa verificada contra NeoForge 26.2.0.37-beta con cero errores o warnings críticos.
- Subido a CurseForge vía `curseforge-upload.ps1`.

## 0.0.0-beta.9

- Checkpoint de estabilidad. Sin cambios visibles respecto a beta.8.

## 0.0.0-beta.8

- Reconstruido contra NeoForge 26.2.0.37-beta. Arranque verificado mediante dev server run.

## 0.0.0-beta.7

- **Fix ciclo de carga (crash de arranque)**: la dependencia opcional de `ascendant_equipment` declaraba `ordering="AFTER"`, pero `ascendant_equipment` también declara a este mod con `ordering="AFTER"`. El par de restricciones mutuas formaba un ciclo que NeoForge no puede ordenar → `Mod Sorting failed. Detected Cycles` y el cliente no arranca. Corregido a `ordering="NONE"` (la dependencia sigue siendo `optional` y su floor sube a `[0.0.0-beta.5,)`).
- Subido a CurseForge vía `curseforge-upload.ps1` (file ID `8583893`).

## 0.0.0-beta.6

- **Fix dependencias**: sustituido el parche `[0.0.0-alpha,)` introducido en beta.5 por floors beta exactos: `common_toolkit` `[0.0.0-beta.1,)` y `ascendant_equipment` (optional) `[0.0.0-beta.4,)`. El rango `[0.0.0-alpha,)` funcionaba (la comparación Maven ordena `alpha` por debajo de `beta`) pero era impreciso y confuso — el floor ahora refleja la versión beta real de cada dependencia.
- Subido a CurseForge vía `curseforge-upload.ps1` (file ID `8582957`).

## 0.0.0-beta.5

- Port Fase 4: contenido data-driven y arte propio (cierra lo dejado fuera deliberadamente en la Fase 1).
- `en_us.json` reemplazado por el contenido real traducido (namespace `ascendant_spawners`), incluidas las claves de compat pendientes desde la Fase 2.
- 14 JSON de advancement portados a `data/ascendant_spawners/advancement/` (namespace, trigger id y ruta de fondo actualizados).
- Tags estáticas portadas: `data/ascendant_spawners/tags/entity_type/blacklisted_from_spawners.json`, `data/minecraft/tags/enchantment/non_treasure.json`.
- `./gradlew.bat runData` genera correctamente el encantamiento `capturing`, la loot table `unstable_spawner` y las 32 recetas de `spawner_modifiers` (16 + 16 `_inverse`) desde los providers ya portados.
- 2 texturas placeholder propias: `textures/gui/spawner_jei.png` (256×256) y `textures/gui/advancements/backgrounds/apoth.png` (64×64).
- **Bugs de infraestructura encontrados y corregidos** (arrastrados del scaffold inicial, nunca detectados porque las fases 1-3 solo corrían `compileJava`, no `build`/`runData` completos):
  - `generateModMetadata` leía de `src/main/templates`, pero la plantilla real estaba en `src/main/resources/templates` — `neoforge.mods.toml` nunca se generaba de verdad. Movida la plantilla a la ruta correcta.
  - `versionRange="[0.0.0,)"` en las dependencias a `common_toolkit`/`ascendant_equipment` rechazaba cualquier build beta (en el esquema de versiones Maven, un pre-release como `0.0.0-beta.1` ordena por debajo de `0.0.0`). Corregido a `[0.0.0-alpha,)`.
  - `ascendant_equipment` bajado temporalmente de `required` a `optional` — no tiene build todavía y bloqueaba la carga completa del mod (se revierte cuando exista un JAR real).
  - `BlocksMixin`/`ItemsMixin`: `Blocks.register()` cambió de una sobrecarga por `String` a una por `BlockItemId` en 26.2, y el registro de vanilla ya no usa constantes de tipo `String` (usa campos estáticos `BlockItemIds.*`). Reescrito el `@Slice`/`@At` del mixin contra el bytecode real de 26.2.
  - `ItemsMixin`: el campo estático `SPAWNER_KEY` quedaba `null` en el momento del `@ModifyVariable` porque los campos fusionados por mixin se añaden al final del `<clinit>` del target, después de que `Items` ya registrara `spawner`. Cambiado a cálculo en línea.
  - `runs.data` en `build.gradle` estaba en `serverData()`; el código escucha `GatherDataEvent.Client`, que solo dispara `clientData()`. Revertido.
  - JEI pinnado a `30.15.0.121` (no la última `30.16.x`, que exige `neoforge >= 26.2.0.40-beta`; este proyecto se queda en `26.2.0.32-beta`).
- `./gradlew.bat clean build` en verde (primer build completo con JAR + `runData`, no solo `compileJava`).

## 0.0.0-beta.4

- Port Fase 3: mixins portados 1:1 desde ApothicSpawners (26.1.2) a 26.2.
- `mixin` (3): `BlocksMixin` (reemplaza la factoría de `Blocks.SPAWNER` por `SpawnerBlock`), `ItemsMixin` (reemplaza la factoría de `Items.SPAWNER` por `SpawnerItem`), `ItemStackMixin` (suprime el tooltip vanilla de data components en ítems `SpawnerItem`).
- Adaptación 26.2: `ResourceKey.identifier()` → comparación por igualdad de `ResourceKey` con una constante creada vía `ResourceKey.create(Registries.ITEM, Identifier.withDefaultNamespace("spawner"))`.
- `./gradlew.bat compileJava` en verde.

## 0.0.0-beta.3

- Port Fase 2: compat opcional JEI + Jade portado 1:1 desde ApothicSpawners (26.1.2) a 26.2.
- `compat` (5): `SpawnerCategory` (categoría de recetas JEI), `SpawnerJEIPlugin` (`@JeiPlugin` + catalysts), `SpawnerClientProvider` (tooltip cliente Jade), `SpawnerServerDataProvider` (datos servidor→cliente Jade), `SpawnerHwylaPlugin` → **`SpawnerJadePlugin`** (`@WailaPlugin`, renombrado porque integra contra Jade).
- `./gradlew.bat compileJava` en verde.
- Nota: las claves de traducción que referencian las nuevas clases (`title.*`, `misc.*`, `info.*` de `AscendantSpawners.lang`) aún no están portadas a `en_us.json`; quedan pendientes junto al resto del contenido data-driven.

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
- `lib_ext/ApothicSpawners-26.1.2-2.0.1.jar` añadido como referencia de decompilación para la Fase 0 del roadmap.---

## [0.0.0-beta.8] - 2026-08-05

### Change

- **Recompilado contra NeoForge `26.2.0.37-beta`**: bump de `neo_version` en `gradle.properties` (`26.2.0.32-beta` -> `26.2.0.37-beta`). Verificado con `runServer` (arranque sin errores).

## [angelog — Ascendant Spawners

## 0.0.0-beta.7

- **Fix ciclo de carga (crash de arranque)**: la dependencia opcional de `ascendant_equipment` declaraba `ordering="AFTER"`, pero `ascendant_equipment` también declara a este mod con `ordering="AFTER"`. El par de restricciones mutuas formaba un ciclo que NeoForge no puede ordenar → `Mod Sorting failed. Detected Cycles` y el cliente no arranca. Corregido a `ordering="NONE"` (la dependencia sigue siendo `optional` y su floor sube a `[0.0.0-beta.5,)`).
- Subido a CurseForge vía `curseforge-upload.ps1` (file ID `8583893`).

## 0.0.0-beta.6

- **Fix dependencias**: sustituido el parche `[0.0.0-alpha,)` introducido en beta.5 por floors beta exactos: `common_toolkit` `[0.0.0-beta.1,)` y `ascendant_equipment` (optional) `[0.0.0-beta.4,)`. El rango `[0.0.0-alpha,)` funcionaba (la comparación Maven ordena `alpha` por debajo de `beta`) pero era impreciso y confuso — el floor ahora refleja la versión beta real de cada dependencia.
- Subido a CurseForge vía `curseforge-upload.ps1` (file ID `8582957`).

## 0.0.0-beta.5

- Port Fase 4: contenido data-driven y arte propio (cierra lo dejado fuera deliberadamente en la Fase 1).
- `en_us.json` reemplazado por el contenido real traducido (namespace `ascendant_spawners`), incluidas las claves de compat pendientes desde la Fase 2.
- 14 JSON de advancement portados a `data/ascendant_spawners/advancement/` (namespace, trigger id y ruta de fondo actualizados).
- Tags estáticas portadas: `data/ascendant_spawners/tags/entity_type/blacklisted_from_spawners.json`, `data/minecraft/tags/enchantment/non_treasure.json`.
- `./gradlew.bat runData` genera correctamente el encantamiento `capturing`, la loot table `unstable_spawner` y las 32 recetas de `spawner_modifiers` (16 + 16 `_inverse`) desde los providers ya portados.
- 2 texturas placeholder propias: `textures/gui/spawner_jei.png` (256×256) y `textures/gui/advancements/backgrounds/apoth.png` (64×64).
- **Bugs de infraestructura encontrados y corregidos** (arrastrados del scaffold inicial, nunca detectados porque las fases 1-3 solo corrían `compileJava`, no `build`/`runData` completos):
  - `generateModMetadata` leía de `src/main/templates`, pero la plantilla real estaba en `src/main/resources/templates` — `neoforge.mods.toml` nunca se generaba de verdad. Movida la plantilla a la ruta correcta.
  - `versionRange="[0.0.0,)"` en las dependencias a `common_toolkit`/`ascendant_equipment` rechazaba cualquier build beta (en el esquema de versiones Maven, un pre-release como `0.0.0-beta.1` ordena por debajo de `0.0.0`). Corregido a `[0.0.0-alpha,)`.
  - `ascendant_equipment` bajado temporalmente de `required` a `optional` — no tiene build todavía y bloqueaba la carga completa del mod (se revierte cuando exista un JAR real).
  - `BlocksMixin`/`ItemsMixin`: `Blocks.register()` cambió de una sobrecarga por `String` a una por `BlockItemId` en 26.2, y el registro de vanilla ya no usa constantes de tipo `String` (usa campos estáticos `BlockItemIds.*`). Reescrito el `@Slice`/`@At` del mixin contra el bytecode real de 26.2.
  - `ItemsMixin`: el campo estático `SPAWNER_KEY` quedaba `null` en el momento del `@ModifyVariable` porque los campos fusionados por mixin se añaden al final del `<clinit>` del target, después de que `Items` ya registrara `spawner`. Cambiado a cálculo en línea.
  - `runs.data` en `build.gradle` estaba en `serverData()`; el código escucha `GatherDataEvent.Client`, que solo dispara `clientData()`. Revertido.
  - JEI pinnado a `30.15.0.121` (no la última `30.16.x`, que exige `neoforge >= 26.2.0.40-beta`; este proyecto se queda en `26.2.0.32-beta`).
- `./gradlew.bat clean build` en verde (primer build completo con JAR + `runData`, no solo `compileJava`).

## 0.0.0-beta.4

- Port Fase 3: mixins portados 1:1 desde ApothicSpawners (26.1.2) a 26.2.
- `mixin` (3): `BlocksMixin` (reemplaza la factoría de `Blocks.SPAWNER` por `SpawnerBlock`), `ItemsMixin` (reemplaza la factoría de `Items.SPAWNER` por `SpawnerItem`), `ItemStackMixin` (suprime el tooltip vanilla de data components en ítems `SpawnerItem`).
- Adaptación 26.2: `ResourceKey.identifier()` → comparación por igualdad de `ResourceKey` con una constante creada vía `ResourceKey.create(Registries.ITEM, Identifier.withDefaultNamespace("spawner"))`.
- `./gradlew.bat compileJava` en verde.

## 0.0.0-beta.3

- Port Fase 2: compat opcional JEI + Jade portado 1:1 desde ApothicSpawners (26.1.2) a 26.2.
- `compat` (5): `SpawnerCategory` (categoría de recetas JEI), `SpawnerJEIPlugin` (`@JeiPlugin` + catalysts), `SpawnerClientProvider` (tooltip cliente Jade), `SpawnerServerDataProvider` (datos servidor→cliente Jade), `SpawnerHwylaPlugin` → **`SpawnerJadePlugin`** (`@WailaPlugin`, renombrado porque integra contra Jade).
- `./gradlew.bat compileJava` en verde.
- Nota: las claves de traducción que referencian las nuevas clases (`title.*`, `misc.*`, `info.*` de `AscendantSpawners.lang`) aún no están portadas a `en_us.json`; quedan pendientes junto al resto del contenido data-driven.

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
