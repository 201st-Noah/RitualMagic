package be.noah.ritual_magic.item.custom;

import be.noah.ritual_magic.Multiblocks.MultiblockBaseEntityBlock;
import be.noah.ritual_magic.block.custom.MiningCoreBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;

import java.lang.reflect.Method;

public class BuildersStaff extends Item {
    public BuildersStaff(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        Level level = pContext.getLevel();
        BlockPos blockpos = pContext.getClickedPos();
        Block clickedBlock = level.getBlockState(blockpos).getBlock();
        if(clickedBlock instanceof MultiblockBaseEntityBlock){
            ((MultiblockBaseEntityBlock) clickedBlock).getStructure().buildStructure(level, blockpos.getX(), blockpos.getY(), blockpos.getZ());
        }
        return InteractionResult.sidedSuccess(level.isClientSide());
    }
}
