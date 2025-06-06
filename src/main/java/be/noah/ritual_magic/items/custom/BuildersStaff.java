package be.noah.ritual_magic.items.custom;

import be.noah.ritual_magic.multiblocks.MultiblockBaseEntityBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;


public class BuildersStaff extends Item {
    public BuildersStaff(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        Level level = pContext.getLevel();
        BlockPos blockpos = pContext.getClickedPos();
        Block clickedBlock = level.getBlockState(blockpos).getBlock();
        if (clickedBlock instanceof MultiblockBaseEntityBlock) {
            ((MultiblockBaseEntityBlock) clickedBlock).getStructure().buildStructure(level, blockpos.getX(), blockpos.getY(), blockpos.getZ());
        }
        return InteractionResult.sidedSuccess(level.isClientSide());
    }
}
