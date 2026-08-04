# Graph Report - .  (2026-08-04)

## Corpus Check
- cluster-only mode — file stats not available

## Summary
- 581 nodes · 1274 edges · 25 communities (24 shown, 1 thin omitted)
- Extraction: 99% EXTRACTED · 1% INFERRED · 0% AMBIGUOUS · INFERRED: 17 edges (avg confidence: 0.8)
- Token cost: 1,218 input · 225 output

## Graph Freshness
- Built from commit: `2f2fe911`
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
- Mod Support
- Predicate Logic
- Stat Management
- Percentage Stat
- JEI Integration
- Documentation
- Mixin Overrides
- Initial Setup
- Build Scripts
- Release Notes
- Project Overview

## God Nodes (most connected - your core abstractions)
1. `SpawnerTile` - 52 edges
2. `LyingLevel` - 47 edges
3. `SpawnerModifier` - 37 edges
4. `SpawnerStat` - 33 edges
5. `StatModifier` - 24 edges
6. `Provider` - 21 edges
7. `SpawnerCategory` - 17 edges
8. `AscendantSpawners` - 15 edges
9. `AscSpEvents` - 14 edges
10. `AscSpObjects` - 14 edges

## Surprising Connections (you probably didn't know these)
- `Flujo de trabajo — Ascendant Spawners (NeoForge)` --references--> `AscendantSpawners`  [EXTRACTED]
  docs/WORKFLOW_ASCENDANT_SPAWNERS_26-2.md → src/main/java/com/skd/ascendantspawners/AscendantSpawners.java
- `AscendantSpawners` --implements--> `Ascendant Equipment`  [EXTRACTED]
  src/main/java/com/skd/ascendantspawners/AscendantSpawners.java → docs/WORKFLOW_ASCENDANT_SPAWNERS_26-2.md
- `AscendantSpawners` --depends_on--> `Common Toolkit`  [EXTRACTED]
  src/main/java/com/skd/ascendantspawners/AscendantSpawners.java → docs/WORKFLOW_ASCENDANT_SPAWNERS_26-2.md
- `v0.0.0-beta.1 - Initial scaffold` --references--> `Icon for Ascendant Spawners`  [EXTRACTED]
  docs/curseforge/versions/0.0.0-beta.1.md → src/main/resources/assets/ascendant_spawners/icon.png
- `v0.0.0-beta.1 - Initial scaffold` --references--> `Apoth Advancement Background`  [EXTRACTED]
  docs/curseforge/versions/0.0.0-beta.1.md → src/main/resources/assets/ascendant_spawners/textures/gui/advancements/backgrounds/apoth.png

## Import Cycles
- None detected.

## Hyperedges (group relationships)
- **Project Overview** — .gitlab-ci.yml_publish-public, CHANGELOG.md_0.0.0-beta.5, README.md_ascendant-spawners [INFERRED]
- **Workflow for Ascendancy** — docs_WORKFLOW_ASCENDANT_SPAWNERS_26-2, ascendant_spawners_ascendantspawners [EXTRACTED 1.00]
- **Dependencies for Ascendancy** — ascendant_spawners_common_toolkit, ascendant_spawners_apothic_spawners [EXTRACTED 1.00]

## Communities (25 total, 1 thin omitted)

### Community 0 - "World Generation"
Cohesion: 0.06
Nodes (37): Biome, BiomeManager, ChunkAccess, ChunkSource, ChunkStatus, Context, DifficultyInstance, DimensionType (+29 more)

### Community 1 - "Recipe Management"
Cohesion: 0.09
Nodes (24): LegacyRecipeProvider, RecipeOutput, ASRecipeProvider, PackOutput, Recipe, getSerializedName(), Codec, MapCodec (+16 more)

### Community 2 - "Spawner Behavior"
Cohesion: 0.08
Nodes (18): SpawnerBlockEntity, SpawnerTile, BooleanStat, Codec, Component, Override, RegistryFriendlyByteBuf, StreamCodec (+10 more)

### Community 3 - "Event Handling"
Cohesion: 0.09
Nodes (23): EventBusSubscriber, Ingredient, Nullable, PlacementInfo, Recipe, RecipeBookCategory, RecipeDisplay, RecipeInput (+15 more)

### Community 4 - "Mod Integration"
Cohesion: 0.08
Nodes (27): ApothicSpawners, Ascendant Equipment, Common Toolkit, Client, Flujo de trabajo — Ascendant Spawners (NeoForge), FMLCommonSetupEvent, IBlockComponentProvider, IPluginConfig (+19 more)

### Community 5 - "Entity Spawning"
Cohesion: 0.12
Nodes (22): AABB, BaseSpawner, Either, EntitySpawnReason, Mob, SpawnData, BlockEntity, BlockPos (+14 more)

### Community 6 - "Data Providers"
Cohesion: 0.11
Nodes (21): ConnectionProtocol, CustomPacketPayload, FriendlyByteBuf, IPayloadContext, LootTableProvider, LootTableSubProvider, PacketFlow, PayloadProvider (+13 more)

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

### Community 12 - "Mod Support"
Cohesion: 0.18
Nodes (13): CompoundTag, IServerDataProvider, IWailaClientRegistration, IWailaCommonRegistration, IWailaPlugin, Override, SpawnerJadePlugin, BlockAccessor (+5 more)

### Community 13 - "Predicate Logic"
Cohesion: 0.26
Nodes (8): ContextAwarePredicate, Ints, ServerPlayer, SimpleCriterionTrigger, SimpleInstance, Codec, ModifierTrigger, TriggerInstance

### Community 14 - "Stat Management"
Cohesion: 0.25
Nodes (6): Codec, Component, Override, RegistryFriendlyByteBuf, StreamCodec, LevelStat

### Community 15 - "Percentage Stat"
Cohesion: 0.25
Nodes (6): Codec, Component, Override, RegistryFriendlyByteBuf, StreamCodec, PercentageStat

### Community 16 - "JEI Integration"
Cohesion: 0.27
Nodes (8): IModPlugin, IRecipeCatalystRegistration, IRecipeCategoryRegistration, IRecipeRegistration, JeiPlugin, Identifier, Override, SpawnerJEIPlugin

### Community 17 - "Documentation"
Cohesion: 0.29
Nodes (7): CLAUDE.md — ascendant_spawners (26.2), ../../codex-docs/reference/CLAUDE.md, ../../codex-docs/reference/CURSEFORGE.md, ../../codex-docs/reference/GRAPHIFY.md, ../../codex-docs/reference/REPO_SETUP.md, docs/WORKFLOW_ASCENDANT_SPAWNERS_26-2.md, stalking-dragons/minecraft

### Community 18 - "Mixin Overrides"
Cohesion: 0.53
Nodes (4): ModifyArg, BlocksMixin, Block, Mixin

### Community 19 - "Initial Setup"
Cohesion: 0.50
Nodes (4): v0.0.0-beta.1 - Initial scaffold, Icon for Ascendant Spawners, Apoth Advancement Background, JEI GUI for Spawner

### Community 20 - "Build Scripts"
Cohesion: 0.83
Nodes (3): gradlew script, die(), warn()

### Community 21 - "Release Notes"
Cohesion: 0.67
Nodes (3): Publish Public Job, Changelog Version 0.0.0-beta.5, Ascendant Spawners README

## Knowledge Gaps
- **18 isolated node(s):** `ADD`, `SET`, `Changelog Version 0.0.0-beta.5`, `Ascendant Spawners README`, `stalking-dragons/minecraft` (+13 more)
  These have ≤1 connection - possible missing edges or undocumented components.
- **1 thin communities (<3 nodes) omitted from report** — run `graphify query` to explore isolated nodes.

## Suggested Questions
_Questions this graph is uniquely positioned to answer:_

- **Why does `SpawnerTile` connect `Spawner Behavior` to `Recipe Management`, `Event Handling`, `Mod Integration`, `Entity Spawning`, `Predicate Logic`, `Stat Management`, `Percentage Stat`?**
  _High betweenness centrality (0.237) - this node is a cross-community bridge._
- **Why does `SpawnerStat` connect `Recipe Management` to `Entity Spawning`, `Spawner Behavior`, `Mod Integration`, `Predicate Logic`?**
  _High betweenness centrality (0.093) - this node is a cross-community bridge._
- **Why does `SpawnerModifier` connect `Event Handling` to `Recipe Management`, `Spawner Behavior`, `Data Management`, `GUI Elements`, `Predicate Logic`?**
  _High betweenness centrality (0.088) - this node is a cross-community bridge._
- **What connects `ADD`, `SET`, `Changelog Version 0.0.0-beta.5` to the rest of the system?**
  _18 weakly-connected nodes found - possible documentation gaps or missing edges._
- **Should `World Generation` be split into smaller, more focused modules?**
  _Cohesion score 0.05617283950617284 - nodes in this community are weakly interconnected._
- **Should `Recipe Management` be split into smaller, more focused modules?**
  _Cohesion score 0.08928571428571429 - nodes in this community are weakly interconnected._
- **Should `Spawner Behavior` be split into smaller, more focused modules?**
  _Cohesion score 0.07908163265306123 - nodes in this community are weakly interconnected._