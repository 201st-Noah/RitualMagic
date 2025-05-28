package be.noah.ritual_magic.effect;

import be.noah.ritual_magic.block.ModBlocks;
import be.noah.ritual_magic.entities.HomingProjectile;
import be.noah.ritual_magic.entities.ModEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.PointedDripstoneBlock;
import net.minecraft.world.phys.Vec3;

import java.util.Random;

public class IceRain extends MobEffect {

    private static final Random random = new Random();

    protected IceRain(MobEffectCategory pCategory, int pColor) {
        super(MobEffectCategory.HARMFUL, pColor);
    }

    @Override
    public void applyEffectTick(LivingEntity pLivingEntity, int pAmplifier) {
        int randX = 0;
        int randZ = 0;
        if (pAmplifier > 0) {
            randX = random.nextInt(-((int)Math.sqrt(pAmplifier)), (int)Math.sqrt(pAmplifier));
            randZ = random.nextInt(-((int)Math.sqrt(pAmplifier)), (int)Math.sqrt(pAmplifier));
        }
        Vec3i spikePos = new Vec3i((int)Math.round(pLivingEntity.getX()) + randX, (int)Math.round(pLivingEntity.getY()) + 20, (int)Math.round(pLivingEntity.getZ()) + randZ);

        FallingBlockEntity fallingBlock = FallingBlockEntity.fall(
                pLivingEntity.level(),
                new BlockPos(spikePos),
                ModBlocks.POINTED_ICICLE.get().defaultBlockState().
                        setValue(PointedDripstoneBlock.TIP_DIRECTION, Direction.DOWN)
        );
        fallingBlock.setDeltaMovement(new Vec3(0, -0.3, 0));
        fallingBlock.dropItem = false;
        fallingBlock.setHurtsEntities(6,40);

        super.applyEffectTick(pLivingEntity, pAmplifier);
    }

    @Override
    public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
        if (pAmplifier <= 0) {return (pDuration % 20) == 0;}
        return pDuration % (21 - pAmplifier) == 0;
    }


}
