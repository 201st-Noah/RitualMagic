package be.noah.ritual_magic.entities;

import be.noah.ritual_magic.RitualMagic;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, RitualMagic.MODID);
    public static final RegistryObject<EntityType<BallLightning>> BALL_LIGHTNING = ENTITIES.register("ball_lightning",
            () -> EntityType.Builder.<BallLightning>of(BallLightning::new, MobCategory.MISC)
                    .sized(0.25F, 0.25F)
                    .build(new ResourceLocation(RitualMagic.MODID, "ball_lightning").toString()));

    public static void register(IEventBus eventBus)
    {
        ENTITIES.register(eventBus);
    }
}
