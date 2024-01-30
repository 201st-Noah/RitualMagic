package be.noah.ritual_magic.datagen;

import be.noah.ritual_magic.RitualMagic;
import be.noah.ritual_magic.item.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, RitualMagic.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        simpleItem(ModItems.DRAGON_PLATE);
        simpleItem(ModItems.DRAGON_SCALE);
        simpleItem(ModItems.ATLANTIAN_SCRAP);
        simpleItem(ModItems.ATLANTIAN_STEEL_INGOT);
        simpleItem(ModItems.DWARVEN_SCRAP);
        simpleItem(ModItems.DWARVEN_STEEL_INGOT);
        simpleItem(ModItems.WARDEN_CORE);
        simpleItem(ModItems.DWARVEN_TEMPLATE);

        //custom
        getBuilder("speer").parent(getExistingFile(mcLoc("item/generated"))).texture("layer0", mcLoc("ritual_magic:item/speer"));
        getBuilder("dwarven_axe").parent(getExistingFile(mcLoc("item/generated"))).texture("layer0", mcLoc("ritual_magic:item/dwarven_axe"));
    }
    private ItemModelBuilder simpleItem(RegistryObject<Item> item) {
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/generated")).texture("layer0",
                new ResourceLocation(RitualMagic.MODID,"item/" + item.getId().getPath()));
    }
}
