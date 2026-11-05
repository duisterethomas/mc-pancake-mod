package nl.duisterethomas.pancakemod.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import nl.duisterethomas.pancakemod.items.BakedPancakeItem;

public class PancakeStackBlock extends Block {
    public static final IntegerProperty PANCAKE_COUNT = IntegerProperty.create("pancake_count", 1, 16);

    public PancakeStackBlock(BlockBehaviour.Properties properties) {
        super(properties);

        registerDefaultState(defaultBlockState().setValue(PANCAKE_COUNT, 1));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(PANCAKE_COUNT);
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return Block.box(1, 0, 1, 15, state.getValue(PANCAKE_COUNT), 15);
    }

    @Override
    protected VoxelShape getBlockSupportShape(BlockState state, BlockGetter level, BlockPos pos) {
        return state.getValue(PANCAKE_COUNT) == 16 ? Block.box(0, 15, 0, 16, 16, 16) : Shapes.empty();
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockState existing = context.getLevel().getBlockState(context.getClickedPos());

        if (existing.is(this)) {
            return existing.setValue(PANCAKE_COUNT, Math.min(16, existing.getValue(PANCAKE_COUNT) + 1));
        }

        return super.getStateForPlacement(context);
    }

    @Override
    protected boolean canBeReplaced(BlockState state, BlockPlaceContext context) {
        if (context.getItemInHand().getItem() instanceof BakedPancakeItem && state.getValue(PANCAKE_COUNT) < 16) {
            // Stack when clicking the top face, or when not clicking the block directly
            return !context.replacingClickedOnBlock() || context.getClickedFace() == Direction.UP;
        }

        return false;
    }

    @Override
    protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        return level.getBlockState(pos.below()).isFaceSturdy(level, pos.below(), Direction.UP);
    }

    @Override
    protected BlockState updateShape(BlockState state, LevelReader level, ScheduledTickAccess ticks, BlockPos pos, Direction direction, BlockPos neighborPos, BlockState neighborState, RandomSource random) {
        if (direction == Direction.DOWN && !state.canSurvive(level, pos)) {
            return Blocks.AIR.defaultBlockState();
        }

        return super.updateShape(state, level, ticks, pos, direction, neighborPos, neighborState, random);
    }
}
