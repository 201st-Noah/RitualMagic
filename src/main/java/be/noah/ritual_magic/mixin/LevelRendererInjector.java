package be.noah.ritual_magic.mixin;

import be.noah.ritual_magic.block.custom.PointedIcicleBlock;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.core.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LevelRenderer.class)
public class LevelRendererInjector {
    @Inject(method = "levelEvent", at = @At("TAIL"))
    public void onLevelEvent(int pType, BlockPos pPos, int pData, CallbackInfo ci) {
        if (pType == 1504) {
            PointedIcicleBlock.spawnDripParticle(
                    ((LevelRendererAccessor) this).get$level(),
                    pPos,
                    ((LevelRendererAccessor) this).get$level().getBlockState(pPos)
            );
        }
    }
}
