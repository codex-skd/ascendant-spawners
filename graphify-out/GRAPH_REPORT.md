# Graph Report - 26.2  (2026-08-07)

## Corpus Check
- 102 files · ~25,547 words
- Verdict: corpus is large enough that graph structure adds value.

## Summary
- 665 nodes · 1331 edges · 52 communities (37 shown, 15 thin omitted)
- Extraction: 99% EXTRACTED · 1% INFERRED · 0% AMBIGUOUS · INFERRED: 17 edges (avg confidence: 0.8)
- Token cost: 0 input · 0 output

## Graph Freshness
- Built from commit: `80402708`
- Run `git rev-parse HEAD` and compare to check if the graph is stale.
- Run `graphify update .` after code changes (no API cost).

## Community Hubs (Navigation)
- World Generation
- Recipe Management
- Spawner Behavior
- Event Handling
- Mod Integration
- Entity Spawning
- Data Providers
- Block Interaction
- Item Handling
- Data Management
- Game Events
- GUI Elements
- SpawnerServerDataProvider.java
- Stat Management
- Percentage Stat
- JEI Integration
- Documentation
- Mixin Overrides
- Initial Setup
- Build Scripts
- Release Notes
- Project Overview
- Roadmap — Ascendant Spawners (port de ApothicSpawners)
- Mapa de renombrado — ApothicSpawners → Ascendant Spawners
- Flujo de trabajo — Ascendant Spawners (NeoForge)
- CurseForge — Variables del proyecto
- Changelog — Ascendant Spawners
- CLAUDE.md — ascendant_spawners (26.2)
- ApothicSpawners
- Ascendant Equipment
- Common Toolkit
- ../../codex-docs/reference/CURSEFORGE.md
- ../../codex-docs/reference/GRAPHIFY.md
- ../../codex-docs/reference/REPO_SETUP.md
- Project Variables
- docs/WORKFLOW_ASCENDANT_SPAWNERS_26-2.md
- Icon for Ascendant Spawners
- Apoth Advancement Background
- JEI GUI for Spawner
- stalking-dragons/minecraft
- BooleanStat

## God Nodes (most connected - your core abstractions)
1. `SpawnerTile` - 52 edges
2. `LyingLevel` - 47 edges
3. `SpawnerModifier` - 37 edges
4. `SpawnerStat` - 33 edges
5. `StatModifier` - 24 edges
6. `Provider` - 21 edges
7. `Changelog — Ascendant Spawners` - 20 edges
8. `SpawnerCategory` - 17 edges
9. `AscSpEvents` - 14 edges
10. `AscSpObjects` - 14 edges

## Surprising Connections (you probably didn't know these)
- `Publish Public Job` --references--> `Changelog Version 0.0.0-beta.5`  [EXTRACTED]
  .gitlab-ci.yml → CHANGELOG.md
- `Publish Public Job` --references--> `Ascendant Spawners README`  [EXTRACTED]
  .gitlab-ci.yml → README.md
- `AscSpObjects` --references--> `ModifierTrigger`  [EXTRACTED]
  src/main/java/com/skd/ascendantspawners/AscSpObjects.java → src/main/java/com/skd/ascendantspawners/advancements/ModifierTrigger.java
- `AscSpObjects` --references--> `SpawnerModifier`  [EXTRACTED]
  src/main/java/com/skd/ascendantspawners/AscSpObjects.java → src/main/java/com/skd/ascendantspawners/modifiers/SpawnerModifier.java
- `SpawnerTile` --references--> `SpawnerStat`  [EXTRACTED]
  src/main/java/com/skd/ascendantspawners/block/SpawnerTile.java → src/main/java/com/skd/ascendantspawners/stats/SpawnerStat.java

## Import Cycles
- None detected.

## Hyperedges (group relationships)
- **Project Overview** — .gitlab-ci.yml_publish-public, CHANGELOG.md_0.0.0-beta.5, README.md_ascendant-spawners [INFERRED]
- **Dependencies for Ascendancy** — ascendant_spawners_common_toolkit, ascendant_spawners_apothic_spawners [EXTRACTED 1.00]

## Communities (52 total, 15 thin omitted)

### Community 0 - "World Generation"
Cohesion: 0.05
Nodes (38): AABB, Biome, BiomeManager, ChunkAccess, ChunkSource, ChunkStatus, Context, DifficultyInstance (+30 more)

### Community 1 - "Recipe Management"
Cohesion: 0.09
Nodes (24): LegacyRecipeProvider, RecipeOutput, ASRecipeProvider, PackOutput, Recipe, getSerializedName(), Codec, MapCodec (+16 more)

### Community 2 - "Spawner Behavior"
Cohesion: 0.08
Nodes (17): SpawnerTile, BooleanStat, Codec, Component, Override, RegistryFriendlyByteBuf, StreamCodec, CustomStat (+9 more)

### Community 3 - "Event Handling"
Cohesion: 0.09
Nodes (23): EventBusSubscriber, Ingredient, Nullable, PlacementInfo, Recipe, RecipeBookCategory, RecipeDisplay, RecipeInput (+15 more)

### Community 4 - "Mod Integration"
Cohesion: 0.09
Nodes (24): Client, FMLCommonSetupEvent, IBlockComponentProvider, IPluginConfig, ITooltip, Mod, NewRegistryEvent, Registry (+16 more)

### Community 5 - "Entity Spawning"
Cohesion: 0.13
Nodes (22): BaseSpawner, Either, EntitySpawnReason, Mob, SpawnData, SpawnerBlockEntity, BlockEntity, BlockPos (+14 more)

### Community 6 - "Data Providers"
Cohesion: 0.12
Nodes (20): ConnectionProtocol, CustomPacketPayload, FriendlyByteBuf, IPayloadContext, LootTableProvider, LootTableSubProvider, PacketFlow, PayloadProvider (+12 more)

### Community 7 - "Block Interaction"
Cohesion: 0.17
Nodes (19): BlockHitResult, Explosion, InteractionHand, InteractionResult, LevelReader, LivingEntity, BlockEntity, BlockPos (+11 more)

### Community 8 - "Item Handling"
Cohesion: 0.12
Nodes (20): BlockItem, ModifyVariable, Operation, Block, Component, ItemStack, Override, Player (+12 more)

### Community 9 - "Data Management"
Cohesion: 0.13
Nodes (19): BootstrapContext, DataComponentGetter, DataComponentPredicate, DataComponentType, DeferredHelper, DeferredHolder, MapCodec, SpawnEggItemPredicate (+11 more)

### Community 10 - "Game Events"
Cohesion: 0.15
Nodes (14): EntityTeleportEvent, ItemTooltipEvent, LivingDropsEvent, LivingExperienceDropEvent, MethodHandle, MobDespawnEvent, MobSplitEvent, OnDatapackSyncEvent (+6 more)

### Community 11 - "GUI Elements"
Cohesion: 0.16
Nodes (13): GuiGraphicsExtractor, IDrawable, IFocusGroup, IGuiHelper, IRecipeCategory, IRecipeLayoutBuilder, IRecipeSlotsView, IRecipeType (+5 more)

### Community 13 - "SpawnerServerDataProvider.java"
Cohesion: 0.18
Nodes (13): CompoundTag, IServerDataProvider, IWailaClientRegistration, IWailaCommonRegistration, IWailaPlugin, Override, SpawnerJadePlugin, BlockAccessor (+5 more)

### Community 14 - "Stat Management"
Cohesion: 0.25
Nodes (6): Codec, Component, Override, RegistryFriendlyByteBuf, StreamCodec, LevelStat

### Community 15 - "Percentage Stat"
Cohesion: 0.25
Nodes (6): Codec, Component, Override, RegistryFriendlyByteBuf, StreamCodec, PercentageStat

### Community 16 - "JEI Integration"
Cohesion: 0.27
Nodes (8): IModPlugin, IRecipeCatalystRegistration, IRecipeCategoryRegistration, IRecipeRegistration, JeiPlugin, Identifier, Override, SpawnerJEIPlugin

### Community 18 - "Mixin Overrides"
Cohesion: 0.53
Nodes (4): ModifyArg, BlocksMixin, Block, Mixin

### Community 20 - "Build Scripts"
Cohesion: 0.83
Nodes (3): gradlew script, die(), warn()

### Community 21 - "Release Notes"
Cohesion: 0.67
Nodes (3): Publish Public Job, Changelog Version 0.0.0-beta.5, Ascendant Spawners README

### Community 25 - "Roadmap — Ascendant Spawners (port de ApothicSpawners)"
Cohesion: 0.12
Nodes (14): Base legal — obligatorio mantener siempre, Convención de renombrado, Cómo se alimenta a OpenCode, Dependencias externas, Estado, Estructura real del JAR original (35 clases), Fases, Naturaleza del proyecto (+6 more)

### Community 26 - "Mapa de renombrado — ApothicSpawners → Ascendant Spawners"
Cohesion: 0.15
Nodes (12): `com.skd.ascendantspawners.advancements`, `com.skd.ascendantspawners.block`, `com.skd.ascendantspawners.compat`, `com.skd.ascendantspawners.data`, `com.skd.ascendantspawners.mixin`, `com.skd.ascendantspawners.modifiers`, `com.skd.ascendantspawners.stats`, Conteo (+4 more)

### Community 27 - "Flujo de trabajo — Ascendant Spawners (NeoForge)"
Cohesion: 0.15
Nodes (12): Buenas prácticas, Commits (Conventional Commits), Convenciones de nomenclatura, Específico del mod, Estructura del proyecto, Flujo de trabajo — Ascendant Spawners (NeoForge), Flujo por tarea, Idioma (+4 more)

### Community 28 - "CurseForge — Variables del proyecto"
Cohesion: 0.18
Nodes (10): CurseForge — Variables del proyecto, Datos para el alta manual (formulario "Create Project"), Icono / imagen del proyecto, Nota, Proyecto, Rama, Tag, Tokens (+2 more)

### Community 29 - "Changelog — Ascendant Spawners"
Cohesion: 0.09
Nodes (21): 0.0.0-beta.1, 0.0.0-beta.10, 0.0.0-beta.1, 0.0.0-beta.2, 0.0.0-beta.2, 0.0.0-beta.3, 0.0.0-beta.3, 0.0.0-beta.4 (+13 more)

### Community 30 - "CLAUDE.md — ascendant_spawners (26.2)"
Cohesion: 0.50
Nodes (3): CLAUDE.md — ascendant_spawners (26.2), Prioridad de instrucciones, Workflow del mod

### Community 48 - "BooleanStat"
Cohesion: 0.26
Nodes (8): ContextAwarePredicate, Ints, ServerPlayer, SimpleCriterionTrigger, SimpleInstance, Codec, ModifierTrigger, TriggerInstance

## Knowledge Gaps
- **79 isolated node(s):** `ADD`, `SET`, `Workflow del mod`, `Prioridad de instrucciones`, `0.0.0-beta.10` (+74 more)
  These have ≤1 connection - possible missing edges or undocumented components.
- **15 thin communities (<3 nodes) omitted from report** — run `graphify query` to explore isolated nodes.

## Suggested Questions
_Questions this graph is uniquely positioned to answer:_

- **Why does `SpawnerTile` connect `Spawner Behavior` to `Recipe Management`, `Event Handling`, `Mod Integration`, `Entity Spawning`, `Stat Management`, `Percentage Stat`, `BooleanStat`?**
  _High betweenness centrality (0.179) - this node is a cross-community bridge._
- **Why does `SpawnerStat` connect `Recipe Management` to `BooleanStat`, `Spawner Behavior`, `Mod Integration`, `Entity Spawning`?**
  _High betweenness centrality (0.071) - this node is a cross-community bridge._
- **Why does `SpawnerModifier` connect `Event Handling` to `BooleanStat`, `Data Management`, `GUI Elements`, `Recipe Management`?**
  _High betweenness centrality (0.067) - this node is a cross-community bridge._
- **What connects `ADD`, `SET`, `Workflow del mod` to the rest of the system?**
  _79 weakly-connected nodes found - possible documentation gaps or missing edges._
- **Should `World Generation` be split into smaller, more focused modules?**
  _Cohesion score 0.054363796650014694 - nodes in this community are weakly interconnected._
- **Should `Recipe Management` be split into smaller, more focused modules?**
  _Cohesion score 0.08928571428571429 - nodes in this community are weakly interconnected._
- **Should `Spawner Behavior` be split into smaller, more focused modules?**
  _Cohesion score 0.0841813135985199 - nodes in this community are weakly interconnected._