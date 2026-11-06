package net.fabricmc.fabric.mixin.registry.sync;

import net.neoforged.neoforge.registries.NewDatapackRegistryEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

// NeoForge 26.3 renamed DataPackRegistryEvent.NewRegistry to NewDatapackRegistryEvent and made its constructor package-private.
@Mixin(NewDatapackRegistryEvent.class)
public interface DataPackRegistryEventNewRegistryAccessor {
	@Invoker("<init>")
	static NewDatapackRegistryEvent create() {
		throw new AssertionError();
	}

	@Invoker
	void invokeProcess();
}
