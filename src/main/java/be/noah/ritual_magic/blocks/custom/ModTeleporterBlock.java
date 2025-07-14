package be.noah.ritual_magic.blocks.custom;

import be.noah.ritual_magic.blocks.BlockTier;
import be.noah.ritual_magic.blocks.ModBlockEntities;
import be.noah.ritual_magic.blocks.RitualBaseBlock;
import be.noah.ritual_magic.blocks.entity.ModTeleporterEntity;
import be.noah.ritual_magic.worldgen.dimension.ModDimensions;
import be.noah.ritual_magic.worldgen.portal.ModTeleporter;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public class ModTeleporterBlock extends RitualBaseBlock<ModTeleporterEntity> {

    public ModTeleporterBlock(BlockTier tier, Properties pProperties) {
        super(tier, pProperties, ModBlockEntities.MOD_TELEPORTER);
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        BlockEntity blockentity = pLevel.getBlockEntity(pPos);
        if (blockentity instanceof ModTeleporterEntity) {
            if (pPlayer.canChangeDimensions() && ((ModTeleporterEntity) blockentity).structureIsOk()) {
                handleInfinityPortal(pPlayer, pPos);
                return InteractionResult.SUCCESS;
            } else {
                return InteractionResult.CONSUME;
            }
        } else {
            return InteractionResult.CONSUME;
        }
    }

    private void handleInfinityPortal(Entity player, BlockPos pPos) {
        if (player.level() instanceof ServerLevel serverlevel) {
            MinecraftServer minecraftserver = serverlevel.getServer();
            ResourceKey<Level> resourcekey = player.level().dimension() == ModDimensions.INFINITY_NEXUS_LEVEL_KEY ?
                    Level.OVERWORLD : ModDimensions.INFINITY_NEXUS_LEVEL_KEY;

            ServerLevel portalDimension = minecraftserver.getLevel(resourcekey);
            if (portalDimension != null && !player.isPassenger()) {
                if (resourcekey == ModDimensions.INFINITY_NEXUS_LEVEL_KEY) {
                    player.changeDimension(portalDimension, new ModTeleporter(pPos, true));
                } else {
                    player.changeDimension(portalDimension, new ModTeleporter(pPos, false));
                }
            }
        }
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new ModTeleporterEntity(pPos, pState);
    }
}
