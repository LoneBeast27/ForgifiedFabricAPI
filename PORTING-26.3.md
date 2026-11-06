# FFAPI 26.3 porting checklist

Base: FFAPI `26.2.x` (upstream 26.2 fully ported). Upstream 26.3 split from 26.2 at `db46eff76`.
Commits below are upstream 26.3 commits not already on 26.2 (cherry-pick duplicates removed), oldest first.
Mark each: [x] ported, [-] not applicable on NeoForge (say why).

| # | Status | Commit | Subject | Modules |
|---|---|---|---|---|
| 1 | [ ] | `64ade2094` | 26.3-snapshot-1 | biome-api-v1 content-registries-v0 data-attachment-api-v1 data-generation-api-v1 entity-events-v1 registry-sync-v0 renderer-api-v1 resource-conditions-api-v1 resource-loader-v1 transfer-api-v1 transitive-access-wideners-v1 gradle.properties src  |
| 2 | [ ] | `1dd6f2280` | 26.3-snapshot-2 | api-lookup-api-v1 biome-api-v1 client-gametest-api-v1 content-registries-v0 data-generation-api-v1 dimensions-v1 menu-api-v1 registry-sync-v0 renderer-api-v1 renderer-indigo rendering-v1 transitive-access-wideners-v1 gradle.properties  |
| 3 | [ ] | `86d237bc9` | Add callback for registering custom `BuiltInBlockModels` (#5447) | rendering-v1 transitive-access-wideners-v1 gradle  |
| 4 | [ ] | `264a9eac6` | 26.3 Convention Tags (#5453) | convention-tags-v2  |
| 5 | [ ] | `233ecb21d` | [26.3] Complete todo comments for AttachmentChange (#5430) | data-attachment-api-v1  |
| 6 | [ ] | `b7b417006` | Bump version | gradle.properties  |
| 7 | [ ] | `4401f1500` | Bump version | gradle.properties  |
| 8 | [ ] | `8687cec83` | Bump version | gradle.properties  |
| 9 | [ ] | `bca581690` | 26.3-snapshot-3 (#5478) | biome-api-v1 client-gametest-api-v1 content-registries-v0 data-generation-api-v1 entity-events-v1 item-api-v1 lifecycle-events-v1 recipe-api-v1 registry-sync-v0 renderer-api-v1 renderer-indigo rendering-v1 resource-conditions-api-v1 resource-loader-v1 tag-api-v1 transfer-api-v1 transitive-access-wideners-v1 gradle gradle.properties settings.gradle  |
| 10 | [ ] | `a3b5542f2` | Block Transformer Registry (#5463) | content-registries-v0  |
| 11 | [ ] | `32a0f6f27` | Dyeable tags, and fixes to dyed tag generation (#5464) | convention-tags-v2  |
| 12 | [ ] | `f580942df` | fix: fixed some mixins having unimplemented interface methods (#5492) | .gitignore build.gradle deprecated networking-api-v1 resource-loader-v1  |
| 13 | [ ] | `c7d63709f` | Allow easier checking if entity is in fluid, fluid enter/leaves methods, fluid flying travel override, some optimization (#5481) | content-registries-v0  |
| 14 | [ ] | `f8b9cfb07` | Bump version | gradle.properties  |
| 15 | [ ] | `c010610f5` | 26.3-snapshot-4 (#5497) | biome-api-v1 client-gametest-api-v1 content-registries-v0 creative-tab-api-v1 data-attachment-api-v1 data-generation-api-v1 events-interaction-v0 gametest-api-v1 item-api-v1 key-mapping-api-v1 lifecycle-events-v1 loot-api-v3 menu-api-v1 networking-api-v1 object-builder-api-v1 renderer-api-v1 rendering-v1 resource-conditions-api-v1 screen-api-v1 transitive-access-wideners-v1 gradle.properties settings.gradle  |
| 16 | [ ] | `e58d54f4c` | Fix datagen crash with non-fabric-api tag providers (closes #5431) (#5494) | data-generation-api-v1  |
| 17 | [ ] | `e18ee678a` | Bump version | gradle.properties  |
| 18 | [ ] | `4ad7a1d1e` | Port FRAPI & co to Snapshot 4 (#5498) | renderer-api-v1 renderer-indigo settings.gradle  |
| 19 | [ ] | `0f57eee76` | 26.3-snapshot-5 | data-generation-api-v1 networking-api-v1 recipe-api-v1 registry-sync-v0 rendering-v1 transfer-api-v1 transitive-access-wideners-v1 gradle.properties  |
| 20 | [ ] | `3dc58bfd2` | Loot api port (#5509) | loot-api-v3 settings.gradle  |
| 21 | [ ] | `2937cdae2` | Cleanup item data init during tests. (#5512) | item-api-v1 recipe-api-v1 transfer-api-v1  |
| 22 | [ ] | `2757fa7f6` | Make BlockModelGenerators's PropertyDispatch fields public (#5513) | data-generation-api-v1  |
| 23 | [ ] | `20f69ddd7` | Bump version | gradle.properties  |
| 24 | [ ] | `eca106b8f` | 26.3-snapshot-6 | biome-api-v1 data-attachment-api-v1 entity-events-v1 events-interaction-v0 rendering-v1 transfer-api-v1 transitive-access-wideners-v1 gradle.properties  |
| 25 | [ ] | `1290c0802` | 26.3-snapshot-7 | biome-api-v1 content-registries-v0 data-generation-api-v1 entity-events-v1 events-interaction-v0 particles-v1 renderer-api-v1 renderer-indigo resource-loader-v1 transfer-api-v1 transitive-access-wideners-v1 gradle.properties  |
| 26 | [ ] | `bd82bc0b7` | Snapshot 7 porting fixes (#5530) | rendering-v1 transfer-api-v1  |
| 27 | [ ] | `520d4f658` | Add Concrete Slabs and Stairs to dyed tags (#5529) | convention-tags-v2  |
| 28 | [ ] | `28ac08263` | Bump version | gradle.properties  |
| 29 | [ ] | `5b64ffdec` | Bump version | gradle.properties  |
| 30 | [ ] | `d4a3cef76` | 26.3-snapshot-8 | biome-api-v1 client-gametest-api-v1 dimensions-v1 item-api-v1 renderer-api-v1 rendering-v1 transitive-access-wideners-v1 gradle.properties  |
| 31 | [ ] | `c00f1dd43` | 26.3-snapshot-9 | data-generation-api-v1 rendering-v1 gradle.properties  |
| 32 | [ ] | `1725617d4` | Fix FRAPI & co porting errors/mistakes (#5516) | renderer-api-v1 renderer-indigo  |
| 33 | [ ] | `d38fe3102` | Allow `FabricDynamicRegistryProvider` to target reloadable registries (#5545) | data-generation-api-v1 registry-sync-v0  |
| 34 | [ ] | `92968092d` | Bump version | gradle.properties  |
| 35 | [ ] | `74f70341d` | Rewrite build script (#5416) | .github .gitignore build-logic build.gradle buildSrc deprecated api-base api-bom api-catalog api-lookup-api-v1 biome-api-v1 block-api-v1 block-getter-api-v2 client-gametest-api-v1 command-api-v2 content-registries-v0 convention-tags-v2 crash-report-info-v1 creative-tab-api-v1 data-attachment-api-v1 data-generation-api-v1 debug-api-v1 dimensions-v1 entity-events-v1 events-interaction-v0 game-rule-api-v1 gametest-api-v1 item-api-v1 key-mapping-api-v1 lifecycle-events-v1 loot-api-v3 menu-api-v1 message-api-v1 model-loading-api-v1 networking-api-v1 object-builder-api-v1 particles-v1 permission-api-v1 recipe-api-v1 registry-sync-v0 renderer-api-v1 renderer-indigo rendering-fluids-v1 rendering-v1 resource-conditions-api-v1 resource-loader-v1 screen-api-v1 serialization-api-v1 sound-api-v1 tag-api-v1 transfer-api-v1 transitive-access-wideners-v1 gradle gradle.properties gradlew gradlew.bat settings.gradle  |
| 36 | [ ] | `3434d6d9f` | Update fabric-loader | deprecated api-base api-lookup-api-v1 biome-api-v1 block-api-v1 block-getter-api-v2 client-gametest-api-v1 command-api-v2 content-registries-v0 convention-tags-v2 crash-report-info-v1 creative-tab-api-v1 data-attachment-api-v1 data-generation-api-v1 debug-api-v1 dimensions-v1 entity-events-v1 events-interaction-v0 game-rule-api-v1 gametest-api-v1 item-api-v1 key-mapping-api-v1 lifecycle-events-v1 loot-api-v3 menu-api-v1 message-api-v1 model-loading-api-v1 networking-api-v1 object-builder-api-v1 particles-v1 permission-api-v1 recipe-api-v1 registry-sync-v0 renderer-api-v1 renderer-indigo rendering-fluids-v1 rendering-v1 resource-conditions-api-v1 resource-loader-v1 screen-api-v1 serialization-api-v1 sound-api-v1 tag-api-v1 transfer-api-v1 transitive-access-wideners-v1 gradle src  |
| 37 | [ ] | `8a29444a2` | Bump version | gradle.properties  |
| 38 | [ ] | `47f74c98e` | 26.3-snapshot-10 | biome-api-v1 content-registries-v0 dimensions-v1 lifecycle-events-v1 model-loading-api-v1 permission-api-v1 rendering-v1 transitive-access-wideners-v1 gradle.properties  |
| 39 | [ ] | `596e54c6c` | Revamped Block Transformer API (#5552) | content-registries-v0 item-api-v1 gradle  |
| 40 | [ ] | `647ab97ae` | Fix tracy capture of client tests in CI (#5550) | .github build-logic  |
| 41 | [ ] | `4731dd4d3` | Bump version | gradle.properties  |
| 42 | [ ] | `00c76339b` | Rename hud status bar code to be less confusing (#5558) | rendering-v1  |
| 43 | [ ] | `4c7d9fa3c` | Add `ConventionalBlockItemTags` (#5551) | convention-tags-v2 data-generation-api-v1  |
| 44 | [ ] | `08f11cc74` | Add support for info bar (#5557) | rendering-v1  |
| 45 | [ ] | `352f27c7f` | Bump version | gradle.properties  |
| 46 | [ ] | `74ed1ea5f` | 26.3-pre-1 | biome-api-v1 client-gametest-api-v1 content-registries-v0 data-generation-api-v1 item-api-v1 loot-api-v3 serialization-api-v1 transfer-api-v1 gradle.properties  |
| 47 | [ ] | `52268a0e4` | Add TAWs for loot functions without builders (#5564) | transitive-access-wideners-v1  |
| 48 | [ ] | `5a4e5fc69` | Fix dependency cycle in ConventionalBlockItemTags (#5565) | convention-tags-v2  |
| 49 | [ ] | `5f36c2b4c` | Dynamic registry tweaks for Minecraft 26.3 (#5566) | data-generation-api-v1 registry-sync-v0  |
| 50 | [ ] | `edb2878f2` | Load vanillas reloadable registry data during datagen (#5567) | data-generation-api-v1  |
| 51 | [ ] | `fdb9bf400` | Add FabricRenderState to PlayerRenderState (#5568) | rendering-v1  |
| 52 | [ ] | `2a136704c` | Fix bad merge by adding empty modded registries. | data-generation-api-v1  |
| 53 | [ ] | `c138bc9d2` | Bump version | gradle.properties  |
| 54 | [ ] | `61478cab8` | Dont modify loot tables during datagen. (#5570) | data-generation-api-v1 loot-api-v3  |
| 55 | [ ] | `3071734ea` | Bump version | gradle.properties  |
| 56 | [ ] | `895f85e25` | 26.3-pre-2 | client-gametest-api-v1 data-generation-api-v1 loot-api-v3 gradle.properties  |
| 57 | [ ] | `c3260d5cc` | Add validation and warning for missing content registry entries. (#5503) | registry-sync-v0  |
| 58 | [ ] | `06d55ed64` | Fluid Flow API (#5553) | block-api-v1  |
| 59 | [ ] | `b1988e060` | Replace enableColoredVanillaFluidNames with saner alternative (#5569) | transfer-api-v1  |
| 60 | [ ] | `82d0ab41e` | Allow mods to define reloadable registries in DataGeneratorEntrypoint (#5575) | data-generation-api-v1  |
| 61 | [ ] | `fcdff87fe` | Indent enhanced switches (#5576) | build-logic checkstyle.xml api-base client-gametest-api-v1 command-api-v2 crash-report-info-v1 creative-tab-api-v1 item-api-v1 model-loading-api-v1 networking-api-v1 permission-api-v1 recipe-api-v1 registry-sync-v0 renderer-api-v1 renderer-indigo resource-loader-v1 tag-api-v1 transfer-api-v1  |
| 62 | [ ] | `355f611db` | Fix javadoc build | data-generation-api-v1  |
| 63 | [ ] | `7984737f4` | Bump version | gradle.properties  |
| 64 | [ ] | `4be74c3f5` | 26.3-pre-3 | client-gametest-api-v1 gradle.properties  |
| 65 | [ ] | `6317c5a76` | Fix vanilla killAllMobs advancement check failing due to modded entities. (#5579) | data-generation-api-v1  |
| 66 | [ ] | `5918f9137` | Bump version | gradle.properties  |
| 67 | [ ] | `2ce735b86` | Port to 26.3-rc-1 (#5585) | renderer-api-v1 gradle.properties  |
| 68 | [ ] | `b108b08bd` | Fix loot providers lacking reloadable registry context (#5583) | loot-api-v3  |
| 69 | [ ] | `901a437c7` | Fix hud status bars for height != 10 (#5578) | rendering-v1  |
| 70 | [ ] | `542047fff` | 26.3-rc-2 & Bump version | gradle.properties  |
| 71 | [ ] | `b9d63e336` | Fix Indigo handling of special glint (#5590) | renderer-indigo  |
| 72 | [ ] | `d9882cf86` | 26.3-rc-3 | gradle.properties  |
| 73 | [ ] | `859f946c6` | Bump version | gradle.properties  |
| 74 | [ ] | `82f7a65d8` | Fix waxed recipe generation not being blocked off (#5592) | data-generation-api-v1  |
| 75 | [ ] | `1a1d7cf04` | Allow providing component tooltips via TooltipProvider.Getter (#5589) | item-api-v1  |
| 76 | [ ] | `ed66f0a31` | Fix ExtendedMenuType javadoc example (#5582) | menu-api-v1  |
| 77 | [ ] | `4068fd646` | Access widen ContextIntProviders compostable and cooking helpers (#5593) | loot-api-v3  |
| 78 | [ ] | `cf6bc2db3` | Remove `BlockTransformer` codec size limit (#5584) | item-api-v1  |
| 79 | [ ] | `b28e9d20b` | Improve FabricLanguageProvider (#5580) | data-generation-api-v1  |
| 80 | [ ] | `fed534874` | 26.3 | gradle.properties  |
| 81 | [ ] | `ea97b339c` | Bump version | gradle.properties  |
| 82 | [ ] | `79385d0b1` | Respect MovingBlockFeatureRenderer$Submit.forceTranslucent in MovingBlockFeatureRendererMixin (#5598) | renderer-api-v1  |
| 83 | [ ] | `48607d038` | Fix ScreenEvents extraction example (#5596) | screen-api-v1  |
| 84 | [ ] | `d001c209d` | Allow `BrewingRecipeBuilder` to generate based off modid (#5595) | data-generation-api-v1  |
| 85 | [ ] | `64902b849` | Make encode failure in FabricDynamicRegistryProvider throw a proper exception (#5594) | data-generation-api-v1  |
| 86 | [ ] | `7531c909b` | Bump version | gradle.properties  |
| 87 | [ ] | `427eab971` | Fix common issues with brewing providers (#5602) | data-generation-api-v1  |
| 88 | [ ] | `6b5b47fd2` | Add missing Dapple Forest tags (#5601) | convention-tags-v2  |
| 89 | [ ] | `38c7a2d54` | Advancement API (#5600) | advancement-api-v1 gradle.properties settings.gradle  |
| 90 | [ ] | `84831249d` | Bump version | gradle.properties  |
| 91 | [ ] | `ba0d6c0dc` | Various DX fixes (#5604) | build-logic resource-loader-v1  |
| 92 | [ ] | `b9b88df41` | Various fixes/improvements | data-attachment-api-v1 events-interaction-v0 networking-api-v1 recipe-api-v1  |
| 93 | [ ] | `c6aa69a8b` | Add live read access and RegistryAccess to the biome modification API (#5606) | biome-api-v1 gradle  |
| 94 | [ ] | `5be2453cb` | Expose tag removals on TagBuilder through a public API (#5608) | data-generation-api-v1  |
| 95 | [ ] | `2561807f6` | Set uncompressNestedJars for a 32.8% decrease in the file size of the final built jar (#5611) | build-logic  |
| 96 | [ ] | `0d124ae83` | Rename OverlayConditionsMetadata to FabricOverlayMetadataSection and move it into api (#5618) | resource-conditions-api-v1  |
| 97 | [ ] | `1ca9f6360` | Fix typo in registry sync error message (#5620) | registry-sync-v0  |
| 98 | [ ] | `0b50ffdc5` | Bump version | gradle.properties  |
| 99 | [ ] | `e985c3608` | Allow registering built-in packs at a custom path (#5609) | data-generation-api-v1 resource-loader-v1  |
| 100 | [ ] | `e2f63d3e7` | Backported 'TAW PoiTypes#register(...) and deprecate old utility class (#5626)' to 26.3. (#5636) | object-builder-api-v1 transitive-access-wideners-v1  |
| 101 | [ ] | `56ddf8645` | Add hud status bars replace height (#5577) | rendering-v1  |
| 102 | [ ] | `98524bff5` | Update contributing guidelines to include more nuance about context objects for events (#5633) | CONTRIBUTING.md  |
| 103 | [ ] | `5af7744bc` | Deprecate public utility class constructors for removal (#5634) | checkstyle.xml debug-api-v1 dimensions-v1 item-api-v1 rendering-v1 resource-loader-v1  |
| 104 | [ ] | `5c1fee4db` | Add warnings about using large max split payload sizes on serverbound packets. (#5639) | networking-api-v1  |
| 105 | [ ] | `33b369075` | Bump version | gradle.properties  |
| 106 | [ ] | `501c7ef57` | Fix duplicate brewing container recipes for vanilla and other mods potions (#5645) | data-generation-api-v1  |
| 107 | [ ] | `d1c0d72b3` | Allow custom fishing rods to function (#5646) | item-api-v1  |
| 108 | [ ] | `94bd0b6d6` | Add Poplar Chest Boat to boat tag (#5650) | convention-tags-v2  |
| 109 | [ ] | `899e30534` | Add missing tag entries and some reordering of tag entries (#5654) | convention-tags-v2  |
| 110 | [ ] | `131f8d474` | Fix possible deadlock during client tests. (#5656) | client-gametest-api-v1  |
| 111 | [ ] | `a335c35c0` | Bump version | gradle.properties  |
| 112 | [ ] | `a21c9eb6c` | Add support for conditional payload decoding (#5653) | networking-api-v1  |
| 113 | [ ] | `85210bcaf` | Do not mutate the BiomeSpecialEffects record. (#5657) | biome-api-v1  |
| 114 | [ ] | `53c10cb77` | Add no-op run() impl in SimpleFabricLootTableSubProvider (#5658) | data-generation-api-v1  |
| 115 | [ ] | `eb9fe70b0` | Add VanillaHudElements.INFO_BAR_FOREGROUND (#5659) | rendering-v1  |
| 116 | [ ] | `187b6490a` | [26.3] Ice tag and datagen util (#5649 Backport) (#5660) | convention-tags-v2 data-generation-api-v1  |
| 117 | [ ] | `2065040b2` | Bump version | gradle.properties  |
| 118 | [ ] | `c5812978c` | Various fixes and improvements | networking-api-v1  |
| 119 | [ ] | `99fb1a769` | Fix registry-dependent attachment persistence and simplify codec (#5673) | data-attachment-api-v1  |
| 120 | [ ] | `140095664` | Fix item loss when extracting from bundles (#5674) | transfer-api-v1  |
| 121 | [ ] | `8f511c9f8` | Fix custom damage handler break callbacks (#5675) | item-api-v1  |
| 122 | [ ] | `fd3b1d562` | Fix registry_contains creating unbound holders (#5676) | resource-conditions-api-v1  |
| 123 | [ ] | `47a39d69a` | Bump version | gradle.properties  |
