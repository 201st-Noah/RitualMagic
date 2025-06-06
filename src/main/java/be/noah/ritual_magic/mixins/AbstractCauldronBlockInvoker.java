package be.noah.ritual_magic.mixins;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AbstractCauldronBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(AbstractCauldronBlock.class)
public interface AbstractCauldronBlockInvoker {
    @Invoker("canReceiveStalactiteDrip")
    boolean call$canReceiveStalactiteDrip(Fluid fluid);

    @Invoker("receiveStalactiteDrip")
    void call$receiveStalactiteDrip(BlockState pState, Level pLevel, BlockPos pPos, Fluid pFluid);
}
