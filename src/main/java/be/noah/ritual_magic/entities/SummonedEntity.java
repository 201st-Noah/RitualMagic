package be.noah.ritual_magic.entities;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.OwnableEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.pathfinder.WalkNodeEvaluator;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.EnumSet;
import java.util.UUID;

public abstract class SummonedEntity extends PathfinderMob implements OwnableEntity, GeoEntity {

    private final AnimatableInstanceCache geoCache = GeckoLibUtil.createInstanceCache(this);
    private UUID ownerUUID;

    protected SummonedEntity(EntityType<? extends PathfinderMob> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }


    @Override
    public boolean fireImmune() {
        return true;
    }

    @Override
    public @Nullable UUID getOwnerUUID() {
        return this.ownerUUID;
    }

    public void setOwner(UUID ownerUUID) {
        this.ownerUUID = ownerUUID;
    }

    @Nullable
    @Override
    public LivingEntity getOwner() {
        return this.ownerUUID == null ? null : this.level().getPlayerByUUID(this.ownerUUID);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        if (ownerUUID != null) {
            tag.putUUID("OwnerUUID", ownerUUID);
        }
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        if (tag.hasUUID("OwnerUUID")) {
            this.ownerUUID = tag.getUUID("OwnerUUID");
        }
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.geoCache;
    }

    class OwnerHurtByTargetGoal extends TargetGoal {
        private final SummonedEntity summonedEntity;
        private LivingEntity ownerLastHurtBy;
        private int timestamp;

        public OwnerHurtByTargetGoal(SummonedEntity summonedEntity) {
            super(summonedEntity, false);
            this.summonedEntity = summonedEntity;
            this.setFlags(EnumSet.of(Goal.Flag.TARGET));
        }

        public boolean canUse() {
            LivingEntity livingentity = this.summonedEntity.getOwner();
            if (livingentity == null) {
                return false;
            } else {
                this.ownerLastHurtBy = livingentity.getLastHurtByMob();
                int i = livingentity.getLastHurtByMobTimestamp();
                return i != this.timestamp && this.canAttack(this.ownerLastHurtBy, TargetingConditions.DEFAULT);// && this.tameAnimal.wantsToAttack(this.ownerLastHurtBy, livingentity);
            }
        }

        public void start() {
            this.mob.setTarget(this.ownerLastHurtBy);
            LivingEntity livingentity = this.summonedEntity.getOwner();
            if (livingentity != null) {
                this.timestamp = livingentity.getLastHurtByMobTimestamp();
            }

            super.start();
        }
    }

    class OwnerHurtTargetGoal extends TargetGoal {
        private final SummonedEntity summonedEntity;
        private LivingEntity ownerLastHurt;
        private int timestamp;

        public OwnerHurtTargetGoal(SummonedEntity summonedEntity) {
            super(summonedEntity, false);
            this.summonedEntity = summonedEntity;
            this.setFlags(EnumSet.of(Goal.Flag.TARGET));
        }

        public boolean canUse() {
            LivingEntity livingentity = this.summonedEntity.getOwner();
            if (livingentity == null) {
                return false;
            } else {
                this.ownerLastHurt = livingentity.getLastHurtMob();
                int i = livingentity.getLastHurtMobTimestamp();
                return i != this.timestamp && this.canAttack(this.ownerLastHurt, TargetingConditions.DEFAULT) ;//&& this.tameAnimal.wantsToAttack(this.ownerLastHurt, livingentity);
            }
        }

        public void start() {
            this.mob.setTarget(this.ownerLastHurt);
            LivingEntity livingentity = this.summonedEntity.getOwner();
            if (livingentity != null) {
                this.timestamp = livingentity.getLastHurtMobTimestamp();
            }

            super.start();
        }
    }

    class FollowOwnerGoal extends Goal {
        private final SummonedEntity summonedEntity;
        private LivingEntity owner;
        private final LevelReader level;
        private final double speedModifier;
        private final PathNavigation navigation;
        private int timeToRecalcPath;
        private final float stopDistance;
        private final float startDistance;
        private float oldWaterCost;
        private final boolean canFly;

        public FollowOwnerGoal(SummonedEntity pSummon, double pSpeedModifier, float pStartDistance, float pStopDistance, boolean pCanFly) {
            this.summonedEntity = pSummon;
            this.level = pSummon.level();
            this.speedModifier = pSpeedModifier;
            this.navigation = pSummon.getNavigation();
            this.startDistance = pStartDistance;
            this.stopDistance = pStopDistance;
            this.canFly = pCanFly;
            this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
            if (!(pSummon.getNavigation() instanceof GroundPathNavigation) && !(pSummon.getNavigation() instanceof FlyingPathNavigation)) {
                throw new IllegalArgumentException("Unsupported mob type for FollowOwnerGoal");
            }
        }

        public boolean canUse() {
            LivingEntity livingentity = this.summonedEntity.getOwner();
            if (livingentity == null) {
                return false;
            } else if (livingentity.isSpectator()) {
                return false;
            } else if (this.unableToMove()) {
                return false;
            } else if (this.summonedEntity.distanceToSqr(livingentity) < (double)(this.startDistance * this.startDistance)) {
                return false;
            } else {
                this.owner = livingentity;
                return true;
            }
        }

        public boolean canContinueToUse() {
            if (this.navigation.isDone()) {
                return false;
            } else if (this.unableToMove()) {
                return false;
            } else {
                return !(this.summonedEntity.distanceToSqr(this.owner) <= (double)(this.stopDistance * this.stopDistance));
            }
        }

        private boolean unableToMove() {
            return false;//this.tamable.isOrderedToSit() || this.tamable.isPassenger() || this.tamable.isLeashed();
        }

        public void start() {
            this.timeToRecalcPath = 0;
            this.oldWaterCost = this.summonedEntity.getPathfindingMalus(BlockPathTypes.WATER);
            this.summonedEntity.setPathfindingMalus(BlockPathTypes.WATER, 0.0F);
        }

        public void stop() {
            this.owner = null;
            this.navigation.stop();
            this.summonedEntity.setPathfindingMalus(BlockPathTypes.WATER, this.oldWaterCost);
        }

        public void tick() {
            this.summonedEntity.getLookControl().setLookAt(this.owner, 10.0F, (float)this.summonedEntity.getMaxHeadXRot());
            if (--this.timeToRecalcPath <= 0) {
                this.timeToRecalcPath = this.adjustedTickDelay(10);
                if (this.summonedEntity.distanceToSqr(this.owner) >= 144.0D) {
                    this.teleportToOwner();
                } else {
                    this.navigation.moveTo(this.owner, this.speedModifier);
                }

            }
        }

        private void teleportToOwner() {
            BlockPos blockpos = this.owner.blockPosition();

            for(int i = 0; i < 10; ++i) {
                int j = this.randomIntInclusive(-3, 3);
                int k = this.randomIntInclusive(-1, 1);
                int l = this.randomIntInclusive(-3, 3);
                boolean flag = this.maybeTeleportTo(blockpos.getX() + j, blockpos.getY() + k, blockpos.getZ() + l);
                if (flag) {
                    return;
                }
            }

        }

        private boolean maybeTeleportTo(int pX, int pY, int pZ) {
            if (Math.abs((double)pX - this.owner.getX()) < 2.0D && Math.abs((double)pZ - this.owner.getZ()) < 2.0D) {
                return false;
            } else if (!this.canTeleportTo(new BlockPos(pX, pY, pZ))) {
                return false;
            } else {
                this.summonedEntity.moveTo((double)pX + 0.5D, (double)pY, (double)pZ + 0.5D, this.summonedEntity.getYRot(), this.summonedEntity.getXRot());
                this.navigation.stop();
                return true;
            }
        }

        private boolean canTeleportTo(BlockPos pPos) {
            BlockPathTypes blockpathtypes = WalkNodeEvaluator.getBlockPathTypeStatic(this.level, pPos.mutable());
            if (blockpathtypes != BlockPathTypes.WALKABLE) {
                return false;
            } else {
                BlockState blockstate = this.level.getBlockState(pPos.below());
                if (!this.canFly && blockstate.getBlock() instanceof LeavesBlock) {
                    return false;
                } else {
                    BlockPos blockpos = pPos.subtract(this.summonedEntity.blockPosition());
                    return this.level.noCollision(this.summonedEntity, this.summonedEntity.getBoundingBox().move(blockpos));
                }
            }
        }

        private int randomIntInclusive(int pMin, int pMax) {
            return this.summonedEntity.getRandom().nextInt(pMax - pMin + 1) + pMin;
        }
    }
}

