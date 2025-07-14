package be.noah.ritual_magic.blocks.custom;


import be.noah.ritual_magic.multiblocks.MultiBlockStructure;
import be.noah.ritual_magic.multiblocks.StructureMemberBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.List;

public class RitualMemberBlock extends Block implements StructureMemberBlock {
    private final List<String> structureNames;

    public RitualMemberBlock(String structureName, Properties pProperties) {
        super(pProperties);
        this.structureNames = List.of(structureName);
    }
    public RitualMemberBlock(List<String> structureNames, Properties pProperties) {
        super(pProperties);
        this.structureNames = structureNames;
    }

    @Override
    public List<MultiBlockStructure> getPossibleStructures() {
        List<MultiBlockStructure> structures = new ArrayList<>();
//        TODO
//        Loader loader = new Loader();
//        for (String structureName : structureNames) {
//            structures.add(loader.load(structureName));
//        }
        return structures;
    }

    @Override
    public void onPlace(BlockState pState, Level pLevel, BlockPos pPos, BlockState pOldState, boolean pMovedByPiston) {
        super.onPlace(pState, pLevel, pPos, pOldState, pMovedByPiston);
        findAndMarkControllerDirty(pLevel, pPos);
    }

    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pMovedByPiston) {
        super.onRemove(pState, pLevel, pPos, pNewState, pMovedByPiston);
        findAndMarkControllerDirty(pLevel, pPos);
    }
}
