package be.noah.ritual_magic.entities.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

public class DwarvenAxeModel<T extends Entity> extends HierarchicalModel<T> {
	private final ModelPart axe;
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("modid","DwarvenAxeModel"),"main");

	public DwarvenAxeModel(ModelPart root) {
		this.axe = root.getChild("axe");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition axe = partdefinition.addOrReplaceChild("axe", CubeListBuilder.create().texOffs(10, 11).addBox(-1.0486F, -6.7727F, -0.9994F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(-0.5486F, -11.0227F, -0.4994F, 1.0F, 21.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(8, 0).addBox(1.9514F, -11.5227F, -0.2494F, 1.0F, 11.75F, 0.5F, new CubeDeformation(0.0F))
		.texOffs(12, 3).addBox(0.9514F, -6.7727F, -0.4994F, 4.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(24, 7).addBox(4.9514F, -7.3227F, -0.4994F, 1.75F, 3.2F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(0, 24).addBox(-6.6736F, -7.4143F, -0.4994F, 1.75F, 3.2F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(12, 0).addBox(-4.9236F, -6.7643F, -0.4994F, 4.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(4, 0).addBox(-2.9236F, -11.6643F, -0.2494F, 1.0F, 11.75F, 0.5F, new CubeDeformation(0.0F))
		.texOffs(21, 4).addBox(-1.0486F, 9.7273F, -0.9994F, 2.0F, 0.5F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(21, 1).addBox(-1.0486F, 4.7273F, -0.9994F, 2.0F, 0.5F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0486F, 13.7727F, -0.0006F));

		PartDefinition cube_r1 = axe.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(4, 13).addBox(1.8633F, -3.9534F, -0.7475F, 1.75F, 3.5F, 1.5F, new CubeDeformation(0.0F))
		.texOffs(12, 6).addBox(-4.0116F, 1.7801F, -0.7475F, 3.5F, 1.75F, 1.49F, new CubeDeformation(0.0F))
		.texOffs(12, 15).addBox(-10.8576F, -6.7909F, -0.7475F, 1.75F, 3.5F, 1.5F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0139F, -0.646F, -0.0019F, 0.0F, 0.0F, 0.7854F));

		PartDefinition cube_r2 = axe.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(4, 19).addBox(-4.8017F, -5.8552F, -0.5975F, 1.75F, 3.5F, 1.2F, new CubeDeformation(0.0F))
		.texOffs(10, 21).addBox(6.9723F, -7.1099F, -0.5975F, 1.75F, 3.5F, 1.2F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0139F, -0.646F, -0.0019F, 0.0F, 0.0F, -0.3927F));

		PartDefinition cube_r3 = axe.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(20, 14).addBox(-8.7756F, -7.1945F, -0.5975F, 1.75F, 3.5F, 1.2F, new CubeDeformation(0.0F))
		.texOffs(19, 20).addBox(3.105F, -5.7706F, -0.5975F, 1.75F, 3.5F, 1.2F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0139F, -0.646F, -0.0019F, 0.0F, 0.0F, 0.3927F));

		PartDefinition cube_r4 = axe.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(18, 9).addBox(9.0244F, -6.7327F, -0.7475F, 1.75F, 3.5F, 1.49F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0139F, -0.646F, -0.0019F, 0.0F, 0.0F, -0.7854F));

		return LayerDefinition.create(meshdefinition, 32, 32);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		axe.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	@Override
	public ModelPart root() {
		return axe;
	}
}