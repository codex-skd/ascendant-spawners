# Roadmap — Ascendant Spawners (port de ApothicSpawners)

> Documento de planificación. No es el workflow operativo (ese es `WORKFLOW_ASCENDANT_SPAWNERS_26-2.md`) — este archivo define **qué construir y en qué orden**, para ir alimentando el trabajo a OpenCode fase a fase.

## Naturaleza del proyecto

**Ascendant Spawners es un port declarado de [ApothicSpawners](https://www.curseforge.com/minecraft/mc-mods/apothic-spawners) por Shadows_of_Fire**, de NeoForge 26.1.2 (v2.0.1) a NeoForge 26.2, con todos los identificadores (paquetes, clases, campos, mod id) renombrados a nuestra convención. No es un mod "inspirado en" — es un port funcional 1:1 del código.

ApothicSpawners es *"The Spawner Module of Apotheosis, allowing for silk-touching and modification"* — un addon separado (JAR propio) que depende de `placebo` y se integra con Apotheosis. En nuestra familia de mods:

- **ApothicSpawners → Ascendant Spawners** (este proyecto)
- **Apotheosis → Ascendant Equipment** (`ascendant_equipment/neoforge/26.2`) — Ascendant Spawners es su módulo de spawners.
- **Placebo → Common Toolkit** (`common_toolkit/neoforge/26.2`) — dependencia de compilación/runtime real de este mod.

### Base legal — obligatorio mantener siempre

- **Código**: licencia MIT del original (confirmado en `neoforge.mods.toml` del JAR original: `license="MIT License"`). Podemos copiar, modificar y renombrar libremente, pero el aviso de copyright/atribución **debe** conservarse en `LICENSE`, `README.md`, `docs/curseforge/project_description.md` y el campo `credits` de `neoforge.mods.toml`. Frase fija a usar en los cuatro sitios: *"Ascendant Spawners is a port of ApothicSpawners by Shadows_of_Fire, ported from NeoForge 26.1.2 to NeoForge 26.2."*
- **Assets**: el original (`assets/apothic_spawners/`) incluye texturas de GUI/JEI y fondos de advancement — se tratan como no reutilizables por defecto (mismo criterio que Ascendant Equipment con Apotheosis). Cada fase que necesite un asset lo sustituye por uno propio (placeholder al principio, arte final después).
- **Fuente de referencia**: `ApothicSpawners-26.1.2-2.0.1.jar` en `lib_ext/` es solo bytecode compilado. Fase 0 lo decompila a `temp/apothic-spawners-src/` (no versionado) como referencia de lectura — nunca se commitea el código decompilado tal cual, se reescribe fase a fase dentro de `src/`.

## Estructura real del JAR original (35 clases)

Extraída directamente del JAR (`dev/shadowsoffire/apothic_spawners/`):

| Paquete | Clases | Notas |
|---|---|---|
| raíz | `ApothicSpawners` (`@Mod`), `ASClient`, `ASConfig` (+ `ConfigPayload`), `ASEvents`, `ASObjects` | Núcleo: entrypoint, config, registro de objetos, eventos |
| `advancements` | `ModifierTrigger` (+ `TriggerInstance`), `SpawnEggItemPredicate` | Triggers de advancement propios |
| `block` | `ApothSpawnerBlock`, `ApothSpawnerItem`, `ApothSpawnerTile` (+ `SpawnerLogicExt`), `LyingLevel` | El bloque/item/tile del spawner modificado — núcleo funcional del mod |
| `compat` | `SpawnerCategory`, `SpawnerClientProvider`, `SpawnerHwylaPlugin`, `SpawnerJEIPlugin`, `SpawnerRecipeCache`, `SpawnerServerDataProvider` | Integraciones opcionales: JEI, Hwyla/Jade (tooltip overlay) |
| `data` | `ASEnchantmentProvider`, `ASLootProvider` (+ `GameplayLoot`), `ASRecipeProvider` | Datagen |
| `mixin` | `BlocksMixin`, `ItemsMixin`, `ItemStackMixin` | Mixins sobre vanilla — se hacen al final |
| `modifiers` | `SpawnerModifier`, `StatModifier` (+ `Mode`) | Sistema de recetas que modifican stats del spawner (silk touch, ignore_light, no_ai, etc.) |
| `stats` | `BooleanStat`, `CustomStat`, `LevelStat`, `PercentageStat`, `SpawnerStat`, `SpawnerStats`, `VanillaStat` | Los stats configurables del spawner |

Contenido data-driven (no Java, se porta como JSON propio, no copiado): `data/apothic_spawners/advancement/*` (14 archivos), `data/apothic_spawners/enchantment/capturing.json` (encantamiento Capturing), `data/apothic_spawners/loot_table/gameplay/unstable_spawner.json`, `data/apothic_spawners/recipe/spawner_modifiers/*` (16 recetas + 16 inversas), `data/apothic_spawners/tags/entity_type/blacklisted_from_spawners.json`, `data/minecraft/tags/enchantment/non_treasure.json`.

Un `apothic_spawners.mixins.json` + `META-INF/coremods.json` + `META-INF/accesstransformer.cfg` — confirmar en Fase 0 si el AT sigue haciendo falta contra 26.2.

## Convención de renombrado

| Original | Ascendant Spawners |
|---|---|
| Paquete raíz `dev.shadowsoffire.apothic_spawners` | `com.skd.ascendantspawners` |
| Clase principal `ApothicSpawners` (`@Mod`) | `AscendantSpawners` |
| `ASClient` | `AscSpClient` |
| `ASConfig` | `AscSpConfig` |
| `ASEvents` | `AscSpEvents` |
| `ASObjects` | `AscSpObjects` |
| MODID `apothic_spawners` | `ascendant_spawners` |
| Namespace de assets/data `apothic_spawners:` | `ascendant_spawners:` |
| `ApothSpawnerBlock` / `ApothSpawnerItem` / `ApothSpawnerTile` | `SpawnerBlock` / `SpawnerItem` / `SpawnerTile` (se retira el prefijo de marca `Apoth`, queda el nombre de dominio) |

Regla general: cada subpaquete (`advancements`, `block`, `compat`, `data`, `mixin`, `modifiers`, `stats`) se mantiene igual en minúsculas (son nombres de dominio, no de marca), solo cambia el paquete raíz y las clases que llevan el nombre/prefijo del mod original.

## Dependencias externas

Confirmado (no pendiente, a diferencia de Ascendant Equipment): el `neoforge.mods.toml` original declara **obligatoria** `placebo` — mapea 1:1 a **Common Toolkit**, ya disponible como JAR compilado en `libs/common_toolkit-26.2-neoforge-0.0.0-beta.1.jar` (dependencia real de compilación, ver `build.gradle`).

Además, este mod se declara **módulo de Ascendant Equipment** (relación conceptual con Apotheosis en el original, aunque ApothicSpawners no lo declara como dependencia MODID — es Apotheosis quien depende de `apothic_spawners`, no al revés). Se añade como `required` en `neoforge.mods.toml` por decisión de producto (queremos que Ascendant Spawners no cargue sin Ascendant Equipment), pero **de momento no hay JAR de Ascendant Equipment** (aún es solo scaffold, sin build). Añadir a `libs/` y a `build.gradle` en cuanto Ascendant Equipment tenga un build utilizable — no bloquea las fases 0-2 de este roadmap, que no llaman a su API.

Compat opcionales del original a confirmar en Fase 0: JEI (sí, mismo JEI de siempre) y Hwyla — **Hwyla es el WAILA original, discontinuado**; verificar si Shadows_of_Fire sigue integrando contra Hwyla o si en la versión 26.1.2 ya migró a Jade (fork activo). No asumir, comprobar en el decompilado antes de portar `compat/`.

## Fases

Cada fase = un encargo a OpenCode. Orden pensado por dependencia técnica.

| Fase | Alcance | Clases origen | Depende de |
|---|---|---|---|
| **0** | Setup: decompilar jar a `temp/apothic-spawners-src/`, confirmar estado de Hwyla vs Jade y del AT, definir mapping final en `docs/ASCENDANT_SPAWNERS_RENAME_MAP.md` | — | — |
| **1** | **Núcleo funcional completo** (fusiona las fases 1-6 originales): entrypoint, config, registro de objetos, eventos, stats, modificadores, bloque/item/tile, datagen y advancements. Ver nota abajo sobre por qué van juntas. | raíz (5) + `stats` (7) + `modifiers` (3) + `block` (5) + `data` (4) + `advancements` (3) = 27 clases | Fase 0 |
| **2** | Compat opcional: JEI + Jade (confirmado en Fase 0, ver `docs/ASCENDANT_SPAWNERS_RENAME_MAP.md`) | `compat` (6) | Fase 1 |
| **3** | Mixins (al final: tocan clases vanilla, lo más frágil entre versiones de MC) | `mixin` (3) | Fase 1, 2 |
| **4** | Arte propio: sustituir texturas de GUI/JEI y fondo de advancement por versiones propias | — (todo `assets/`) | Trabajo paralelo, no bloquea el resto |
| **5** | QA de paridad funcional + integración real con Ascendant Equipment (una vez tenga build) | — | Todas |

> **Nota (post Fase 0)**: el plan original tenía las fases 1-6 como paquetes independientes compilando uno a uno. Al revisar los imports internos del decompilado se confirmó que `root`/`stats`/`modifiers`/`block`/`data`/`advancements` se referencian circularmente entre sí (p.ej. `stats` usa `block.ApothSpawnerTile`, `block` usa `ASConfig`/`modifiers`/`stats`, `modifiers` usa `block`/`compat`/`stats`) — no hay un orden lineal donde cada paquete compile por separado. Se fusionan en una sola Fase 1 (27 de las 32 clases, ~2.400 de las 2.774 líneas totales). `compat` y `mixin` sí son genuinamente separables (solo dependen del núcleo ya cerrado), así que quedan como fases propias.

## Cómo se alimenta a OpenCode

1. Antes de cada fase: confirmar contigo el alcance exacto — no se abre una fase sin la fase anterior mergeada y compilando.
2. El prompt a OpenCode por fase incluye: ruta al código decompilado de referencia en `temp/apothic-spawners-src/<paquete>/`, la convención de renombrado de este documento, y el resultado esperado (`src/main/java/com/skd/ascendantspawners/<paquete>/...` compilando con `./gradlew.bat build`).
3. Al cerrar cada fase: build verde, commit (`feat[<paquete>]: port <subsistema> from ApothicSpawners`, versión bump beta), push, actualizar `CHANGELOG.md` y marcar la fase como hecha en este documento.
4. Graphify se actualiza tras cada fase.

## Estado

**Fase 0 — HECHA** (decompilación + investigación + documentación).

- Decompilado `lib_ext/ApothicSpawners-26.1.2-2.0.1.jar` con VineFlower a `temp/apothic-spawners-src/` (no versionado): 32 clases `.java` bajo `dev/shadowsoffire/apothic_spawners/`.
- **Hwyla vs Jade: JADE.** `SpawnerHwylaPlugin.java` (nombre legado) integra contra el fork activo: imports `snownee.jade.api.IWailaPlugin`, `snownee.jade.api.WailaPlugin`, `snownee.jade.api.IWailaCommonRegistration` / `IWailaClientRegistration`, con `@WailaPlugin` sobre el plugin y registros `registerBlockDataProvider` / `registerBlockComponent` de Jade. `SpawnerClientProvider` y `SpawnerServerDataProvider` implementan `IBlockComponentProvider` e `IServerDataProvider<BlockAccessor>` de Jade. No queda integración con el Hwyla/WAILA discontinuado. Se renombra a `SpawnerJadePlugin` en el port (ver `docs/ASCENDANT_SPAWNERS_RENAME_MAP.md`).
- **Access Transformer: SIGUE SIENDO NECESARIO contra 26.2, sin cambios.** Verificado con `javap` contra `minecraft_26.2_client.jar`: todos los miembros listados en `META-INF/accesstransformer.cfg` del JAR original siguen `private` en MC 26.2 — `SpawnerBlockEntity.spawner` (`private final`), y en `BaseSpawner`: `spawnDelay`, `spawnPotentials`, `nextSpawnData`, `spin`, `oSpin`, `minSpawnDelay`, `maxSpawnDelay`, `spawnCount`, `maxNearbyEntities`, `requiredPlayerRange`, `spawnRange` (todos `private`), además de `getOrCreateNextSpawnData` e `isNearPlayer` (`private`).
- Mapping de renombrado definitivo documentado en `docs/ASCENDANT_SPAWNERS_RENAME_MAP.md` (32 clases top-level + 6 tipos internos).

**Fase 1 — HECHA** (núcleo funcional: root + stats + modifiers + block + data + advancements).

- 27 de las 32 clases portadas 1:1 a `src/main/java/com/skd/ascendantspawners/` (renombradas según el mapping), más `compat/SpawnerRecipeCache.java` (dependencia interna de `SpawnerModifier`, adelantada de la Fase 2).
- `./gradlew.bat compileJava` en verde (release `v0.0.0-beta.2`).
- Adaptaciones 26.2 aplicadas: reflexión estándar en vez de `ObfuscationReflectionHelper`, lookups de registros por `BuiltInRegistries.*.getValue(...)` (se eliminaron las constantes `BlockEntityType.MOB_SPAWNER`/`EntityType.PIG`), paquetes de advancements reubicados (`net.minecraft.advancements.predicates[.entity]`, `net.minecraft.advancements.triggers`), `LootContextParams`/`LootContextParamSets` bajo `net.minecraft.world.level.storage.loot.parameters`, `BlockBehaviour` bajo `net.minecraft.world.level.block.state`.
- Quedan fuera: `compat` (JEI + Jade, 6 clases), `mixin` (3 clases), arte y QA de paridad.

**Fase 2 — HECHA** (compat opcional: JEI + Jade).

- 5 clases de `compat/` portadas 1:1 a `src/main/java/com/skd/ascendantspawners/compat/` (renombradas según el mapping): `SpawnerCategory`, `SpawnerJEIPlugin`, `SpawnerClientProvider`, `SpawnerServerDataProvider` y `SpawnerHwylaPlugin` → `SpawnerJadePlugin`.
- `SpawnerRecipeCache` (adelantada en la Fase 1) intacta.
- `./gradlew.bat compileJava` en verde (release `v0.0.0-beta.3`).
- Verificado contra las APIs reales resueltas por gradle (JEI `30.16.0.124`, Jade `26.2.8+neoforge`): las firmas del decompilado coinciden, incluido `IRecipeCategory.draw(... GuiGraphicsExtractor ...)`, `ITooltipBuilder`, `IBlockComponentProvider`/`IServerDataProvider` de Jade y `level.holder(...)` vía `ILevelReaderExtension` de NeoForge. Único ajuste de compilación: import de `IRecipeCategory` en `SpawnerJEIPlugin`.
- Quedan fuera: `mixin` (3 clases), arte y QA de paridad. Las claves de traducción que referencian las clases de compat (`title.*`, `misc.*`, `info.*`) aún no están en `en_us.json` (pendiente junto al resto de contenido data-driven).

**Fase 3 — HECHA** (mixins).

- 3 clases mixin portadas 1:1 a `src/main/java/com/skd/ascendantspawners/mixin/` (nombres conservados según el mapping): `BlocksMixin`, `ItemsMixin`, `ItemStackMixin`.
- `./gradlew.bat compileJava` en verde (release `v0.0.0-beta.4`).
- Adaptación 26.2: en `ItemsMixin`, `ResourceKey.identifier()` no existe en 26.2 → comparación directa de `ResourceKey` contra constante `ResourceKey.create(Registries.ITEM, Identifier.withDefaultNamespace("spawner"))`.
- MixinExtras (`WrapOperation`) usado transitivamente vía NeoForge, sin dependencia extra.
- Quedan fuera: arte y QA de paridad.

Próximo paso: **Fase 4** (arte propio).
