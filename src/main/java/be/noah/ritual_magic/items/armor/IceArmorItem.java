package be.noah.ritual_magic.items.armor;

import be.noah.ritual_magic.client.IceArmorRenderer;
import be.noah.ritual_magic.items.LeveldMagicArmor;
import be.noah.ritual_magic.mana.ManaNetworkData;
import be.noah.ritual_magic.mana.ManaType;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodData;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.FrostWalkerEnchantment;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.renderer.GeoArmorRenderer;
import software.bernie.geckolib.util.GeckoLibUtil;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

public class IceArmorItem extends ArmorItem implements GeoItem, LeveldMagicArmor {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private static final UUID ICE_SPEED_BOOST_UUID = UUID.fromString("11111111-2222-3333-4444-555555555555");
    private boolean NOMANA = true;

    public IceArmorItem(ArmorMaterial pMaterial, Type pType, Properties pProperties) {
        super(pMaterial, pType, pProperties);
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        LeveldMagicArmor.super.appendLevelTooltip(stack, tooltip);
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            private GeoArmorRenderer<?> renderer;

            @Override
            public @NotNull HumanoidModel<?> getHumanoidArmorModel(LivingEntity livingEntity, ItemStack itemStack, EquipmentSlot equipmentSlot, HumanoidModel<?> original) {
                if (this.renderer == null)
                    this.renderer = new IceArmorRenderer();

                // This prepares our GeoArmorRenderer for the current render frame.
                // These parameters may be null however, so we don't do anything further with them
                this.renderer.prepForRender(livingEntity, itemStack, equipmentSlot, original);

                return this.renderer;
            }
        });
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {}

    @Override
    public void inventoryTick(ItemStack pStack, Level pLevel, Entity pEntity, int pSlotId, boolean pIsSelected) {
        if (pEntity instanceof Player player) {
            if (!pLevel.isClientSide()) {

                // Boots give Frostwaker on Steroids
                if (this.hasBoots(player)) {
                    BlockPos pos = pEntity.blockPosition();
                    FrostWalkerEnchantment.onEntityMoved(player, pLevel, pos, bootLevel(player) / 10);
                }

                // Legings gives up to 100% movementSpeed boost
                if (this.hasLeggings(player)) {
                    AttributeInstance movementSpeed = player.getAttribute(Attributes.MOVEMENT_SPEED);

                    if (movementSpeed != null && movementSpeed.getModifier(ICE_SPEED_BOOST_UUID) == null) {
                        AttributeModifier speedModifier = new AttributeModifier(
                                ICE_SPEED_BOOST_UUID,
                                "IceArmorSpeedBoost",
                                (float)leggingsLevel(player)/100,
                                AttributeModifier.Operation.MULTIPLY_TOTAL
                        );
                        movementSpeed.addPermanentModifier(speedModifier);
                    }
                } else {
                    AttributeInstance movementSpeed = player.getAttribute(Attributes.MOVEMENT_SPEED);
                    if (movementSpeed != null && movementSpeed.getModifier(ICE_SPEED_BOOST_UUID) != null) {
                        movementSpeed.removeModifier(ICE_SPEED_BOOST_UUID);
                    }
                }
                // you get slower Hunger
                if(this.hasHelmet(player)) {
                    int level = helmetLevel(player);
                    float scaling = 0.0002f;
                    float totalReduction = scaling * level;

                    FoodData food = player.getFoodData();
                    float exhaustion = food.getExhaustionLevel();

                    if (exhaustion > 0f) {
                        food.setExhaustion(Math.max(0f, exhaustion - totalReduction));
                    }
                }

                // Fullset Effect = IceShield previous VoidShield (level depends on Chestplate)
                if(this.hasFullSet(player) && this.type == Type.CHESTPLATE){
                    CompoundTag persistentData = player.getPersistentData();
                    int timer = persistentData.getInt("ice_shield_timer");
                    persistentData.putInt("ice_shield_timer", timer + 1);
                    int chestplateLevel = chestPlateLevel(player);

                    if (timer >= 48000 / Math.max(chestplateLevel, 1)) { // 1200 ticks = 1 min
                        persistentData.putInt("ice_shield_timer", 0);

                        int shield = persistentData.getInt("void_shield");
                        if (shield < (chestplateLevel/10)) {
                            ServerLevel serverLevel = ((ServerPlayer) player).serverLevel();
                            ManaNetworkData data = ManaNetworkData.get(serverLevel.getServer());
                            if (NOMANA || data.consume(player.getUUID(), getManaType(), chestplateLevel * 100)){
                                persistentData.putInt("void_shield", shield + 1);
                            }
                        }
                    }
                }
            }
        }
        super.inventoryTick(pStack, pLevel, pEntity, pSlotId, pIsSelected);
    }

    @Override
    public boolean canWalkOnPowderedSnow(ItemStack stack, LivingEntity wearer){
        return true;
    }

    @Override
    public ManaType getManaType() {
        return ManaType.ATLANTIAN;
    }

    @Override
    public int getItemLevelCap() {
        return 100;
    }
}
