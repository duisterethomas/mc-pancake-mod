package nl.duisterethomas.pancakemod.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.Containers;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import nl.duisterethomas.pancakemod.registry.ModBlockEntityTypes;

import java.util.ArrayList;
import java.util.List;

public class PancakeStackBlockEntity extends BlockEntity {
    private final List<ItemStack> pancakes = new ArrayList<>();

    public PancakeStackBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntityTypes.PANCAKE_STACK_ENTITY, pos, state);
    }

    public void addPancake(ItemStack stack) {
        pancakes.add(stack.copyWithCount(1));
        sync();
    }

    public ItemStack removeTopPancake() {
        if (pancakes.isEmpty()) {
            return ItemStack.EMPTY;
        }

        ItemStack topPancake = pancakes.removeLast();
        sync();

        return topPancake;
    }

    public ItemStack getTopPancake() {
        if (pancakes.isEmpty()) {
            return ItemStack.EMPTY;
        }

        return pancakes.getLast();
    }

    public List<ItemStack> getPancakes() {
        return pancakes;
    }

    private void sync() {
        setChanged();

        if (level != null && !level.isClientSide()) {
            BlockState state = getBlockState();

            if (!pancakes.isEmpty()) {
                level.setBlockAndUpdate(
                    worldPosition,
                    state.setValue(PancakeStackBlock.PANCAKE_COUNT, pancakes.size())
                );
            }
        }
    }

    @Override
    public void preRemoveSideEffects(BlockPos pos, BlockState state) {
        super.preRemoveSideEffects(pos, state);

        if (level != null && !level.isClientSide()) {
            for (ItemStack stack : pancakes) {
                Containers.dropItemStack(level, pos.getX(), pos.getY(), pos.getZ(), stack);
            }

            pancakes.clear();
        }
    }

    @Override
    protected void saveAdditional(ValueOutput output) {
        super.saveAdditional(output);
        output.store("pancakes", ItemStack.CODEC.listOf(), pancakes);
    }

    @Override
    protected void loadAdditional(ValueInput input) {
        super.loadAdditional(input);
        pancakes.clear();
        input.read("pancakes", ItemStack.CODEC.listOf()).ifPresent(pancakes::addAll);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        return saveWithoutMetadata(registries);
    }

    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }
}
