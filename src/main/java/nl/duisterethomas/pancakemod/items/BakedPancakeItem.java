package nl.duisterethomas.pancakemod.items;

import java.util.List;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

public class BakedPancakeItem extends Item {
    private final Item rolledVariant;

    public BakedPancakeItem(Item.Properties properties, Item rolledVariant) {
        super(properties);
        this.rolledVariant = rolledVariant;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag type) {
        tooltip.add(Component.translatable("item.pancakemod.baked_pancake.tooltip_1"));
        tooltip.add(Component.translatable("item.pancakemod.baked_pancake.tooltip_2"));
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
}
