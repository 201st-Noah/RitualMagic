package be.noah.ritual_magic.item.custom;

import be.noah.ritual_magic.entities.ThrownDwarvenAxe;
import be.noah.ritual_magic.Mana.ManaType;
import be.noah.ritual_magic.item.LeveldMagicItem;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

import javax.annotation.Nullable;
import java.util.*;

public class DwarvenAxe extends AxeItem implements LeveldMagicItem {

    private record LeafNode(BlockPos pos, int depth) {}

    public DwarvenAxe(Tier pTier, float pAttackDamageModifier, float pAttackSpeedModifier, Properties pProperties) {
        super(pTier, pAttackDamageModifier, pAttackSpeedModifier, pProperties);
    }
    @Override
    public int getDamage(ItemStack stack) {
        return 0;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);
        if (itemstack.getDamageValue() >= itemstack.getMaxDamage() - 1) {
            return InteractionResultHolder.fail(itemstack);
        } else if (EnchantmentHelper.getRiptide(itemstack) > 0 && !pPlayer.isInWaterOrRain()) {
            return InteractionResultHolder.fail(itemstack);
        } else {
            pPlayer.startUsingItem(pHand);
            return InteractionResultHolder.consume(itemstack);
        }
    }

    @Override
    public void releaseUsing(ItemStack pStack, Level pLevel, LivingEntity pLivingEntity, int pTimeCharged) {
        if (pLivingEntity instanceof Player player) {
            int i = this.getUseDuration(pStack) - pTimeCharged;
            if (i >= 10) {
                ThrownDwarvenAxe thrownDwarvenAxe = new ThrownDwarvenAxe(pLevel, player, pStack);
                thrownDwarvenAxe.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 2.5F, 0F);
                if (player.getAbilities().instabuild) {
                    thrownDwarvenAxe.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
                }
                pLevel.addFreshEntity(thrownDwarvenAxe);
                pLevel.playSound((Player)null, thrownDwarvenAxe, SoundEvents.ANVIL_FALL, SoundSource.PLAYERS, 1.0F, 1.0F);
                if (!player.getAbilities().instabuild) {
                    player.getInventory().removeItem(pStack);
                }
            }
        }
    }

    public static List<BlockPos> getLogsToBeDestroyed(int maxLogs, BlockPos initialBlockPos, ServerPlayer player) {
        ServerLevel level = player.serverLevel();
        Set<BlockPos> visitedLogs = new HashSet<>();
        Queue<BlockPos> toVisit = new LinkedList<>();
        toVisit.add(initialBlockPos);
        visitedLogs.add(initialBlockPos);

        // 1. Collect logs
        while (!toVisit.isEmpty() && visitedLogs.size() < maxLogs) {
            BlockPos current = toVisit.poll();
            if (!level.getBlockState(current).is(BlockTags.LOGS)) continue;

            for (int dx = -1; dx <= 1; dx++) {
                for (int dy = -1; dy <= 1; dy++) {
                    for (int dz = -1; dz <= 1; dz++) {

                        if (dx == 0 && dy == 0 && dz == 0) continue;
                        BlockPos neighbor = current.offset(dx, dy, dz);
                        if (!visitedLogs.contains(neighbor)
                                && level.isLoaded(neighbor)
                                && level.getBlockState(neighbor).is(BlockTags.LOGS)) {
                            toVisit.add(neighbor);
                            visitedLogs.add(neighbor);
                        }
                    }
                }
            }
        }

        List<BlockPos> result = new ArrayList<>(visitedLogs);
        return result;
    }

    //not Working Correctly Have fun Luka
    public static List<BlockPos> getLeavesToBeDestroyed(List<BlockPos> destroyedLogs, ServerPlayer player){
        ServerLevel level = player.serverLevel();
        Set<BlockPos> result = new HashSet<>();
        Set<BlockPos> visited = new HashSet<>();
        Queue<LeafNode> queue = new ArrayDeque<>();

        // Start from non-persistent leaves adjacent to logs
        for (BlockPos logPos : destroyedLogs) {
            for (Direction dir : Direction.values()) {
                BlockPos neighbor = logPos.relative(dir);
                BlockState state = level.getBlockState(neighbor);

                if (state.is(BlockTags.LEAVES) &&
                        !state.getOptionalValue(BlockStateProperties.PERSISTENT).orElse(false)) {
                    queue.add(new LeafNode(neighbor.immutable(), 0));
                    visited.add(neighbor.immutable());
                    result.add(neighbor.immutable());
                }
            }
        }
        while (!queue.isEmpty()) {
            LeafNode node = queue.poll();
            BlockPos current = node.pos();
            int depth = node.depth();

            if (depth >= 4) continue;

            for (Direction dir : Direction.values()) {
                BlockPos neighbor = current.relative(dir);
                if (visited.contains(neighbor)) continue;

                BlockState state = level.getBlockState(neighbor);
                if (state.is(BlockTags.LEAVES) &&
                        !state.getOptionalValue(BlockStateProperties.PERSISTENT).orElse(false)) {
                    visited.add(neighbor.immutable());
                    result.add(neighbor.immutable());
                    queue.add(new LeafNode(neighbor.immutable(), depth + 1));
                }
            }
        }

        return new ArrayList<>(result);
    }

    public int getUseDuration(ItemStack pStack) {
        return 72000;
    }
    
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        LeveldMagicItem.super.appendLevelTooltip(stack, tooltip);
    }

    @Override
    public ManaType getManaType() {
        return ManaType.DWARVEN;
    }

    @Override
    public int getItemLevelCap() {
        return 16;
    }
}
