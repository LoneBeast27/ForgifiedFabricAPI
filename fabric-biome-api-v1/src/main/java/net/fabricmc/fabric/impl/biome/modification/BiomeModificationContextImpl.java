/*
 * Copyright (c) 2016, 2017, 2018, 2019 FabricMC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.fabricmc.fabric.impl.biome.modification;

import java.util.Collections;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiPredicate;

import net.neoforged.neoforge.common.world.BiomeGenerationSettingsBuilder;
import net.neoforged.neoforge.common.world.BiomeSpecialEffectsBuilder;
import net.neoforged.neoforge.common.world.ClimateSettingsBuilder;
import net.neoforged.neoforge.common.world.EnvironmentAttributeMapBuilder;
import net.neoforged.neoforge.common.world.MobSpawnSettingsBuilder;
import net.neoforged.neoforge.common.world.ModifiableBiomeInfo;
import org.jetbrains.annotations.UnmodifiableView;
import org.jspecify.annotations.Nullable;

import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.ARGB;
import net.minecraft.util.random.Weighted;
import net.minecraft.util.random.WeightedList;
import net.minecraft.world.attribute.EnvironmentAttribute;
import net.minecraft.world.attribute.EnvironmentAttributeMap;
import net.minecraft.world.attribute.EnvironmentAttributes;
import net.minecraft.world.attribute.modifier.AttributeModifier;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSpecialEffects;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.carver.WorldCarver;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

import net.fabricmc.fabric.api.biome.v1.BiomeModificationContext;

/**
 * NeoForge implementation: all modifications go through NeoForge's {@link ModifiableBiomeInfo.BiomeInfo.Builder},
 * which also takes care of folding mob spawn settings into the {@code NATURAL_MOB_SPAWNS} environment attribute (26.3).
 */
public class BiomeModificationContextImpl implements BiomeModificationContext {
	private final RegistryAccess registries;
	private final Biome biome;
	private final ModifiableBiomeInfo.BiomeInfo.Builder builder;
	private final WeatherContext weather;
	private final AttributesContextImpl attributes;
	private final EffectsContextImpl effects;
	private final GenerationSettingsContextImpl generationSettings;
	private final SpawnSettingsContextImpl spawnSettings;

	public BiomeModificationContextImpl(RegistryAccess registries, Biome biome, ModifiableBiomeInfo.BiomeInfo.Builder builder) {
		this.registries = registries;
		this.biome = biome;
		this.builder = builder;
		this.weather = new WeatherContextImpl();
		this.attributes = new AttributesContextImpl();
		this.effects = new EffectsContextImpl();
		this.generationSettings = new GenerationSettingsContextImpl();
		this.spawnSettings = new SpawnSettingsContextImpl();
	}

	@Override
	public WeatherContext getWeather() {
		return weather;
	}

	@Override
	public AttributesContext getAttributes() {
		return attributes;
	}

	@Override
	public EffectsContext getEffects() {
		return effects;
	}

	@Override
	public GenerationSettingsContext getGenerationSettings() {
		return generationSettings;
	}

	@Override
	public MobSpawnSettingsContext getMobSpawnSettings() {
		return spawnSettings;
	}

	private class WeatherContextImpl implements WeatherContext {
		private final ClimateSettingsBuilder climateSettings = builder.getClimateSettings();

		@Override
		public boolean hasPrecipitation() {
			return climateSettings.hasPrecipitation();
		}

		@Override
		public float getTemperature() {
			return climateSettings.getTemperature();
		}

		@Override
		public Biome.TemperatureModifier getTemperatureModifier() {
			return climateSettings.getTemperatureModifier();
		}

		@Override
		public float getDownfall() {
			return climateSettings.getDownfall();
		}

		@Override
		public void setPrecipitation(boolean hasPrecipitation) {
			climateSettings.setHasPrecipitation(hasPrecipitation);
		}

		@Override
		public void setTemperature(float temperature) {
			climateSettings.setTemperature(temperature);
		}

		@Override
		public void setTemperatureModifier(Biome.TemperatureModifier temperatureModifier) {
			climateSettings.setTemperatureModifier(temperatureModifier);
		}

		@Override
		public void setDownfall(float downfall) {
			climateSettings.setDownfall(downfall);
		}
	}

	private class AttributesContextImpl implements AttributesContext {
		private final EnvironmentAttributeMapBuilder attributes = builder.getAttributes();

		@Override
		public void addAll(EnvironmentAttributeMap map) {
			attributes.putAll(map);
		}

		@Override
		public <T> void set(EnvironmentAttribute<T> key, T value) {
			attributes.set(key, value);
		}

		@Override
		public <T, M> void setModifier(EnvironmentAttribute<T> key, AttributeModifier<T, M> modifier, M value) {
			attributes.modify(key, modifier, value);
		}

		@Override
		public <T> EnvironmentAttributeMap.@Nullable Entry<T, ?> get(EnvironmentAttribute<T> attribute) {
			return attributes.get(attribute);
		}

		@Override
		public <T> T applyModifier(EnvironmentAttribute<T> attribute, T value) {
			EnvironmentAttributeMap.Entry<T, ?> entry = attributes.get(attribute);
			return entry == null ? value : entry.applyModifier(value);
		}
	}

	private class EffectsContextImpl implements EffectsContext {
		private final BiomeSpecialEffectsBuilder effects = builder.getSpecialEffects();

		@Override
		public int getWaterColor() {
			return effects.waterColor();
		}

		@Override
		public Optional<Integer> getFoliageColorOverride() {
			return effects.getFoliageColorOverride();
		}

		@Override
		public Optional<Integer> getDryFoliageColorOverride() {
			return effects.getDryFoliageColorOverride();
		}

		@Override
		public Optional<Integer> getGrassColorOverride() {
			return effects.getGrassColorOverride();
		}

		@Override
		public BiomeSpecialEffects.GrassColorModifier getGrassColorModifier() {
			return effects.getGrassColorModifier();
		}

		@Override
		public void setFogColor(int color) {
			attributes.set(EnvironmentAttributes.FOG_COLOR, ARGB.vector3fFromRGB24(color));
		}

		@Override
		public void setWaterColor(int color) {
			effects.waterColor(color);
		}

		@Override
		public void setWaterFogColor(int color) {
			attributes.set(EnvironmentAttributes.WATER_FOG_COLOR, ARGB.vector3fFromRGB24(color));
		}

		@Override
		public void setSkyColor(int color) {
			attributes.set(EnvironmentAttributes.SKY_COLOR, ARGB.vector3fFromRGB24(color));
		}

		@Override
		public void setFoliageColorOverride(Optional<Integer> color) {
			effects.foliageColorOverride(Objects.requireNonNull(color));
		}

		@Override
		public void setDryFoliageColorOverride(Optional<Integer> color) {
			effects.dryFoliageColorOverride(Objects.requireNonNull(color));
		}

		@Override
		public void setGrassColorOverride(Optional<Integer> color) {
			effects.grassColorOverride(Objects.requireNonNull(color));
		}

		@Override
		public void setGrassColorModifier(BiomeSpecialEffects.GrassColorModifier colorModifier) {
			effects.grassColorModifier(Objects.requireNonNull(colorModifier));
		}

		@Override
		public void setMusicVolume(float volume) {
			attributes.set(EnvironmentAttributes.MUSIC_VOLUME, volume);
		}
	}

	private class GenerationSettingsContextImpl implements GenerationSettingsContext {
		private final Registry<WorldCarver> carvers = registries.lookupOrThrow(Registries.CARVER);
		private final Registry<PlacedFeature> features = registries.lookupOrThrow(Registries.PLACED_FEATURE);
		private final BiomeGenerationSettingsBuilder generationSettings = builder.getGenerationSettings();

		@Override
		public boolean removeFeature(GenerationStep.Decoration step, ResourceKey<PlacedFeature> placedFeatureKey) {
			PlacedFeature placedFeature = getHolder(features, placedFeatureKey).value();
			List<Holder<PlacedFeature>> featureSteps = generationSettings.getFeatures(step);
			return featureSteps.removeIf(feature -> feature.value() == placedFeature);
		}

		@Override
		public void addFeature(GenerationStep.Decoration step, ResourceKey<PlacedFeature> entry) {
			generationSettings.addFeature(step, features.getOrThrow(entry));
		}

		@Override
		public void addCarver(ResourceKey<WorldCarver> entry) {
			// We do not need to delay evaluation of this since the registries are already fully built
			generationSettings.addCarver(getHolder(carvers, entry));
		}

		@Override
		public boolean removeCarver(ResourceKey<WorldCarver> carverKey) {
			WorldCarver carver = getHolder(carvers, carverKey).value();
			return generationSettings.getCarvers().removeIf(holder -> holder.value() == carver);
		}

		@Override
		public List<Holder<PlacedFeature>> getFeatures(GenerationStep.Decoration step) {
			return List.copyOf(generationSettings.getFeatures(step));
		}

		@Override
		public boolean hasFeature(GenerationStep.Decoration step, Holder<PlacedFeature> feature) {
			return generationSettings.getFeatures(step).stream().anyMatch(holder -> holder.value() == feature.value());
		}

		@Override
		public List<Holder<WorldCarver>> getCarvers() {
			return List.copyOf(generationSettings.getCarvers());
		}

		@Override
		public boolean hasCarver(Holder<WorldCarver> carver) {
			return generationSettings.getCarvers().stream().anyMatch(holder -> holder.value() == carver.value());
		}
	}

	/**
	 * Gets an entry from the given registry, assuming it's a registry loaded from data packs.
	 */
	private static <T> Holder.Reference<T> getHolder(Registry<T> registry, ResourceKey<T> key) {
		Holder.Reference<T> holder = registry.get(key).orElse(null);

		if (holder == null) {
			// The key doesn't exist in the data packs
			throw new IllegalArgumentException("Couldn't find holder for " + key);
		}

		return holder;
	}

	private class SpawnSettingsContextImpl implements MobSpawnSettingsContext {
		private final MobSpawnSettingsBuilder spawnSettings = builder.getMobSpawnSettings();

		@Override
		public void setCreatureGenerationProbability(float probability) {
			spawnSettings.creatureGenerationProbability(probability);
		}

		@Override
		public @UnmodifiableView List<Weighted<MobSpawnSettings.SpawnerData>> getMobs(MobCategory category) {
			Objects.requireNonNull(category);
			WeightedList.Builder<MobSpawnSettings.SpawnerData> spawner = spawnSettings.getSpawner(category);
			return spawner == null ? List.of() : Collections.unmodifiableList(spawner.getList());
		}

		@Override
		public @UnmodifiableView Map<MobCategory, List<Weighted<MobSpawnSettings.SpawnerData>>> getMobs() {
			Map<MobCategory, List<Weighted<MobSpawnSettings.SpawnerData>>> mobs = new EnumMap<>(MobCategory.class);

			for (MobCategory category : spawnSettings.getSpawnerTypes()) {
				mobs.put(category, getMobs(category));
			}

			return Collections.unmodifiableMap(mobs);
		}

		@Override
		public @UnmodifiableView Set<MobCategory> getMobCategories() {
			return Collections.unmodifiableSet(spawnSettings.getSpawnerTypes());
		}

		@Override
		public void addSpawn(MobCategory category, MobSpawnSettings.SpawnerData data, int weight) {
			Objects.requireNonNull(category);
			Objects.requireNonNull(data);

			spawnSettings.addSpawn(category, weight, data);
		}

		@Override
		public boolean removeSpawns(BiPredicate<MobCategory, MobSpawnSettings.SpawnerData> predicate) {
			boolean anyRemoved = false;

			for (MobCategory group : spawnSettings.getSpawnerTypes()) {
				WeightedList.Builder<MobSpawnSettings.SpawnerData> spawner = spawnSettings.getSpawner(group);

				if (spawner == null) continue;

				int oldSize = spawner.getList().size();
				spawner.removeIf(entry -> predicate.test(group, entry.value()));

				if (oldSize > spawner.getList().size()) {
					anyRemoved = true;
				}
			}

			return anyRemoved;
		}

		@Override
		public MobSpawnSettings.@Nullable MobSpawnCost getMobCharge(EntityType<?> entityType) {
			return spawnSettings.getCost(Objects.requireNonNull(entityType));
		}

		@Override
		public Map<EntityType<?>, MobSpawnSettings.MobSpawnCost> getMobCharges() {
			Map<EntityType<?>, MobSpawnSettings.MobSpawnCost> costs = new HashMap<>();

			for (EntityType<?> type : spawnSettings.getEntityTypes()) {
				MobSpawnSettings.MobSpawnCost cost = spawnSettings.getCost(type);
				if (cost != null) costs.put(type, cost);
			}

			return Collections.unmodifiableMap(costs);
		}

		@Override
		public void addMobCharge(EntityType<?> entityType, double charge, double energyBudget) {
			Objects.requireNonNull(entityType);
			spawnSettings.addMobCharge(entityType, charge, energyBudget);
		}

		@Override
		public void clearMobCharge(EntityType<?> entityType) {
			spawnSettings.removeSpawnCost(entityType);
		}
	}
}
