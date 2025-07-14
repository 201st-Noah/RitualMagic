package be.noah.ritual_magic.blocks.entity;

import be.noah.ritual_magic.blocks.ModBlockEntities;
import be.noah.ritual_magic.blocks.RitualBaseBlockEntity;
import be.noah.ritual_magic.mana.ManaType;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class AntiTeleportRitualBlockEntity extends RitualBaseBlockEntity {

    public AntiTeleportRitualBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.ANTI_TELEPORTATION_RITUAL.get(), pPos, pBlockState);
    }

    @Override
    public void tick() {
        Level pLevel = getLevel();
        BlockPos pPos = getBlockPos();
        RitualBaseBlockEntity pRitualBaseBlockEntity = this;
        if(!(pLevel.getGameTime() % 20 == 3)) {return;} //Timer
        if (!pLevel.isClientSide && pRitualBaseBlockEntity.getOwner() != null) {
            if (!pRitualBaseBlockEntity.structureIsOk() ){return;}
            boolean res = pRitualBaseBlockEntity.consumeMana(pLevel, getOwner(), (getBlockTier(pLevel, pPos).getInt() * 10) + 10);
        }
    }

    @Override
    public @NotNull ManaType getManaType() {
        return ManaType.DRACONIC;
    }
}
