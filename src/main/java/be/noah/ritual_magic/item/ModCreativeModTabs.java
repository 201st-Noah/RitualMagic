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

    public static final RegistryObject<CreativeModeTab> RITUAL_MAGIC_TAB = CREATIVE_MODE_TABS.register("ritual_magic_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.ATLANTIAN_STEEL_INGOT.get()))
                    .title(Component.translatable("creativetab.ritual_magic_tab"))
                    .displayItems((pParameters, pOutput) -> {
                        pOutput.accept(ModItems.DWARVEN_SCRAP.get());
                        pOutput.accept(ModItems.DWARVEN_STEEL_INGOT.get());
                        pOutput.accept(ModItems.ATLANTIAN_SCRAP.get());
                        pOutput.accept(ModItems.ATLANTIAN_STEEL_INGOT.get());
                        pOutput.accept(ModItems.DRAGON_PLATE.get());
                        pOutput.accept(ModItems.DRAGON_SCALE.get());
                        pOutput.accept(ModItems.WARDEN_CORE.get());

                       // pOutput.accept(ModBlocks.ITEM_HOLDER.get());
                        pOutput.accept(ModBlocks.SOUL_BRICKS.get());
                        //pOutput.accept(ModBlocks.SOUL_FORGE.get());
                        pOutput.accept(ModBlocks.POLISHED_OBSIDIAN.get());
                        pOutput.accept(ModBlocks.DWARVEN_DEBRIS.get());
                        pOutput.accept(ModBlocks.ATLANTIAN_DEBRIS.get());
                        pOutput.accept(ModBlocks.PETRIFIED_DRAGON_SCALE.get());

                        pOutput.accept(ModItems.SPEER.get());
                        pOutput.accept(ModItems.DRAWEN_AXE.get());
                    })
                    .build());


    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}