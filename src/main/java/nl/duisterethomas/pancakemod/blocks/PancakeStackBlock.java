package nl.duisterethomas.pancakemod.blocks;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;

public class PancakeStackBlock extends Block {
    public static final IntProperty PANCAKE_COUNT = IntProperty.of("pancake_count", 1, 16);

    public PancakeStackBlock() {
        super(AbstractBlock.Settings.create()
                .sounds(BlockSoundGroup.WOOL)
                .pistonBehavior(PistonBehavior.DESTROY));

        setDefaultState(getDefaultState().with(PANCAKE_COUNT, 1));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(PANCAKE_COUNT);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, ShapeContext context){

        return VoxelShapes.cuboid(0.0625f, 0.0f, 0.0625f, 0.9375f, 0.0625f*state.get(PANCAKE_COUNT), 0.9375f);

    }
}
