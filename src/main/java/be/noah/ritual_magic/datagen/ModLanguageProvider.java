package be.noah.ritual_magic.datagen;

import be.noah.ritual_magic.RitualMagic;
import be.noah.ritual_magic.blocks.ModBlocks;
import be.noah.ritual_magic.effects.ModEffects;
import be.noah.ritual_magic.items.ModCreativeModTabs;
import be.noah.ritual_magic.items.ModItems;
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
        addBlock(ModBlocks.B_SOUL_FARMLAND, "Basic Soul Farmland");
        addBlock(ModBlocks.I_SOUL_FARMLAND, "Intermediate Soul Farmland");
        addBlock(ModBlocks.A_SOUL_FARMLAND, "Advanced Soul Farmland");
        addBlock(ModBlocks.U_SOUL_FARMLAND, "Ultimate Soul Farmland");
    }

    private void blockEntities() {
        addBlock(ModBlocks.MOD_TELEPORTER, "Teleporter Core");
        addBlock(ModBlocks.MINING_CORE, "Mining Core");
        addBlock(ModBlocks.ANCIENT_ANVIL, "Ancient Anvil");
        addBlock(ModBlocks.FORGE_T0, "Beginner Forge");
        addBlock(ModBlocks.FORGE_T1, "Basic Forge");
        addBlock(ModBlocks.FORGE_T2, "Advanced Forge");
        addBlock(ModBlocks.B_NEXUS_PEDESTAL, "Basic Nexus Pedestal");
        addBlock(ModBlocks.I_NEXUS_PEDESTAL, "Intermediate Nexus Pedestal");
        addBlock(ModBlocks.A_NEXUS_PEDESTAL, "Advanced Nexus Pedestal");
        addBlock(ModBlocks.U_NEXUS_PEDESTAL, "Ultimate Nexus Pedestal");
        addBlock(ModBlocks.B_ATLANTIAN_PEDESTAL, "Basic Atlantian Pedestal");
        addBlock(ModBlocks.I_ATLANTIAN_PEDESTAL, "Intermediate Atlantian Pedestal");
        addBlock(ModBlocks.A_ATLANTIAN_PEDESTAL, "Advanced Atlantian Pedestal");
        addBlock(ModBlocks.U_ATLANTIAN_PEDESTAL, "Ultimate Atlantian Pedestal");
        addBlock(ModBlocks.B_DWARVEN_PEDESTAL, "Basic Dwarven Pedestal");
        addBlock(ModBlocks.I_DWARVEN_PEDESTAL, "Intermediate Dwarven Pedestal");
        addBlock(ModBlocks.A_DWARVEN_PEDESTAL, "Advanced Dwarven Pedestal");
        addBlock(ModBlocks.U_DWARVEN_PEDESTAL, "Ultimate Dwarven Pedestal");
        addBlock(ModBlocks.B_HELLISH_PEDESTAL, "Basic Hellish Pedestal");
        addBlock(ModBlocks.I_HELLISH_PEDESTAL, "Intermediate Hellish Pedestal");
        addBlock(ModBlocks.A_HELLISH_PEDESTAL, "Advanced Hellish Pedestal");
        addBlock(ModBlocks.U_HELLISH_PEDESTAL, "Ultimate Hellish Pedestal");
        addBlock(ModBlocks.B_DRACONIC_PEDESTAL, "Basic Draconic Pedestal");
        addBlock(ModBlocks.I_DRACONIC_PEDESTAL, "Intermediate Draconic Pedestal");
        addBlock(ModBlocks.A_DRACONIC_PEDESTAL, "Advanced Draconic Pedestal");
        addBlock(ModBlocks.U_DRACONIC_PEDESTAL, "Ultimate Draconic Pedestal");
        addBlock(ModBlocks.B_NEXUS_INFUSION_CORE, "Basic Nexus Infusion Core");
        addBlock(ModBlocks.I_NEXUS_INFUSION_CORE, "Intermediate Nexus Infusion Core");
        addBlock(ModBlocks.A_NEXUS_INFUSION_CORE, "Advanced Nexus Infusion Core");
        addBlock(ModBlocks.U_NEXUS_INFUSION_CORE, "Ultimate Nexus Infusion Core");
        addBlock(ModBlocks.B_ATLANTIAN_INFUSION_CORE, "Basic Atlantian Infusion Core");
        addBlock(ModBlocks.I_ATLANTIAN_INFUSION_CORE, "Intermediate Atlantian Infusion Core");
        addBlock(ModBlocks.A_ATLANTIAN_INFUSION_CORE, "Advanced Atlantian Infusion Core");
        addBlock(ModBlocks.U_ATLANTIAN_INFUSION_CORE, "Ultimate Atlantian Infusion Core");
        addBlock(ModBlocks.B_DWARVEN_INFUSION_CORE, "Basic Dwarven Infusion Core");
        addBlock(ModBlocks.I_DWARVEN_INFUSION_CORE, "Intermediate Dwarven Infusion Core");
        addBlock(ModBlocks.A_DWARVEN_INFUSION_CORE, "Advanced Dwarven Infusion Core");
        addBlock(ModBlocks.U_DWARVEN_INFUSION_CORE, "Ultimate Dwarven Infusion Core");
        addBlock(ModBlocks.B_HELLISH_INFUSION_CORE, "Basic Hellish Infusion Core");
        addBlock(ModBlocks.I_HELLISH_INFUSION_CORE, "Intermediate Hellish Infusion Core");
        addBlock(ModBlocks.A_HELLISH_INFUSION_CORE, "Advanced Hellish Infusion Core");
        addBlock(ModBlocks.U_HELLISH_INFUSION_CORE, "Ultimate Hellish Infusion Core");
        addBlock(ModBlocks.B_DRACONIC_INFUSION_CORE, "Basic Draconic Infusion Core");
        addBlock(ModBlocks.I_DRACONIC_INFUSION_CORE, "Intermediate Draconic Infusion Core");
        addBlock(ModBlocks.A_DRACONIC_INFUSION_CORE, "Advanced Draconic Infusion Core");
        addBlock(ModBlocks.U_DRACONIC_INFUSION_CORE, "Ultimate Draconic Infusion Core");
        addBlock(ModBlocks.B_SOUL_SACRIFICE, "Basic Sacrifice Altar");
        addBlock(ModBlocks.I_SOUL_SACRIFICE, "Intermediate Sacrifice Altar");
        addBlock(ModBlocks.A_SOUL_SACRIFICE, "Advanced Sacrifice Altar");
        addBlock(ModBlocks.U_SOUL_SACRIFICE, "Ultimate Sacrifice Altar");
        addBlock(ModBlocks.B_WEATHER_RITUAL, "Basic Weather Ritual Block");
        addBlock(ModBlocks.I_WEATHER_RITUAL, "Intermediate Weather Ritual Block");
        addBlock(ModBlocks.A_WEATHER_RITUAL, "Advanced Weather Ritual Block");
        addBlock(ModBlocks.U_WEATHER_RITUAL, "Ultimate Weather Ritual Block");
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
        addItem(ModItems.DWARVEN_STEEL_HELMET, "Dwarven Steel Helmet");
        addItem(ModItems.DWARVEN_STEEL_CHESTPLATE, "Dwarven Steel Chestplate");
        addItem(ModItems.DWARVEN_STEEL_LEGGINGS, "Dwarven Steel Leggings");
        addItem(ModItems.DWARVEN_STEEL_BOOTS, "Dwarven Steel Boots");
        addItem(ModItems.BUILDERSTAFF, "Builder's Staff");
        addItem(ModItems.NETHER_SCEPTRE, "Sceptre of the Nether");
        addItem(ModItems.ICE_SWORD, "Eternal Ice Sword");
        addItem(ModItems.ICE_SHARD, "Ice Shard");
        addItem(ModItems.SHARP_ICE_SHARD, "Sharp Ice Shard");
        addItem(ModItems.ICE_SWORD_HILT, "Ice Sword Hilt");
        addItem(ModItems.DWARVEN_PICKAXE, "Dwarven Pickaxe");
        addItem(ModItems.ATLANTIAN_MANA_RUNE, "Atlantian Mana Rune");
        addItem(ModItems.DRACONIC_MANA_RUNE, "Draconic Mana Rune");
        addItem(ModItems.DWARVEN_MANA_RUNE, "Dwarven Mana Rune");
        addItem(ModItems.HELLISH_MANA_RUNE, "Hellish Mana Rune");
        addItem(ModItems.NEXUS_MANA_RUNE, "Nexus Mana Rune");
        addItem(ModItems.SOUL_SCYTHE, "Soul Scythe");
        addItem(ModItems.ICE_HELMET, "Ice Helmet");
        addItem(ModItems.ICE_CHESTPLATE, "Ice Chestplate");
        addItem(ModItems.ICE_LEGGINGS, "Ice Leggings");
        addItem(ModItems.ICE_BOOTS, "Ice Boots");
    }

    private void effects() {
        addEffect(ModEffects.ICERAIN, "Ice Rain");
        addEffect(ModEffects.FROSTAURA, "Frost Aura");
        addEffect(ModEffects.FIREAURA, "Fire Aura");
    }

    private void messages() {
        add("ritual_magic.item.ice_sword.mode.0", "Ice Darts");
        add("ritual_magic.item.ice_sword.mode.1", "Ice Rain");
        add("ritual_magic.item.ice_sword.mode.2", "Ice Field");
        add("ritual_magic.item.ice_sword.mode.3", "Frost Aura.");
        add("ritual_magic.item.ice_sword.noTarget", "No Target found.");
        add("ritual_magic.item.nether_scepter.mode.0", "Lava Lake");
        add("ritual_magic.item.nether_scepter.mode.1", "Fire Aura");
        add("ritual_magic.item.nether_scepter.mode.2", "Temp setLevel");
        add("ritual_magic.item.speer.mode.0", "Traveling Mode");
        add("ritual_magic.item.speer.mode.1", "Telekinesis");
        add("ritual_magic.item.speer.mode.2", "Void Energy Beam");
        add("ritual_magic.item.dwarven_pickaxe.mode.0", "Select Dig AOE");
        add("ritual_magic.item.dwarven_pickaxe.mode.1", "Beginner's Luck");
        add("ritual_magic.item.dwarven_pickaxe.aoe", "Size: ");
        add("ritual_magic.item.soul_scythe.aoe", "Size: ");
    }

    private void deathMessages() {
        add("death.attack.sacrifice", "%1$s's Soul was sacrificed");
    }

    @Override
    protected void addTranslations() {
        blocks();
        blockEntities();
        creativeTabs();
        items();
        messages();
        effects();
        deathMessages();
    }

    public void addCreativeTab(CreativeModeTab key, String name) {
        add(key.getDisplayName().getString(), name);
    }
}
