package be.noah.ritual_magic.item.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.phys.Vec3;

public class NetherScepter extends Item {

    private final int cooldowntime = 32;
    public NetherScepter(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        if (!level.isClientSide && level instanceof ServerLevel serverLevel) {
            Vec3 direction = player.getLookAngle();
            BlockPos start = BlockPos.containing(player.getX(), player.getY(), player.getZ());
            player.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 4000, 1, false, false, false));
            if(!player.isShiftKeyDown()){
                BlockPos pos = BlockPos.containing(player.getX(), player.getY(), player.getZ());
                Lavafield(serverLevel, pos, 3);
            }else{
            RiftManager.addRift(new RiftOpener(serverLevel, start, direction));}

        }
        return InteractionResultHolder.success(player.getItemInHand(hand));
    }

    private void CreatRift(Vec3 playerPos, Vec3 direction, double length, Level level){
        System.out.println("create rift start");

        Vec3 horizontalDir = new Vec3(direction.x, 0, direction.z);
        Vec3 playerPosFeet = new Vec3(playerPos.x, playerPos.y -2, playerPos.z);

        for (int i = 1; i <= length * 4; i++) { // 4 steps per block for smoother line
            Vec3 point = playerPosFeet.add(horizontalDir.scale(i * 0.25)); // move in 0.25 block steps
            BlockPos pos = BlockPos.containing(point);

            // Skip air positions that haven't changed since last step
            if (!level.getBlockState(pos).isAir()) continue;

            // Set a block at that position (e.g., stone)
            level.setBlock(pos, Blocks.LAVA.defaultBlockState(), 3);
        }

        double width = length/5;
        double startPoint  = length/2;
        for (int i = 0; i < width; i++){

        }

        System.out.println("create rift end");
    }

    private void Lavafield(ServerLevel level, BlockPos center, int radius){
        for (int dx = -radius; dx <= radius; dx++) {
            for (int dz = -radius; dz <= radius; dz++) {
                BlockPos pos = center.offset(dx, -1, dz);
                BlockPos top = level.getHeightmapPos(Heightmap.Types.WORLD_SURFACE, pos);
                BlockPos below = top.below();

                if (!level.isEmptyBlock(below)) {
                    level.setBlockAndUpdate(below, Blocks.LAVA.defaultBlockState());
                }
            }
        }
    }

}
