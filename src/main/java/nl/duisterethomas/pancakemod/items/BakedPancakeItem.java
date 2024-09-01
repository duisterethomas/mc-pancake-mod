package nl.duisterethomas.pancakemod.items;

import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import static nl.duisterethomas.pancakemod.blocks.PancakeStackBlock.PANCAKE_COUNT;
import static nl.duisterethomas.pancakemod.registry.ModBlocks.PANCAKE_STACK;

public class BakedPancakeItem extends Item {
    private final Item rolledVariant;

    public BakedPancakeItem(Item rolledVariant) {
        super(new Item.Settings());
        this.rolledVariant = rolledVariant;
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        BlockPos blockPos = context.getBlockPos();
        BlockState blockState = world.getBlockState(blockPos);
        ItemStack itemStack = context.getStack();
        PlayerEntity player = context.getPlayer();

        BlockPos blockPosSide = blockPos.offset(context.getSide());

        if (blockState.getBlock() == PANCAKE_STACK) {
            int new_state = blockState.get(PANCAKE_COUNT)+1;

            if (new_state <= 16 && itemStack.getItem().getClass() == BakedPancakeItem.class) {
                // Add pancake to stack

                if (!world.isClient){
                    itemStack.decrementUnlessCreative(1, player);

                    world.setBlockState(context.getBlockPos(), blockState.with(PANCAKE_COUNT, new_state));

                    world.playSound(null, blockPos, SoundEvents.BLOCK_WOOL_PLACE, SoundCategory.BLOCKS, 1.0F, 1.0F);
                }

                return ActionResult.success(world.isClient());
            } else if (world.getBlockState(blockPosSide).getBlock() == Blocks.AIR || world.getBlockState(blockPosSide).getBlock() == Blocks.SHORT_GRASS || world.getBlockState(blockPos).getBlock() == Blocks.TALL_GRASS) {
                // Place new stack if stack full

                if (!world.isClient) {
                    itemStack.decrementUnlessCreative(1, context.getPlayer());

                    world.setBlockState(blockPosSide, PANCAKE_STACK.getDefaultState());

                    world.playSound(null, blockPosSide, SoundEvents.BLOCK_WOOL_PLACE, SoundCategory.BLOCKS, 1.0F, 1.0F);
                }

                return ActionResult.success(world.isClient());
            }

            return ActionResult.PASS;

        } else {
            // Place new stack
            if (!world.isClient) {
                if (world.getBlockState(blockPos).getBlock() == Blocks.SHORT_GRASS
                        || world.getBlockState(blockPos).getBlock() == Blocks.TALL_GRASS) {
                    itemStack.decrementUnlessCreative(1, context.getPlayer());

                    world.setBlockState(blockPos, PANCAKE_STACK.getDefaultState());

                    world.playSound(null, blockPos, SoundEvents.BLOCK_WOOL_PLACE, SoundCategory.BLOCKS, 1.0F, 1.0F);
                } else if (world.getBlockState(blockPosSide).getBlock() == Blocks.AIR
                        || world.getBlockState(blockPosSide).getBlock() == Blocks.SHORT_GRASS
                        || world.getBlockState(blockPos).getBlock() == Blocks.TALL_GRASS) {
                    itemStack.decrementUnlessCreative(1, context.getPlayer());

                    world.setBlockState(blockPosSide, PANCAKE_STACK.getDefaultState());

                    world.playSound(null, blockPosSide, SoundEvents.BLOCK_WOOL_PLACE, SoundCategory.BLOCKS, 1.0F, 1.0F);
                }
            }

            return ActionResult.success(world.isClient());

        }
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        // Ensure we don't roll a pancake only on the client.
        // This is to prevent desync.
        if (world.isClient) {
            return TypedActionResult.pass(player.getStackInHand(hand));
        }

        // Create a new item stack, requiredRolledPancakeCount and rolledPancakeCount
        ItemStack rolledPancakeStack = new ItemStack(rolledVariant);
        int requiredRolledPancakeCount = 1;
        int rolledPancakeCount;

        // Get the current held stack
        ItemStack heldStack = player.getStackInHand(hand);

        if (player.isSneaking()) {
            if (heldStack.getCount() <= 16) {
                // Return pancake stack immediately when player is sneaking and has 16 or less baked pancakes
                rolledPancakeStack.setCount(heldStack.getCount());

                return TypedActionResult.success(rolledPancakeStack);
            }

            // Set the required rolled amount
            requiredRolledPancakeCount = heldStack.getCount();

        } else if (heldStack.getCount() == 1) {
            // Return rolled pancake immediately when player holds only one pancake
            return TypedActionResult.success(rolledPancakeStack);
        }

        // Roll the pancakes
        for (int i = requiredRolledPancakeCount; i > 0; i--) {
            // If only 16 pancakes left return the rolled pancake stack
            if (heldStack.getCount() == 16 && player.isSneaking()) {
                rolledPancakeStack.setCount(16);
                return TypedActionResult.success(rolledPancakeStack);
            }

            // Get the current amount of rolled pancakes
            rolledPancakeCount = player.getInventory().count(rolledVariant);

            // Try to add a new rolled pancake
            player.getInventory().insertStack(new ItemStack(rolledVariant));

            if (rolledPancakeCount < player.getInventory().count(rolledVariant)) {
                heldStack.decrement(1);
            } else if (i == requiredRolledPancakeCount) {
                return TypedActionResult.pass(heldStack);
            } else {
                return TypedActionResult.success(heldStack);
            }

        }

        return TypedActionResult.success(heldStack);
    }
}
