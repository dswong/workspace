package warmage;

import java.awt.Insets;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class GenericTerrain extends JButton {

	protected int positionIndex, terrainType;
	protected ImageIcon terrainIcon;
	protected String terrainName;

	public GenericTerrain(ImageIcon icon, int pos) {
		super(icon);
		terrainIcon = icon;
		setMargin(new Insets(-2, -2, -2, -2));
		positionIndex = pos;
	}

	public ImageIcon getTerrainIcon() {
		return terrainIcon;
	}

	public String getTerrainName() {
		return terrainName;
	}

}
