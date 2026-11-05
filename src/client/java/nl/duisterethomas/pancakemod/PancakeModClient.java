package nl.duisterethomas.pancakemod;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import nl.duisterethomas.pancakemod.registry.ModBlockEntityTypes;

public class PancakeModClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		BlockEntityRenderers.register(ModBlockEntityTypes.PANCAKE_STACK_ENTITY, PancakeStackRenderer::new);
	}
}