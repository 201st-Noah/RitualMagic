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

    /*public static final RegistryObject<BlockEntityType<SoulForgeBlockEntity>> SOUL_FORGE =
            BLOCK_ENTITIES.register("soul_forge", () ->
                    BlockEntityType.Builder.of(SoulForgeBlockEntity::new,
                            ModBlocks.SOUL_FORGE.get()).build(null));
*/

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}
