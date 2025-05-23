package be.noah.ritual_magic.effect;

import be.noah.ritual_magic.RitualMagic;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEffects {

    public static final DeferredRegister<MobEffect> MOB_EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, RitualMagic.MODID);

    public static final RegistryObject<MobEffect> ICECAVESYNDROM = MOB_EFFECTS.register("ice_cave_syndrom", () -> new IceCaveSyndrom(MobEffectCategory.HARMFUL, 0x36ebab));

    public static void register(IEventBus eventBus) {
        MOB_EFFECTS.register(eventBus);
    }
}
