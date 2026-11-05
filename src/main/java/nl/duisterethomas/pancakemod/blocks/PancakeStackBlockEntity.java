package nl.duisterethomas.pancakemod.blocks;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DynamicOps;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import nl.duisterethomas.pancakemod.registry.ModBlockEntityTypes;

import java.util.ArrayList;
import java.util.List;

public class PancakeStackBlockEntity extends BlockEntity {
    private final List<ItemStack> pancakes = new ArrayList<>();
    private final List<Integer> rotations = new ArrayList<>();

    public PancakeStackBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntityTypes.PANCAKE_STACK_ENTITY, pos, state);
    }

    public void addPancake(ItemStack stack, int rotation) {
        pancakes.add(stack.copyWithCount(1));
        rotations.add(rotation);
        sync();
    }

    public ItemStack removeTopPancake() {
        if (pancakes.isEmpty()) {
            return ItemStack.EMPTY;
        }

        ItemStack topPancake = pancakes.removeLast();
        if (!rotations.isEmpty()) {
            rotations.removeLast();
        }

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

    public List<Integer> getRotations() {
        return rotations;
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
    protected void saveAdditional(CompoundTag nbt, HolderLookup.Provider registryLookup) {
        DynamicOps<Tag> ops = registryLookup.createSerializationContext(NbtOps.INSTANCE);

        nbt.put("pancakes", ItemStack.CODEC.listOf().encodeStart(ops, pancakes).getOrThrow());
        nbt.put("rotations", Codec.INT.listOf().encodeStart(ops, rotations).getOrThrow());

        super.saveAdditional(nbt, registryLookup);
    }

    @Override
    protected void loadAdditional(CompoundTag nbt, HolderLookup.Provider registryLookup) {
        super.loadAdditional(nbt, registryLookup);

        DynamicOps<Tag> ops = registryLookup.createSerializationContext(NbtOps.INSTANCE);

        pancakes.clear();
        rotations.clear();

        ItemStack.CODEC.listOf()
                .parse(ops, nbt.get("pancakes"))
                .result()
                .ifPresent(pancakes::addAll);

        Codec.INT.listOf()
                .parse(ops, nbt.get("rotations"))
                .result()
                .ifPresent(rotations::addAll);
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
