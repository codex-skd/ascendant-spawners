# Mapa de renombrado — ApothicSpawners → Ascendant Spawners

> Referencia definitiva del mapeo de identificadores entre el mod original y el port, según la convención definida en `docs/ROADMAP_ASCENDANT_SPAWNERS.md` (sección "Convención de renombrado").
> Fuente real de la estructura: decompilado de `lib_ext/ApothicSpawners-26.1.2-2.0.1.jar` en `temp/apothic-spawners-src/` (Fase 0).

La estructura real decompilada son **32 clases top-level** (+ 6 tipos internos = **38 tipos**). La cifra "~35" del roadmap era una estimación previa del JAR; este documento es la referencia canónica.

Regla general (heredada de la convención): cada subpaquete (`advancements`, `block`, `compat`, `data`, `mixin`, `modifiers`, `stats`) se mantiene igual en minúsculas; solo cambia el paquete raíz y las clases que llevan el nombre/prefijo de marca del mod original. Los nombres de los tipos internos se conservan tal cual salvo que cuelguen de una clase renombrada.

## Paquete raíz `com.skd.ascendantspawners`

| Original (`dev.shadowsoffire.apothic_spawners`) | Destino (`com.skd.ascendantspawners`) | Notas |
|---|---|---|
| `ApothicSpawners` (`@Mod`) | `AscendantSpawners` | Entrypoint del mod |
| `ASClient` | `AscSpClient` | Lado cliente |
| `ASConfig` | `AscSpConfig` | Config + payload de red |
| `ASEvents` | `AscSpEvents` | Eventos |
| `ASObjects` | `AscSpObjects` | Registro de objetos |

### Tipos internos de `ASConfig` → `AscSpConfig`

| Original | Destino |
|---|---|
| `ASConfig.ConfigPayload` (record) | `AscSpConfig.ConfigPayload` |
| `ASConfig.ConfigPayload.Provider` | `AscSpConfig.ConfigPayload.Provider` |

## `com.skd.ascendantspawners.advancements`

| Original | Destino |
|---|---|
| `ModifierTrigger` | `ModifierTrigger` |
| `ModifierTrigger.TriggerInstance` (record) | `ModifierTrigger.TriggerInstance` |
| `SpawnEggItemPredicate` | `SpawnEggItemPredicate` |

## `com.skd.ascendantspawners.block`

| Original | Destino | Notas |
|---|---|---|
| `ApothSpawnerBlock` | `SpawnerBlock` | Se retira el prefijo de marca `Apoth` |
| `ApothSpawnerItem` | `SpawnerItem` | Idem |
| `ApothSpawnerTile` | `SpawnerTile` | Idem |
| `LyingLevel` | `LyingLevel` | |

### Tipos internos de `ApothSpawnerTile` → `SpawnerTile`

| Original | Destino |
|---|---|
| `ApothSpawnerTile.SpawnerLogicExt` | `SpawnerTile.SpawnerLogicExt` |

## `com.skd.ascendantspawners.compat`

| Original | Destino | Notas |
|---|---|---|
| `SpawnerCategory` | `SpawnerCategory` | Categoría de recetas JEI |
| `SpawnerClientProvider` | `SpawnerClientProvider` | Proveedor de tooltip (Jade) |
| `SpawnerHwylaPlugin` | `SpawnerJadePlugin` | **Renombrado**: el plugin integra contra **Jade** (imports `snownee.jade.api.*`, ver Fase 0); el nombre "Hwyla" era legado y resultaría engañoso en un port con identificadores renombrados |
| `SpawnerJEIPlugin` | `SpawnerJEIPlugin` | Integración con JEI |
| `SpawnerRecipeCache` | `SpawnerRecipeCache` | Caché de recetas del cliente |
| `SpawnerServerDataProvider` | `SpawnerServerDataProvider` | Proveedor de datos de servidor (Jade) |

## `com.skd.ascendantspawners.data`

| Original | Destino |
|---|---|
| `ASEnchantmentProvider` | `ASEnchantmentProvider` |
| `ASLootProvider` | `ASLootProvider` |
| `ASLootProvider.GameplayLoot` (record) | `ASLootProvider.GameplayLoot` |
| `ASRecipeProvider` | `ASRecipeProvider` |

## `com.skd.ascendantspawners.mixin`

| Original | Destino |
|---|---|
| `BlocksMixin` | `BlocksMixin` |
| `ItemsMixin` | `ItemsMixin` |
| `ItemStackMixin` | `ItemStackMixin` |

## `com.skd.ascendantspawners.modifiers`

| Original | Destino |
|---|---|
| `SpawnerModifier` (record) | `SpawnerModifier` |
| `StatModifier` (record) | `StatModifier` |
| `StatModifier.Mode` (enum) | `StatModifier.Mode` |

## `com.skd.ascendantspawners.stats`

| Original | Destino |
|---|---|
| `BooleanStat` | `BooleanStat` |
| `CustomStat` | `CustomStat` |
| `LevelStat` | `LevelStat` |
| `PercentageStat` | `PercentageStat` |
| `SpawnerStat` (interface) | `SpawnerStat` |
| `SpawnerStats` | `SpawnerStats` |
| `VanillaStat` | `VanillaStat` |

## Conteo

- 32 clases top-level + 6 tipos internos = 38 tipos mapeados.
- Único tipo con renombrado de nombre (además de los de la convención de marca): `SpawnerHwylaPlugin` → `SpawnerJadePlugin`.
