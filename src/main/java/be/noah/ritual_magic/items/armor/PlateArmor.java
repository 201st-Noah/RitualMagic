package be.noah.ritual_magic.items.armor;

import be.noah.ritual_magic.items.LeveldMagicArmor;
import be.noah.ritual_magic.items.custom.ArmorPlate;
import be.noah.ritual_magic.mana.ManaType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.List;
import java.util.function.Consumer;

public abstract class PlateArmor extends ArmorItem implements GeoItem, LeveldMagicArmor {

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private static final String ARMOR_PLATES_TAG = "ArmorPlates";
    private int required_plates;


    public PlateArmor(int required_plates, ArmorMaterial pMaterial, Type pType, Properties pProperties) {
        super(pMaterial, pType, pProperties);
        this.required_plates = required_plates;
    }

    public boolean addArmorPlate(ItemStack armorStack, ItemStack plateStack, int slot) {
        if (!(plateStack.getItem() instanceof ArmorPlate) || slot < 0 || slot >= required_plates) {
            return false;
        }

        CompoundTag armorTag = armorStack.getOrCreateTag();
        ListTag platesList;

        if (!armorTag.contains(ARMOR_PLATES_TAG)) {
            platesList = new ListTag();
            for (int i = 0; i < required_plates; i++) {
                platesList.add(new CompoundTag()); // Empty slots
            }
            armorTag.put(ARMOR_PLATES_TAG, platesList);
        } else {
            platesList = armorTag.getList(ARMOR_PLATES_TAG, Tag.TAG_COMPOUND);
        }

        // Store the entire ItemStack
        CompoundTag plateItemStack = new CompoundTag();
        plateStack.save(plateItemStack);
        platesList.set(slot, plateItemStack);

        return true;
    }

    public ItemStack extractArmorPlate(ItemStack armorStack, int slot) {
        if (!hasArmorPlateInSlot(armorStack, slot)) {
            return ItemStack.EMPTY;
        }

        CompoundTag armorTag = armorStack.getTag();
        ListTag platesList = armorTag.getList(ARMOR_PLATES_TAG, Tag.TAG_COMPOUND);
        CompoundTag plateItemStack = platesList.getCompound(slot);

        // Create ItemStack from stored data
        ItemStack plateStack = ItemStack.of(plateItemStack);

        // Clear the slot
        platesList.set(slot, new CompoundTag());

        return plateStack;
    }

    public boolean hasArmorPlateInSlot(ItemStack armorStack, int slot) {
        if (!armorStack.hasTag() || !armorStack.getTag().contains(ARMOR_PLATES_TAG) || slot < 0 || slot >= required_plates) {
            return false;
        }
        ListTag platesList = armorStack.getTag().getList(ARMOR_PLATES_TAG, Tag.TAG_COMPOUND);
        return !platesList.getCompound(slot).isEmpty();
    }

    public int getInstalledPlatesCount(ItemStack armorStack) {
        if (!armorStack.hasTag() || !armorStack.getTag().contains(ARMOR_PLATES_TAG)) {
            return 0;
        }
        ListTag platesList = armorStack.getTag().getList(ARMOR_PLATES_TAG, Tag.TAG_COMPOUND);
        int count = 0;
        for (int i = 0; i < platesList.size(); i++) {
            if (!platesList.getCompound(i).isEmpty()) {
                count++;
            }
        }
        return count;
    }

    public boolean isCompleteArmor(ItemStack armorStack) {
        return getInstalledPlatesCount(armorStack) == required_plates;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        LeveldMagicArmor.super.appendLevelTooltip(stack, tooltip);

        if (stack.hasTag() && stack.getTag().contains(ARMOR_PLATES_TAG)) {
            ListTag platesList = stack.getTag().getList(ARMOR_PLATES_TAG, Tag.TAG_COMPOUND);
            tooltip.add(Component.literal("§6Installed Armor Plates: " + getInstalledPlatesCount(stack) + "/" + required_plates));

            if (flag.isAdvanced()) { // Show detailed info only in advanced tooltip (F3+H)
                for (int i = 0; i < platesList.size(); i++) {
                    CompoundTag plateTag = platesList.getCompound(i);
                    if (!plateTag.isEmpty()) {
                        tooltip.add(Component.literal("§7Plate " + (i + 1) + ":"));
                        tooltip.add(Component.literal("§8 Purity: " + plateTag.getInt("Purity_lvl")));
                        tooltip.add(Component.literal("§8 Experience: " + plateTag.getInt("UniqVar_lvl")));
                    }
                }
            }
        }
    }

    @Override
    public boolean canEquip(ItemStack stack, EquipmentSlot armorType, Entity entity) {
        return super.canEquip(stack, armorType, entity) && isCompleteArmor(stack);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);
        if (itemstack.getItem() instanceof PlateArmor && isCompleteArmor(itemstack)) {
            return this.swapWithEquipmentSlot(this, pLevel, pPlayer, pHand);
        }
        return InteractionResultHolder.fail(itemstack);
    }

    @Override
    public abstract ManaType getManaType();

    @Override
    public int getItemLevelCap() {
        return 100;
    }

    @Override
    public boolean isDamageable(ItemStack stack) {return false;}

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {

    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    @Override
    public abstract void initializeClient(Consumer<IClientItemExtensions> consumer);
}
