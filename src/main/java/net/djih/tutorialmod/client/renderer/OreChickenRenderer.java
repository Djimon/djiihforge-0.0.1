package net.djih.tutorialmod.client.renderer;

import net.djih.tutorialmod.TutorialMod;
import net.djih.tutorialmod.client.model.OreChickenModel;
import net.djih.tutorialmod.entity.custom.OreChicken;
import net.minecraft.client.model.ChickenModel;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

import java.util.List;

public class OreChickenRenderer extends MobRenderer<OreChicken, OreChickenModel> {

    private static final ResourceLocation TEXTURE = new ResourceLocation(TutorialMod.MOD_ID,"textures/entities/ore_chicken.png");

    public OreChickenRenderer(EntityRendererProvider.Context context) {
        super(context, new OreChickenModel(context.bakeLayer(OreChickenModel.LAYER_LOCATION)), 0.2f);
    }

    @Override
    public ResourceLocation getTextureLocation(OreChicken pEntity) {
        return TEXTURE;
    }

}
