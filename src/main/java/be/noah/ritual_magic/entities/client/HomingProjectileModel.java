package be.noah.ritual_magic.entities.client;

import be.noah.ritual_magic.RitualMagic;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

public class HomingProjectileModel<T extends Entity> extends HierarchicalModel<T> {
    private final ModelPart projectile;
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(
            new ResourceLocation(RitualMagic.MODID, "homing_projectile"), "main");

    public HomingProjectileModel(ModelPart root) {
        this.projectile = root.getChild("projectile");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition projectile = partdefinition.addOrReplaceChild("projectile", CubeListBuilder.create()
                        // Hauptkörper des Eisstachels
                        .texOffs(0, 0)
                        .addBox(-1.0F, -1.0F, -8.0F, 2.0F, 2.0F, 16.0F, new CubeDeformation(0.0F))
                        // Spitze
                        .texOffs(0, 18)
                        .addBox(-2.0F, -2.0F, 6.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                        // Eisige Details
                        .texOffs(20, 0)
                        .addBox(-1.5F, -1.5F, 0.0F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
                        .texOffs(20, 6)
                        .addBox(-1.5F, -1.5F, -4.0F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        // Eiskristalle
        PartDefinition crystal1 = projectile.addOrReplaceChild("crystal1", CubeListBuilder.create()
                        .texOffs(16, 18)
                        .addBox(-0.5F, -3.0F, -2.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition crystal2 = projectile.addOrReplaceChild("crystal2", CubeListBuilder.create()
                        .texOffs(20, 18)
                        .addBox(2.0F, -0.5F, -1.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 32, 32);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount,
                          float ageInTicks, float netHeadYaw, float headPitch) {
        projectile.xRot = headPitch * ((float)Math.PI / 180F);
        projectile.yRot = netHeadYaw * ((float)Math.PI / 180F);
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer,
                               int packedLight, int packedOverlay,
                               float red, float green, float blue, float alpha) {
        projectile.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }

    @Override
    public ModelPart root() {
        return projectile;
    }
}