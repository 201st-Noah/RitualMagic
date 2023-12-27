package be.noah.ritual_magic.entities;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

public class BallLightning extends ThrowableProjectile {

    private final int explosionPower = 0;
    private final int onFireTimeSec = 5;
    protected BallLightning(EntityType<? extends ThrowableProjectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }
    public BallLightning(EntityType<? extends ThrowableProjectile> type, double x, double y, double z, Level worldIn)
    {
        super(type, x, y, z, worldIn);
    }

    public BallLightning(EntityType<? extends ThrowableProjectile> type, LivingEntity livingEntityIn, Level worldIn)
    {
        super(type, livingEntityIn, worldIn);
    }

    protected void onHit(HitResult p_37218_)
    {
        super.onHit(p_37218_);
        if (!this.level().isClientSide())
        {
            boolean flag = net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.level(), this.getOwner());
            this.level().explode((Entity)null, this.getX(), this.getY(), this.getZ(), (float)this.explosionPower, flag, flag ? Level.ExplosionInteraction.TNT : Level.ExplosionInteraction.NONE);
            this.discard();
        }

    }
    protected void onHitEntity(EntityHitResult entityHitResult)
    {
        super.onHitEntity(entityHitResult);
        if (!this.level().isClientSide())
        {
            Entity entity = entityHitResult.getEntity();
            entity.setSecondsOnFire(onFireTimeSec);
            entity.hurt(level().damageSources().explosion(this.getOwner(), entity), 40.0F);
        }
    }
    protected void onHitBlock(BlockHitResult result)
    {
        super.onHitBlock(result);
    }



    @Override
    protected void defineSynchedData() {

    }
}
