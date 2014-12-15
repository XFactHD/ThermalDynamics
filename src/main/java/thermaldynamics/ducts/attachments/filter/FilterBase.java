package thermaldynamics.ducts.attachments.filter;

import cofh.core.network.PacketCoFHBase;
import cofh.core.render.RenderUtils;
import cofh.repack.codechicken.lib.vec.Cuboid6;
import cofh.repack.codechicken.lib.vec.Translation;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import thermaldynamics.ThermalDynamics;
import thermaldynamics.block.BlockDuct;
import thermaldynamics.block.TileMultiBlock;
import thermaldynamics.ducts.attachments.ConnectionBase;
import thermaldynamics.render.RenderDuct;

import java.util.LinkedList;
import java.util.List;

public abstract class FilterBase extends ConnectionBase {

    public FilterBase(TileMultiBlock tile, byte side) {
        super(tile, side);
    }

    public FilterBase(TileMultiBlock tile, byte side, int type) {
        super(tile, side, type);

    }

    @Override
    public TileMultiBlock.NeighborTypes getNeighbourType() {
        return isValidInput ? TileMultiBlock.NeighborTypes.OUTPUT : TileMultiBlock.NeighborTypes.DUCT_ATTACHMENT;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean render(int pass, RenderBlocks renderBlocks) {
        if (pass == 1)
            return false;

        Translation trans = RenderUtils.getRenderVector(tile.xCoord + 0.5, tile.yCoord + 0.5, tile.zCoord + 0.5).translation();
        RenderDuct.modelConnection[stuffed ? 2 : 1][side].render(trans, RenderUtils.getIconTransformation(RenderDuct.filterTexture[type]));
        return true;
    }


    @Override
    public ItemStack getPickBlock() {
        return new ItemStack(ThermalDynamics.itemFilter, 1, type);
    }


}
