package nl.duisterethomas.pancakemod;

import net.fabricmc.api.ModInitializer;

import nl.duisterethomas.pancakemod.registry.ModBlockEntityTypes;
import nl.duisterethomas.pancakemod.registry.ModItems;
import nl.duisterethomas.pancakemod.registry.ModBlocks;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PancakeMod implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final String MOD_ID = "pancakemod";
    public static final Logger LOGGER = LoggerFactory.getLogger("PancakeMod");

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		LOGGER.info("Initializing mod items");
		ModItems.initialize();
		LOGGER.info("Initializing mod blocks");
		ModBlocks.initialize();
		LOGGER.info("Initializing mod block entity types");
		ModBlockEntityTypes.initialize();
	}
}