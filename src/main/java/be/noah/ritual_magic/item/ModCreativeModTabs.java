package be.noah.ritual_magic.item;

import be.noah.ritual_magic.block.ModBlocks;
import net.minecraft.core.registries.Registries;
import be.noah.ritual_magic.RitualMagic;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModCreativeModTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, RitualMagic.MODID);

    public static final RegistryObject<CreativeModeTab> RITUAL_MAGIC_DWARVEN_TAB = CREATIVE_MODE_TABS.register(
            "ritual_magic_dwarven_tab",
            () -> CreativeModeTab.builder()
                    .icon(() -> new ItemStack(ModBlocks.DWARVEN_STEEL_BLOCK.get()))
                    .title(Component.translatable("creativetab.ritual_magic_dwarven_tab"))
                    .displayItems((pParameters, pOutput) -> {
                        //Basic Items
                        pOutput.accept(ModItems.DWARVEN_SCRAP.get());
                        pOutput.accept(ModItems.DWARVEN_STEEL_INGOT.get());
                        pOutput.accept(ModItems.WARDEN_CORE.get());
                        pOutput.accept(ModItems.DWARVEN_TEMPLATE.get());
                        pOutput.accept(ModItems.DWARVEN_STEEL_ARMOR_PLATE.get());
                        //Functional Items (Wapons, Tools, Armor,...)
                        pOutput.accept(ModItems.DWARVEN_AXE.get());
                        pOutput.accept(ModItems.DWARVEN_PICKAXE.get());
                        pOutput.accept(ModItems.TORCH.get());
                        pOutput.accept(ModItems.BUILDERSTAFF.get());
                        pOutput.accept(ModItems.DWAREN_STEEL_HELMET.get());
                        pOutput.accept(ModItems.DWAREN_STEEL_CHESTPLATE.get());
                        pOutput.accept(ModItems.DWAREN_STEEL_LEGGINGS.get());
                        pOutput.accept(ModItems.DWAREN_STEEL_BOOTS.get());
                        //Blocks
                        pOutput.accept(ModBlocks.DWARVEN_DEBRIS.get());
                        pOutput.accept(ModBlocks.DWARVEN_STEEL_BLOCK.get());
                        //BlockEntities
                        pOutput.accept(ModBlocks.ANCIENT_ANVIL.get());
                        pOutput.accept(ModBlocks.FORGE_T0.get());
                        pOutput.accept(ModBlocks.FORGE_T1.get());
                        pOutput.accept(ModBlocks.FORGE_T2.get());
                    })
                    .build()
    );

    public static final RegistryObject<CreativeModeTab> RITUAL_MAGIC_ATLANTIAN_TAB = CREATIVE_MODE_TABS.register(
            "ritual_magic_atlantian_tab",
            () -> CreativeModeTab.builder()
                    .icon(() -> new ItemStack(ModBlocks.ATLANTIAN_DEBRIS.get()))
                    .title(Component.translatable("creativetab.ritual_magic_atlantian_tab"))
                    .displayItems((pParameters, pOutput) -> {
                        //Basic Items
                        pOutput.accept(ModItems.ATLANTIAN_SCRAP.get());
                        pOutput.accept(ModItems.ATLANTIAN_STEEL_INGOT.get());
                        pOutput.accept(ModItems.ICE_SHARD.get());
                        //Functional Items (Wapons, Tools, Armor,...)
                        pOutput.accept(ModItems.ICE_SWORD.get());
                        //Blocks
                        pOutput.accept(ModBlocks.ATLANTIAN_DEBRIS.get());
                        pOutput.accept(ModBlocks.ICE_SPIKE.get());
                        //BlockEntities
                    })
                    .build()
    );

    public static final RegistryObject<CreativeModeTab> RITUAL_MAGIC_VOIDWALKER_TAB = CREATIVE_MODE_TABS.register(
            "ritual_magic_voidwalker_tab",
            () -> CreativeModeTab.builder()
                    .icon(() -> new ItemStack(ModBlocks.PETRIFIED_DRAGON_SCALE.get()))
                    .title(Component.translatable("creativetab.ritual_magic_voidwalker_tab"))
                    .displayItems((pParameters, pOutput) -> {
                        //Basic Items
                        pOutput.accept(ModItems.DRAGON_PLATE.get());
                        pOutput.accept(ModItems.DRAGON_SCALE.get());
                        //Functional Items (Wapons, Tools, Armor,...)
                        pOutput.accept(ModItems.SPEER.get());
                        //Blocks
                        pOutput.accept(ModBlocks.PETRIFIED_DRAGON_SCALE.get());
                        pOutput.accept(ModBlocks.DRAGON_FIRE.get()); // Remove later just here for testing
                        //BlockEntities
                    })
                    .build()
    );

    public static final RegistryObject<CreativeModeTab> RITUAL_MAGIC_SOULEATER_TAB = CREATIVE_MODE_TABS.register(
            "ritual_magic_souleater_tab",
            () -> CreativeModeTab.builder()
                    .icon(() -> new ItemStack(ModBlocks.SOUL_BRICKS.get()))
                    .title(Component.translatable("creativetab.ritual_magic_souleater_tab"))
                    .displayItems((pParameters, pOutput) -> {
                        //Basic Items
                        //Functional Items (Wapons, Tools, Armor,...)
                        pOutput.accept(ModItems.NETHER_SCEPTRE.get());
                        //Blocks
                        pOutput.accept(ModBlocks.SOUL_BRICKS.get());
                        //BlockEntities
                    })
                    .build()
    );

    public static final RegistryObject<CreativeModeTab> RITUAL_MAGIC_NEXUS_TAB = CREATIVE_MODE_TABS.register(
            "ritual_magic_nexus_tab",
            () -> CreativeModeTab.builder()
                    .icon(() -> new ItemStack(ModBlocks.MINING_CORE.get()))
                    .title(Component.translatable("creativetab.ritual_magic_nexus_tab"))
                    .displayItems((pParameters, pOutput) -> {
                        //Basic Items
                        //Functional Items (Wapons, Tools, Armor,...)
                        //Blocks
                        pOutput.accept(ModBlocks.POLISHED_OBSIDIAN.get());
                        //BlockEntities
                        pOutput.accept(ModBlocks.MINING_CORE.get());
                        pOutput.accept(ModBlocks.MOD_TELEPORTER.get());
                    })
                    .build()
    );

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}