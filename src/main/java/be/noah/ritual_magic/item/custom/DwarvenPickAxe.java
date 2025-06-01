package be.noah.ritual_magic.item.custom;

import be.noah.ritual_magic.Mana.ManaNetworkData;
import be.noah.ritual_magic.Mana.ManaType;
import be.noah.ritual_magic.item.LeveldMagicItem;
import be.noah.ritual_magic.networking.ModMessages;
import be.noah.ritual_magic.networking.packet.BlockHighlightS2CPacket;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

import java.util.ArrayList;
import java.util.List;

public class DwarvenPickAxe extends PickaxeItem implements LeveldMagicItem {

    private static final int COOLDOWN = 8;
    private int mode = 0;
    private int digAOE = 0;

    public DwarvenPickAxe(Tier pTier, int pAttackDamageModifier, float pAttackSpeedModifier, Properties pProperties) {
        super(pTier, pAttackDamageModifier, pAttackSpeedModifier, pProperties);
    }

    public int getDigAoe() {
        return digAOE;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);

        if (!level.isClientSide) {
            if (player.isShiftKeyDown()) {
                mode = (mode + 1) % 3;
                switch (mode) {
                    case 0:
                        player.displayClientMessage(Component.translatable("ritual_magic.item.dwarven_pickaxe.mode.0"), true);
                        break;
                    case 1:
                        player.displayClientMessage(Component.translatable("ritual_magic.item.dwarven_pickaxe.mode.1"), true);
                        break;
                    case 2:
                        player.displayClientMessage(Component.translatable("ritual_magic.item.dwarven_pickaxe.mode.2"), true);
                        break;
                    case 3:
                        player.displayClientMessage(Component.translatable("ritual_magic.item.dwarven_pickaxe.mode.3"), true);
                        break;
                }
                return InteractionResultHolder.success(itemstack);
            } else {
                    switch (mode) {
                        case 0:
                            digAOE = (digAOE + 1) % 5;
                            int holeSize = (digAOE * 2) + 1;
                            player.displayClientMessage(Component.translatable("ritual_magic.item.dwarven_pickaxe.aoe").append(holeSize + "x" + holeSize), true);
                            level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.EXPERIENCE_ORB_PICKUP, SoundSource.PLAYERS, 1.0F, 1.0F);
                            break;
                        case 1:
                            break;
                        case 2:
                            if (player instanceof ServerPlayer serverPlayer) {
                                ServerLevel serverLevel = serverPlayer.serverLevel();
                                ManaNetworkData data = ManaNetworkData.get(serverLevel);
                                data.add(player.getUUID(), ManaType.DWARVEN, 20);
                                level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.EXPERIENCE_ORB_PICKUP, SoundSource.PLAYERS, 1.0F, 1.0F);
                            }
                            break;
                        case 3:

                            break;
                    }
                    player.getCooldowns().addCooldown(this, COOLDOWN);
                    return InteractionResultHolder.success(itemstack);
            }
        }
        return InteractionResultHolder.pass(itemstack);
    }

    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        Player player = pContext.getPlayer();
        Level level = pContext.getLevel();
        BlockPos clicked = pContext.getClickedPos();

        if (level.isClientSide) return InteractionResult.SUCCESS; // skip client side

        BlockState clickedState = level.getBlockState(clicked);

        // Search for all nearby same blocks (adjust radius as needed)
        int radius = 50;
        List<BlockPos> matching = new ArrayList<>();

        Block blockToMatch = clickedState.getBlock();
        BlockPos.betweenClosedStream(clicked.offset(-radius, -radius, -radius), clicked.offset(radius, radius, radius))
                .forEach(pos -> {
                    if (level.getBlockState(pos).is(blockToMatch)) {
                        matching.add(pos.immutable());
                    }
                });

        // Send positions to the player who used the item
        ModMessages.sendToPlayer(new BlockHighlightS2CPacket(matching), (ServerPlayer) player);
        return InteractionResult.SUCCESS;
    }

    //stolen from KaupenJoe and then a bit modified
    public static List<BlockPos> getBlocksToBeDestroyed(int range, BlockPos initalBlockPos, ServerPlayer player) {
        List<BlockPos> positions = new ArrayList<>();
        int offset = 0;
        if(range == 0){offset = 1;}

        BlockHitResult traceResult = player.level().clip(new ClipContext(player.getEyePosition(1f),
                (player.getEyePosition(1f).add(player.getViewVector(1f).scale(6f))),
                ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, player));
        if(traceResult.getType() == HitResult.Type.MISS) {
            return positions;
        }

        if (traceResult.getDirection() == Direction.DOWN || traceResult.getDirection() == Direction.UP) {
            for (int x = -range; x <= range; x++) {
                for (int y = -range; y <= range; y++) {
                    positions.add(new BlockPos(initalBlockPos.getX() + x, initalBlockPos.getY(), initalBlockPos.getZ() + y));
                }
            }
        }

        if(traceResult.getDirection() == Direction.NORTH || traceResult.getDirection() == Direction.SOUTH) {
            for(int x = -range; x <= range; x++) {
                for(int y = -range; y <= range; y++) {
                    positions.add(new BlockPos(initalBlockPos.getX() + x, initalBlockPos.getY() + y + range -1 + offset, initalBlockPos.getZ()));
                }
            }
        }

        if(traceResult.getDirection() == Direction.EAST || traceResult.getDirection() == Direction.WEST) {
            for(int x = -range; x <= range; x++) {
                for(int y = -range; y <= range; y++) {
                    positions.add(new BlockPos(initalBlockPos.getX(), initalBlockPos.getY() + y + range -1 + offset, initalBlockPos.getZ() + x));
                }
            }
        }
        return positions;
    }


    @Override
    public ManaType getManaType() {
        return null;
    }

    @Override
    public int getItemLevelCap() {
        return 0;
    }
}
