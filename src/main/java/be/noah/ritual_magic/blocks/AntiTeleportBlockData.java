package be.noah.ritual_magic.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.LongTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.saveddata.SavedData;

import java.util.HashSet;
import java.util.Set;

public class AntiTeleportBlockData extends SavedData {
    private static final String DATA_NAME = "anti_teleport_blocks";

    private final Set<BlockPos> b_list = new HashSet<>();
    private final Set<BlockPos> i_list = new HashSet<>();
    private final Set<BlockPos> a_list = new HashSet<>();
    private final Set<BlockPos> u_list = new HashSet<>();

    public static AntiTeleportBlockData get(ServerLevel level) {
        return level.getDataStorage().computeIfAbsent(
                AntiTeleportBlockData::load,
                AntiTeleportBlockData::new,
                DATA_NAME
        );
    }

    public Set<BlockPos> get(BlockTier tier) {
        return switch (tier) {
            case BASIC -> b_list;
            case INTERMEDIATE -> i_list;
            case ADVANCED -> a_list;
            case ULTIMATE -> u_list;
        };
    }

    public void add(BlockTier tier, BlockPos pos) {
        get(tier).add(pos);
        setDirty();
    }

    public void remove(BlockTier tier, BlockPos pos) {
        get(tier).remove(pos);
        setDirty();
    }

    // === SAVE ===
    @Override
    public CompoundTag save(CompoundTag tag) {
        tag.put("basic", saveSet(b_list));
        tag.put("intermediate", saveSet(i_list));
        tag.put("advanced", saveSet(a_list));
        tag.put("ultimate", saveSet(u_list));
        return tag;
    }

    public static AntiTeleportBlockData load(CompoundTag tag) {
        AntiTeleportBlockData data = new AntiTeleportBlockData();
        data.b_list.addAll(loadSet(tag.getList("basic", Tag.TAG_LONG)));
        data.i_list.addAll(loadSet(tag.getList("intermediate", Tag.TAG_LONG)));
        data.a_list.addAll(loadSet(tag.getList("advanced", Tag.TAG_LONG)));
        data.u_list.addAll(loadSet(tag.getList("ultimate", Tag.TAG_LONG)));
        return data;
    }

    private static ListTag saveSet(Set<BlockPos> set) {
        ListTag list = new ListTag();
        for (BlockPos pos : set) {
            list.add(LongTag.valueOf(pos.asLong()));
        }
        return list;
    }

    private static Set<BlockPos> loadSet(ListTag list) {
        Set<BlockPos> set = new HashSet<>();
        for (Tag tag : list) {
            set.add(BlockPos.of(((LongTag) tag).getAsLong()));
        }
        return set;
    }
}
