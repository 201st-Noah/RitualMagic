
package be.noah.ritual_magic.item.custom;

import be.noah.ritual_magic.block.ModBlocks;
import be.noah.ritual_magic.effect.ModEffects;
import be.noah.ritual_magic.entities.HomingProjectile;
import be.noah.ritual_magic.entities.ModEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.level.ClipContext;
import net.minecraft.network.chat.Component;

public class IceSword extends SwordItem {
    private static final int PROJECTILE_COUNT = 17;
    private static final int COOLDOWN = 30;
    private static final double TARGET_RANGE = 3200.0;
    private int mode = 0;
    public IceSword(Tier pTier, int pAttackDamageModifier, float pAttackSpeedModifier, Properties pProperties) {
        super(pTier, pAttackDamageModifier, pAttackSpeedModifier, pProperties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);

        if (!level.isClientSide) {
            if (player.isShiftKeyDown()) {
                mode = (mode + 1) % 3;
                switch (mode) {
                    case 0:
                        player.displayClientMessage(Component.translatable("ritual_magic.item.ice_sword.mode.0"), true);
                        break;
                    case 1:
                        player.displayClientMessage(Component.translatable("ritual_magic.item.ice_sword.mode.1"), true);
                        break;
                    case 2:
                        player.displayClientMessage(Component.translatable("ritual_magic.item.ice_sword.mode.2"), true);
                        break;
                }
                return InteractionResultHolder.success(itemstack);
            } else {
                Entity target = findTargetInLineOfSight(player);
                if (target != null) {
                    switch (mode) {
                        case 0:
                            spawnProjectiles(level, player, target);
                            level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.TRIDENT_THROW, SoundSource.PLAYERS, 1.0F, 1.0F);
                            break;
                        case 1:
                            if (target instanceof LivingEntity) {
                                ((LivingEntity) target).addEffect(new MobEffectInstance(ModEffects.ICERAIN.get(), 200, 20));
                            }
                            break;
                        case 2:
                            createIceField(level, player, target, 8);
                            break;
                    }
                    player.getCooldowns().addCooldown(this, COOLDOWN);
                    return InteractionResultHolder.success(itemstack);
                } else{
                    player.displayClientMessage(Component.translatable("No Target detected"), true);
                    return InteractionResultHolder.fail(itemstack);
                }

            }
        }
        return InteractionResultHolder.pass(itemstack);

    }

    private void createIceField(Level level, Player player, Entity target, int radius){
        BlockPos center = target.blockPosition();

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
                        level.setBlock(abovePos, ModBlocks.ICE_SPIKE.get().defaultBlockState(), 3);
                        break;
                    }
                }
            }
        }
    }

    private void spawnProjectiles(Level level, Player player, Entity target) {
        // Berechne die Basis-Vektoren für das Portal-ähnliche Spawn-Muster
        Vec3 lookVec = player.getViewVector(1.0F);
        Vec3 upVec = new Vec3(0, 1, 0);
        Vec3 rightVec = lookVec.cross(upVec).normalize();
        Vec3 trueUpVec = rightVec.cross(lookVec).normalize();

        // Position hinter dem Spieler
        double distanceBehind = 2.0;
        Vec3 portalCenter = player.position()
                .subtract(lookVec.scale(distanceBehind))
                .add(0, 1.5, 0);

        double radius = 10.0;
        double angleStep = 360.0 / PROJECTILE_COUNT;

        for (int i = 0; i < PROJECTILE_COUNT; i++) {
            double angle = Math.toRadians(i * angleStep);

            // Berechne spiralförmige Position
            double spiralRadius = radius * (1 - (i / (double) PROJECTILE_COUNT) * 0.3);
            double x = Math.cos(angle) * spiralRadius;
            double y = Math.sin(angle) * spiralRadius;

            // Transformiere in Weltkoordinaten
            Vec3 spawnPos = portalCenter
                    .add(rightVec.scale(x))
                    .add(trueUpVec.scale(y));

            HomingProjectile projectile = new HomingProjectile(ModEntities.HOMING_PROJECTILE.get(), level);
            projectile.setPos(spawnPos.x, spawnPos.y, spawnPos.z);
            projectile.setOwner(player);
            projectile.setTarget(target);
            projectile.setNoGravity(true);
            projectile.setDelayRange(10, 80);

            // Richtung zum Ziel
            Vec3 direction = new Vec3(
                    target.getX() - spawnPos.x,
                    (target.getY() + target.getBbHeight() / 2) - spawnPos.y,
                    target.getZ() - spawnPos.z
            ).normalize();

            projectile.shoot(direction.x, direction.y, direction.z, 0.0F, 0.0F);
            level.addFreshEntity(projectile);
        }
    }

    private Entity findTargetInLineOfSight(Player player) {
        Vec3 eyePos = player.getEyePosition();
        Vec3 lookVec = player.getViewVector(1.0F);
        Vec3 targetVec = eyePos.add(lookVec.scale(TARGET_RANGE));

        // Prüfe, ob ein Entity direkt im Fadenkreuz ist
        HitResult hit = player.level().clip(new ClipContext(
                eyePos,
                targetVec,
                ClipContext.Block.COLLIDER,
                ClipContext.Fluid.NONE,
                player
        ));

        // Wenn ein Block im Weg ist, verkürze die Reichweite
        if (hit.getType() == HitResult.Type.BLOCK) {
            targetVec = hit.getLocation();
        }

        Entity targetEntity = null;
        double smallestAngle = 0.1; // Maximaler Winkel in Radiant (etwa 5.7 Grad)

        for (Entity entity : player.level().getEntities(player,
                new AABB(eyePos.x, eyePos.y, eyePos.z, targetVec.x, targetVec.y, targetVec.z).inflate(1.0),
                e -> e instanceof Entity && !(e instanceof Player || e instanceof Projectile || e instanceof ItemEntity || e instanceof ExperienceOrb))) {

            Vec3 directionToEntity = entity.position()
                    .add(0, entity.getBbHeight() * 0.5, 0)
                    .subtract(eyePos)
                    .normalize();

            // Berechne den Winkel zwischen Blickrichtung und Richtung zum Entity
            double angle = Math.acos(directionToEntity.dot(lookVec));

            if (angle < smallestAngle) {
                // Prüfe, ob keine Blöcke im Weg sind
                HitResult blockHit = player.level().clip(new ClipContext(
                        eyePos,
                        entity.position().add(0, entity.getBbHeight() * 0.5, 0),
                        ClipContext.Block.COLLIDER,
                        ClipContext.Fluid.NONE,
                        player
                ));

                if (blockHit.getType() == HitResult.Type.MISS) {
                    targetEntity = entity;
                    smallestAngle = angle;
                }
            }
        }

        return targetEntity;
    }

}