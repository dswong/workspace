package warmage;

import javax.swing.ImageIcon;

public class SandTerrain extends GenericTerrain {

	public SandTerrain(ImageIcon icon, int pos) {
		super(icon, pos);
		setTerrainType();
		setTerrainName();
	}

	private void setTerrainName() {
		terrainName = "Desert";
	}

	private void setTerrainType() {
		terrainType = 1;
	}

}
