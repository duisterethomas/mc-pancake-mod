package nl.duisterethomas.pancakemod.blocks;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;

public class PancakeStackBlock extends BlockWithEntity {
    public static final IntProperty PANCAKE_COUNT = IntProperty.of("pancake_count", 1, 16);

    public PancakeStackBlock(Settings settings) {
        super(settings);

        setDefaultState(getDefaultState().with(PANCAKE_COUNT, 1));
    }

    @Override
    protected MapCodec<? extends PancakeStackBlock> getCodec() {
        return createCodec(PancakeStackBlock::new);
    }

    // Runs when a new block is places
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new PancakeStackBlockEntity(pos, state);
    }

    // Make sure it renders the model
    @Override
    protected BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(PANCAKE_COUNT);
    }

    // Provide the collision and outline shape
    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, ShapeContext context){

        return VoxelShapes.cuboid(0.0625f, 0.0f, 0.0625f, 0.9375f, 0.0625f*state.get(PANCAKE_COUNT), 0.9375f);

    }
}
