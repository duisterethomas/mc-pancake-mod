package nl.duisterethomas.pancakemod;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import nl.duisterethomas.pancakemod.blocks.PancakeStackBlockEntity;

import java.util.List;

public class PancakeStackRenderer implements BlockEntityRenderer<PancakeStackBlockEntity> {
    private final ItemRenderer itemRenderer;

    public PancakeStackRenderer(BlockEntityRendererProvider.Context context) {
        this.itemRenderer = context.getItemRenderer();
    }

    @Override
    public void render(PancakeStackBlockEntity blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay, Vec3 cameraPos) {
        List<ItemStack> pancakes = blockEntity.getPancakes();
        List<Integer> rotations = blockEntity.getRotations();

        for (int i = 0; i < pancakes.size(); i++) {
            poseStack.pushPose();
            // The texture is centered on its origin, 0.5 centers it on the block
            poseStack.translate(0.5, i / 16.0 + 0.5, 0.5);
            poseStack.mulPose(Axis.YP.rotationDegrees(rotations.get(i) * -90));

            itemRenderer.renderStatic(
                pancakes.get(i),
                ItemDisplayContext.NONE,
                packedLight,
                packedOverlay,
                poseStack,
                bufferSource,
                blockEntity.getLevel(),
                0
            );
            poseStack.popPose();
        }
    }
}
