package be.noah.ritual_magic.blocks.entity;

import be.noah.ritual_magic.blocks.ModBlockEntities;
import be.noah.ritual_magic.blocks.RitualBaseBlockEntity;
import be.noah.ritual_magic.mana.ManaType;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class WeatherRitualBlockEntity extends RitualBaseBlockEntity {
    public WeatherRitualBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.WEATHER_RITUAL.get(), pPos, pBlockState);
    }

    @Override
    public @NotNull ManaType getManaType() {
        return ManaType.ATLANTIAN;
    }

    @Override
    public void tick() {
        Level pLevel = getLevel();
        BlockPos pPos = getBlockPos();
        RitualBaseBlockEntity pRitualBaseBlockEntity = this;
        /*
        for Infos https://minecraft.wiki/w/Biome#Temperature
        base temperature = -0.7 bis 2.0
        downfall = 0.0 to 1.0
        */
        if(pLevel.isClientSide()) {return;}
        if(!(pLevel.getGameTime() % 20 == 0)) {return;} //Timer
        if(pLevel.dimension() != Level.OVERWORLD) {return;} // only works in OverWorld
        if (pRitualBaseBlockEntity.getOwner() == null) {return;}

        Biome biome = pLevel.getBiome(pPos).value();
        float downfall = biome.modifiableBiomeInfo().getOriginalBiomeInfo().climateSettings().downfall(); // 0.0 - 1.0
        if(downfall == 0f) {return;}
        float basetemperature = biome.getBaseTemperature(); // -0.7 to 2.0
        int blockTier = getBlockTier(pLevel, pPos).getInt(); // 0 - 3
        boolean isRaining = pLevel.isRaining();
        boolean isThundering = pLevel.isThundering();

        //Calculating Mana to add
        float baseFactor = 1 + blockTier;
        if(isRaining) { baseFactor *= 2; }
        if(isThundering) { baseFactor *= 4; }
        baseFactor = baseFactor * (1 + downfall);
        float basetempfactor =(-(basetemperature - 2)/3) +1;
        baseFactor = baseFactor * basetempfactor;
        pRitualBaseBlockEntity.addMana(pLevel, pRitualBaseBlockEntity.getOwner(), (int)baseFactor);
        //System.out.println("manaToAdd: " + (int)baseFactor);
    }
}
