package nl.duisterethomas.pancakemod.items;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import java.util.List;

public class BakedPancakeItem extends Item {
    private final Item rolledVariant;

    public BakedPancakeItem(Item rolledVariant) {
        super(new Item.Settings());
        this.rolledVariant = rolledVariant;
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {

        tooltip.add(Text.translatable("item.pancakemod.baked_pancake.tooltip_1"));
        tooltip.add(Text.translatable("item.pancakemod.baked_pancake.tooltip_2"));
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
            // Set the required amount of rolled pancakes
            requiredRolledPancakeCount = heldStack.getCount();
        } else if (heldStack.getCount() == 1) {
            // Return rolled pancake immediately when player holds only one pancake
            return TypedActionResult.success(rolledPancakeStack);
        }

        // Roll the pancakes
        for (int i = requiredRolledPancakeCount; i > 0; i--) {
            // Return the remaining rolled pancakes if there are 16 or less left and the player is sneaking
            if (heldStack.getCount() <= 16 && player.isSneaking()) {
                rolledPancakeStack.setCount(heldStack.getCount());

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
