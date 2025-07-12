package be.noah.ritual_magic.items.custom;

import be.noah.ritual_magic.items.ModItems;
import be.noah.ritual_magic.items.armor.PlateArmor;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SmithingTableBlock;
import net.minecraft.world.phys.AABB;

import java.util.List;

public class BlacksmithHammer extends Item {

    public BlacksmithHammer(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        Level level = pContext.getLevel();
        Player player = pContext.getPlayer();
        BlockPos blockpos = pContext.getClickedPos();
        Block clickedBlock = level.getBlockState(blockpos).getBlock();
        if (!(clickedBlock instanceof SmithingTableBlock)) {return InteractionResult.FAIL;}

        if (!level.isClientSide) {
            AABB itemBox = new AABB(blockpos.above()).inflate(0.5);
            List<ItemEntity> itemsOnTable = level.getEntitiesOfClass(ItemEntity.class, itemBox);

            if (!itemsOnTable.isEmpty()) {

                //Armor deconstruction
                ItemStack armorStack = itemsOnTable.get(0).getItem();
                if (itemsOnTable.size() == 1 && armorStack.getItem() instanceof PlateArmor plateArmor) {
                    for (int i = 0; i < plateArmor.getRequiredPlates(); i++) {
                        if (plateArmor.hasArmorPlateInSlot(armorStack, i)) {
                            ItemStack plate = plateArmor.extractArmorPlate(armorStack, i);

                            if (!plate.isEmpty()) {
                                // Drop the extracted plate in the world
                                ItemEntity dropped = new ItemEntity(
                                        level,
                                        blockpos.getX() + 0.5,
                                        blockpos.getY() + 1,
                                        blockpos.getZ() + 0.5,
                                        plate
                                );
                                level.addFreshEntity(dropped);
                            }
                        }
                    }
                    itemsOnTable.get(0).setItem(armorStack);
                    return InteractionResult.SUCCESS;
                }
                else{
                    for (ItemEntity itemEntity : itemsOnTable) {
                        ItemStack stack = itemEntity.getItem();

                        //Increasing ArmorPlate Purity
                        if (stack.getItem() instanceof ArmorPlate plateItem && plateItem.getPurityLvl(stack) < 10) {

                            Item consumable = ModItems.DWARVEN_STEEL_INGOT.get();
                            switch (plateItem.getManaType()) {
                                case DRACONIC -> consumable = ModItems.DRAGON_PLATE.get();
                                case DWARVEN -> consumable = ModItems.DWARVEN_STEEL_INGOT.get();
                                case ATLANTIAN -> consumable = ModItems.ATLANTIAN_STEEL_INGOT.get();
                                case HELLISH -> consumable = ModItems.PURE_NETHERITE_INGOT.get();
                            }
                            for (ItemEntity otherEntity : itemsOnTable) {
                                ItemStack otherStack = otherEntity.getItem();

                                if (otherStack.getItem() == consumable) {
                                    int maxConsumable = plateItem.getValueCap(plateItem.getPurityLvl(stack)) - plateItem.getPurity(stack);
                                    if (otherStack.getCount() <= maxConsumable) {
                                        plateItem.addPurity(stack, otherStack.getCount());
                                        otherStack.shrink(otherStack.getCount());
                                    }else{
                                        plateItem.addPurity(stack, maxConsumable);
                                        otherStack.shrink(maxConsumable);
                                    }
                                    if (otherStack.isEmpty()) {
                                        otherEntity.discard();
                                    } else {
                                        otherEntity.setItem(otherStack);
                                    }

                                    if(plateItem.getPurity(stack) >= plateItem.getValueCap(plateItem.getPurityLvl(stack))){
                                        plateItem.setPurity(stack, 0);
                                        plateItem.addPurityLvl(stack);
                                    }
                                    itemEntity.setItem(stack);
                                    level.playSound(null, blockpos, SoundEvents.ANVIL_USE, SoundSource.BLOCKS, 1.0F, 1.0F);
                                    return InteractionResult.SUCCESS;
                                }
                            }
                        }
                    }
                }


            }
        }
        return InteractionResult.sidedSuccess(level.isClientSide());
    }
}
