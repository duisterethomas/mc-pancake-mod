package nl.duisterethomas.pancakemod.registry;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import nl.duisterethomas.pancakemod.PancakeMod;
import nl.duisterethomas.pancakemod.blocks.PancakeStackBlockEntity;

public class ModBlockEntityTypes {
    public static void initialize() {}

    public static <T extends BlockEntity> BlockEntityType<T> register(String name, FabricBlockEntityTypeBuilder.Factory<? extends T> entityFactory, Block... blocks) {
        return Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, Identifier.fromNamespaceAndPath(PancakeMod.MOD_ID, name), FabricBlockEntityTypeBuilder.<T>create(entityFactory, blocks).build());
    }

    public static final BlockEntityType<PancakeStackBlockEntity> PANCAKE_STACK_ENTITY = register(
        "pancake_stack_entity",
        PancakeStackBlockEntity::new,
        ModBlocks.PANCAKE_STACK
    );
}
