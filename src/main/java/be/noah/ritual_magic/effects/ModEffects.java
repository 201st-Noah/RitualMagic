package be.noah.ritual_magic.effects;

import be.noah.ritual_magic.RitualMagic;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.awt.*;

public class ModEffects {

    public static final DeferredRegister<MobEffect> MOB_EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, RitualMagic.MODID);

    public static final RegistryObject<MobEffect> ICERAIN = MOB_EFFECTS.register("ice_rain", () -> new IceRain(MobEffectCategory.HARMFUL, Color.lightGray.getBlue()));
    public static final RegistryObject<MobEffect> FROSTAURA = MOB_EFFECTS.register("frost_aura", () -> new FrostAura(MobEffectCategory.BENEFICIAL, Color.lightGray.getBlue()));
    public static final RegistryObject<MobEffect> FIREAURA = MOB_EFFECTS.register("fire_aura", () -> new FireAura(MobEffectCategory.BENEFICIAL, Color.lightGray.getBlue()));

    public static void register(IEventBus eventBus) {
        MOB_EFFECTS.register(eventBus);
    }
}
