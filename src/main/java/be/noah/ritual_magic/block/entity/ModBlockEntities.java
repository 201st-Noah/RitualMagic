package be.noah.ritual_magic.block.entity;

import be.noah.ritual_magic.RitualMagic;
import be.noah.ritual_magic.block.ModBlocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, RitualMagic.MODID);

    public static final RegistryObject<BlockEntityType<MiningCoreBlockEntity>> MINING_CORE =
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
                            ModBlocks.RITUAL_PEDESTAL.get()).build(null));
    public static final RegistryObject<BlockEntityType<InfusionBlockEntity>> INFUSION =
            BLOCK_ENTITIES.register("infusion", () ->
                    BlockEntityType.Builder.of(InfusionBlockEntity::new,
                            ModBlocks.INFUSION.get()).build(null));

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}
