package nl.duisterethomas.pancakemod.registry;

import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import nl.duisterethomas.pancakemod.PancakeMod;
import nl.duisterethomas.pancakemod.blocks.PancakeStackBlockEntity;

public class ModBlockEntityTypes {
    public static <T extends BlockEntityType<?>> T register(String path, T blockEntityType) {
        return Registry.register(Registries.BLOCK_ENTITY_TYPE, Identifier.of(PancakeMod.MOD_ID, path), blockEntityType);
    }

    public static final BlockEntityType<PancakeStackBlockEntity> PANCAKE_STACK = register(
            "pancake_stack",
            BlockEntityType.Builder.create(PancakeStackBlockEntity::new, ModBlocks.PANCAKE_STACK).build()
    );

    public static void initialize() {
    }
}
