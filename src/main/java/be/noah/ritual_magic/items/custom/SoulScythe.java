package be.noah.ritual_magic.items.custom;

import be.noah.ritual_magic.items.LeveldMagicItem;
import be.noah.ritual_magic.mana.ManaNetworkData;
import be.noah.ritual_magic.mana.ManaType;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.ForgeMod;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

public class SoulScythe extends HoeItem implements LeveldMagicItem {

    public SoulScythe(Tier pTier, int pAttackDamageModifier, float pAttackSpeedModifier, Properties pProperties) {
        super(pTier, pAttackDamageModifier, pAttackSpeedModifier, pProperties);
    }

    @Override
    public ManaType getManaType() {
        return ManaType.HELLISH;
    }

    @Override
    public int getItemLevelCap() {
        return 100;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        LeveldMagicItem.super.appendLevelTooltip(stack, tooltip);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();

        if (slot == EquipmentSlot.MAINHAND) {
            int level = 0;
            if (stack.getItem() instanceof LeveldMagicItem magicItem) {
                level = magicItem.getItemLevel(stack);
            }

            double baseReach = 0D;
            double baseDamage = 7D;
            double dynamicReach = baseReach + (level * 0.1);
            double dynamicDamage = baseDamage + (level * 0.5);

            builder.put(Attributes.ATTACK_DAMAGE,
                    new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Weapon modifier", dynamicDamage, AttributeModifier.Operation.ADDITION));

            builder.put(ForgeMod.ENTITY_REACH.get(),
                    new AttributeModifier(UUID.nameUUIDFromBytes("dynamic_reach".getBytes()),
                            "Dynamic reach modifier", dynamicReach, AttributeModifier.Operation.ADDITION));
        }

        return builder.build();
    }

    @Override
    public boolean hurtEnemy(@NotNull ItemStack pStack, @NotNull LivingEntity pTarget, @NotNull LivingEntity pAttacker) {
        boolean didDamage = super.hurtEnemy(pStack, pTarget, pAttacker);

        if (!pTarget.level().isClientSide && pAttacker instanceof ServerPlayer player && pTarget.isDeadOrDying()) {
            ServerLevel serverLevel = player.serverLevel();
            ManaNetworkData data = ManaNetworkData.get(serverLevel.getServer());
            int extraMana = 0;
            if (this.getItemLevel(pStack) == 0){extraMana = 1;}
            data.add(player.getUUID(), getManaType(), ((int) pTarget.getMaxHealth()/2 * this.getItemLevel(pStack)) + extraMana);
            spawnSoulParticles(pTarget);
        }
        return didDamage;
    }

    private void spawnSoulParticles(LivingEntity entity) {
        if (entity.level() instanceof ServerLevel serverLevel) {
            for (int i = 0; i < 10; i++) {
                double offsetX = (entity.getRandom().nextDouble() - 0.5) * 0.5;
                double offsetY = entity.getRandom().nextDouble() * 0.5 + 0.1;
                double offsetZ = (entity.getRandom().nextDouble() - 0.5) * 0.5;

                serverLevel.sendParticles(
                        ParticleTypes.SOUL,
                        entity.getX() + offsetX,
                        entity.getY() + offsetY,
                        entity.getZ() + offsetZ,
                        1,
                        0, 0, 0, 0.01
                );
            }
        }
    }
}
