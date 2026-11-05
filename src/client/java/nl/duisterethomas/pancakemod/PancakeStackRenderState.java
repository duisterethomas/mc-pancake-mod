package nl.duisterethomas.pancakemod;

import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.client.renderer.item.ItemStackRenderState;

import java.util.ArrayList;
import java.util.List;

public class PancakeStackRenderState extends BlockEntityRenderState {
    public final List<ItemStackRenderState> pancakes = new ArrayList<>();
    public final List<Integer> rotations = new ArrayList<>();
}

