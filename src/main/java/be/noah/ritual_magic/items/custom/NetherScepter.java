package be.noah.ritual_magic.items.custom;

import be.noah.ritual_magic.effects.ModEffects;
import be.noah.ritual_magic.items.LeveldMagicItem;
import be.noah.ritual_magic.mana.ManaNetworkData;
import be.noah.ritual_magic.mana.ManaType;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;
import java.util.List;

public class NetherScepter extends Item implements LeveldMagicItem {

    private static final int COOLDOWN = 3;

    public NetherScepter(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        LeveldMagicItem.super.appendLevelTooltip(stack, tooltip); // call default method
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);

        if (!level.isClientSide) {
            int mode = getItemMode(itemstack);
            if (player.isShiftKeyDown()) {
                mode = (mode + 1) % 6; //Math.min(this.getItemLevel(itemstack) ,3) //for getting abilities with leveling
                setItemMode(itemstack, mode);
                switch (mode) {
                    case 0:
                        player.displayClientMessage(Component.translatable("ritual_magic.item.nether_scepter.mode.0"), true);
                        break;
                    case 1:
                        player.displayClientMessage(Component.translatable("ritual_magic.item.nether_scepter.mode.1"), true);
                        break;
                    case 2:
                        player.displayClientMessage(Component.translatable("ritual_magic.item.nether_scepter.mode.2"), true);
                        break;
                    case 3:
                        player.displayClientMessage(Component.translatable("ritual_magic.item.nether_scepter.mode.3"), true);
                        break;
                    case 4:
                        player.displayClientMessage(Component.translatable("ritual_magic.item.nether_scepter.mode.4"), true);
                        break;
                    case 5:
                        player.displayClientMessage(Component.translatable("ritual_magic.item.nether_scepter.mode.5"), true);
                        break;
                }
                return InteractionResultHolder.success(itemstack);
            } else {
                switch (mode) {
                    case 0:
                        Lavafield((ServerLevel) level, player.getOnPos(), this.getItemLevel(itemstack) * 2);
                        level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.FIRECHARGE_USE, SoundSource.PLAYERS, 1.0F, 1.0F);
                        break;
                    case 1:
                        player.addEffect(new MobEffectInstance(ModEffects.FIREAURA.get(), 800, this.getItemLevel(itemstack) * 3, false, false, false));
                        level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.FIRECHARGE_USE, SoundSource.PLAYERS, 1.0F, 1.0F);
                        break;
                    case 2:
                        this.addItemLevel(itemstack, 1);
                        level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.AMETHYST_BLOCK_HIT, SoundSource.PLAYERS, 1.0F, 1.0F);
                        break;
                    case 3:
                        if (player instanceof ServerPlayer serverPlayer) {
                            ServerLevel serverLevel = serverPlayer.serverLevel();
                            ManaNetworkData data = ManaNetworkData.get(serverLevel.getServer());
                            if (data.consume(player.getUUID(), this.getManaType(), 20)) {
                                // cast spell or attack
                                level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.EXPERIENCE_ORB_PICKUP, SoundSource.PLAYERS, 1.0F, 1.0F);
                            } else {
                                player.displayClientMessage(Component.literal("Not enough HELLISH mana!"), true);
                            }
                        }
                        break;
                    case 4:
                        if (player instanceof ServerPlayer serverPlayer) {
                            ServerLevel serverLevel = serverPlayer.serverLevel();
                            ManaNetworkData data = ManaNetworkData.get(serverLevel.getServer());
                            data.add(player.getUUID(), ManaType.HELLISH, 30);
                            // player.displayClientMessage(Component.literal("Not enough Arcane mana!" + data.getOrCreate(player.getUUID())), true);
                        }
                        level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.SNOW_PLACE, SoundSource.PLAYERS, 1.0F, 1.0F);
                        break;
                    case 5:
                        if (player instanceof ServerPlayer serverPlayer) {
                            ServerLevel serverLevel = serverPlayer.serverLevel();
                            ManaNetworkData data = ManaNetworkData.get(serverLevel.getServer());
                            data.setMax(player.getUUID(), ManaType.HELLISH, 300);
                        }
                        level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.EXPERIENCE_ORB_PICKUP, SoundSource.PLAYERS, 1.0F, 1.0F);
                        break;
                }
                player.getCooldowns().addCooldown(this, COOLDOWN);
                return InteractionResultHolder.success(itemstack);
            }
        }
        return InteractionResultHolder.pass(itemstack);

    }

    private void Lavafield(ServerLevel level, BlockPos center, int radius) {
        for (int dx = -radius; dx <= radius; dx++) {
            for (int dz = -radius; dz <= radius; dz++) {

                if (dx * dx + dz * dz > radius * radius) continue;
                int x = center.getX() + dx;
                int z = center.getZ() + dz;

                for (int dy = center.getY() + 5; dy >= center.getY() - 10; dy--) {
                    BlockPos floorPos = new BlockPos(x, dy, z);
                    BlockPos abovePos = floorPos.above();

                    BlockState floorState = level.getBlockState(floorPos);
                    BlockState aboveState = level.getBlockState(abovePos);

                    if (floorState.isSolidRender(level, floorPos) && aboveState.isAir()) {
                        level.setBlock(abovePos, Blocks.FIRE.defaultBlockState(), 3);
                        break;
                    }
                }
            }
        }
    }

    @Override
    public ManaType getManaType() {
        return ManaType.HELLISH;
    }

    @Override
    public int getItemLevelCap() {
        return 16;
    }
}
