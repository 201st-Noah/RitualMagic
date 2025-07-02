package be.noah.ritual_magic.datagen;

import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public class ModMultiblockStructureProvider implements DataProvider {
    private final PackOutput output;

    public ModMultiblockStructureProvider(PackOutput output) {
        this.output = output;
    }

    @Override
    @NotNull
    public CompletableFuture<?> run(@NotNull CachedOutput pOutput) {
        return null;
    }

    @Override
    @NotNull
    public String getName() {
        return "Ritual Magic Multiblock Structures";
    }
}
