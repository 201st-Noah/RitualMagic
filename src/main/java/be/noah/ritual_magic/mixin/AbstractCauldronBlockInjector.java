package be.noah.ritual_magic.mixin;

import be.noah.ritual_magic.block.custom.PointedIcicleBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.AbstractCauldronBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractCauldronBlock.class)
public abstract class AbstractCauldronBlockInjector {
    @Inject(method = "tick", at = @At("TAIL"))
    private void onTick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom, CallbackInfo ci) {
        BlockPos blockpos = PointedIcicleBlock.findStalactiteTipAboveCauldron(pLevel, pPos);
        if (blockpos != null) {
            Fluid fluid = PointedIcicleBlock.getCauldronFillFluidType(pLevel, blockpos);
            if (fluid != Fluids.EMPTY && ((AbstractCauldronBlockInvoker) this).call$canReceiveStalactiteDrip(fluid))
                ((AbstractCauldronBlockInvoker) this).call$receiveStalactiteDrip(pState, pLevel, pPos, fluid);
        }
    }
}
