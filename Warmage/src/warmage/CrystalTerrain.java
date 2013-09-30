package warmage;

import javax.swing.ImageIcon;

public class CrystalTerrain extends GenericTerrain {

	public CrystalTerrain(ImageIcon icon, int pos) {
		super(icon, pos);
		setTerrainType();
		setTerrainName();
	}

	private void setTerrainName() {
		terrainName = "Crystal";
	}

	private void setTerrainType() {
		terrainType = 5;
	}

}
