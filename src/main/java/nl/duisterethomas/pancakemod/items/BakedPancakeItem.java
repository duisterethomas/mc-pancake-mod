package nl.duisterethomas.pancakemod.items;

import java.util.List;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import nl.duisterethomas.pancakemod.blocks.PancakeStackBlockEntity;

import static nl.duisterethomas.pancakemod.registry.ModBlocks.PANCAKE_STACK;

public class BakedPancakeItem extends Item {
    public final Item rolledVariant;

    public BakedPancakeItem(Item rolledVariant) {
        super(new Item.Properties());
        this.rolledVariant = rolledVariant;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag type) {

        tooltip.add(Component.translatable("item.pancakemod.baked_pancake.tooltip_1"));
        tooltip.add(Component.translatable("item.pancakemod.baked_pancake.tooltip_2"));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        // Ensure we don't roll a pancake only on the client.
        // This is to prevent desync.
        if (world.isClientSide) {
            return InteractionResultHolder.pass(player.getItemInHand(hand));
        }

        // Create a new item stack, requiredRolledPancakeCount and rolledPancakeCount
        ItemStack rolledPancakeStack = new ItemStack(rolledVariant);
        int requiredRolledPancakeCount = 1;
        int rolledPancakeCount;

        // Get the current held stack
        ItemStack heldStack = player.getItemInHand(hand);

        if (player.isShiftKeyDown()) {
            // Set the required amount of rolled pancakes
            requiredRolledPancakeCount = heldStack.getCount();
        } else if (heldStack.getCount() == 1) {
            // Return rolled pancake immediately when player holds only one pancake
            return InteractionResultHolder.success(rolledPancakeStack);
        }

        // Roll the pancakes
        for (int i = requiredRolledPancakeCount; i > 0; i--) {
            // Return the remaining rolled pancakes if there are 16 or less left and the player is sneaking
            if (heldStack.getCount() <= 16 && player.isShiftKeyDown()) {
                rolledPancakeStack.setCount(heldStack.getCount());

                return InteractionResultHolder.success(rolledPancakeStack);
            }

            // Get the current amount of rolled pancakes
            rolledPancakeCount = player.getInventory().countItem(rolledVariant);

            // Try to add a new rolled pancake
            player.getInventory().add(new ItemStack(rolledVariant));

            if (rolledPancakeCount < player.getInventory().countItem(rolledVariant)) {
                heldStack.shrink(1);
            } else if (i == requiredRolledPancakeCount) {
                return InteractionResultHolder.pass(heldStack);
            } else {
                return InteractionResultHolder.success(heldStack);
            }

        }

        return InteractionResultHolder.success(heldStack);
    }

    public static boolean canPlacePancakeStack(UseOnContext context) {
        // Place a new stack if possible
        BlockPlaceContext placeContext = new BlockPlaceContext(context);
        if (!placeContext.canPlace()) {
            return false;
        }

        BlockState state = PANCAKE_STACK.getStateForPlacement(placeContext);
        if (state == null) {
            return false;
        }

        Level level = context.getLevel();
        BlockPos pos = placeContext.getClickedPos();
        return state.canSurvive(level, pos) && level.isUnobstructed(state, pos, CollisionContext.of(context.getPlayer()));
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        if (!canPlacePancakeStack(context)) {
            return InteractionResult.PASS;
        }

        BlockPlaceContext placeContext = new BlockPlaceContext(context);
        BlockPos pos = placeContext.getClickedPos();
        BlockState state = PANCAKE_STACK.getStateForPlacement(placeContext);
        Level level = context.getLevel();

        if (!level.isClientSide()) {
            level.setBlockAndUpdate(pos, state);

            if (level.getBlockEntity(pos) instanceof PancakeStackBlockEntity stack) {
                stack.addPancake(context.getItemInHand().copyWithCount(1), context.getHorizontalDirection().getOpposite().get2DDataValue());
            }

            SoundType sound = state.getSoundType();
            level.playSound(null, pos, sound.getPlaceSound(), SoundSource.BLOCKS, (sound.getVolume() + 1.0f) / 2.0f, sound.getPitch() * 0.8f);

            context.getItemInHand().consume(1, context.getPlayer());
        }

        return InteractionResult.SUCCESS;
    }
}
