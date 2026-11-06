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

package net.fabricmc.fabric.impl.registry.sync;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import com.mojang.serialization.Codec;
import net.neoforged.neoforge.registries.NewDatapackRegistryEvent;
import org.jetbrains.annotations.Unmodifiable;
import org.jspecify.annotations.Nullable;

import net.minecraft.core.Registry;
import net.minecraft.resources.RegistryDataLoader;
import net.minecraft.resources.ResourceKey;

import net.fabricmc.fabric.api.event.registry.DynamicRegistries;
import net.fabricmc.fabric.mixin.registry.sync.DataPackRegistryEventNewRegistryAccessor;

/**
 * NeoForge implementation: Fabric dynamic registries are registered through {@link NewDatapackRegistryEvent}.
 * NeoForge keeps {@link RegistryDataLoader}'s world/reloadable/synced lists mutable and appends modded registries to them,
 * so the getters simply read those lists.
 */
public final class DynamicRegistriesImpl {
	private static final List<PendingRegistry<?>> PENDING_REGISTRIES = new ArrayList<>();
	public static final Set<ResourceKey<? extends Registry<?>>> FABRIC_DYNAMIC_REGISTRY_KEYS = new HashSet<>();
	public static final Set<ResourceKey<? extends Registry<?>>> SKIP_EMPTY_SYNC_REGISTRIES = new HashSet<>();

	private static boolean initialized;

	private DynamicRegistriesImpl() {
	}

	public static @Unmodifiable List<RegistryDataLoader.RegistryData<?>> getAllDynamicRegistries() {
		List<RegistryDataLoader.RegistryData<?>> all = new ArrayList<>(RegistryDataLoader.WORLD_REGISTRIES);
		all.addAll(RegistryDataLoader.DIMENSION_REGISTRIES);
		all.addAll(RegistryDataLoader.RELOADABLE_REGISTRIES);
		return List.copyOf(all);
	}

	public static @Unmodifiable List<RegistryDataLoader.RegistryData<?>> getWorldRegistries() {
		return List.copyOf(RegistryDataLoader.WORLD_REGISTRIES);
	}

	public static @Unmodifiable List<RegistryDataLoader.RegistryData<?>> getReloadableRegistries() {
		return List.copyOf(RegistryDataLoader.RELOADABLE_REGISTRIES);
	}

	public static <T> void register(ResourceKey<? extends Registry<T>> key, Codec<T> serverCodec) {
		addPending(new PendingRegistry<>(key, serverCodec, null, false));
	}

	/**
	 * On NeoForge the network codec has to be supplied together with the registry, so this attaches it to the
	 * pending registration created by {@link #register} just before.
	 */
	public static synchronized <T> void addSyncedRegistry(ResourceKey<? extends Registry<T>> key, Codec<T> clientCodec, DynamicRegistries.SyncOption... options) {
		Objects.requireNonNull(key, "Registry key cannot be null");
		Objects.requireNonNull(clientCodec, "Client codec cannot be null");
		Objects.requireNonNull(options, "Options cannot be null");

		for (DynamicRegistries.SyncOption option : options) {
			if (option == DynamicRegistries.SyncOption.SKIP_WHEN_EMPTY) {
				SKIP_EMPTY_SYNC_REGISTRIES.add(key);
			}
		}

		for (int i = 0; i < PENDING_REGISTRIES.size(); i++) {
			PendingRegistry<?> pending = PENDING_REGISTRIES.get(i);

			if (pending.key().equals(key)) {
				PENDING_REGISTRIES.set(i, withNetworkCodec(pending, clientCodec));
				return;
			}
		}

		throw new IllegalStateException("Dynamic registry " + key + " must be registered before it can be synced, and before NeoForge processes new datapack registries");
	}

	public static <T> void registerReloadable(ResourceKey<? extends Registry<T>> key, Codec<T> serverCodec) {
		addPending(new PendingRegistry<>(key, serverCodec, null, true));
	}

	@SuppressWarnings("unchecked")
	private static <T> PendingRegistry<T> withNetworkCodec(PendingRegistry<T> pending, Codec<?> networkCodec) {
		return new PendingRegistry<>(pending.key(), pending.serverCodec(), (Codec<T>) networkCodec, pending.reloadable());
	}

	private static synchronized <T> void addPending(PendingRegistry<T> registry) {
		Objects.requireNonNull(registry.key(), "Registry key cannot be null");
		Objects.requireNonNull(registry.serverCodec(), "Server codec cannot be null");

		if (getAllDynamicRegistries().stream().anyMatch(d -> d.key().equals(registry.key()))
				|| FABRIC_DYNAMIC_REGISTRY_KEYS.contains(registry.key())) {
			throw new IllegalArgumentException("Dynamic registry " + registry.key() + " has already been registered!");
		}

		FABRIC_DYNAMIC_REGISTRY_KEYS.add(registry.key());

		if (initialized) {
			// Late registration: run a private event instance so NeoForge appends the registry immediately.
			NewDatapackRegistryEvent event = DataPackRegistryEventNewRegistryAccessor.create();
			registry.register(event);
			((DataPackRegistryEventNewRegistryAccessor) (Object) event).invokeProcess();
		} else {
			PENDING_REGISTRIES.add(registry);
		}
	}

	static synchronized void onNewDatapackRegistries(NewDatapackRegistryEvent event) {
		for (PendingRegistry<?> registry : PENDING_REGISTRIES) {
			registry.register(event);
		}

		PENDING_REGISTRIES.clear();
		initialized = true;
	}

	private record PendingRegistry<T>(ResourceKey<? extends Registry<T>> key, Codec<T> serverCodec, @Nullable Codec<T> networkCodec, boolean reloadable) {
		@SuppressWarnings("unchecked")
		void register(NewDatapackRegistryEvent event) {
			ResourceKey<Registry<T>> registryKey = (ResourceKey<Registry<T>>) this.key;

			if (this.reloadable) {
				event.reloadableRegistry(registryKey, this.serverCodec);
			} else {
				event.worldRegistry(registryKey, this.serverCodec, this.networkCodec);
			}
		}
	}
}
