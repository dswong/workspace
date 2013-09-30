package warmage;

import javax.swing.ImageIcon;

public class DirtTerrain extends GenericTerrain {

	public DirtTerrain(ImageIcon icon, int pos) {
		super(icon, pos);
		setTerrainType();
		setTerrainName();
	}

	private void setTerrainName() {
		terrainName = "Dirt";
	}

	private void setTerrainType() {
		terrainType = 2;
	}

}
