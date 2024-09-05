package nl.duisterethomas.pancakemod.blocks;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import nl.duisterethomas.pancakemod.registry.ModBlockEntityTypes;

public class PancakeStackBlockEntity extends BlockEntity {
    public PancakeStackBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntityTypes.PANCAKE_STACK, pos, state);
    }
}
