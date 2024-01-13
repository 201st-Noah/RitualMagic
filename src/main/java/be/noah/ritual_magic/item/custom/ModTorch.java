package be.noah.ritual_magic.item.custom;

import be.noah.ritual_magic.block.ModBlocks;
import be.noah.ritual_magic.block.custom.fire.DragonFireBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;

public class ModTorch extends Item {
    private static final String BURN_TIME_TAG = "BurnTime";
    private static final String BURN_TIME_LEFT_TAG = "BurnTimeLeft";
    private static final String FIRE_TYPE_TAG = "FireType";
    private static final String ON_TAG = "On";

//    private int burnTime = 1;
  //  private int burnTimeLeft = 1;
    private enum Typ {NONE, FIRE, SOULFIRE, DRAGONFIRE, DEEPSEEFIRE}
    //private Typ fireType;
    //private boolean ON = false;
    public ModTorch(Item.Properties pProperties) {
        super(pProperties);
    }
    private int getBurnTime(ItemStack stack) {
        return stack.hasTag() ? stack.getTag().getInt(BURN_TIME_TAG) : 0;
    }

    private void setBurnTime(ItemStack stack, int value) {
        stack.getOrCreateTag().putInt(BURN_TIME_TAG, value);
    }

    private int getBurnTimeLeft(ItemStack stack) {
        return stack.hasTag() ? stack.getTag().getInt(BURN_TIME_LEFT_TAG) : 0;
    }

    private void setBurnTimeLeft(ItemStack stack, int value) {
        stack.getOrCreateTag().putInt(BURN_TIME_LEFT_TAG, value);
    }

    private Typ getFireType(ItemStack stack) {
        return stack.hasTag() ? Typ.valueOf(stack.getTag().getString(FIRE_TYPE_TAG)) : Typ.NONE;
    }

    private void setFireType(ItemStack stack, Typ type) {
        stack.getOrCreateTag().putString(FIRE_TYPE_TAG, type.name());
    }

    private boolean isOn(ItemStack stack) {
        return stack.hasTag() && stack.getTag().getBoolean(ON_TAG);
    }

    private void setOn(ItemStack stack, boolean value) {
        stack.getOrCreateTag().putBoolean(ON_TAG, value);
    }

    @Override
    public int getBarColor(ItemStack pStack) {
        switch (getFireType(pStack)){
            case FIRE:
                return Mth.hsvToRgb(0,1,1);
            case SOULFIRE:
                return Mth.hsvToRgb(0.5f,1,1);
            case DRAGONFIRE:
                return Mth.hsvToRgb(0.83f,1,1);
            case DEEPSEEFIRE:
                return Mth.hsvToRgb(0,0,0);
            default:
                return Mth.hsvToRgb(0.6f,1,1);
        }
    }
    @Override
    public int getBarWidth(ItemStack pStack) {
        return Math.round((float)getBurnTimeLeft(pStack) * 13.0F / (float)getBurnTime(pStack));
    }
    @Override
    public int getDamage(ItemStack stack) {
        return 0;
    }
    @Override
    public boolean isBarVisible(ItemStack pStack) {
        return isOn(pStack);
    }
    @Override
    public int getMaxStackSize(ItemStack stack) {
        return 1;
    }
    @Override
    public boolean onLeftClickEntity(ItemStack stack, Player player, Entity entity) {
        System.out.println("ne ey");
        if(isOn(stack)){
            switch (getFireType(stack)){
                case FIRE:
                    entity.setSecondsOnFire(4);
                    break;
                case SOULFIRE:
                    entity.setSecondsOnFire(8);
                    break;
                case DRAGONFIRE:
                    entity.setSecondsOnFire(16);
                    break;
            }
        }
        return super.onLeftClickEntity(stack, player, entity);
    }
    //has to set the burnTime, and the burnTimeLeft both to the same start value, acording to wich fire is on (in ticks=> 72000, 36000, 12000, 2400s)
    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        ItemStack pStack = pContext.getItemInHand();
        //Player player = pContext.getPlayer();
        Level level = pContext.getLevel();
        BlockPos blockpos = pContext.getClickedPos();
        //BlockState blockstateUp = level.getBlockState(blockpos.above());
        Block blockUp = (level.getBlockState(blockpos.above())).getBlock();
        BlockState blockstate = level.getBlockState(blockpos);
        Block clickedBlock = blockstate.getBlock();
        if(isOn(pStack)){
            BlockState blockstate1;
            switch (getFireType(pStack)){
                case FIRE:
                    blockstate1 = FireBlock.getState(level, blockpos.above());
                    break;
                case SOULFIRE:
                    blockstate1 = SoulFireBlock.getState(level, blockpos.above());
                    break;
                case DRAGONFIRE:
                    blockstate1 = DragonFireBlock.getState(level, blockpos.above());
                    break;
                default:
                    return InteractionResult.FAIL;
            }
            if(blockUp == Blocks.AIR){
                level.setBlock(blockpos.above(), blockstate1, 11);
            } else if (blockUp == Blocks.WATER) {
                return InteractionResult.FAIL;
            }else {
                return InteractionResult.FAIL;
            }
        }
        else {
            if (clickedBlock == ModBlocks.DRAGON_FIRE.get()){
               /* fireType = Typ.DRAGONFIRE;
                burnTime = 12000;
                burnTimeLeft = 12000;
                ON = true;*/
                setFireType(pStack,Typ.DRAGONFIRE);
                setBurnTime(pStack,12000);
                setBurnTimeLeft(pStack,12000);
                setOn(pStack,true);
            } else if (clickedBlock == Blocks.FIRE) {
               /* fireType = Typ.FIRE;
                burnTime = 72000;
                burnTimeLeft = 72000;
                ON = true;*/
                setFireType(pStack,Typ.FIRE);
                setBurnTime(pStack,72000);
                setBurnTimeLeft(pStack,72000);
                setOn(pStack,true);
            } else if (clickedBlock == Blocks.SOUL_FIRE) {
                /*fireType = Typ.SOULFIRE;
                burnTime = 36000;
                burnTimeLeft = 36000;
                ON = true;*/
                setFireType(pStack,Typ.SOULFIRE);
                setBurnTime(pStack,36000);
                setBurnTimeLeft(pStack,36000);
                setOn(pStack,true);
            } else {
                return InteractionResult.FAIL;
            }
        }
        return InteractionResult.sidedSuccess(level.isClientSide());
    }

    @Override
    public void inventoryTick(ItemStack pStack, Level pLevel, Entity pEntity, int pSlotId, boolean pIsSelected) {
        if(pEntity instanceof Player && isOn(pStack)){
            int burnTimeLeft = getBurnTimeLeft(pStack) -1;
            setBurnTimeLeft(pStack, burnTimeLeft);
            if (getBurnTimeLeft(pStack) <= 0){
                setFireType(pStack, Typ.NONE);
                setBurnTime(pStack,100);
                setBurnTimeLeft(pStack,100);
                setOn(pStack,false);
            }
        }
    }
}
