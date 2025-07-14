package be.noah.ritual_magic.blocks.custom;

import be.noah.ritual_magic.blocks.BlockTier;
import be.noah.ritual_magic.blocks.ModBlockEntities;
import be.noah.ritual_magic.blocks.RitualBaseBlock;
import be.noah.ritual_magic.blocks.entity.MiningCoreBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class MiningCoreBlock extends RitualBaseBlock<MiningCoreBlockEntity> {
    public MiningCoreBlock(BlockTier tier, Properties pProperties) {
        super(tier, pProperties, ModBlockEntities.MINING_CORE);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new MiningCoreBlockEntity(pPos, pState);
    }
}
