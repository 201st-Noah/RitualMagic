package be.noah.ritual_magic.datagen;

import be.noah.ritual_magic.RitualMagic;
import be.noah.ritual_magic.block.ModBlocks;
import be.noah.ritual_magic.effect.ModEffects;
import be.noah.ritual_magic.item.ModCreativeModTabs;
import be.noah.ritual_magic.item.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.common.data.LanguageProvider;

public class ModLanguageProvider extends LanguageProvider {
    public ModLanguageProvider(PackOutput output) {
        super(output, RitualMagic.MODID, "en_us");
    }

    private void blocks() {
        addBlock(ModBlocks.DWARVEN_DEBRIS, "Dwarven Debris");
        addBlock(ModBlocks.ATLANTIAN_DEBRIS, "Atlantian Debris");
        addBlock(ModBlocks.PETRIFIED_DRAGON_SCALE, "Petrified Dragon Scale");
        addBlock(ModBlocks.POLISHED_OBSIDIAN, "Polished Obsidian");
        addBlock(ModBlocks.SOUL_BRICKS, "Soul Bricks");
        addBlock(ModBlocks.DWARVEN_STEEL_BLOCK, "Dwarven Steel Block");
        addBlock(ModBlocks.DRAGON_FIRE, "Dragon Fire");
        addBlock(ModBlocks.ICE_SPIKE, "Ice Spike");
        addBlock(ModBlocks.POINTED_ICICLE, " Pointed Icicle");
    }

    private void blockEntities() {
        addBlock(ModBlocks.MOD_TELEPORTER, "Teleporter Core");
        addBlock(ModBlocks.MINING_CORE, "Mining Core");
        addBlock(ModBlocks.ANCIENT_ANVIL, "Ancient Anvil");
        addBlock(ModBlocks.FORGE_T0, "Beginner Forge");
        addBlock(ModBlocks.FORGE_T1, "Basic Forge");
        addBlock(ModBlocks.FORGE_T2, "Advanced Forge");
        addBlock(ModBlocks.RITUAL_PEDESTAL, "Ritual Pedestal");
        addBlock(ModBlocks.INFUSION, "Infusion Core");
    }

    private void creativeTabs() {
        addCreativeTab(ModCreativeModTabs.RITUAL_MAGIC_ATLANTIAN_TAB.get(), "Ritual Magic - Atlantian");
        addCreativeTab(ModCreativeModTabs.RITUAL_MAGIC_DWARVEN_TAB.get(), "Ritual Magic - Dwarven");
        addCreativeTab(ModCreativeModTabs.RITUAL_MAGIC_SOULEATER_TAB.get(), "Ritual Magic - Souleater");
        addCreativeTab(ModCreativeModTabs.RITUAL_MAGIC_VOIDWALKER_TAB.get(), "Ritual Magic - Voidwalker");
        addCreativeTab(ModCreativeModTabs.RITUAL_MAGIC_NEXUS_TAB.get(), "Ritual Magic - Nexus");
    }

    private void items() {
        addItem(ModItems.DRAGON_SCALE, "Dragon Scale");
        addItem(ModItems.DRAGON_PLATE, "Dragon Plate");
        addItem(ModItems.ATLANTIAN_STEEL_INGOT, "Atlantian Steel Ingot");
        addItem(ModItems.ATLANTIAN_SCRAP, "Atlantian Scrap");
        addItem(ModItems.DWARVEN_SCRAP, "Dwarf Scrap");
        addItem(ModItems.WARDEN_CORE, "Warden Core");
        addItem(ModItems.DWARVEN_STEEL_INGOT, "Dwarven Steel Ingot");
        addItem(ModItems.DWARVEN_TEMPLATE, "Dwarven Template");
        addItem(ModItems.LOST_SOUL, "Lost Soul");
        addItem(ModItems.DWARVEN_STEEL_ARMOR_PLATE, "Dwarven Steel Armor Plate");
        addItem(ModItems.PURE_NETHERITE, "Pure Netherite");
        addItem(ModItems.TORCH, "Torch");
        addItem(ModItems.SPEER, "Dragon Rider's Speer");
        addItem(ModItems.DWARVEN_AXE, "Dwarven Axe");
        addItem(ModItems.DWAREN_STEEL_HELMET, "Dwarven Steel Helmet");
        addItem(ModItems.DWAREN_STEEL_CHESTPLATE, "Dwarven Steel Chestplate");
        addItem(ModItems.DWAREN_STEEL_LEGGINGS, "Dwarven Steel Leggings");
        addItem(ModItems.DWAREN_STEEL_BOOTS, "Dwarven Steel Boots");
        addItem(ModItems.BUILDERSTAFF, "Builder's Staff");
        addItem(ModItems.NETHER_SCEPTRE, "Sceptre of the Nether");
        addItem(ModItems.ICE_SWORD, "Eternal Ice Sword");
        addItem(ModItems.ICE_SHARD, "Ice Shard");
        addItem(ModItems.DWARVEN_PICKAXE, "Dwarven Pickaxe");
    }

    private void effects(){
        addEffect(ModEffects.ICERAIN, "Ice Rain");
        addEffect(ModEffects.FROSTAURA, "Frost Aura");
        addEffect(ModEffects.FIREAURA, "Fire Aura");
    }

    private void messages(){
        add("ritual_magic.item.ice_sword.mode.0", "Ice Darts");
        add("ritual_magic.item.ice_sword.mode.1", "Ice Rain");
        add("ritual_magic.item.ice_sword.mode.2", "Ice Field");
        add("ritual_magic.item.ice_sword.mode.3", "Frost Aura.");
        add("ritual_magic.item.ice_sword.noTarget", "No Target found.");
        add("ritual_magic.item.nether_scepter.mode.0", "Lava Lake");
        add("ritual_magic.item.nether_scepter.mode.1", "Fire Aura");
        add("ritual_magic.item.nether_scepter.mode.2", "Temp setLevel");
        add("ritual_magic.item.speer.mode.1", "Fire Ball");
        add("ritual_magic.item.speer.mode.0", "Traveling Mode");
        add("ritual_magic.item.speer.mode.2", "Void Shield");
        add("ritual_magic.item.speer.mode.3", "Void Energy Beam");
        add("ritual_magic.item.dwarven_pickaxe.mode.0", "Select Dig AOE");
        add("ritual_magic.item.dwarven_pickaxe.mode.1", "Beginner's Luck");
        add("ritual_magic.item.dwarven_pickaxe.aoe", "Size: ");
    }

    @Override
    protected void addTranslations() {
        blocks();
        blockEntities();
        creativeTabs();
        items();
        messages();
        effects();
    }

    public void addCreativeTab(CreativeModeTab key, String name) {
        add(key.getDisplayName().getString(), name);
    }
}
