package nl.duisterethomas.pancakemod.registry;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import nl.duisterethomas.pancakemod.PancakeMod;
import nl.duisterethomas.pancakemod.items.BakedPancakeItem;

import static net.minecraft.item.Items.BUCKET;

public class ModItems {
    public static void initialize() {
        // Add all the items to the right item groups
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS)
                .register((itemGroup) -> itemGroup.add(PANCAKE_BATTER_BUCKET));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FOOD_AND_DRINK)
                .register((itemGroup) -> itemGroup.add(RAW_PANCAKE));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FOOD_AND_DRINK)
                .register((itemGroup) -> itemGroup.add(BAKED_PANCAKE));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FOOD_AND_DRINK)
                .register((itemGroup) -> itemGroup.add(ROLLED_PANCAKE));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FOOD_AND_DRINK)
                .register((itemGroup) -> itemGroup.add(BAKED_PANCAKE_SUGAR));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FOOD_AND_DRINK)
                .register((itemGroup) -> itemGroup.add(ROLLED_PANCAKE_SUGAR));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FOOD_AND_DRINK)
                .register((itemGroup) -> itemGroup.add(BAKED_PANCAKE_HONEY));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FOOD_AND_DRINK)
                .register((itemGroup) -> itemGroup.add(ROLLED_PANCAKE_HONEY));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FOOD_AND_DRINK)
                .register((itemGroup) -> itemGroup.add(BAKED_PANCAKE_CHOCOLATE));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FOOD_AND_DRINK)
                .register((itemGroup) -> itemGroup.add(ROLLED_PANCAKE_CHOCOLATE));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FOOD_AND_DRINK)
                .register((itemGroup) -> itemGroup.add(BAKED_PANCAKE_APPLE));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FOOD_AND_DRINK)
                .register((itemGroup) -> itemGroup.add(ROLLED_PANCAKE_APPLE));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FOOD_AND_DRINK)
                .register((itemGroup) -> itemGroup.add(BAKED_PANCAKE_BACON));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FOOD_AND_DRINK)
                .register((itemGroup) -> itemGroup.add(ROLLED_PANCAKE_BACON));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FOOD_AND_DRINK)
                .register((itemGroup) -> itemGroup.add(GOLDEN_BAKED_PANCAKE));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FOOD_AND_DRINK)
                .register((itemGroup) -> itemGroup.add(GOLDEN_ROLLED_PANCAKE));
    }

    public static Item register(Item item, String id) {
        // Create the identifier for the item
        Identifier itemID = Identifier.of(PancakeMod.MOD_ID, id);

        // Register and return the item
        return Registry.register(Registries.ITEM, itemID, item);
    }

    public static final Item PANCAKE_BATTER_BUCKET = register(
            new Item(new Item.Settings()
                    .recipeRemainder(BUCKET)
                    .maxCount(1)),
            "pancake_batter_bucket"
    );

    public static final Item RAW_PANCAKE = register(
            new Item(new Item.Settings()
                    .food(new FoodComponent.Builder()
                            .nutrition(1)
                            .saturationModifier(0.25f)
                            // The duration is in ticks, 20 ticks = 1 second
                            .statusEffect(new StatusEffectInstance(StatusEffects.HUNGER, 400, 1), 0.5f)
                            .build())),
            "raw_pancake"
    );

    public static final Item ROLLED_PANCAKE = register(
            new Item(new Item.Settings()
                    .maxCount(16)
                    .food(new FoodComponent.Builder()
                            .nutrition(4)
                            .saturationModifier(1.0f)
                            .build())),
            "rolled_pancake"
    );

    public static final BakedPancakeItem BAKED_PANCAKE = (BakedPancakeItem) register(
            new BakedPancakeItem(ROLLED_PANCAKE),
            "baked_pancake"
    );

    public static final Item ROLLED_PANCAKE_SUGAR = register(
            new Item(new Item.Settings()
                    .maxCount(16)
                    .food(new FoodComponent.Builder()
                            .nutrition(6)
                            .saturationModifier(1.0f)
                            .build())),
            "rolled_pancake_sugar"
    );

    public static final BakedPancakeItem BAKED_PANCAKE_SUGAR = (BakedPancakeItem) register(
            new BakedPancakeItem(ROLLED_PANCAKE_SUGAR),
            "baked_pancake_sugar"
    );

    public static final Item ROLLED_PANCAKE_HONEY = register(
            new Item(new Item.Settings()
                    .maxCount(16)
                    .food(new FoodComponent.Builder()
                            .nutrition(10)
                            .saturationModifier(1.0f)
                            .build())),
            "rolled_pancake_honey"
    );

    public static final BakedPancakeItem BAKED_PANCAKE_HONEY = (BakedPancakeItem) register(
            new BakedPancakeItem(ROLLED_PANCAKE_HONEY),
            "baked_pancake_honey"
    );

    public static final Item ROLLED_PANCAKE_CHOCOLATE = register(
            new Item(new Item.Settings()
                    .maxCount(16)
                    .food(new FoodComponent.Builder()
                            .nutrition(8)
                            .saturationModifier(1.0f)
                            .build())),
            "rolled_pancake_chocolate"
    );

    public static final BakedPancakeItem BAKED_PANCAKE_CHOCOLATE = (BakedPancakeItem) register(
            new BakedPancakeItem(ROLLED_PANCAKE_CHOCOLATE),
            "baked_pancake_chocolate"
    );

    public static final Item ROLLED_PANCAKE_APPLE = register(
            new Item(new Item.Settings()
                    .maxCount(16)
                    .food(new FoodComponent.Builder()
                            .nutrition(8)
                            .saturationModifier(1.0f)
                            .build())),
            "rolled_pancake_apple"
    );

    public static final BakedPancakeItem BAKED_PANCAKE_APPLE = (BakedPancakeItem) register(
            new BakedPancakeItem(ROLLED_PANCAKE_APPLE),
            "baked_pancake_apple"
    );

    public static final Item ROLLED_PANCAKE_BACON = register(
            new Item(new Item.Settings()
                    .maxCount(16)
                    .food(new FoodComponent.Builder()
                            .nutrition(14)
                            .saturationModifier(1.0f)
                            .build())),
            "rolled_pancake_bacon"
    );

    public static final BakedPancakeItem BAKED_PANCAKE_BACON = (BakedPancakeItem) register(
            new BakedPancakeItem(ROLLED_PANCAKE_BACON),
            "baked_pancake_bacon"
    );

    public static final Item GOLDEN_ROLLED_PANCAKE = register(
            new Item(new Item.Settings()
                    .maxCount(16)
                    .food(new FoodComponent.Builder()
                            .nutrition(8)
                            .saturationModifier(1.0f)
                            .build())),
            "golden_rolled_pancake"
    );

    public static final BakedPancakeItem GOLDEN_BAKED_PANCAKE = (BakedPancakeItem) register(
            new BakedPancakeItem(GOLDEN_ROLLED_PANCAKE),
            "golden_baked_pancake"
    );
}
