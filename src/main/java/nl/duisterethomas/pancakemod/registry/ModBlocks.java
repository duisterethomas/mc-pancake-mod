package nl.duisterethomas.pancakemod.registry;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.PushReaction;
import nl.duisterethomas.pancakemod.PancakeMod;
import nl.duisterethomas.pancakemod.blocks.PancakeStackBlock;

public class ModBlocks {
    public static void initialize() {}

    private static Block register(Block block, String name) {
        // Create the block id
        ResourceLocation id = ResourceLocation.fromNamespaceAndPath(PancakeMod.MOD_ID, name);

        // Register the block
        return Registry.register(BuiltInRegistries.BLOCK, id, block);
    }

    public static final PancakeStackBlock PANCAKE_STACK = (PancakeStackBlock) register(
        new PancakeStackBlock(
            BlockBehaviour.Properties.of()
                .sound(SoundType.WOOL)
                .pushReaction(PushReaction.DESTROY)
        ),
        "pancake_stack"
    );
}
