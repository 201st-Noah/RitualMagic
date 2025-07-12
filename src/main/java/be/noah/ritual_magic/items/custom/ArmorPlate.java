package be.noah.ritual_magic.items.custom;

import be.noah.ritual_magic.items.armor.PlateArmor;
import be.noah.ritual_magic.mana.ManaType;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SmithingTableBlock;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ArmorPlate extends Item {
    /*
    24 Plates per armor set

    Darven Armor Plate:
        Purity
        Surface finish (better Movement speed)
    Ice Armor Plate:
        Purity
        Tension (if hit everyone in a radius gets splinters)
    SoulEater Armor Plate:
        Purity
        Soul count fused in (gets infused by a ritual)
    VoidWalker Armor Plate:
        Purity
        VoidEnergy (gets infused by a ritual)

    */

    private static final String PURITY = "Purity";
    private static final String PURITY_LVL = "Purity_lvl";
    private static final String UNIQVAR = "UniqVar";
    private static final String UNIQVAR_LVL = "UniqVar_lvl";

    private static final String TEMPERATURE = "Temperature";
    private ManaType manaType;

    public ArmorPlate(ManaType manaType, Properties pProperties) {
        super(pProperties);
        this.manaType = manaType;
    }

    @Override
    public int getMaxStackSize(ItemStack stack) {
        return 1;
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        if (getPurityLvl(pStack) < 10) {
            pTooltipComponents.add(Component.literal("Purity Level: " + getPurityLvl(pStack)));
            pTooltipComponents.add(Component.literal("Progress: " + getPurity(pStack) + "/" + getValueCap(getPurityLvl(pStack))));
        }else{
            pTooltipComponents.add(Component.literal("Purity Level: MAX"));
        }
        String spesificsForType = "";
        switch (manaType) {
            case ATLANTIAN -> spesificsForType = "Tension: ";
            case DWARVEN -> spesificsForType = "Surface Finish: ";
            case DRACONIC -> spesificsForType = "Void Energy: ";
            case HELLISH -> spesificsForType = "Soul Energy: ";
            default -> spesificsForType = "Energy: ";
        }
        pTooltipComponents.add(Component.literal(spesificsForType + getUniqVarLvl(pStack)));
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }

    public ManaType getManaType() {
        return manaType;
    }

    public int getValueCap(int level) {
        return (int) Math.pow(2, level + 1 );
    }

    //Purity
    public int getPurity(ItemStack stack) {
        return stack.hasTag() ? stack.getTag().getInt(PURITY) : 0;
    }
    public void setPurity(ItemStack stack, int value) {
        stack.getOrCreateTag().putInt(PURITY, value);
    }
    public void addPurity(ItemStack stack) {
        stack.getOrCreateTag().putInt(PURITY, getPurity(stack) + 1);
    }
    public void addPurity(ItemStack stack, int value) {
        stack.getOrCreateTag().putInt(PURITY, value + getPurity(stack));
    }

    public int getPurityLvl(ItemStack stack) {
        return stack.hasTag() ? stack.getTag().getInt(PURITY_LVL) : 0;
    }
    public void setPurityLvl(ItemStack stack, int value) {
        stack.getOrCreateTag().putInt(PURITY_LVL, value);
    }
    public void addPurityLvl(ItemStack stack) {
        stack.getOrCreateTag().putInt(PURITY_LVL, getPurityLvl(stack) + 1);
    }
    public void addPurityLvl(ItemStack stack, int value) {
        stack.getOrCreateTag().putInt(PURITY_LVL, getPurityLvl(stack) + value);
    }

    //UniqVar
    private int getUniqVar(ItemStack stack) {
        return stack.hasTag() ? stack.getTag().getInt(UNIQVAR) : 0;
    }
    private void setUniqVar(ItemStack stack, int value) {
        stack.getOrCreateTag().putInt(UNIQVAR, value);
    }
    private void addUniqVar(ItemStack stack, int value) {
        stack.getOrCreateTag().putInt(UNIQVAR, getUniqVar(stack) + value);
    }
    private void addUniqVar(ItemStack stack) {
        stack.getOrCreateTag().putInt(UNIQVAR, getUniqVar(stack) + 1);
    }

    private int getUniqVarLvl(ItemStack stack) {
        return stack.hasTag() ? stack.getTag().getInt(UNIQVAR_LVL) : 0;
    }
    private void setUniqVarLvl(ItemStack stack, int value) {
        stack.getOrCreateTag().putInt(UNIQVAR_LVL, value);
    }
    private void addUniqVarLvl(ItemStack stack, int value) {
        stack.getOrCreateTag().putInt(UNIQVAR_LVL, getUniqVarLvl(stack) + value);
    }
    private void addUniqVarLvl(ItemStack stack) {
        stack.getOrCreateTag().putInt(UNIQVAR_LVL, getUniqVarLvl(stack) + 1);
    }

    //TODO remove just temp for testing
    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        if (level.isClientSide()) {
            return InteractionResultHolder.pass(player.getItemInHand(hand));
        }

        ItemStack plateStack = player.getItemInHand(hand);
        ItemStack offhandStack = player.getOffhandItem();

        // Check if offhand has plate armor
        if (offhandStack.getItem() instanceof PlateArmor plateArmor && plateArmor.getManaType() == this.getManaType()) {
            // Find first empty slot
            for (int i = 0; i <= plateArmor.getInstalledPlatesCount(offhandStack); i++) {
                if (!plateArmor.hasArmorPlateInSlot(offhandStack, i)) {
                    // Try to add the plate
                    if (plateArmor.addArmorPlate(offhandStack, plateStack, i)) {
                        // Success - consume one plate
                        plateStack.shrink(1);
                        player.playSound(SoundEvents.ARMOR_EQUIP_NETHERITE, 1.0F, 1.0F);
                        player.displayClientMessage(Component.literal("§aArmor plate installed!"), true);
                        return InteractionResultHolder.success(plateStack);
                    }
                }
            }
            // No empty slots found
            player.displayClientMessage(Component.literal("§cNo empty slots in armor!"), true);
            return InteractionResultHolder.fail(plateStack);
        }

        return InteractionResultHolder.pass(plateStack);
    }

    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        Level level = pContext.getLevel();
        Player player = pContext.getPlayer();
        ItemStack stack = pContext.getItemInHand();
        BlockPos blockpos = pContext.getClickedPos();
        Block clickedBlock = level.getBlockState(blockpos).getBlock();
        if (!(clickedBlock instanceof SmithingTableBlock)) {
            setUniqVarLvl(stack, getUniqVarLvl(stack) + 1);
            return InteractionResult.SUCCESS;
        }
        return super.useOn(pContext);
    }
}
