package net.djih.tutorialmod.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;

public class MagicLampBlock extends Block {
    //Blockstate declaration the String in .create has to be used in the block_state .json to switch the texture
    //there also exists an IntegerProperty (or EnumProperty) and a DirectionProperty (Facing North, etc.)
    public static final BooleanProperty CLICKED = BooleanProperty.create("clicked");

    public MagicLampBlock(Properties properties){
        super(properties);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        //add each blockstate via comma separated list
        pBuilder.add(CLICKED);
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        //block interaciton need to be handled server side NOT client side.
        //you can change behaviour for each interactionHand (Main, Off)
        if(!pLevel.isClientSide() && pHand == InteractionHand.MAIN_HAND){
            boolean currentState = pState.getValue(CLICKED);
            pLevel.setBlock(pPos,pState.setValue(CLICKED, !currentState),3);
        }

        return InteractionResult.SUCCESS;
    }
}
