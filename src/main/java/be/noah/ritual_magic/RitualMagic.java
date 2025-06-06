package be.noah.ritual_magic;

import be.noah.ritual_magic.blocks.ModBlocks;
import be.noah.ritual_magic.blocks.entity.ModBlockEntities;
import be.noah.ritual_magic.effects.ModEffects;
import be.noah.ritual_magic.entities.ModEntities;
import be.noah.ritual_magic.entities.client.BallLightningRenderer;
import be.noah.ritual_magic.entities.client.HomingProjectileRenderer;
import be.noah.ritual_magic.entities.client.ThrownDwarvenAxeRenderer;
import be.noah.ritual_magic.items.ModCreativeModTabs;
import be.noah.ritual_magic.items.ModItems;
import be.noah.ritual_magic.networking.ModMessages;
import be.noah.ritual_magic.recipes.ModRecipes;
import be.noah.ritual_magic.screens.AncientAnvilScreen;
import be.noah.ritual_magic.screens.ModMenuTypes;
import com.mojang.logging.LogUtils;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(RitualMagic.MODID)
public class RitualMagic {
    public static final String MODID = "ritual_magic";
    private static final Logger LOGGER = LogUtils.getLogger();

    public RitualMagic() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);
        ModCreativeModTabs.register(modEventBus);
        ModMenuTypes.register(modEventBus);
        ModEffects.register(modEventBus);
        ModBlockEntities.register(modEventBus);
        ModEntities.register(modEventBus);
        ModRecipes.register(modEventBus);
        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::addCreative);

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(ModMessages::register);
    }

    private void addCreative(BuildCreativeModeTabContentsEvent event) {

    }

    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            EntityRenderers.register(ModEntities.BALL_LIGHTNING.get(), BallLightningRenderer::new);
            EntityRenderers.register(ModEntities.THROWN_DWARVEN_AXE.get(), ThrownDwarvenAxeRenderer::new);
            EntityRenderers.register(ModEntities.HOMING_PROJECTILE.get(), HomingProjectileRenderer::new);
            MenuScreens.register(ModMenuTypes.ANCIENT_ANVIL_MENU.get(), AncientAnvilScreen::new);
        }
    }

}
