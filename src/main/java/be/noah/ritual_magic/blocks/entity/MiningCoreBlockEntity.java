package be.noah.ritual_magic.blocks.entity;

import be.noah.ritual_magic.blocks.ModBlockEntities;
import be.noah.ritual_magic.blocks.RitualBaseBlockEntity;
import be.noah.ritual_magic.mana.ManaType;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class MiningCoreBlockEntity extends RitualBaseBlockEntity {

    public MiningCoreBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.MINING_CORE.get(), pPos, pBlockState);
    }

    @Override
    public void tick() {
        Level pLevel = getLevel();
        if(pLevel.isClientSide()) {return;}
        if (pLevel.getGameTime() % 20L == 0L) {
            if (this.structureIsOk()) {
                System.out.println("Jaaaaaaaaaaaaaaaaaaaaaaa");
            } else {
                System.out.println("Neiiiiiiiiiiiiiiiiiiiiiiin");
            }
        }
    }

    @Override
    public @NotNull ManaType getManaType() {
        return ManaType.NEXUS;
    }

}
