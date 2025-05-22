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
    public static final RegistryObject<EntityType<ThrownDwarvenAxe>> THROWN_DWARVEN_AXE = ENTITIES.register("thrown_dwarven_axe",
            () -> EntityType.Builder.<ThrownDwarvenAxe>of(ThrownDwarvenAxe::new, MobCategory.MISC)
                    .sized(0.25F, 0.25F)
                    //.clientTrackingRange(4)
                    .updateInterval(20)
                    .build(new ResourceLocation(RitualMagic.MODID, "thrown_dwarven_axe").toString()));

    public static final RegistryObject<EntityType<HomingProjectile>> HOMING_PROJECTILE = ENTITIES.register("homing_projectile",
            () -> EntityType.Builder.<HomingProjectile>of(HomingProjectile::new, MobCategory.MISC)
                    .sized(0.5F, 0.5F)
                    .updateInterval(1) // Update frequently for smooth homing
                    .build(new ResourceLocation(RitualMagic.MODID, "homing_projectile").toString()));

    public static void register(IEventBus eventBus)
    {
        ENTITIES.register(eventBus);
    }
}
