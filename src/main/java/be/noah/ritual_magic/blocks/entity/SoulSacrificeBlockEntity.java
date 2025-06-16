package be.noah.ritual_magic.blocks.entity;

import be.noah.ritual_magic.blocks.ModBlockEntities;
import be.noah.ritual_magic.blocks.RitualBaseBlockEntity;
import be.noah.ritual_magic.mana.ManaType;
import be.noah.ritual_magic.utils.ModDamageSources;
import net.minecraft.core.BlockPos;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.NotNull;

import java.util.List;


public class SoulSacrificeBlockEntity extends RitualBaseBlockEntity {

    public SoulSacrificeBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.SOUL_SACRIFICE.get(), pPos, pBlockState);
    }

    @Override
    public @NotNull ManaType getManaType() {
        return ManaType.HELLISH;
    }

    public static void tick(Level pLevel, BlockPos pPos, BlockState pBlockState, RitualBaseBlockEntity pRitualBaseBlockEntity) {
        if (pLevel.isClientSide) return;
        if (!pRitualBaseBlockEntity.structureIsOk(pLevel, pPos) ){return;}
        final int blockTier = getBlockTier(pLevel, pPos).getInt();
        final double radius = 4.0D + (double) blockTier;
        final float damageAmount = 1.0F + 3f * (blockTier + 1);

        AABB area = new AABB(pPos).inflate(radius);
        List<LivingEntity> entities = pLevel.getEntitiesOfClass(LivingEntity.class, area);

        for (LivingEntity entity : entities) {
            if (entity.isAlive() && !(entity instanceof Player player && pRitualBaseBlockEntity.getOwner().equals(player.getUUID()))) {
                float healthBefore = entity.getHealth();

                DamageSource source = ModDamageSources.sacrifice(pLevel);
                entity.hurt(source, damageAmount);

                if (!entity.isAlive() && healthBefore > 0) {
                    if (pRitualBaseBlockEntity.getOwner() != null) {
                        pRitualBaseBlockEntity.addMana(pLevel, pRitualBaseBlockEntity.getOwner(), (int)entity.getMaxHealth() * (1 + blockTier));
                    }
                }
            }
        }
    }

}
