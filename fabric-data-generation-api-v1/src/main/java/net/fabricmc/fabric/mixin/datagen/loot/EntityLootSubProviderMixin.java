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

package net.fabricmc.fabric.mixin.datagen.loot;

import java.util.stream.Stream;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.loot.EntityLootSubProvider;
import net.minecraft.data.loot.packs.VanillaEntityLoot;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.EntityType;

import net.fabricmc.fabric.api.datagen.v1.loot.FabricEntityLootSubProvider;

@Mixin(EntityLootSubProvider.class)
public class EntityLootSubProviderMixin implements FabricEntityLootSubProvider {
	// NeoForge iterates getKnownEntityTypes() instead of the context's ENTITY_TYPE elements directly
	@ModifyExpressionValue(method = "run", at = @At(value = "INVOKE", target = "Lnet/minecraft/data/loot/EntityLootSubProvider;getKnownEntityTypes()Ljava/util/stream/Stream;"))
	private Stream<EntityType<?>> onlyVanillaEntities(Stream<EntityType<?>> entities) {
		if ((Object) this instanceof VanillaEntityLoot) {
			return entities.filter(entity -> BuiltInRegistries.ENTITY_TYPE.getKey(entity).getNamespace().equals(Identifier.DEFAULT_NAMESPACE));
		}

		return entities;
	}
}
