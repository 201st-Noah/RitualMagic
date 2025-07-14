package be.noah.ritual_magic.blocks.entity;

import be.noah.ritual_magic.blocks.ModBlockEntities;
import be.noah.ritual_magic.blocks.RitualBaseBlockEntity;
import be.noah.ritual_magic.mana.ManaType;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;


public class ModTeleporterEntity extends RitualBaseBlockEntity {

    public ModTeleporterEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.MOD_TELEPORTER.get(), pPos, pBlockState);
    }

    @Override
    public void tick() {

    }

    @Override
    public @NotNull ManaType getManaType() {
        return ManaType.NEXUS;
    }
}
