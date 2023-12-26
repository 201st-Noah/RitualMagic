package be.noah.ritual_magic.block.custom;

import be.noah.ritual_magic.block.entity.SoulForgeBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class SoulForgeBlock extends Block implements EntityBlock {
    public SoulForgeBlock(Properties pProperties) {
        super(pProperties);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new SoulForgeBlockEntity(pPos,pState);
    }
}
