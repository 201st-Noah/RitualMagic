package be.noah.ritual_magic.item.custom;

import be.noah.ritual_magic.effect.ModEffects;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class NetherScepter extends Item {

    private static final int COOLDOWN = 30;
    private int mode = 0;

    public NetherScepter(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);

        if (!level.isClientSide) {
            if (player.isShiftKeyDown()) {
                mode = (mode + 1) % 2;
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
                }
                return InteractionResultHolder.success(itemstack);
            } else {
                switch (mode) {
                    case 0:
                        Lavafield((ServerLevel) level, player.getOnPos(), 6);
                        level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.BUCKET_EMPTY_LAVA, SoundSource.PLAYERS, 1.0F, 1.0F);
                        break;
                    case 1:
                        player.addEffect(new MobEffectInstance(ModEffects.FIREAURA.get(), 800, 10, false, false, false));
                        level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.FIRECHARGE_USE, SoundSource.PLAYERS, 1.0F, 1.0F);
                        break;
                    case 2:
                        level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.AMETHYST_BLOCK_HIT, SoundSource.PLAYERS, 1.0F, 1.0F);
                        break;
                    case 3:
                        level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.SNOW_PLACE, SoundSource.PLAYERS, 1.0F, 1.0F);
                        break;
                    }
                    player.getCooldowns().addCooldown(this, COOLDOWN);
                    return InteractionResultHolder.success(itemstack);
            }
        }
        return InteractionResultHolder.pass(itemstack);

    }

    private void Lavafield(ServerLevel level, BlockPos center, int radius){
        for (int dx = -radius; dx <= radius; dx++) {
            for (int dz = -radius; dz <= radius; dz++) {

                if (dx * dx + dz * dz > radius * radius) continue;
                int x = center.getX() + dx;
                int z = center.getZ() + dz;

                for (int dy = center.getY() + 5; dy >= center.getY() -10 ; dy--) {
                    BlockPos floorPos = new BlockPos(x, dy, z);
                    BlockPos abovePos = floorPos.above();

                    BlockState floorState = level.getBlockState(floorPos);
                    BlockState aboveState = level.getBlockState(abovePos);

                    if (floorState.isSolidRender(level, floorPos) && aboveState.isAir()) {
                        level.setBlock(floorPos, Blocks.LAVA.defaultBlockState(), 3);
                        break;
                    }
                }
            }
        }
    }

}
