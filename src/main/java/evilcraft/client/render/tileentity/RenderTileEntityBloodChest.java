package evilcraft.client.render.tileentity;

import evilcraft.block.BloodChest;
import evilcraft.tileentity.TileBloodChest;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelChest;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import org.cyclops.cyclopscore.client.render.tileentity.RenderTileEntityModel;
import org.cyclops.cyclopscore.helper.BlockHelpers;

/**
 * Renderer for the {@link BloodChest}.
 * @author rubensworks
 *
 */
public class RenderTileEntityBloodChest extends RenderTileEntityModel<TileBloodChest, ModelBase> {
	
	/**
     * Make a new instance.
     * @param model The model to render.
     * @param texture The texture to render the model with.
     */
    public RenderTileEntityBloodChest(ModelBase model, ResourceLocation texture) {
        super(model, texture);
    }
    
    @Override
    protected void renderModel(TileBloodChest tile, ModelBase model, float partialTick, int destroyStage) {
        ModelChest modelchest = (ModelChest) model;
    	float lidangle = tile.prevLidAngle + (tile.lidAngle - tile.prevLidAngle) * partialTick;

        lidangle = 1.0F - lidangle;
        lidangle = 1.0F - lidangle * lidangle * lidangle;
        modelchest.chestLid.rotateAngleX = -(lidangle * (float)Math.PI / 2.0F);
        modelchest.renderAll();
    }
}
