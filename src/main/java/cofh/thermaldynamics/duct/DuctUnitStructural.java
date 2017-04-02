package cofh.thermaldynamics.duct;

import cofh.thermaldynamics.duct.attachments.relay.Relay;
import cofh.thermaldynamics.duct.nutypeducts.DuctToken;
import cofh.thermaldynamics.duct.nutypeducts.DuctUnit;
import cofh.thermaldynamics.duct.nutypeducts.TileGrid;
import cofh.thermaldynamics.duct.tiles.TileStructuralDuct;
import net.minecraft.tileentity.TileEntity;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class DuctUnitStructural extends DuctUnit<DuctUnitStructural, GridStructural, Void> {

	@Nullable
	private final DuctUnit mainDuct;

	public DuctUnitStructural(TileGrid parent, DuctUnit mainDuct) {
		super(parent, mainDuct.getDuctType());
		this.mainDuct = mainDuct;
	}

	public DuctUnitStructural(TileStructuralDuct parent, Duct duct) {
		super(parent, duct);
		this.mainDuct = null;
	}

	@Override
	public DuctToken<DuctUnitStructural, GridStructural, Void> getToken() {
		return DuctToken.STRUCTURAL;
	}

	@Override
	public GridStructural createGrid() {
		return new GridStructural(world());
	}

	@Override
	public Void cacheTile(@Nonnull TileEntity tile, byte side) {
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean canConnectToOtherDuct(DuctUnit<DuctUnitStructural, GridStructural, Void> adjDuct, byte side) {
		return mainDuct == null || mainDuct.canConnectToOtherDuct(adjDuct.cast().mainDuct, side);
	}

	public void addRelays() {
		if (parent.attachmentData != null && grid != null) {
			for (Attachment attachment : parent.attachmentData.attachments) {
				if (attachment != null) {
					if (attachment.getId() == AttachmentRegistry.RELAY) {
						Relay signaller = (Relay) attachment;
						if (signaller.isInput()) {
							grid.addSignalInput(signaller);
						} else {
							grid.addSignalOutput(attachment);
						}
					} else if (attachment.respondsToSignallum()) {
						grid.addSignalOutput(attachment);
					}
				}
			}
		}
	}
}
