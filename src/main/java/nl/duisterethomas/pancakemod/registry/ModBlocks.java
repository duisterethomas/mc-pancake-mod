package nl.duisterethomas.pancakemod.registry;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.PushReaction;
import nl.duisterethomas.pancakemod.PancakeMod;
import nl.duisterethomas.pancakemod.blocks.PancakeStackBlock;

import java.util.function.Function;

public class ModBlocks {
    public static void initialize() {}

    private static Block register(String name, Function<BlockBehaviour.Properties, Block> blockFactory, BlockBehaviour.Properties properties) {
        // Create the block Key
        ResourceKey<Block> blockKey = ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(PancakeMod.MOD_ID, name));

        // Create the block instance
        Block block = blockFactory.apply(properties.setId(blockKey));

        // Register the block
        return Registry.register(BuiltInRegistries.BLOCK, blockKey, block);
    }

    public static final PancakeStackBlock PANCAKE_STACK = (PancakeStackBlock) register(
        "pancake_stack",
        PancakeStackBlock::new,
        BlockBehaviour.Properties.of()
            .sound(SoundType.WOOL)
            .pushReaction(PushReaction.POPPED)
    );
}
