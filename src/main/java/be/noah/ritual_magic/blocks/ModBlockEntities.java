package be.noah.ritual_magic.blocks;

import be.noah.ritual_magic.RitualMagic;
import be.noah.ritual_magic.blocks.entity.*;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, RitualMagic.MODID);

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }    public static final RegistryObject<BlockEntityType<MiningCoreBlockEntity>> MINING_CORE =
            BLOCK_ENTITIES.register("mining_core", () ->
                    BlockEntityType.Builder.of(MiningCoreBlockEntity::new,
                            ModBlocks.MINING_CORE.get()).build(null));
    public static final RegistryObject<BlockEntityType<ModTeleporterEntity>> MOD_TELEPORTER =
            BLOCK_ENTITIES.register("mod_teleporter", () ->
                    BlockEntityType.Builder.of(ModTeleporterEntity::new,
                            ModBlocks.MOD_TELEPORTER.get()).build(null));
    public static final RegistryObject<BlockEntityType<AncientAnvilBlockEntity>> ANCIENT_ANVIL =
            BLOCK_ENTITIES.register("ancient_anvil", () ->
                    BlockEntityType.Builder.of(AncientAnvilBlockEntity::new,
                            ModBlocks.ANCIENT_ANVIL.get()).build(null));
    public static final RegistryObject<BlockEntityType<ForgeBlockEntity>> FORGE =
            BLOCK_ENTITIES.register("forge", () ->
                    BlockEntityType.Builder.of(ForgeBlockEntity::new,
                            ModBlocks.FORGE_T0.get()).build(null));
    public static final RegistryObject<BlockEntityType<RitualPedestalBlockEntity>> RITUAL_PEDESTAL =
            BLOCK_ENTITIES.register("ritual_pedestal", () ->
                    BlockEntityType.Builder.of(RitualPedestalBlockEntity::new,
                            ModBlocks.B_NEXUS_PEDESTAL.get(),
                            ModBlocks.I_NEXUS_PEDESTAL.get(),
                            ModBlocks.A_NEXUS_PEDESTAL.get(),
                            ModBlocks.U_NEXUS_PEDESTAL.get(),
                            ModBlocks.B_ATLANTIAN_PEDESTAL.get(),
                            ModBlocks.I_ATLANTIAN_PEDESTAL.get(),
                            ModBlocks.A_ATLANTIAN_PEDESTAL.get(),
                            ModBlocks.U_ATLANTIAN_PEDESTAL.get(),
                            ModBlocks.B_DWARVEN_PEDESTAL.get(),
                            ModBlocks.I_DWARVEN_PEDESTAL.get(),
                            ModBlocks.A_DWARVEN_PEDESTAL.get(),
                            ModBlocks.U_DWARVEN_PEDESTAL.get(),
                            ModBlocks.B_HELLISH_PEDESTAL.get(),
                            ModBlocks.I_HELLISH_PEDESTAL.get(),
                            ModBlocks.A_HELLISH_PEDESTAL.get(),
                            ModBlocks.U_HELLISH_PEDESTAL.get(),
                            ModBlocks.B_DRACONIC_PEDESTAL.get(),
                            ModBlocks.I_DRACONIC_PEDESTAL.get(),
                            ModBlocks.A_DRACONIC_PEDESTAL.get(),
                            ModBlocks.U_DRACONIC_PEDESTAL.get()).build(null));
    public static final RegistryObject<BlockEntityType<InfusionBlockEntity>> INFUSION =
            BLOCK_ENTITIES.register("infusion", () ->
                    BlockEntityType.Builder.of(InfusionBlockEntity::new,
                            ModBlocks.INFUSION.get(),
                            ModBlocks.B_NEXUS_INFUSION_CORE.get(),
                            ModBlocks.I_NEXUS_INFUSION_CORE.get(),
                            ModBlocks.A_NEXUS_INFUSION_CORE.get(),
                            ModBlocks.U_NEXUS_INFUSION_CORE.get(),
                            ModBlocks.B_ATLANTIAN_INFUSION_CORE.get(),
                            ModBlocks.I_ATLANTIAN_INFUSION_CORE.get(),
                            ModBlocks.A_ATLANTIAN_INFUSION_CORE.get(),
                            ModBlocks.U_ATLANTIAN_INFUSION_CORE.get(),
                            ModBlocks.B_DWARVEN_INFUSION_CORE.get(),
                            ModBlocks.I_DWARVEN_INFUSION_CORE.get(),
                            ModBlocks.A_DWARVEN_INFUSION_CORE.get(),
                            ModBlocks.U_DWARVEN_INFUSION_CORE.get(),
                            ModBlocks.B_HELLISH_INFUSION_CORE.get(),
                            ModBlocks.I_HELLISH_INFUSION_CORE.get(),
                            ModBlocks.A_HELLISH_INFUSION_CORE.get(),
                            ModBlocks.U_HELLISH_INFUSION_CORE.get(),
                            ModBlocks.B_DRACONIC_INFUSION_CORE.get(),
                            ModBlocks.I_DRACONIC_INFUSION_CORE.get(),
                            ModBlocks.A_DRACONIC_INFUSION_CORE.get(),
                            ModBlocks.U_DRACONIC_INFUSION_CORE.get()).build(null));

    public static final RegistryObject<BlockEntityType<SoulSacrificeBlockEntity>> SOUL_SACRIFICE =
            BLOCK_ENTITIES.register("soul_sacrifice", () ->
                    BlockEntityType.Builder.of(SoulSacrificeBlockEntity::new,
                            ModBlocks.B_SOUL_SACRIFICE.get(),
                            ModBlocks.I_SOUL_SACRIFICE.get(),
                            ModBlocks.A_SOUL_SACRIFICE.get(),
                            ModBlocks.U_SOUL_SACRIFICE.get()).build(null));

    public static final RegistryObject<BlockEntityType<WeatherRitualBlockEntity>> WEATHER_RITUAL =
            BLOCK_ENTITIES.register("weather_ritual", () ->
                    BlockEntityType.Builder.of(WeatherRitualBlockEntity::new,
                            ModBlocks.B_WEATHER_RITUAL.get(),
                            ModBlocks.I_WEATHER_RITUAL.get(),
                            ModBlocks.A_WEATHER_RITUAL.get(),
                            ModBlocks.U_WEATHER_RITUAL.get()).build(null));

    public static final RegistryObject<BlockEntityType<AntiTeleportRitualBlockEntity>> ANTI_TELEPORTATION_RITUAL =
            BLOCK_ENTITIES.register("anti_teleportation_ritual", () ->
                    BlockEntityType.Builder.of(AntiTeleportRitualBlockEntity::new,
                            ModBlocks.B_ANTI_TELEPORT_RITUAL.get(),
                            ModBlocks.I_ANTI_TELEPORT_RITUAL.get(),
                            ModBlocks.A_ANTI_TELEPORT_RITUAL.get(),
                            ModBlocks.U_ANTI_TELEPORT_RITUAL.get()
                            ).build(null));

}
