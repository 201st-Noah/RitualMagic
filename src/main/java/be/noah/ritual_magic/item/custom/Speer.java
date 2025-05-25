package be.noah.ritual_magic.item.custom;

import be.noah.ritual_magic.entities.BallLightning;
import be.noah.ritual_magic.entities.ModEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class Speer extends SwordItem {

    private final int COOLDOWN = 8;
    private int mode = 0;

    public Speer(Tier pTier, int pAttackDamageModifier, float pAttackSpeedModifier, Properties pProperties) {
        super(pTier, pAttackDamageModifier, pAttackSpeedModifier, pProperties);
    }

    @Override
    public int getMaxStackSize(ItemStack stack) {
        return 1;
    }

    @Override
    public int getDamage(ItemStack stack) {
        return 0;
    }

    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand){

        ItemStack itemstack = player.getItemInHand(hand);
        if (!level.isClientSide) {
            if(player.isShiftKeyDown()){
                mode = (mode + 1) % 3;
                switch (mode) {
                    case 0:
                        player.displayClientMessage(Component.translatable("ritual_magic.item.speer.mode.0"), true);
                        break;
                    case 1:
                        player.displayClientMessage(Component.translatable("ritual_magic.item.speer.mode.1"), true);
                        break;
                    case 2:
                        player.displayClientMessage(Component.translatable("ritual_magic.item.speer.mode.2"), true);
                        break;
                }

            }else {
                switch (mode) {
                    case 1:
                        BallLightning ballLightning = new BallLightning(ModEntities.BALL_LIGHTNING.get(), player, level);
                        ballLightning.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 5.0F, 0F);
                        level.addFreshEntity(ballLightning);
                        break;
                    case 0:
                        travel(level, player, 80);
                        break;
                    case 2:

                        break;
                }
                player.getCooldowns().addCooldown(this, COOLDOWN);
            }
        }
        return InteractionResultHolder.sidedSuccess(itemstack, level.isClientSide());
    }

    private void travel (Level level, Player player, int range){
        Vec3 dest = getDestiation(level, player, range);
        Vec3 playerPos = new Vec3(player.getX(), player.getY(), player.getZ());
        if (playerPos.distanceTo(dest) > 2) {
            player.teleportTo(dest.x, dest.y, dest.z);
        }else{
            dest = getNextAirBlockBehindHit(level, player, range/2);
            player.teleportTo(dest.x, dest.y, dest.z);
        }
        level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.CHORUS_FRUIT_TELEPORT, SoundSource.PLAYERS, 1.0F, 1.0F);
    }

    private Vec3 getDestiation(Level level, Player player, int range){
        Vec3 eyePos = player.getEyePosition();
        Vec3 lookVec = player.getViewVector(1.0F);
        Vec3 targetVec = eyePos.add(lookVec.scale(range));

        HitResult hit = level.clip(new ClipContext(
                eyePos,
                targetVec,
                ClipContext.Block.COLLIDER,
                ClipContext.Fluid.NONE,
                player));

        if (hit.getType() == HitResult.Type.BLOCK) {
            BlockHitResult blockHit = (BlockHitResult) hit;
            BlockPos blockPos = blockHit.getBlockPos();
            Direction hitFace = blockHit.getDirection();

            BlockPos destination;
            switch (hitFace) {
                case UP -> destination = blockPos.above();       // teleport on top
                case DOWN -> destination = blockPos.below();     // teleport below
                default -> destination = blockPos.relative(hitFace); // side hit
            }

            return new Vec3(destination.getX() + 0.5, destination.getY(), destination.getZ() + 0.5);
        }else{
            return targetVec;
        }
    }

    private Vec3 getNextAirBlockBehindHit(Level level, Player player, int maxRange) {
        Vec3 eyePos = player.getEyePosition();
        Vec3 lookVec = player.getViewVector(1.0F);
        Vec3 targetVec = eyePos.add(lookVec.scale(maxRange));

        BlockHitResult hit = level.clip(new ClipContext(
                eyePos,
                targetVec,
                ClipContext.Block.COLLIDER,
                ClipContext.Fluid.NONE,
                player
        ));

        if (hit.getType() == HitResult.Type.BLOCK) {
            // Start just behind the hit block (in the direction of the ray)
            Vec3 direction = lookVec.normalize().scale(0.5); // small step size
            Vec3 checkPos = Vec3.atCenterOf(hit.getBlockPos()).add(direction);

            for (int i = 0; i < maxRange * 2; i++) {
                BlockPos blockPos = BlockPos.containing(checkPos);
                if (level.getBlockState(blockPos).isAir()) {
                    return new Vec3(blockPos.getX() + 0.5, blockPos.getY() -0.62, blockPos.getZ() + 0.5);
                }
                checkPos = checkPos.add(direction); // continue stepping forward
            }
        }else{
            // Fallback: return max-range end point
            return targetVec;
        }
        return new Vec3(player.getX(), player.getY(), player.getZ());
    }
        /*
         Use Mode Ideas:
         -----------------
         Void Shield
         Laser Beam
         Staff Of Traveling |Check but not perfect
         Summoning Energy Dragon
         No escape Zone: Disables Teleportation in an Area
         Throwing it with 0 Gravity
         Reset Y Movement => cancels Fall damage

         */
}
