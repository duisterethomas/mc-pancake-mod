package nl.duisterethomas.pancakemod;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import nl.duisterethomas.pancakemod.blocks.PancakeStackBlockEntity;
import org.jetbrains.annotations.Nullable;

public class PancakeStackRenderer
    implements BlockEntityRenderer<PancakeStackBlockEntity, PancakeStackRenderState> {
    private final ItemModelResolver itemModelResolver;

    public PancakeStackRenderer(BlockEntityRendererProvider.Context context) {
        this.itemModelResolver = context.itemModelResolver();
    }

    @Override
    public PancakeStackRenderState createRenderState() {
        return new PancakeStackRenderState();
    }

    @Override
    public void extractRenderState(PancakeStackBlockEntity blockEntity, PancakeStackRenderState state, float partialTick, Vec3 cameraPos, @Nullable ModelFeatureRenderer.CrumblingOverlay crumblingOverlay) {
        BlockEntityRenderer.super.extractRenderState(blockEntity, state, partialTick, cameraPos, crumblingOverlay);

        state.pancakes.clear();
        for (ItemStack stack : blockEntity.getPancakes()) {
            ItemStackRenderState item = new ItemStackRenderState();
            itemModelResolver.updateForTopItem(
                item, stack, ItemDisplayContext.NONE, blockEntity.getLevel(), null, 0);
            state.pancakes.add(item);
        }

        state.rotations.clear();
        state.rotations.addAll(blockEntity.getRotations());
    }

    @Override
    public void submit(PancakeStackRenderState state, PoseStack poseStack, SubmitNodeCollector collector, CameraRenderState cameraState) {
        for (int i = 0; i < state.pancakes.size(); i++) {
            poseStack.pushPose();
            // The texture is centered on its origin, 0.5 centers it on the block
            poseStack.translate(0.5, i / 16.0 + 0.5, 0.5);
            poseStack.mulPose(Axis.YP.rotationDegrees(state.rotations.get(i) * -90));
            state.pancakes.get(i).submit(poseStack, collector, state.lightCoords, OverlayTexture.NO_OVERLAY, 0);
            poseStack.popPose();
        }
    }
}
