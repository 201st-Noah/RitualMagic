package be.noah.ritual_magic.items.custom;

import be.noah.ritual_magic.blocks.ModBlocks;
import be.noah.ritual_magic.items.LeveldMagicItem;
import be.noah.ritual_magic.mana.ManaType;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
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
            int extraMana = 0;
            if (this.getItemLevel(pStack) == 0){extraMana = 1;}
            addMana(player, ((int) pTarget.getMaxHealth()/2 * this.getItemLevel(pStack)) + extraMana);
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

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        ItemStack stack = context.getItemInHand();

        int radius = getItemAoe(stack);
        boolean changed = false;
        if (!level.isClientSide && context.getPlayer() != null) {
            for (int dx = -radius; dx <= radius; dx++) {
                for (int dz = -radius; dz <= radius; dz++) {
                    BlockPos targetPos = pos.offset(dx, 0, dz);
                    Block block = level.getBlockState(targetPos).getBlock();
                    int itemLevel = getItemLevel(stack);
                    if (block == Blocks.SOUL_SOIL && ifAirAbove(targetPos, level) && consumeMana(context.getPlayer(),Math.max(1, itemLevel * 10))) {
                        if (itemLevel >= 90){
                            level.setBlock(targetPos, ModBlocks.U_SOUL_FARMLAND.get().defaultBlockState(), 3);
                        } else if (itemLevel >= 65) {
                            level.setBlock(targetPos, ModBlocks.A_SOUL_FARMLAND.get().defaultBlockState(), 3);
                        }else if (itemLevel >= 30) {
                            level.setBlock(targetPos, ModBlocks.I_SOUL_FARMLAND.get().defaultBlockState(), 3);
                        }else {
                            level.setBlock(targetPos, ModBlocks.B_SOUL_FARMLAND.get().defaultBlockState(), 3);
                        }

                        level.playSound(null, targetPos, SoundEvents.SOUL_SAND_PLACE, SoundSource.BLOCKS, 1.0F, 1.0F);
                        changed = true;
                    } else if ((block == Blocks.GRASS_BLOCK || block == Blocks.DIRT) && ifAirAbove(targetPos, level)&& consumeMana(context.getPlayer(), 1)) {
                        level.setBlock(targetPos, Blocks.FARMLAND.defaultBlockState(), 3);
                        level.playSound(null, targetPos, SoundEvents.HOE_TILL, SoundSource.BLOCKS, 1.0F, 1.0F);
                        changed = true;
                    }
                }
            }
        }
        if (changed && !level.isClientSide) {
            stack.hurtAndBreak(1, context.getPlayer(), (player) -> player.broadcastBreakEvent(context.getHand()));
        }
        return changed ? InteractionResult.SUCCESS : InteractionResult.PASS;
    }

    private boolean ifAirAbove(BlockPos targetPos, Level level) {
        return level.getBlockState(targetPos.above()).isAir();
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (!level.isClientSide && player.isShiftKeyDown()) {
            int aoe = getItemAoe(stack);
            aoe = (aoe + 1) % lvlLinear(stack, 10);
            setItemAoe(stack, aoe);
            int hoeAoe = (aoe * 2) + 1;
            player.displayClientMessage(Component.translatable("ritual_magic.item.soul_scythe.aoe").append(hoeAoe + "x" + hoeAoe), true);
            level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.EXPERIENCE_ORB_PICKUP, SoundSource.PLAYERS, 1.0F, 1.0F);
            return InteractionResultHolder.success(stack);
        }
        return InteractionResultHolder.pass(stack);
    }
}
