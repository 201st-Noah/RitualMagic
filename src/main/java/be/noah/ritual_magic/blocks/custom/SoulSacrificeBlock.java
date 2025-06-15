package be.noah.ritual_magic.blocks.custom;

import be.noah.ritual_magic.blocks.RitualBaseBlock;
import be.noah.ritual_magic.blocks.entity.ModBlockEntities;
import be.noah.ritual_magic.blocks.entity.SoulSacrificeBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class SoulSacrificeBlock extends RitualBaseBlock<SoulSacrificeBlockEntity> {

    public SoulSacrificeBlock(Properties pProperties) {
        super(pProperties,
                ModBlockEntities.SOUL_SACRIFICE,
                SoulSacrificeBlockEntity::tick);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new SoulSacrificeBlockEntity(pPos, pState);
    }
}
