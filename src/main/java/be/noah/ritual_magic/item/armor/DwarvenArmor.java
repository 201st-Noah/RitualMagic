package be.noah.ritual_magic.item.armor;

import com.google.common.collect.ImmutableMap;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;

public class DwarvenArmor extends ArmorItem {
    private static final Map<ArmorMaterial, MobEffectInstance> MATERIAL_TO_EFFECT_MAP =
            (new ImmutableMap.Builder<ArmorMaterial, MobEffectInstance>())
                    .put(ModArmorMaterials.DWARVEN_STEEL, new MobEffectInstance(MobEffects.HEALTH_BOOST, 1000000000, 2,
                            false,false, true)).build();
    //Reduces and removes the negativ effects like slowness, not able to swim, falldamage = x2
    private static final String OPTIMISING_COUNT = "OptimisingCount";
    //makes armor stronger, less damage, absorption hearts
    private static final String PURITY = "Purity";
    private static final String MAGIC_CAPACITY = "MagicCapacity";
    //gives strength, speed, regeneration,(maby also saturation)
    private static final String MAGIC_LV = "MagicLv";

    public DwarvenArmor(ArmorMaterial pMaterial, Type pType, Properties pProperties) {
        super(pMaterial, pType, pProperties);
    }

    @Override
    public void inventoryTick(ItemStack pStack, Level pLevel, Entity pEntity, int pSlotId, boolean pIsSelected) {
        if(pEntity instanceof Player){
            if (!pLevel.isClientSide()) {
                if (hasFullSuitOfArmorOn((Player) pEntity)) {
                    evaluateArmorEffects((Player) pEntity);
                }
            }
        }
        super.inventoryTick(pStack, pLevel, pEntity, pSlotId, pIsSelected);
    }

    private void evaluateArmorEffects(Player player) {
        for (Map.Entry<ArmorMaterial, MobEffectInstance> entry : MATERIAL_TO_EFFECT_MAP.entrySet()) {
            ArmorMaterial mapArmorMaterial = entry.getKey();
            MobEffectInstance mapStatusEffect = entry.getValue();

            if(hasCorrectArmorOn(mapArmorMaterial, player)) {
                addStatusEffectForMaterial(player, mapArmorMaterial, mapStatusEffect);
            }
        }
    }
    private void addStatusEffectForMaterial(Player player, ArmorMaterial mapArmorMaterial,
                                            MobEffectInstance mapStatusEffect) {
        boolean hasPlayerEffect = player.hasEffect(mapStatusEffect.getEffect());

        if(hasCorrectArmorOn(mapArmorMaterial, player) && !hasPlayerEffect) {
            player.addEffect(new MobEffectInstance(mapStatusEffect));
        }
    }
    private boolean hasFullSuitOfArmorOn(Player player) {
        ItemStack boots = player.getInventory().getArmor(0);
        ItemStack leggings = player.getInventory().getArmor(1);
        ItemStack breastplate = player.getInventory().getArmor(2);
        ItemStack helmet = player.getInventory().getArmor(3);

        return !helmet.isEmpty() && !breastplate.isEmpty()
                && !leggings.isEmpty() && !boots.isEmpty();
    }
    private boolean hasCorrectArmorOn(ArmorMaterial material, Player player) {
        for (ItemStack armorStack : player.getInventory().armor) {
            if(!(armorStack.getItem() instanceof ArmorItem)) {
                return false;
            }
        }
        ArmorItem boots = ((ArmorItem)player.getInventory().getArmor(0).getItem());
        ArmorItem leggings = ((ArmorItem)player.getInventory().getArmor(1).getItem());
        ArmorItem breastplate = ((ArmorItem)player.getInventory().getArmor(2).getItem());
        ArmorItem helmet = ((ArmorItem)player.getInventory().getArmor(3).getItem());

        return helmet.getMaterial() == material && breastplate.getMaterial() == material &&
                leggings.getMaterial() == material && boots.getMaterial() == material;
    }
    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Component.literal("Purity: " + getPurity(pStack)));
        pTooltipComponents.add(Component.literal("Magic Capacity: " + getMagicCapacity(pStack)));
        pTooltipComponents.add(Component.literal("Optimisation: " + getOptimisingCount(pStack)));
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
    @Override
    public boolean isDamageable(ItemStack stack) {
        return false;
    }
    public static void setPurity(ItemStack stack, int value) {
        stack.getOrCreateTag().putInt(PURITY, value);
    }
    public static int getPurity(ItemStack stack) {
        return stack.hasTag() ? stack.getTag().getInt(PURITY) : 0;
    }
    public void setMagicCapacity(ItemStack stack, int value) {
        stack.getOrCreateTag().putInt(MAGIC_CAPACITY, value);
    }
    public int getMagicCapacity(ItemStack stack) {
        return stack.hasTag() ? stack.getTag().getInt(MAGIC_CAPACITY) : 0;
    }
    public void setOptimisingCount(ItemStack stack, int value) {stack.getOrCreateTag().putInt(OPTIMISING_COUNT, value);}
    public int getOptimisingCount(ItemStack stack) {return stack.hasTag() ? stack.getTag().getInt(OPTIMISING_COUNT) : 0;}
}
