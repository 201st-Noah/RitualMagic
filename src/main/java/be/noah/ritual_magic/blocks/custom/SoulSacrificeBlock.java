package be.noah.ritual_magic.blocks.custom;

import be.noah.ritual_magic.blocks.BlockTier;
import be.noah.ritual_magic.blocks.RitualBaseBlock;
import be.noah.ritual_magic.blocks.ModBlockEntities;
import be.noah.ritual_magic.blocks.entity.SoulSacrificeBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class SoulSacrificeBlock extends RitualBaseBlock<SoulSacrificeBlockEntity> {

    public SoulSacrificeBlock(BlockTier tier, Properties pProperties) {
        super(tier, pProperties,
                ModBlockEntities.SOUL_SACRIFICE,
                SoulSacrificeBlockEntity::tick);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new SoulSacrificeBlockEntity(pPos, pState);
    }
}
