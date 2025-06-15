package be.noah.ritual_magic.blocks.entity;

import be.noah.ritual_magic.blocks.RitualBaseBlockEntity;
import be.noah.ritual_magic.mana.ManaType;
import be.noah.ritual_magic.utils.ModDamageTypes;
import be.noah.ritual_magic.utils.SacrificeDamageSource;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
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
        final double radius = 5.0D;
        final float damageAmount = 4.0F;

        AABB area = new AABB(pPos).inflate(radius);
        List<LivingEntity> entities = pLevel.getEntitiesOfClass(LivingEntity.class, area);

        for (LivingEntity entity : entities) {
            if (entity.isAlive() && !(entity instanceof Player player && pRitualBaseBlockEntity.getOwner() == player.getUUID())) { // optionally exclude players
                float healthBefore = entity.getHealth();

                Holder<DamageType> damageTypeHolder = pLevel.registryAccess()
                        .registryOrThrow(Registries.DAMAGE_TYPE)
                        .getHolder(ModDamageTypes.SACRIFICE)
                        .orElseThrow(() -> new IllegalStateException("DamageType not found!"));

                DamageSource source = new SacrificeDamageSource(damageTypeHolder);
                entity.hurt(source, damageAmount);

                if (!entity.isAlive() && healthBefore > 0) {
                    onEntitySacrificed(pLevel, pPos, pRitualBaseBlockEntity, entity);
                }
            }
        }
    }

    private static void onEntitySacrificed(Level level, BlockPos pos, RitualBaseBlockEntity blockEntity, LivingEntity entity) {
        if (blockEntity.getOwner() != null) {
            Player owner = level.getPlayerByUUID(blockEntity.getOwner());
            if (owner != null && blockEntity instanceof SoulSacrificeBlockEntity) {
                blockEntity.addMana(owner, (int)entity.getMaxHealth());
            }
        }
    }
}
