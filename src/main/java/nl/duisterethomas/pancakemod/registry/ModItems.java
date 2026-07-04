package nl.duisterethomas.pancakemod.registry;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import nl.duisterethomas.pancakemod.PancakeMod;
import nl.duisterethomas.pancakemod.items.BakedPancakeItem;

public class ModItems {
    public static void initialize() {
        // Add all the items to the right item groups
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.FOOD_AND_DRINKS)
                .register((itemGroup) -> itemGroup.accept(RAW_PANCAKE));
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.FOOD_AND_DRINKS)
                .register((itemGroup) -> itemGroup.accept(BAKED_PANCAKE));
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.FOOD_AND_DRINKS)
                .register((itemGroup) -> itemGroup.accept(ROLLED_PANCAKE));
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.FOOD_AND_DRINKS)
                .register((itemGroup) -> itemGroup.accept(BAKED_PANCAKE_SUGAR));
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.FOOD_AND_DRINKS)
                .register((itemGroup) -> itemGroup.accept(ROLLED_PANCAKE_SUGAR));
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.FOOD_AND_DRINKS)
                .register((itemGroup) -> itemGroup.accept(BAKED_PANCAKE_HONEY));
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.FOOD_AND_DRINKS)
                .register((itemGroup) -> itemGroup.accept(ROLLED_PANCAKE_HONEY));
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.FOOD_AND_DRINKS)
                .register((itemGroup) -> itemGroup.accept(BAKED_PANCAKE_CHOCOLATE));
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.FOOD_AND_DRINKS)
                .register((itemGroup) -> itemGroup.accept(ROLLED_PANCAKE_CHOCOLATE));
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.FOOD_AND_DRINKS)
                .register((itemGroup) -> itemGroup.accept(BAKED_PANCAKE_APPLE));
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.FOOD_AND_DRINKS)
                .register((itemGroup) -> itemGroup.accept(ROLLED_PANCAKE_APPLE));
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.FOOD_AND_DRINKS)
                .register((itemGroup) -> itemGroup.accept(BAKED_PANCAKE_BACON));
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.FOOD_AND_DRINKS)
                .register((itemGroup) -> itemGroup.accept(ROLLED_PANCAKE_BACON));
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.FOOD_AND_DRINKS)
                .register((itemGroup) -> itemGroup.accept(GOLDEN_BAKED_PANCAKE));
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.FOOD_AND_DRINKS)
                .register((itemGroup) -> itemGroup.accept(GOLDEN_ROLLED_PANCAKE));
    }

    public static Item register(Item item, String id) {
        // Create the identifier for the item
        ResourceLocation itemID = ResourceLocation.fromNamespaceAndPath(PancakeMod.MOD_ID, id);

        // Register and return the item
        return Registry.register(BuiltInRegistries.ITEM, itemID, item);
    }

    public static final Item RAW_PANCAKE = register(
            new Item(new Item.Properties()
                    .food(new FoodProperties.Builder()
                            .nutrition(1)
                            .saturationModifier(0.25f)
                            // The duration is in ticks, 20 ticks = 1 second
                            .effect(new MobEffectInstance(MobEffects.HUNGER, 400, 1), 0.5f)
                            .build())),
            "raw_pancake"
    );

    public static final Item ROLLED_PANCAKE = register(
            new Item(new Item.Properties()
                    .stacksTo(16)
                    .food(new FoodProperties.Builder()
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
            new Item(new Item.Properties()
                    .stacksTo(16)
                    .food(new FoodProperties.Builder()
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
            new Item(new Item.Properties()
                    .stacksTo(16)
                    .food(new FoodProperties.Builder()
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
            new Item(new Item.Properties()
                    .stacksTo(16)
                    .food(new FoodProperties.Builder()
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
            new Item(new Item.Properties()
                    .stacksTo(16)
                    .food(new FoodProperties.Builder()
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
            new Item(new Item.Properties()
                    .stacksTo(16)
                    .food(new FoodProperties.Builder()
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
            new Item(new Item.Properties()
                    .stacksTo(16)
                    .food(new FoodProperties.Builder()
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
