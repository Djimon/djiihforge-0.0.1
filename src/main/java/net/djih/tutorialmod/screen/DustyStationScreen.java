package net.djih.tutorialmod.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.djih.tutorialmod.TutorialMod;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class DustyStationScreen extends AbstractContainerScreen<DustyStationMenu> {

    private static final ResourceLocation TEXTURE =
            new ResourceLocation(TutorialMod.MOD_ID, "textures/gui/dusty_station_gui.png");
    public DustyStationScreen(DustyStationMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
    }

    //Draws the gui.png textutre on the screen
    @Override
    protected void renderBg(PoseStack pPoseStack, float pPartialTick, int pMouseX, int pMouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F,1.0F,1.0F, 1.0F);
        RenderSystem.setShaderTexture(0,TEXTURE);
        int x = (width - imageWidth) /2;
        int y = (height - imageHeight) /2;

        this.blit(pPoseStack,x,y,0,0,imageWidth,imageHeight);
    }

    @Override
    public void render(PoseStack pPoseStack, int pMouseX, int pMouseY, float delta) {
        renderBackground(pPoseStack);
        super.render(pPoseStack,pMouseX, pMouseY,delta);
        renderTooltip(pPoseStack,pMouseX,pMouseY);
    }
}
