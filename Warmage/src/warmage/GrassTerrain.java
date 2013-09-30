package warmage;

import javax.swing.ImageIcon;

public class GrassTerrain extends GenericTerrain {

	public GrassTerrain(ImageIcon icon, int pos) {
		super(icon, pos);
		setTerrainType();
		setTerrainName();
	}

	private void setTerrainName() {
		terrainName = "Grass";
	}

	private void setTerrainType() {
		terrainType = 3;
	}

}
