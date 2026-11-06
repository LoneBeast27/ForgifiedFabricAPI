# FFAPI 26.3 port — merge notes

Merge of upstream Fabric API `26.3` (0.165.2) into FFAPI `26.2.x`. Target: Minecraft 26.3, NeoForge 26.3.0.100.
The merge is reproducible with the rule script described below; hand-merged files are listed with the reasoning.

## Mechanical rules (scripted)
- Rename detection limited to exact renames (`-X find-renames=100%`): fuzzy detection paired unrelated files.
- Upstream `buildSrc` -> `build-logic` rename dropped; FFAPI keeps its own Kotlin `buildSrc`.
- Files FFAPI deliberately deleted stay deleted even if upstream modified them (49 files); upstream renames of such files are dropped too:
  - dropped fabric-item-api-v1/src/client/java/net/fabricmc/fabric/mixin/item/client/FirstPersonHandsAndItemsMixin.java (rename of FFAPI-deleted fabric-item-api-v1/src/client/java/net/fabricmc/fabric/mixin/item/client/ItemInHandRendererMixin.java)
  - dropped fabric-renderer-api-v1/src/client/java/net/fabricmc/fabric/mixin/client/renderer/block/particle/LevelExtractorMixin.java (rename of FFAPI-deleted fabric-renderer-api-v1/src/client/java/net/fabricmc/fabric/mixin/client/renderer/block/particle/ScreenEffectRendererMixin.java)
- Files upstream deleted are deleted (FFAPI changes to them: 4 files, mostly the removed strippable/tillable/compostable registries replaced upstream by the Block Transformer API).
- 3-way merges use upstream **26.2** as the base (what FFAPI was ported from), not the historical merge-base.
- Generated testmod data: upstream. Per-module `build.gradle`, root build files, CI workflow: FFAPI.
- `*.mixins.json`: FFAPI list, minus mixins upstream removed, plus mixins new in 26.3 whose class exists in the tree.
- `fabric.mod.json`: FFAPI file; dependency changes taken from upstream except deps FFAPI intentionally dropped.

## Vanilla 26.3 changes that drove hand edits
- `com.mojang.blaze3d.pipeline.*` -> `com.mojang.renderpearl.api.pipeline.*` (RenderPipeline, PrimitiveTopology, PolygonMode, ...).
- `BedBlock` -> `AbstractBedBlock`; `startSleepInBed` gained `(AbstractBedBlock, BlockState, BedRule, BlockPos)` params.
- `ConfiguredFeature`/`ConfiguredWorldCarver` -> `Feature`/`WorldCarver` (`Registries.FEATURE`, `Registries.CARVER`).
- Biome mob spawns live in the `NATURAL_MOB_SPAWNS` environment attribute; fog/sky colours are `Vector3f`.
- Loot tables load through `RegistryDataLoader` / `ResourceManagerRegistryLoadTask`.
- Component tooltips use `TooltipProvider.Getter`; `ItemStack.hurtAndBreak` consumer is `Consumer<ItemStack>`.

## NeoForge 26.3 changes that drove hand edits
- `DataPackRegistryEvent.NewRegistry` -> `NewDatapackRegistryEvent` (package-private ctor; accessor updated); reloadable registries supported.
- `RegistryDataLoader.load` in `ReloadableServerRegistries.reload` takes an extra `List<PendingTags>` arg.
- `SimpleJsonResourceReloadListener.scanDirectory(...)` now returns `Map`; `scanDirectoryWithModifier` removed.
- `ServerPlayer.startSleepInBed` vanilla checks run inside a Supplier lambda; captured args are matched by name (`@Local(argsOnly = true)`).
- NeoForge's own `StencilTest` was removed; upstream `RenderPipelineBuilderMixin` applies unchanged.

## Notable decisions (review these)
1. biome-api: `BiomeModificationContextImpl` rewritten on NeoForge's `ModifiableBiomeInfo` builders, adding the 26.3 read accessors.
2. registry-sync: `DynamicRegistriesImpl` rewritten on `NewDatapackRegistryEvent`; `registerSynced` attaches the network codec to the pending registration.
3. registry-sync: upstream's new world "custom content" validation screens + `MinecraftServerMixin` + redirect mixins removed (NeoForge covers it).
4. loot-api: switched to upstream's 26.3 `RegistryDataLoader`-based hooks (FFAPI's old `scheduleRegistryLoad` lambda target no longer exists).
5. item-api: `ResourceUtil` carries FFAPI's `FabricResource` cast + NeoForge known-pack check; tooltip registry bridged to `RegisterTooltipAppendersEvent` with getters.
6. rendering-v1: FFAPI's `fixNonQuadIndexing` GUI workaround dropped (its blaze3d targets are gone) — **re-check non-quad GUI pipelines in a client test**.
7. item-api: new upstream `BlockTransformerMixin` kept; verify it does not double-apply with NeoForge's tool actions.

## Follow-ups
- Upstream edits to FFAPI-deleted files are kept in the review diff for auditing (not committed).

## CI fix log
- Restored FFAPI per-module `build.gradle` files that upstream's build-logic rewrite replaced cleanly in the merge.
- content-registries: kept a classtweaker with only the `EntityFluidExtension` interface injection (upstream deleted the file; potion-brewing entries removed with the API); added `fabric-item-api-v1` module dependency (oxidation scraping now uses `BlockTransformerHelperImpl`); dropped `DataMapHooksMixin` (fuel events removed upstream — fuel is the `cookingFuel` component in 26.3) and the COMPOSTABLES branch of `DataMapModifications` (compostable registry removed upstream).
- biome-api: NeoForge 26.3 `BiomeModifier.modify` now receives `RegistryAccess` (no more `ServerLifecycleHooks` lookup); creature spawn probability is the `CREATURE_WORLD_GEN_SPAWN_PROBABILITY` attribute; spawns/costs written via widened `MobSpawnSettings$Builder.forCategory` / `mobSpawnCosts`.
- advancement-api: advancements are a reloadable registry in 26.3 — removed FFAPI's old `ServerAdvancementManagerMixin` / `SimpleJson*`/`SimplePreparable*` mixins and `AdvancementSourceTracker`; upstream `ReloadableServerRegistriesMixin` adapted to NeoForge's 5-arg `RegistryDataLoader.load`; `AdvancementUtil.determineSource` uses the FFAPI `FabricResource` cast + known-pack check.
- resource-conditions: NeoForge `ResourcePackLoaderMixin` uses upstream's renamed `FabricOverlayMetadataSection` and `PackMetadataResources`.
- events-interaction: removed `FakePlayerPacketListener.handleAnimate` (swing packet no longer exists in 26.3).
- object-builder: widened `PoiTypes.register` locally (private in 26.3).
