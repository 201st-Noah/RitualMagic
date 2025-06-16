package be.noah.ritual_magic.blocks.entity;

import be.noah.ritual_magic.blocks.ModBlockEntities;
import be.noah.ritual_magic.blocks.RitualBaseBlockEntity;
import be.noah.ritual_magic.mana.ManaType;
import be.noah.ritual_magic.utils.ModDamageSources;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Random;


public class SoulSacrificeBlockEntity extends RitualBaseBlockEntity {
    private static final Random random = new Random();

    public SoulSacrificeBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.SOUL_SACRIFICE.get(), pPos, pBlockState);
    }

    @Override
    public @NotNull ManaType getManaType() {
        return ManaType.HELLISH;
    }

    @Override
    public void tick() {
        Level pLevel = getLevel();
        BlockPos pPos = getBlockPos();
        RitualBaseBlockEntity pRitualBaseBlockEntity = this;

        if(!(pLevel.getGameTime() % 40 == 0)) {return;} //Timer
        if (!pRitualBaseBlockEntity.structureIsOk(pLevel, pPos) ){return;}
        final int blockTier = getBlockTier(pLevel, pPos).getInt();
        final double radius = 4.0D + (double) blockTier;
        if (!pLevel.isClientSide && pRitualBaseBlockEntity.getOwner() != null){
            final float damageAmount = 1.0F + 3f * (blockTier + 1);

            AABB area = new AABB(pPos).inflate(radius);
            List<LivingEntity> entities = pLevel.getEntitiesOfClass(LivingEntity.class, area);

            for (LivingEntity entity : entities) {
                if (entity.isAlive() && !(entity instanceof Player player && pRitualBaseBlockEntity.getOwner().equals(player.getUUID()))) {
                    float healthBefore = entity.getHealth();

                    DamageSource source = ModDamageSources.sacrifice(pLevel);
                    entity.hurt(source, damageAmount);

                    if (!entity.isAlive() && healthBefore > 0) {
                        pRitualBaseBlockEntity.addMana(pLevel, pRitualBaseBlockEntity.getOwner(), (int)entity.getMaxHealth() * (1 + blockTier));
                    }
                }
            }
        }
        spawnParticlesInCube(pLevel, pPos, ParticleTypes.SCULK_SOUL, (int)radius, (int)Math.pow(radius, 3));
        pLevel.playSound(
                null,                         // Player (null = everyone hears it)
                pPos,                          // Block position
                SoundEvents.WARDEN_HEARTBEAT,  // The sound you want
                SoundSource.BLOCKS,            // Sound source category
                1.0F,                          // Volume
                1.0F                           // Pitch
        );
    }

    public static void spawnParticlesInCube(Level level, BlockPos centerPos, ParticleOptions particle, int range, int particlesPerTick) {
        RandomSource randomSource = level.getRandom();

        // Use double center coordinates
        double centerX = centerPos.getX() + 0.5;
        double centerY = centerPos.getY() + 0.5;
        double centerZ = centerPos.getZ() + 0.5;

        for (int i = 0; i < particlesPerTick; i++) {
            // Random position
            double posX = centerX - range + randomSource.nextDouble() * (2 * range);
            double posY = centerY - range + randomSource.nextDouble() * (2 * range);
            double posZ = centerZ - range + randomSource.nextDouble() * (2 * range);

            // direction to center
            double dirX = centerX - posX;
            double dirY = centerY - posY;
            double dirZ = centerZ - posZ;

            // Normalize direction
            double length = Math.sqrt(dirX * dirX + dirY * dirY + dirZ * dirZ);
            double speed = 0.5;
            if (length != 0) {
                dirX = dirX / length * speed;
                dirY = dirY / length * speed;
                dirZ = dirZ / length * speed;
            }

            level.addParticle(particle, posX, posY, posZ, dirX, dirY, dirZ);
        }
    }
}
