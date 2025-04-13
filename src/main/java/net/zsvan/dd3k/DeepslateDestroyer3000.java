package net.zsvan.dd3k;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.ToolMaterials;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DeepslateDestroyer3000 implements ModInitializer {
	public static final String MOD_ID = "dd3k";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static final DeepslateDestroyerPickaxe DEEPSLATE_DESTROYER = new DeepslateDestroyerPickaxe(
			ToolMaterials.NETHERITE,
			2,
			-2.8F,
			new FabricItemSettings().maxCount(1).fireproof()
	);

	@Override
	public void onInitialize() {
		LOGGER.info("Hello Fabric world!");
		Registry.register(Registries.ITEM, new Identifier(MOD_ID, "deepslate_destroyer"), DEEPSLATE_DESTROYER);
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(content -> {
			content.add(DEEPSLATE_DESTROYER);
		});
	}
}