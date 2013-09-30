package warmage;

import javax.swing.ImageIcon;

public class RockTerrain extends GenericTerrain {

	public RockTerrain(ImageIcon icon, int pos) {
		super(icon, pos);
		setTerrainType();
		setTerrainName();
	}

	private void setTerrainName() {
		terrainName = "Rock";
	}

	private void setTerrainType() {
		terrainType = 4;
	}

}
