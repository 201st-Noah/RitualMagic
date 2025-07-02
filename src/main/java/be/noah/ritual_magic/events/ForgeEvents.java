package be.noah.ritual_magic.events;

import be.noah.ritual_magic.RitualMagic;
import be.noah.ritual_magic.blocks.AntiTeleportBlockData;
import be.noah.ritual_magic.blocks.BlockTier;
import be.noah.ritual_magic.blocks.RitualBaseBlockEntity;
import be.noah.ritual_magic.entities.LavaMinion;
import be.noah.ritual_magic.entities.ModEntities;
import be.noah.ritual_magic.items.armor.DwarvenArmor;
import be.noah.ritual_magic.items.armor.IceArmorItem;
import be.noah.ritual_magic.items.armor.SoulEaterArmor;
import be.noah.ritual_magic.items.custom.DwarvenAxe;
import be.noah.ritual_magic.items.custom.DwarvenPickAxe;
import be.noah.ritual_magic.mana.MetalToManaJsonReloadListener;
import be.noah.ritual_magic.mana.ManaNetworkData;
import be.noah.ritual_magic.mana.ManaPool;
import be.noah.ritual_magic.mana.ManaType;
import be.noah.ritual_magic.networking.ModMessages;
import be.noah.ritual_magic.networking.packet.ManaDataSyncS2CPacket;
import be.noah.ritual_magic.networking.packet.VoidShieldDataSyncS2CPacket;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityTeleportEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;

import java.util.*;


@Mod.EventBusSubscriber(modid = RitualMagic.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ForgeEvents {
    private static final Random random = new Random();
    private static final String VOID_SHIELD_TAG = "void_shield";
    private static final Set<BlockPos> HARVESTED_STONE_BLOCKS = new HashSet<>();
    private static final Set<BlockPos> HARVESTED_WOOD_BLOCKS = new HashSet<>();
    private static final boolean NOMANA = true;

    //TODO get rid of Code Duplicates
    private static boolean cancleLogic(LivingAttackEvent event){
        if ((event.getEntity() instanceof ServerPlayer player)){
            CompoundTag data = player.getPersistentData();
            int hitsLeft = data.getInt(VOID_SHIELD_TAG);
            Item boots = player.getInventory().getArmor(0).getItem();
            Item leggings = player.getInventory().getArmor(1).getItem();
            Item chesplate = player.getInventory().getArmor(2).getItem();
            Item helmet = player.getInventory().getArmor(3).getItem();
            DamageSource source = event.getSource();
            float amount = event.getAmount();

            // ----- IceArmor Logic -----
            if (chesplate instanceof IceArmorItem iceArmorItem && event.getSource().is(DamageTypes.FREEZE)) {
                return(iceArmorItem.hasFullSet(player));
            }

            if (hitsLeft > 0 && amount != Float.MAX_VALUE) {
                data.putInt(VOID_SHIELD_TAG, hitsLeft - 1);
                player.level().playSound(null, player.blockPosition(), SoundEvents.SHIELD_BLOCK, SoundSource.PLAYERS, 1f, 1f);
                if (hitsLeft == 1) {
                    data.remove(VOID_SHIELD_TAG);
                    player.level().playSound(null, player.blockPosition(), SoundEvents.SHIELD_BREAK, SoundSource.PLAYERS, 1f, 1f);
                    player.displayClientMessage(Component.literal("Void Shield Depleted"), true);
                }
                return true;
            }

            // ----- SoulEaterArmor Logic -----
            if (chesplate instanceof SoulEaterArmor soulEaterArmor && soulEaterArmor.hasFullSet(player)) {
                if(source.is(DamageTypes.IN_FIRE) ||
                        source.is(DamageTypes.ON_FIRE) ||
                        source.is(DamageTypes.LAVA) ||
                        source.is(DamageTypes.FIREBALL)){
                    return true;
                }
            }
            if (boots instanceof SoulEaterArmor soulEaterArmor && soulEaterArmor.hasBoots(player)) {
                if(source.is(DamageTypes.HOT_FLOOR)){
                    return true;
                }
            }


            // ----- DwarvenArmor Logic -----
            if(chesplate instanceof DwarvenArmor dwarvenArmor) {
                float damage  = event.getAmount();
                if(dwarvenArmor.hasFullSet(player) &&
                        !source.is(DamageTypes.FREEZE) &&
                        !source.is(DamageTypes.DROWN) &&
                        !source.is(DamageTypes.FELL_OUT_OF_WORLD) &&
                        !source.is(DamageTypes.IN_FIRE) &&
                        !source.is(DamageTypes.ON_FIRE) &&
                        !source.is(DamageTypes.LAVA) &&
                        !source.is(DamageTypes.HOT_FLOOR) &&
                        !source.is(DamageTypes.STARVE)) {
                    float fullSetLevel = dwarvenArmor.fullSetLevel(player);
                    return (damage <= fullSetLevel/2);
                }
            }
        }
        return false;
    }
    // Done with the help of https://github.com/CoFH/CoFHCore/blob/1.19.x/src/main/java/cofh/core/event/AreaEffectEvents.java
    // Don't be a jerk License
    @SubscribeEvent
    public static void onDiggerToolUsage(BlockEvent.BreakEvent event) {
        Player player = event.getPlayer();
        ItemStack mainHandItem = player.getMainHandItem();
        if (player instanceof ServerPlayer serverPlayer) {
            MinecraftServer minecraftServer = serverPlayer.getServer();

            if (mainHandItem.getItem() instanceof DwarvenPickAxe dwarvenPickAxe && dwarvenPickAxe.getDigAoe(mainHandItem) != 0) {
                ManaNetworkData data = ManaNetworkData.get(minecraftServer);
                BlockPos initialBlockPos = event.getPos();
                if (HARVESTED_STONE_BLOCKS.contains(initialBlockPos)) {
                    return;
                }

                for (BlockPos pos : DwarvenPickAxe.getBlocksToBeDestroyed(dwarvenPickAxe.getDigAoe(mainHandItem), initialBlockPos, serverPlayer)) {
                    if (pos == initialBlockPos || !dwarvenPickAxe.isCorrectToolForDrops(mainHandItem, event.getLevel().getBlockState(pos))) {
                        continue;
                    }
                    if (data.consume(player.getUUID(), ManaType.DWARVEN, 1) || serverPlayer.isCreative()) {
                        HARVESTED_STONE_BLOCKS.add(pos);
                        serverPlayer.gameMode.destroyBlock(pos);
                        HARVESTED_STONE_BLOCKS.remove(pos);
                    } else {
                        break;
                    }
                }
            } else if (mainHandItem.getItem() instanceof DwarvenAxe dwarvenAxe && player.isShiftKeyDown()) {
                ManaNetworkData data = ManaNetworkData.get(minecraftServer);
                BlockPos initialBlockPos = event.getPos();
                if (HARVESTED_WOOD_BLOCKS.contains(initialBlockPos)) {
                    return;
                }
                List<BlockPos> logList = new ArrayList<>();
                int maxLogs = (dwarvenAxe.getItemLevel(mainHandItem) * 3) + 15;
                for (BlockPos pos : DwarvenAxe.getLogsToBeDestroyed(maxLogs, initialBlockPos, serverPlayer)) {
                    if (pos == initialBlockPos || !dwarvenAxe.isCorrectToolForDrops(mainHandItem, event.getLevel().getBlockState(pos))) {
                        continue;
                    }
                    if (serverPlayer.isCreative() || data.consume(player.getUUID(), ManaType.DWARVEN, 1)) {
                        HARVESTED_WOOD_BLOCKS.add(pos);
                        serverPlayer.gameMode.destroyBlock(pos);
                        HARVESTED_WOOD_BLOCKS.remove(pos);
                        logList.add(pos);
                    } else {
                        break;
                    }
                }
                for (BlockPos pos : DwarvenAxe.getLeavesToBeDestroyed(logList, serverPlayer)) {
                    serverPlayer.gameMode.destroyBlock(pos);
                }
            }
        }
    }

    @SubscribeEvent
    public static void onLivingHurt(LivingAttackEvent event) {
        event.setCanceled(cancleLogic(event));
    }

    @SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event) {
        Entity entity = event.getEntity();
        Entity attacker = event.getSource().getDirectEntity();
        float damage = event.getAmount();
        //Debuging (und nein kein Bock das mit Breakpoints etc. zu machen)
        if (entity instanceof Player player && false){
            double defensePoints = player.getAttribute(Attributes.ARMOR).getValue();
            double toughness = player.getAttribute(Attributes.ARMOR_TOUGHNESS).getValue();
            double rec = damage * (1-((Math.min(20, Math.max((defensePoints/2), defensePoints - (damage/(2+(toughness/4))))))/25));
            System.out.println("defensePoints: " + defensePoints );
            System.out.println("toughness: " + toughness);
            System.out.println("damage dealt: " + damage + " |damage recieved " + rec);
            System.out.println("-------------------------------------------");
        }
        event.setCanceled(false);
    }


    @SubscribeEvent
    public static void onPlayerHurt(LivingHurtEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) return;

        CompoundTag data = player.getPersistentData();
        int hitsLeft = data.getInt(VOID_SHIELD_TAG);
        Item boots = player.getInventory().getArmor(0).getItem();
        Item leggings = player.getInventory().getArmor(1).getItem();
        Item chesplate = player.getInventory().getArmor(2).getItem();
        Item helmet = player.getInventory().getArmor(3).getItem();
        Level plevel = player.level();
        DamageSource source = event.getSource();


        // ----- DwarvenArmor Logic -----
        if(chesplate instanceof DwarvenArmor dwarvenArmor) {
            float damage  = event.getAmount();
            if(dwarvenArmor.hasFullSet(player) &&
                    !source.is(DamageTypes.FREEZE) &&
                    !source.is(DamageTypes.DROWN) &&
                    !source.is(DamageTypes.FELL_OUT_OF_WORLD) &&
                    !source.is(DamageTypes.IN_FIRE) &&
                    !source.is(DamageTypes.ON_FIRE) &&
                    !source.is(DamageTypes.LAVA) &&
                    !source.is(DamageTypes.HOT_FLOOR) &&
                    !source.is(DamageTypes.STARVE)) {
                float fullSetLevel = dwarvenArmor.fullSetLevel(player);
                //damage = damage * (Math.min(100, (102 - fullSetLevel))/100);
                damage = damage * ((100 - (fullSetLevel * 0.98f))/100);
            }
            event.setAmount(damage);
        }

        // ----- IceArmor Logic -----
        if(helmet instanceof IceArmorItem iceArmorItem && hitsLeft == 0){
            player.addEffect(new MobEffectInstance(MobEffects.INVISIBILITY, iceArmorItem.helmetLevel(player)*10, 0, false, false));
        }

        if (chesplate instanceof SoulEaterArmor soulEaterArmor && soulEaterArmor.hasFullSet(player) && source.getDirectEntity() instanceof LivingEntity target) {
            LavaMinion entity = new LavaMinion(ModEntities.LAVA_MINION.get(), plevel);
            entity.setPos(player.getX(), player.getY() + 1, player.getZ());
            entity.setOwner(player.getUUID());
            plevel.addFreshEntity(entity);

        }
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.side == LogicalSide.SERVER && event.player instanceof ServerPlayer serverPlayer) {
            CompoundTag tag = serverPlayer.getPersistentData();
            int hitsLeft = tag.getInt(VOID_SHIELD_TAG);
            ModMessages.sendToPlayer(new VoidShieldDataSyncS2CPacket(hitsLeft), serverPlayer);
        }
    }

    @SubscribeEvent
    public static void onServerTick(TickEvent.ServerTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            for (ServerLevel level : event.getServer().getAllLevels()) {
                ManaNetworkData.get(level.getServer()).syncDirty(level);
                TelekinesisHandler.tickAll(level);
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) return;

        MinecraftServer minecraftServer = player.getServer();
        ManaNetworkData data = ManaNetworkData.get(minecraftServer);
        ManaPool pool = data.getOrCreate(player.getUUID());

        CompoundTag tag = pool.serialize();

        // ✅ Send the sync packet
        ModMessages.sendToPlayer(new ManaDataSyncS2CPacket(player.getUUID(), tag), player);
    }

    @SubscribeEvent
    public static void onEntityTeleport(EntityTeleportEvent event) {
        if (event.getEntity() == null || event.getEntity().level().isClientSide()) return;

        BlockPos targetPos = new BlockPos((int)event.getTargetX(), (int)event.getTargetY(), (int)event.getTargetZ());
        BlockPos startPos = new BlockPos((int)event.getPrevX(), (int)event.getPrevY(), (int)event.getPrevZ());

        MinecraftServer minecraftServer = event.getEntity().level().getServer();
        ManaNetworkData data = ManaNetworkData.get(minecraftServer);

        for (BlockTier tier : BlockTier.values()) {
            Entity entity = event.getEntity();
            for (BlockPos blockPos : AntiTeleportBlockData.get((ServerLevel) entity.level()).get(tier)) {

                BlockTier blockTier = RitualBaseBlockEntity.getBlockTier(entity.level(), blockPos);
                if (blockTier == tier && ( blockPos.closerThan(targetPos, tier.getRange()) || blockPos.closerThan(startPos, tier.getRange()))) {
                    RitualBaseBlockEntity be = (RitualBaseBlockEntity) entity.level().getBlockEntity(blockPos);
                    UUID owner = be.getOwner();
                    ManaPool pool = data.getOrCreate(owner);
                    if (!owner.equals(entity.getUUID()) && (NOMANA ||pool.get(ManaType.DRACONIC) > 0)) { //TODO maby later implement a lis of allowed Players
                        event.setCanceled(true);
                        if (entity instanceof LivingEntity livingEntity) {
                            entity.hurt(entity.level().damageSources().cramming(), Math.min(livingEntity.getMaxHealth() * tier.getDamagePercent(), 100 * tier.getDamagePercent()));
                            if (livingEntity instanceof ServerPlayer serverPlayer) {
                                serverPlayer.playNotifySound(SoundEvents.SHIELD_BLOCK, SoundSource.PLAYERS, 1.0f, 1.0f);
                            }
                        }
                        entity.sendSystemMessage(Component.literal("Teleportation denied by Anti-Teleport Field!"));
                        return;
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void onDataReload(AddReloadListenerEvent event) {
        event.addListener(new MetalToManaJsonReloadListener());
    }
}
