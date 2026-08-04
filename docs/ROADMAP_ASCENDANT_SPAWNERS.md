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
| **1** | Núcleo: entrypoint, config, registro de objetos, eventos | raíz (5 clases + payload interno) | Fase 0 |
| **2** | Stats del spawner: framework de stats configurables | `stats` (7) | Fase 1 |
| **3** | Modificadores: sistema de recetas que aplican stats (silk touch, no_ai, ignore_light, redstone_control, etc.) | `modifiers` (3) | Fase 2 |
| **4** | Bloque/Item/Tile del spawner modificado (núcleo funcional visible en juego) | `block` (5) | Fase 2, 3 |
| **5** | Datagen: loot table, recetas, encantamiento Capturing, tags | `data` (4) + JSONs de `data/apothic_spawners/` | Fase 3, 4 |
| **6** | Advancements propios (14 criterios/triggers) | `advancements` (3) + JSONs | Fase 4, 5 |
| **7** | Compat opcional: JEI + Hwyla/Jade (según lo que confirme la Fase 0) | `compat` (6) | Fase 3, 4 |
| **8** | Mixins (al final: tocan clases vanilla, lo más frágil entre versiones de MC) | `mixin` (3) | Todas las anteriores relevantes |
| **9** | Arte propio: sustituir texturas de GUI/JEI y fondo de advancement por versiones propias | — (todo `assets/`) | Trabajo paralelo, no bloquea el resto |
| **10** | QA de paridad funcional + integración real con Ascendant Equipment (una vez tenga build) | — | Todas |

## Cómo se alimenta a OpenCode

1. Antes de cada fase: confirmar contigo el alcance exacto — no se abre una fase sin la fase anterior mergeada y compilando.
2. El prompt a OpenCode por fase incluye: ruta al código decompilado de referencia en `temp/apothic-spawners-src/<paquete>/`, la convención de renombrado de este documento, y el resultado esperado (`src/main/java/com/skd/ascendantspawners/<paquete>/...` compilando con `./gradlew.bat build`).
3. Al cerrar cada fase: build verde, commit (`feat[<paquete>]: port <subsistema> from ApothicSpawners`, versión bump beta), push, actualizar `CHANGELOG.md` y marcar la fase como hecha en este documento.
4. Graphify se actualiza tras cada fase.

## Estado

Ninguna fase iniciada. Próximo paso: **Fase 0** (decompilar + confirmar Hwyla/Jade y AT).
