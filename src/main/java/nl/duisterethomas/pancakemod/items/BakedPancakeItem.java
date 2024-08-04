package nl.duisterethomas.pancakemod.items;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import nl.duisterethomas.pancakemod.registry.ModItems;

public class BakedPancakeItem extends Item {
    private final Item rolled_variant;

    public BakedPancakeItem(Item rolled_variant) {
        super(new Item.Settings());
        this.rolled_variant = rolled_variant;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        // Ensure we don't roll a pancake only on the client.
        // This is to prevent desync.
        if (world.isClient) {
            return TypedActionResult.pass(player.getStackInHand(hand));
        }

        if (player.getStackInHand(hand).getCount() == 1) {
            return TypedActionResult.success(new ItemStack(rolled_variant));
        }

        int rolledPancakeCount = player.getInventory().count(rolled_variant);
        player.getInventory().insertStack(new ItemStack(rolled_variant));

        if (rolledPancakeCount < player.getInventory().count(rolled_variant)) {
            ItemStack heldStack = player.getStackInHand(hand);
            heldStack.decrement(1);
            return TypedActionResult.success(heldStack);
        } else {
            return TypedActionResult.pass(player.getStackInHand(hand));
        }
    }
}
