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

        if(menu.isCrafting()){
            //pX, pY: where to start drawing
            //pUOffset,pVOffset: where to copy from
            //pUWidth: how wide should be drawn from copy source
            //pVHeight: how far should be drawn -> % of ScaledProgress
            blit(pPoseStack,x+102,y+41,176,0,8, menu.getScaledProgress());
        }
    }

    @Override
    public void render(PoseStack pPoseStack, int pMouseX, int pMouseY, float delta) {
        renderBackground(pPoseStack);
        super.render(pPoseStack,pMouseX, pMouseY,delta);
        renderTooltip(pPoseStack,pMouseX,pMouseY);
    }
}
