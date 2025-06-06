package be.noah.ritual_magic.items.custom;

import be.noah.ritual_magic.entities.BallLightning;
import be.noah.ritual_magic.entities.ModEntities;
import be.noah.ritual_magic.items.LeveldMagicItem;
import be.noah.ritual_magic.mana.ManaType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.*;

import java.util.List;
import java.util.Optional;

public class Speer extends SwordItem implements LeveldMagicItem {

    private static final String VOID_SHIELD_TAG = "void_shield";
    private final int COOLDOWN = 8;
    private final int MAX_SHIELD_HITS = 8;
    private int mode = 0;

    public Speer(Tier pTier, int pAttackDamageModifier, float pAttackSpeedModifier, Properties pProperties) {
        super(pTier, pAttackDamageModifier, pAttackSpeedModifier, pProperties);
    }

    public static EntityHitResult getEntityLookingAt(Player player, double range) {
        Vec3 eyePos = player.getEyePosition();
        Vec3 lookVec = player.getLookAngle();
        Vec3 reachVec = eyePos.add(lookVec.scale(range));

        AABB aabb = player.getBoundingBox()
                .expandTowards(lookVec.scale(range))
                .inflate(1.0D, 1.0D, 1.0D);

        // Find entities along the path
        List<Entity> entities = player.level().getEntities(player, aabb, e -> e.isPickable() && e != player);

        EntityHitResult nearestHit = null;
        double nearestDistance = range;

        for (Entity entity : entities) {
            AABB entityBox = entity.getBoundingBox().inflate(0.1D); // Slightly bigger hitbox
            Optional<Vec3> hit = entityBox.clip(eyePos, reachVec);

            if (hit.isPresent()) {
                double dist = eyePos.distanceTo(hit.get());
                if (dist < nearestDistance) {
                    nearestDistance = dist;
                    nearestHit = new EntityHitResult(entity);
                }
            }
        }

        return nearestHit;
    }

    @Override
    public int getMaxStackSize(ItemStack stack) {
        return 1;
    }

    @Override
    public int getDamage(ItemStack stack) {
        return 0;
    }

    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {

        ItemStack itemstack = player.getItemInHand(hand);
        if (!level.isClientSide) {
            if (player.isShiftKeyDown()) {
                mode = (mode + 1) % 4;
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
                    case 3:
                        player.displayClientMessage(Component.translatable("ritual_magic.item.speer.mode.3"), true);
                        break;
                }

            } else {
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
                        avtivateVoidShield(level, player);
                        break;
                    case 3:
                        player.startUsingItem(hand);
                        break;
                }
                player.getCooldowns().addCooldown(this, COOLDOWN);
            }
        }
        return InteractionResultHolder.sidedSuccess(itemstack, level.isClientSide());
    }

    @Override
    public void onUseTick(Level pLevel, LivingEntity pLivingEntity, ItemStack pStack, int pRemainingUseDuration) {
        if (pLivingEntity instanceof ServerPlayer player) {
            if (!player.level().isClientSide()) {
                shootEnergyBeam((ServerLevel) player.level(), player, 60, 40F);
                super.onUseTick(pLevel, pLivingEntity, pStack, pRemainingUseDuration);
            }
        }
    }

    @Override
    public void releaseUsing(ItemStack stack, Level world, LivingEntity entity, int timeLeft) {
        if (!world.isClientSide() && entity instanceof Player player) {
            AABB area = player.getBoundingBox().inflate(30); //range
            List<LivingEntity> nearbyEntities = player.level().getEntitiesOfClass(LivingEntity.class, area);

            for (LivingEntity e : nearbyEntities) {
                e.getPersistentData().remove("LaserLockTime");
            }
        }
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return 200; // Keep using
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.SPEAR;
    }

    private void shootEnergyBeam(ServerLevel world, ServerPlayer player, int range, Float maxDamage) {

        EntityHitResult entityHitResult = getEntityLookingAt(player, range);

        if (entityHitResult != null) {
            Entity target = entityHitResult.getEntity();
            if (target instanceof LivingEntity living) {

                float existingTime = living.getPersistentData().getFloat("LaserLockTime");
                existingTime += 1;
                living.getPersistentData().putFloat("LaserLockTime", existingTime);
                float damage = 1.0F + Math.min(existingTime * 0.2F, maxDamage);
                //System.out.println("Damage: " + damage);
                living.hurt(player.level().damageSources().playerAttack(player), damage);
            }
        }
        // remove LaserLockTime from entities not currently targeted
        AABB cleanupBox = player.getBoundingBox().inflate(range);
        List<LivingEntity> nearbyEntities = world.getEntitiesOfClass(LivingEntity.class, cleanupBox, e -> e != player);

        for (LivingEntity e : nearbyEntities) {
            boolean isCurrentTarget = (entityHitResult != null && entityHitResult.getEntity() == e);
            if (!isCurrentTarget) {
                e.getPersistentData().remove("LaserLockTime");
                //e.addEffect(new MobEffectInstance(MobEffects.GLOWING, 20, 1, false, false, false));
            }
        }
    }

    private void travel(Level level, Player player, int range) {
        Vec3 dest = getDestiation(level, player, range);
        Vec3 playerPos = new Vec3(player.getX(), player.getY(), player.getZ());
        if (playerPos.distanceTo(dest) > 2) {
            player.teleportTo(dest.x, dest.y, dest.z);
        } else {
            dest = getNextAirBlockBehindHit(level, player, range / 2);
            player.teleportTo(dest.x, dest.y, dest.z);
        }
        level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.CHORUS_FRUIT_TELEPORT, SoundSource.PLAYERS, 1.0F, 1.0F);
    }

    private Vec3 getDestiation(Level level, Player player, int range) {
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
        } else {
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
                    return new Vec3(blockPos.getX() + 0.5, blockPos.getY() - 0.62, blockPos.getZ() + 0.5);
                }
                checkPos = checkPos.add(direction); // continue stepping forward
            }
        } else {
            // Fallback: return max-range end point
            return targetVec;
        }
        return new Vec3(player.getX(), player.getY(), player.getZ());
    }

    private void avtivateVoidShield(Level level, Player player) {
        CompoundTag data = player.getPersistentData();
        data.putInt(VOID_SHIELD_TAG, MAX_SHIELD_HITS);
        if (!level.isClientSide()) {
            player.displayClientMessage(Component.literal("Void Shield Activated"), true);
        }
    }

    @Override
    public ManaType getManaType() {
        return ManaType.DRACONIC;
    }

    @Override
    public int getItemLevelCap() {
        return 16;
    }
        /*
         Use Mode Ideas:
         -----------------
         ✅ Void Shield function
         ✅ Void Shield ui + ❌rendering
         ✅ Laser Beam + ❌rendering
         ✅ Staff Of Traveling || not perfect
         ❌ Summoning Energy Dragon (maybe later (and max lv speer required))
         ❌ No escape Zone: Disables Teleportation in an Area (maybe add to armor)
         ❌ Throwing it with 0 Gravity (simply not a fan)
         ❌ Reset Y Movement => cancels Fall damage (useless => voidShield)

         */
}
