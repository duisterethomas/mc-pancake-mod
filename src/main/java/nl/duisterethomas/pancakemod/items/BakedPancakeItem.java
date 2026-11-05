package nl.duisterethomas.pancakemod.items;

import java.util.function.Consumer;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;

import static nl.duisterethomas.pancakemod.registry.ModBlocks.PANCAKE_STACK;

public class BakedPancakeItem extends Item {
    private final Item rolledVariant;

    public BakedPancakeItem(Item.Properties properties, Item rolledVariant) {
        super(properties);
        this.rolledVariant = rolledVariant;
    }

    // Yes this method is officially deprecated, but it still works
    @Override
    public void appendHoverText(ItemStack itemStack, TooltipContext tooltipContext, TooltipDisplay tooltipDisplay, Consumer<Component> consumer, TooltipFlag tooltipFlag) {
        super.appendHoverText(itemStack, tooltipContext, tooltipDisplay, consumer, tooltipFlag);
        consumer.accept(Component.translatable("item.pancakemod.baked_pancake.tooltip_1"));
        consumer.accept(Component.translatable("item.pancakemod.baked_pancake.tooltip_2"));
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        // Ensure we don't roll a pancake only on the client.
        // This is to prevent desync.
        if (level.isClientSide()) {
            return InteractionResult.PASS;
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
            return InteractionResult.SUCCESS.heldItemTransformedTo(rolledPancakeStack);
        }

        // Roll the pancakes
        for (int i = requiredRolledPancakeCount; i > 0; i--) {
            // Return the remaining rolled pancakes if there are 16 or less left and the player is sneaking
            if (heldStack.getCount() <= 16 && player.isShiftKeyDown()) {
                rolledPancakeStack.setCount(heldStack.getCount());

                return InteractionResult.SUCCESS.heldItemTransformedTo(rolledPancakeStack);
            }

            // Get the current amount of rolled pancakes
            rolledPancakeCount = player.getInventory().countItem(rolledVariant);

            // Try to add a new rolled pancake
            player.getInventory().add(new ItemStack(rolledVariant));

            if (rolledPancakeCount < player.getInventory().countItem(rolledVariant)) {
                heldStack.shrink(1);
            } else {
                return InteractionResult.SUCCESS.heldItemTransformedTo(heldStack);
            }

        }

        return InteractionResult.SUCCESS.heldItemTransformedTo(heldStack);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        // Place a new stack if possible
        BlockPlaceContext placeContext = new BlockPlaceContext(context);
        if (!placeContext.canPlace()) {
            return InteractionResult.PASS;
        }

        BlockState state = PANCAKE_STACK.getStateForPlacement(placeContext);
        if (state == null) {
            return InteractionResult.PASS;
        }

        Level level = context.getLevel();
        BlockPos pos = placeContext.getClickedPos();
        Player player = context.getPlayer();

        if (!state.canSurvive(level, pos) || !level.isUnobstructed(state, pos, CollisionContext.placementContext(player))) {
            return InteractionResult.PASS;
        }

        if (!level.isClientSide()) {
            level.setBlockAndUpdate(pos, state);

            SoundType sound = state.getSoundType();
            level.playSound(null, pos, sound.getPlaceSound(), SoundSource.BLOCKS, (sound.getVolume() + 1.0f) / 2.0f, sound.getPitch() * 0.8f);

            context.getItemInHand().consume(1, player);
        }

        return InteractionResult.SUCCESS;
    }
}
