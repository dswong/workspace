package warmage;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;
import javax.swing.Timer;

public class MainFrame extends JFrame {

	private JLayeredPane zOrder;
	private static JPanel terrainPane;
	private static JPanel unitsPane, infoPane;
	private JPanel unitANDterrainContainer;
	private final static Dimension screenSize = new Dimension(
			GraphicsEnvironment.getLocalGraphicsEnvironment()
					.getMaximumWindowBounds().width,
			GraphicsEnvironment.getLocalGraphicsEnvironment()
					.getMaximumWindowBounds().height);
	private static JScrollPane jScrollPane;
	private GridLayout unitORterrainPaneLayout;
	private final ImageIcon[] terrainTiles = {
			new ImageIcon("images/Grass_One.jpg"),
			new ImageIcon("images/Grass_Two.jpg"),
			new ImageIcon("images/Grass_Three.jpg"),
			new ImageIcon("images/Grass_Four.jpg"),
			new ImageIcon("images/Grass_Five.jpg"),
			new ImageIcon("images/Grass_Six.jpg"),
			new ImageIcon("images/Grass_Seven.jpg"),
			new ImageIcon("images/Grass_Eight.jpg"),
			new ImageIcon("images/Grass_Nine.jpg"),
			new ImageIcon("images/Dirt_One.jpg"),
			new ImageIcon("images/Dirt_Two.jpg"),
			new ImageIcon("images/Dirt_Three.jpg"),
			new ImageIcon("images/Dirt_Four.jpg"),
			new ImageIcon("images/Dirt_Five.jpg"),
			new ImageIcon("images/Dirt_Six.jpg"),
			new ImageIcon("images/Dirt_Seven.jpg"),
			new ImageIcon("images/Dirt_Eight.jpg"),
			new ImageIcon("images/Dirt_Nine.jpg"),
			new ImageIcon("images/Rock_One.jpg"),
			new ImageIcon("images/Rock_Two.jpg"),
			new ImageIcon("images/Rock_Three.jpg"),
			new ImageIcon("images/Rock_Four.jpg"),
			new ImageIcon("images/Rock_Five.jpg"),
			new ImageIcon("images/Rock_Six.jpg"),
			new ImageIcon("images/Rock_Seven.jpg"),
			new ImageIcon("images/Rock_Eight.jpg"),
			new ImageIcon("images/Rock_Nine.jpg"),
			new ImageIcon("images/Sand_One.jpg"),
			new ImageIcon("images/Sand_Two.jpg"),
			new ImageIcon("images/Sand_Three.jpg"),
			new ImageIcon("images/Sand_Four.jpg"),
			new ImageIcon("images/Sand_Five.jpg"),
			new ImageIcon("images/Sand_Six.jpg"),
			new ImageIcon("images/Sand_Seven.jpg"),
			new ImageIcon("images/Sand_Eight.jpg"),
			new ImageIcon("images/Sand_Nine.jpg"),
			new ImageIcon("images/Crystal_One.jpg"),
			new ImageIcon("images/Crystal_Two.jpg"),
			new ImageIcon("images/Crystal_Three.jpg"),
			new ImageIcon("images/Crystal_Four.jpg"),
			new ImageIcon("images/Crystal_Five.jpg"),
			new ImageIcon("images/Crystal_Six.jpg"),
			new ImageIcon("images/Crystal_Seven.jpg"),
			new ImageIcon("images/Crystal_Eight.jpg"),
			new ImageIcon("images/Crystal_Nine.jpg") };
	private static int turnCount = 1;
	private final GenericUnit activeUnit = null;
	private boolean leftPressed = false, upPressed = false,
			rightPressed = false, downPressed = false;
	private final boolean northWest = false;
	private final boolean north = false;
	private final boolean northEast = false;
	private final boolean east = false;
	private final boolean southEast = false;
	private final boolean south = false;
	private final boolean southWest = false;
	private final boolean west = false;

	public MainFrame() {
		super("Warmage Alpha");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setUndecorated(true);
		setPreferredSize(screenSize);
		setMaximumSize(screenSize);
		establishUnitAndTerrainPaneLayout();
		estabishTerrainPane();
		establishUnitsPane();
		establishUnitsANDTerrainPaneContainer();
		add(jScrollPane, BorderLayout.CENTER);
		establishInfoPane();
		add(infoPane, BorderLayout.PAGE_END);

		Timer gameStarter = new Timer(100, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				Object[] options = { "START" };

				int n = JOptionPane.showOptionDialog(null,
						"Press the START button to begin", null,
						JOptionPane.OK_OPTION, JOptionPane.PLAIN_MESSAGE, null,
						options, options[0]);
				if (n == JOptionPane.OK_OPTION) {
					beginGame();
				}
			}
		});

		TransparentOverlay transparentOverlay = new TransparentOverlay();
		transparentOverlay.setOpaque(false);
		setGlassPane(transparentOverlay);
		transparentOverlay.setVisible(true);

		gameStarter.setRepeats(false);
		gameStarter.start();
		pack();
		setExtendedState(MAXIMIZED_BOTH);
		unitsPane.requestFocusInWindow();
	}

	public static void displayInformation(int pi) {
		GenericUnit sourceUnit = (GenericUnit) unitsPane.getComponent(pi);

		((JLabel) infoPane.getComponent(2)).setIcon(sourceUnit.getIcon());

		((JLabel) infoPane.getComponent(3)).setText("<html>Unit Name: "
				+ sourceUnit.getUnitName() + "<br>Hit Points: "
				+ sourceUnit.getHitPoints() + "<br>Attack: "
				+ sourceUnit.getAttack() + "<br>Defense: "
				+ sourceUnit.getDefense() + "<br>Mana: " + sourceUnit.getMana()
				+ "<br>Damage: " + sourceUnit.getDamage() + "<br>Speed: "
				+ sourceUnit.getSpeed() + "</html>");
	}

	public static void displayTerrainInformation(int pi) {
		GenericTerrain sourceTerrain = (GenericTerrain) terrainPane
				.getComponent(pi);

		((JLabel) infoPane.getComponent(2)).setIcon(sourceTerrain
				.getTerrainIcon());

		((JLabel) infoPane.getComponent(3)).setText("<html>Terrain Name: "
				+ sourceTerrain.getTerrainName() + "</html>");
	}

	public static void generateHighlight(int pos, int spd) {
		int row = pos / 144, col = pos % 144;

		for (int r = row - spd; r <= row + spd; r++) {
			for (int c = col - spd; c <= col + spd; c++) {
				try {
					((GenericUnit) unitsPane.getComponent(r * 144 + c))
							.updateHighlight();
				} catch (Exception e) {
					// Do Nothing
				}
			}
		}
	}

	public static void removeHighlight(int pos, int spd) {
		int row = pos / 144, col = pos % 144;

		for (int r = row - spd; r <= row + spd; r++) {
			for (int c = col - spd; c <= col + spd; c++) {
				try {
					((GenericUnit) unitsPane.getComponent(r * 144 + c))
							.removeHighlight();
				} catch (Exception e) {
					// Do Nothing
				}
			}
		}
	}

	public static void tryMove(MouseEvent arg0, int src, int spd) {
		int dest;

		if (arg0.getLocationOnScreen().y >= infoPane.getLocation().getY() + 25) {
			removeHighlight(src, spd);
			return;
		}

		try {
			dest = ((GenericUnit) (unitsPane.getComponentAt(
					arg0.getLocationOnScreen().x
							+ jScrollPane.getViewport().getViewPosition().x,
					arg0.getLocationOnScreen().y
							+ jScrollPane.getViewport().getViewPosition().y)))
					.getPositionIndex();
		} catch (Exception e) {
			removeHighlight(src, spd);
			return;
		}

		removeHighlight(src, spd);

		int srcRow = src / 144, destRow = dest / 144;
		int srcCol = src % 144, destCol = dest % 144;

		if (destRow >= srcRow - spd
				&& destRow <= srcRow + spd
				&& destCol >= srcCol - spd
				&& destCol <= srcCol + spd
				&& (destRow != srcRow || destCol != srcCol)
				&& ((GenericUnit) unitsPane.getComponent(dest)).getUnitType() == 0) {
			GenericUnit destUnit = new GenericUnit(
					(GenericUnit) unitsPane.getComponent(dest));
			int srcUnitType = ((GenericUnit) unitsPane.getComponent(src))
					.getUnitType();

			switch (srcUnitType) {
			case 1:
				NoviceMagician srcMage = new NoviceMagician(
						(NoviceMagician) unitsPane.getComponent(src));
				srcMage.setCanMove(false);
				unitsPane.remove(dest);
				unitsPane.add(srcMage, dest);
				srcMage.setPositionIndex(dest);
				unitsPane.remove(src);
				unitsPane.add(destUnit, src);
				destUnit.setPositionIndex(src);
				break;

			default:
				GenericUnit srcUnit = new GenericUnit(src, "");
				unitsPane.remove(dest);
				unitsPane.add(srcUnit, dest);
				srcUnit.setPositionIndex(dest);
				unitsPane.remove(src);
				unitsPane.add(destUnit, src);
				destUnit.setPositionIndex(src);
			}

			unitsPane.revalidate();
			unitsPane.repaint();
		}
	}

	protected static boolean roundComplete() {
		return turnCount % 2 == 0;
	}

	public void endTurn() {
		if (turnCount % 2 != 0) {
			GenericUnit temp;
			for (int i = 0; i < unitsPane.getComponentCount(); i++) {
				temp = (GenericUnit) unitsPane.getComponent(i);
				if (temp.getUnitType() > 0
						&& !temp.getPlayerName().equals("Player 2")) {
					temp.setVisible(false);
				} else if (temp.getUnitType() > 0) {
					temp.setVisible(true);
					temp.setCanMove(true);
				}
			}

			int p2Start = -1;

			for (int i = 0; i < unitsPane.getComponentCount(); i++) {
				if (((GenericUnit) unitsPane.getComponent(i)).getUnitType() == 1
						&& ((GenericUnit) unitsPane.getComponent(i))
								.getPlayerName().equals("Player 2")) {
					p2Start = i;
					break;
				}
			}

			for (int i = 0; i < unitsPane.getComponentCount(); i++) {
				if (((GenericUnit) unitsPane.getComponent(i)).getUnitType() > 0
						&& ((GenericUnit) unitsPane.getComponent(i))
								.getPlayerName().equals("Player 2")) {
					updateVision(i);
				}
			}

			displayInformation(p2Start);
			scrollWindowToUnit(p2Start);
			((TurnTimer) infoPane.getComponent(0)).start(roundComplete());
			turnCount++;
		} else {
			GenericUnit temp;
			for (int i = 0; i < unitsPane.getComponentCount(); i++) {
				temp = (GenericUnit) unitsPane.getComponent(i);
				if (temp.getUnitType() > 0
						&& !temp.getPlayerName().equals("Player 1")) {
					temp.setVisible(false);
				} else if (temp.getUnitType() > 0) {
					temp.setVisible(true);
					temp.setCanMove(true);
				}
			}

			int p1Start = -1;

			for (int i = 0; i < unitsPane.getComponentCount(); i++) {
				if (((GenericUnit) unitsPane.getComponent(i)).getUnitType() == 1
						&& ((GenericUnit) unitsPane.getComponent(i))
								.getPlayerName().equals("Player 1")) {
					p1Start = i;
					break;
				}
			}

			for (int i = 0; i < unitsPane.getComponentCount(); i++) {
				if (((GenericUnit) unitsPane.getComponent(i)).getUnitType() > 0
						&& ((GenericUnit) unitsPane.getComponent(i))
								.getPlayerName().equals("Player 1")) {
					updateVision(i);
				}
			}

			displayInformation(p1Start);
			scrollWindowToUnit(p1Start);
			((TurnTimer) infoPane.getComponent(0)).start(roundComplete());
			turnCount++;
		}

		unitsPane.requestFocusInWindow();
	}

	public void escapeGame() {
		System.exit(0);
	}

	public void scrollDown() {
		Point upperLeft = jScrollPane.getViewport().getViewPosition();
		Dimension visibleSize = jScrollPane.getViewport().getExtentSize();

		if (upperLeft.getY() == unitANDterrainContainer.getHeight()
				- visibleSize.getHeight()) {
			return;
		}

		jScrollPane.getViewport().setViewPosition(
				new Point(jScrollPane.getViewport().getViewRect().x,
						jScrollPane.getViewport().getViewRect().y + 25));
	}

	public void scrollLeft() {
		Point upperLeft = jScrollPane.getViewport().getViewPosition();

		if (upperLeft.getX() == 0) {
			return;
		}

		jScrollPane.getViewport().setViewPosition(
				new Point(jScrollPane.getViewport().getViewRect().x - 25,
						jScrollPane.getViewport().getViewRect().y));
	}

	public void scrollNorthEast() {
		Point upperLeft = jScrollPane.getViewport().getViewPosition();
		Dimension visibleSize = jScrollPane.getViewport().getExtentSize();

		if (upperLeft.getX() == unitANDterrainContainer.getWidth()
				- visibleSize.getWidth()
				&& upperLeft.getY() == 0) {
			return;
		}

		if (upperLeft.getY() == 0) {
			jScrollPane.getViewport().setViewPosition(
					new Point(jScrollPane.getViewport().getViewRect().x + 25,
							jScrollPane.getViewport().getViewRect().y));
			return;
		}

		if (upperLeft.getX() == unitANDterrainContainer.getWidth()
				- visibleSize.getWidth()) {
			jScrollPane.getViewport().setViewPosition(
					new Point(jScrollPane.getViewport().getViewRect().x,
							jScrollPane.getViewport().getViewRect().y - 25));
			return;
		}

		jScrollPane.getViewport().setViewPosition(
				new Point(jScrollPane.getViewport().getViewRect().x + 25,
						jScrollPane.getViewport().getViewRect().y - 25));
	}

	public void scrollNorthWest() {
		Point upperLeft = jScrollPane.getViewport().getViewPosition();

		if (upperLeft.getX() == 0 && upperLeft.getY() == 0) {
			return;
		}

		if (upperLeft.getY() == 0) {
			jScrollPane.getViewport().setViewPosition(
					new Point(jScrollPane.getViewport().getViewRect().x - 25,
							jScrollPane.getViewport().getViewRect().y));
			return;
		}

		if (upperLeft.getX() == 0) {
			jScrollPane.getViewport().setViewPosition(
					new Point(jScrollPane.getViewport().getViewRect().x,
							jScrollPane.getViewport().getViewRect().y - 25));
			return;
		}

		jScrollPane.getViewport().setViewPosition(
				new Point(jScrollPane.getViewport().getViewRect().x - 25,
						jScrollPane.getViewport().getViewRect().y - 25));
	}

	public void scrollRight() {
		Point upperLeft = jScrollPane.getViewport().getViewPosition();
		Dimension visibleSize = jScrollPane.getViewport().getExtentSize();

		if (upperLeft.getX() == unitANDterrainContainer.getWidth()
				- visibleSize.getWidth()) {
			return;
		}

		jScrollPane.getViewport().setViewPosition(
				new Point(jScrollPane.getViewport().getViewRect().x + 25,
						jScrollPane.getViewport().getViewRect().y));
	}

	public void scrollSouthEast() {
		Point upperLeft = jScrollPane.getViewport().getViewPosition();
		Dimension visibleSize = jScrollPane.getViewport().getExtentSize();

		if (upperLeft.getX() == unitANDterrainContainer.getWidth()
				- visibleSize.getWidth()
				&& upperLeft.getY() == unitANDterrainContainer.getHeight()
						- visibleSize.getHeight()) {
			return;
		}

		if (upperLeft.getY() == unitANDterrainContainer.getHeight()
				- visibleSize.getHeight()) {
			jScrollPane.getViewport().setViewPosition(
					new Point(jScrollPane.getViewport().getViewRect().x + 25,
							jScrollPane.getViewport().getViewRect().y));
			return;
		}

		if (upperLeft.getX() == unitANDterrainContainer.getWidth()
				- visibleSize.getWidth()) {
			jScrollPane.getViewport().setViewPosition(
					new Point(jScrollPane.getViewport().getViewRect().x,
							jScrollPane.getViewport().getViewRect().y + 25));
			return;
		}

		jScrollPane.getViewport().setViewPosition(
				new Point(jScrollPane.getViewport().getViewRect().x + 25,
						jScrollPane.getViewport().getViewRect().y + 25));
	}

	public void scrollSouthWest() {
		Point upperLeft = jScrollPane.getViewport().getViewPosition();
		Dimension visibleSize = jScrollPane.getViewport().getExtentSize();

		if (upperLeft.getX() == 0
				&& upperLeft.getY() == unitANDterrainContainer.getHeight()
						- visibleSize.getHeight()) {
			return;
		}

		if (upperLeft.getY() == unitANDterrainContainer.getHeight()
				- visibleSize.getHeight()) {
			jScrollPane.getViewport().setViewPosition(
					new Point(jScrollPane.getViewport().getViewRect().x - 25,
							jScrollPane.getViewport().getViewRect().y));
			return;
		}

		if (upperLeft.getX() == 0) {
			jScrollPane.getViewport().setViewPosition(
					new Point(jScrollPane.getViewport().getViewRect().x,
							jScrollPane.getViewport().getViewRect().y + 25));
			return;
		}

		jScrollPane.getViewport().setViewPosition(
				new Point(jScrollPane.getViewport().getViewRect().x - 25,
						jScrollPane.getViewport().getViewRect().y + 25));
	}

	public void scrollUp() {
		Point upperLeft = jScrollPane.getViewport().getViewPosition();

		if (upperLeft.getY() == 0) {
			return;
		}

		jScrollPane.getViewport().setViewPosition(
				new Point(jScrollPane.getViewport().getViewRect().x,
						jScrollPane.getViewport().getViewRect().y - 25));
	}

	private void beginGame() {
		instantiateUnits();
		GenericUnit temp;
		for (int i = 0; i < unitsPane.getComponentCount(); i++) {
			temp = (GenericUnit) unitsPane.getComponent(i);
			if (temp.getUnitType() > 0
					&& !temp.getPlayerName().equals("Player 1")) {
				temp.setVisible(false);
			}
		}

		int p1Start = -1;

		for (int i = 0; i < unitsPane.getComponentCount(); i++) {
			if (((GenericUnit) unitsPane.getComponent(i)).getUnitType() > 0) {
				p1Start = i;
				break;
			}
		}

		displayInformation(p1Start);
		scrollWindowToUnit(p1Start);
		((TurnTimer) infoPane.getComponent(0)).startTurnTimer();
	}

	private void estabishTerrainPane() {
		terrainPane = new JPanel();
		terrainPane.setLayout(unitORterrainPaneLayout);
		fillTP();
		terrainPane.setVisible(true);
	}

	private void establishInfoPane() {
		infoPane = new JPanel();
		infoPane.setBackground(Color.CYAN);
		infoPane.setPreferredSize(new Dimension((int) screenSize.getWidth(),
				(int) (screenSize.getHeight() / 5)));
		infoPane.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		TurnTimer clock = new TurnTimer();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.1;
		c.weighty = 0.85;
		c.gridx = 0;
		c.gridy = 0;
		infoPane.add(clock, c);

		JButton button = new JButton("End Turn");
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				((TurnTimer) infoPane.getComponent(0)).start(roundComplete());
			}
		});

		c.weightx = 0.1;
		c.weighty = 0.15;
		c.gridx = 0;
		c.gridy = 1;
		infoPane.add(button, c);

		JLabel unitPortrait = new JLabel(new ImageIcon("images/warrior.png"));
		c.weightx = 0.1;
		c.gridx = 1;
		c.gridy = 0;
		infoPane.add(unitPortrait, c);

		JLabel unitInformation = new JLabel(
				"<html>Unit Name: N/A<br>Hit Points: N/A<br>Attack: "
						+ "N/A<br>Defense: N/A<br>Mana: N/A<br>Damage: N/A<br>Speed: N/A</html>");
		c.weightx = 0.2;
		c.gridx = 2;
		infoPane.add(unitInformation, c);

		button = new JButton("Ability 1");
		c.fill = GridBagConstraints.NONE;
		c.weightx = 0.1;
		c.gridx = 3;
		infoPane.add(button, c);
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub

			}
		});

		button = new JButton("Ability 2");
		c.gridx = 4;
		infoPane.add(button, c);

		button = new JButton("Ability 3");
		c.gridy = 1;
		c.gridx = 3;
		infoPane.add(button, c);

		button = new JButton("Ability 4");
		c.gridx = 4;
		infoPane.add(button, c);
	}

	private void establishTPContainerActions() {
		Action leftAction = new ScrollLeftAction();
		Action upAction = new ScrollUpAction();
		Action rightAction = new ScrollRightAction();
		Action downAction = new ScrollDownAction();
		Action leftReleasedAction = new LeftReleasedAction();
		Action upReleasedAction = new UpReleasedAction();
		Action rightReleasedAction = new RightReleasedAction();
		Action downReleasedAction = new DownReleasedAction();
		Action escapeAction = new EscapeAction();
		jScrollPane.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT)
				.put(KeyStroke.getKeyStroke("A"), "Scroll Left");
		jScrollPane.getActionMap().put("Scroll Left", leftAction);
		jScrollPane.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT)
				.put(KeyStroke.getKeyStroke("W"), "Scroll Up");
		jScrollPane.getActionMap().put("Scroll Up", upAction);
		jScrollPane.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT)
				.put(KeyStroke.getKeyStroke("D"), "Scroll Right");
		jScrollPane.getActionMap().put("Scroll Right", rightAction);
		jScrollPane.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT)
				.put(KeyStroke.getKeyStroke("S"), "Scroll Down");
		jScrollPane.getActionMap().put("Scroll Down", downAction);
		jScrollPane.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT)
				.put(KeyStroke.getKeyStroke("released A"), "Left Released");
		jScrollPane.getActionMap().put("Left Released", leftReleasedAction);
		jScrollPane.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT)
				.put(KeyStroke.getKeyStroke("released W"), "Up Released");
		jScrollPane.getActionMap().put("Up Released", upReleasedAction);
		jScrollPane.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT)
				.put(KeyStroke.getKeyStroke("released D"), "Right Released");
		jScrollPane.getActionMap().put("Right Released", rightReleasedAction);
		jScrollPane.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT)
				.put(KeyStroke.getKeyStroke("released S"), "Down Released");
		jScrollPane.getActionMap().put("Down Released", downReleasedAction);
		jScrollPane.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT)
				.put(KeyStroke.getKeyStroke((char) KeyEvent.VK_ESCAPE),
						"Escape Pressed");
		jScrollPane.getActionMap().put("Escape Pressed", escapeAction);
	}

	private void establishUnitAndTerrainPaneLayout() {
		unitORterrainPaneLayout = new GridLayout(144, 144, -1, -1);
	}

	private void establishUnitsANDTerrainPaneContainer() {
		unitANDterrainContainer = new JPanel();
		unitANDterrainContainer.setPreferredSize(new Dimension(9216, 9216));
		unitANDterrainContainer.setLayout(new BorderLayout());
		zOrder = new JLayeredPane();
		unitANDterrainContainer.add(zOrder, BorderLayout.CENTER);
		terrainPane.setBounds(-24, -24, 9216, 9216);
		terrainPane.setOpaque(true);
		unitsPane.setBounds(-24, -24, 9216, 9216);
		unitsPane.setOpaque(false);
		zOrder.add(terrainPane, new Integer(0), 0);
		zOrder.add(unitsPane, new Integer(1), 0);
		unitANDterrainContainer.setVisible(true);
		jScrollPane = new JScrollPane();
		jScrollPane.setPreferredSize(new Dimension((int) screenSize.getWidth(),
				(int) (screenSize.getHeight() / 5 * 4)));
		jScrollPane.setViewportView(unitANDterrainContainer);
		establishTPContainerActions();
		jScrollPane
				.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		jScrollPane
				.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
		jScrollPane.setVisible(true);
	}

	private void establishUnitsPane() {
		unitsPane = new JPanel();
		unitsPane.setLayout(unitORterrainPaneLayout);
		fillUP();
		unitsPane.setVisible(true);
	}

	private void fillTP() {
		Anchor[][] abstractMap = generatePreSetMapOne();
		GenericTerrain tile;
		int pos = 0, type;

		for (int i = 0; i < 144; i++) {
			for (int j = 0; j < 144; j++) {

				type = abstractMap[i][j].getType();

				switch (type) {
				case 0:
					tile = new GrassTerrain(
							terrainTiles[(int) (Math.random() * 9)], pos);
					break;
				case 1:
					tile = new DirtTerrain(
							terrainTiles[9 + (int) (Math.random() * 9)], pos);
					break;
				case 2:
					tile = new RockTerrain(
							terrainTiles[18 + (int) (Math.random() * 9)], pos);
					break;
				case 3:
					tile = new SandTerrain(
							terrainTiles[27 + (int) (Math.random() * 9)], pos);
					break;
				case 4:
					tile = new CrystalTerrain(
							terrainTiles[36 + (int) (Math.random() * 9)], pos);
					break;
				default:
					tile = null;
				}

				terrainPane.add(tile);
				pos++;
			}
		}
	}

	private void fillUP() {
		int pos = 0;
		for (int i = 0; i < 144; i++) {
			for (int j = 0; j < 144; j++) {
				unitsPane.add(new GenericUnit(pos, ""));
				pos++;
			}
		}
	}

	/*
	 * Preset map number one is a collection of rectangular regions. (1) 16
	 * regions of size 32 x 32 grouped in a 4 x 4 square in the upper left
	 * corner. (2) 8 regions of size 16 x 16 found along the upper right side.
	 * (3) 8 regions of size 16 x 16 found along the bottom left side. (4) 1
	 * region of size 16 x 16 found in the lower right corner.
	 * 
	 * Regions (2) and (3) are actually 2 regions of 16 x 64 and 64 x 16 each.
	 * They are divided into 4 16 x 16 regions each for simplicity's sake.
	 */
	private Anchor[][] generatePreSetMapOne() {

		Anchor[][] abstractMap = new Anchor[144][144];

		// Region 0, 0 of (1)
		int tileType = (int) (Math.random() * 5);

		abstractMap[15][15] = new Anchor(15, 1, tileType);
		abstractMap[15][16] = new Anchor(15, 3, tileType);
		abstractMap[16][15] = new Anchor(15, 7, tileType);
		abstractMap[16][16] = new Anchor(15, 5, tileType);

		// Region 0, 1 of (1)
		tileType = (int) (Math.random() * 5);

		abstractMap[15][47] = new Anchor(15, 1, tileType);
		abstractMap[15][48] = new Anchor(15, 3, tileType);
		abstractMap[16][47] = new Anchor(15, 7, tileType);
		abstractMap[16][48] = new Anchor(15, 5, tileType);

		// Region 0, 2 of (1)
		tileType = (int) (Math.random() * 5);

		abstractMap[15][79] = new Anchor(15, 1, tileType);
		abstractMap[15][80] = new Anchor(15, 3, tileType);
		abstractMap[16][79] = new Anchor(15, 7, tileType);
		abstractMap[16][80] = new Anchor(15, 5, tileType);

		// Region 0, 3 of (1)
		tileType = (int) (Math.random() * 5);

		abstractMap[15][111] = new Anchor(15, 1, tileType);
		abstractMap[15][112] = new Anchor(15, 3, tileType);
		abstractMap[16][111] = new Anchor(15, 7, tileType);
		abstractMap[16][112] = new Anchor(15, 5, tileType);

		// Region 1, 0 of (1)
		tileType = (int) (Math.random() * 5);

		abstractMap[47][15] = new Anchor(15, 1, tileType);
		abstractMap[47][16] = new Anchor(15, 3, tileType);
		abstractMap[48][15] = new Anchor(15, 7, tileType);
		abstractMap[48][16] = new Anchor(15, 5, tileType);

		// Region 1, 1 of (1)
		tileType = (int) (Math.random() * 5);

		abstractMap[47][47] = new Anchor(15, 1, tileType);
		abstractMap[47][48] = new Anchor(15, 3, tileType);
		abstractMap[48][47] = new Anchor(15, 7, tileType);
		abstractMap[48][48] = new Anchor(15, 5, tileType);

		// Region 1, 2 of (1)
		tileType = (int) (Math.random() * 5);

		abstractMap[47][79] = new Anchor(15, 1, tileType);
		abstractMap[47][80] = new Anchor(15, 3, tileType);
		abstractMap[48][79] = new Anchor(15, 7, tileType);
		abstractMap[48][80] = new Anchor(15, 5, tileType);

		// Region 1, 3 of (1)
		tileType = (int) (Math.random() * 5);

		abstractMap[47][111] = new Anchor(15, 1, tileType);
		abstractMap[47][112] = new Anchor(15, 3, tileType);
		abstractMap[48][111] = new Anchor(15, 7, tileType);
		abstractMap[48][112] = new Anchor(15, 5, tileType);

		// Region 2, 0 of (1)
		tileType = (int) (Math.random() * 5);

		abstractMap[79][15] = new Anchor(15, 1, tileType);
		abstractMap[79][16] = new Anchor(15, 3, tileType);
		abstractMap[80][15] = new Anchor(15, 7, tileType);
		abstractMap[80][16] = new Anchor(15, 5, tileType);

		// Region 2, 1 of (1)
		tileType = (int) (Math.random() * 5);

		abstractMap[79][47] = new Anchor(15, 1, tileType);
		abstractMap[79][48] = new Anchor(15, 3, tileType);
		abstractMap[80][47] = new Anchor(15, 7, tileType);
		abstractMap[80][48] = new Anchor(15, 5, tileType);

		// Region 2, 2 of (1)
		tileType = (int) (Math.random() * 5);

		abstractMap[79][79] = new Anchor(15, 1, tileType);
		abstractMap[79][80] = new Anchor(15, 3, tileType);
		abstractMap[80][79] = new Anchor(15, 7, tileType);
		abstractMap[80][80] = new Anchor(15, 5, tileType);

		// Region 2, 3 of (1)
		tileType = (int) (Math.random() * 5);

		abstractMap[79][111] = new Anchor(15, 1, tileType);
		abstractMap[79][112] = new Anchor(15, 3, tileType);
		abstractMap[80][111] = new Anchor(15, 7, tileType);
		abstractMap[80][112] = new Anchor(15, 5, tileType);

		// Region 3, 0 of (1)
		tileType = (int) (Math.random() * 5);

		abstractMap[111][15] = new Anchor(15, 1, tileType);
		abstractMap[111][16] = new Anchor(15, 3, tileType);
		abstractMap[112][15] = new Anchor(15, 7, tileType);
		abstractMap[112][16] = new Anchor(15, 5, tileType);

		// Region 3, 1 of (1)
		tileType = (int) (Math.random() * 5);

		abstractMap[111][47] = new Anchor(15, 1, tileType);
		abstractMap[111][48] = new Anchor(15, 3, tileType);
		abstractMap[112][47] = new Anchor(15, 7, tileType);
		abstractMap[112][48] = new Anchor(15, 5, tileType);

		// Region 3, 2 of (1)
		tileType = (int) (Math.random() * 5);

		abstractMap[111][79] = new Anchor(15, 1, tileType);
		abstractMap[111][80] = new Anchor(15, 3, tileType);
		abstractMap[112][79] = new Anchor(15, 7, tileType);
		abstractMap[112][80] = new Anchor(15, 5, tileType);

		// Region 3, 3 of (1)
		tileType = (int) (Math.random() * 5);

		abstractMap[111][111] = new Anchor(15, 1, tileType);
		abstractMap[111][112] = new Anchor(15, 3, tileType);
		abstractMap[112][111] = new Anchor(15, 7, tileType);
		abstractMap[112][112] = new Anchor(15, 5, tileType);

		// Region 0, 0 of (2)
		tileType = (int) (Math.random() * 5);

		abstractMap[7][135] = new Anchor(7, 1, tileType);
		abstractMap[7][136] = new Anchor(7, 3, tileType);
		abstractMap[8][135] = new Anchor(7, 7, tileType);
		abstractMap[8][136] = new Anchor(7, 5, tileType);

		// Region 1, 0 of (2)

		abstractMap[23][135] = new Anchor(7, 1, tileType);
		abstractMap[23][136] = new Anchor(7, 3, tileType);
		abstractMap[24][135] = new Anchor(7, 7, tileType);
		abstractMap[24][136] = new Anchor(7, 5, tileType);

		// Region 2, 0 of (2)

		abstractMap[39][135] = new Anchor(7, 1, tileType);
		abstractMap[39][136] = new Anchor(7, 3, tileType);
		abstractMap[40][135] = new Anchor(7, 7, tileType);
		abstractMap[40][136] = new Anchor(7, 5, tileType);

		// Region 3, 0 of (2)

		abstractMap[55][135] = new Anchor(7, 1, tileType);
		abstractMap[55][136] = new Anchor(7, 3, tileType);
		abstractMap[56][135] = new Anchor(7, 7, tileType);
		abstractMap[56][136] = new Anchor(7, 5, tileType);

		// Region 4, 0 of (2)
		tileType = (int) (Math.random() * 5);

		abstractMap[71][135] = new Anchor(7, 1, tileType);
		abstractMap[71][136] = new Anchor(7, 3, tileType);
		abstractMap[72][135] = new Anchor(7, 7, tileType);
		abstractMap[72][136] = new Anchor(7, 5, tileType);

		// Region 5, 0 of (2)

		abstractMap[87][135] = new Anchor(7, 1, tileType);
		abstractMap[87][136] = new Anchor(7, 3, tileType);
		abstractMap[88][135] = new Anchor(7, 7, tileType);
		abstractMap[88][136] = new Anchor(7, 5, tileType);

		// Region 6, 0 of (2)

		abstractMap[103][135] = new Anchor(7, 1, tileType);
		abstractMap[103][136] = new Anchor(7, 3, tileType);
		abstractMap[104][135] = new Anchor(7, 7, tileType);
		abstractMap[104][136] = new Anchor(7, 5, tileType);

		// Region 7, 0 of (2)

		abstractMap[119][135] = new Anchor(7, 1, tileType);
		abstractMap[119][136] = new Anchor(7, 3, tileType);
		abstractMap[120][135] = new Anchor(7, 7, tileType);
		abstractMap[120][136] = new Anchor(7, 5, tileType);

		// Region 0, 0 of (3)
		tileType = (int) (Math.random() * 5);

		abstractMap[135][7] = new Anchor(7, 1, tileType);
		abstractMap[135][8] = new Anchor(7, 3, tileType);
		abstractMap[136][7] = new Anchor(7, 7, tileType);
		abstractMap[136][8] = new Anchor(7, 5, tileType);

		// Region 0, 1 of (3)

		abstractMap[135][23] = new Anchor(7, 1, tileType);
		abstractMap[135][24] = new Anchor(7, 3, tileType);
		abstractMap[136][23] = new Anchor(7, 7, tileType);
		abstractMap[136][24] = new Anchor(7, 5, tileType);

		// Region 0, 2 of (3)

		abstractMap[135][39] = new Anchor(7, 1, tileType);
		abstractMap[135][40] = new Anchor(7, 3, tileType);
		abstractMap[136][39] = new Anchor(7, 7, tileType);
		abstractMap[136][40] = new Anchor(7, 5, tileType);

		// Region 0, 3 of (3)

		abstractMap[135][55] = new Anchor(7, 1, tileType);
		abstractMap[135][56] = new Anchor(7, 3, tileType);
		abstractMap[136][55] = new Anchor(7, 7, tileType);
		abstractMap[136][56] = new Anchor(7, 5, tileType);

		// Region 0, 4 of (3)

		abstractMap[135][71] = new Anchor(7, 1, tileType);
		abstractMap[135][72] = new Anchor(7, 3, tileType);
		abstractMap[136][71] = new Anchor(7, 7, tileType);
		abstractMap[136][72] = new Anchor(7, 5, tileType);

		// Region 0, 5 of (3)

		abstractMap[135][87] = new Anchor(7, 1, tileType);
		abstractMap[135][88] = new Anchor(7, 3, tileType);
		abstractMap[136][87] = new Anchor(7, 7, tileType);
		abstractMap[136][88] = new Anchor(7, 5, tileType);

		// Region 0, 6 of (3)

		abstractMap[135][103] = new Anchor(7, 1, tileType);
		abstractMap[135][104] = new Anchor(7, 3, tileType);
		abstractMap[136][103] = new Anchor(7, 7, tileType);
		abstractMap[136][104] = new Anchor(7, 5, tileType);

		// Region 0, 7 of (3)

		abstractMap[135][119] = new Anchor(7, 1, tileType);
		abstractMap[135][120] = new Anchor(7, 3, tileType);
		abstractMap[136][119] = new Anchor(7, 7, tileType);
		abstractMap[136][120] = new Anchor(7, 5, tileType);

		// Region 0, 0 of (4)
		tileType = (int) (Math.random() * 5);

		abstractMap[135][135] = new Anchor(7, 1, tileType);
		abstractMap[135][136] = new Anchor(7, 3, tileType);
		abstractMap[136][135] = new Anchor(7, 7, tileType);
		abstractMap[136][136] = new Anchor(7, 5, tileType);

		boolean advance;

		do {
			advance = false;
			for (int i = 0; i < 144; i++) {
				for (int j = 0; j < 144; j++) {

					if (abstractMap[i][j] == null) {
						advance = true;
					} else if (!abstractMap[i][j].hasPropagated()
							&& abstractMap[i][j].getPropagationRadius() != 0) {
						if (abstractMap[i][j].getDirection() == 1) {
							abstractMap[i][j - 1] = new Anchor(
									abstractMap[i][j].getPropagationRadius() - 1,
									8, abstractMap[i][j].getTileType());
							abstractMap[i - 1][j - 1] = new Anchor(
									abstractMap[i][j].getPropagationRadius() - 1,
									1, abstractMap[i][j].getTileType());
							abstractMap[i - 1][j] = new Anchor(
									abstractMap[i][j].getPropagationRadius() - 1,
									2, abstractMap[i][j].getTileType());
						}
						if (abstractMap[i][j].getDirection() == 2) {
							abstractMap[i - 1][j] = new Anchor(
									abstractMap[i][j].getPropagationRadius() - 1,
									2, abstractMap[i][j].getTileType());
						}
						if (abstractMap[i][j].getDirection() == 3) {
							abstractMap[i - 1][j] = new Anchor(
									abstractMap[i][j].getPropagationRadius() - 1,
									2, abstractMap[i][j].getTileType());
							abstractMap[i - 1][j + 1] = new Anchor(
									abstractMap[i][j].getPropagationRadius() - 1,
									3, abstractMap[i][j].getTileType());
							abstractMap[i][j + 1] = new Anchor(
									abstractMap[i][j].getPropagationRadius() - 1,
									4, abstractMap[i][j].getTileType());
						}
						if (abstractMap[i][j].getDirection() == 4) {
							abstractMap[i][j + 1] = new Anchor(
									abstractMap[i][j].getPropagationRadius() - 1,
									4, abstractMap[i][j].getTileType());
						}
						if (abstractMap[i][j].getDirection() == 5) {
							abstractMap[i][j + 1] = new Anchor(
									abstractMap[i][j].getPropagationRadius() - 1,
									4, abstractMap[i][j].getTileType());
							abstractMap[i + 1][j + 1] = new Anchor(
									abstractMap[i][j].getPropagationRadius() - 1,
									5, abstractMap[i][j].getTileType());
							abstractMap[i + 1][j] = new Anchor(
									abstractMap[i][j].getPropagationRadius() - 1,
									6, abstractMap[i][j].getTileType());
						}
						if (abstractMap[i][j].getDirection() == 6) {
							abstractMap[i + 1][j] = new Anchor(
									abstractMap[i][j].getPropagationRadius() - 1,
									6, abstractMap[i][j].getTileType());
						}
						if (abstractMap[i][j].getDirection() == 7) {
							abstractMap[i + 1][j] = new Anchor(
									abstractMap[i][j].getPropagationRadius() - 1,
									6, abstractMap[i][j].getTileType());
							abstractMap[i + 1][j - 1] = new Anchor(
									abstractMap[i][j].getPropagationRadius() - 1,
									7, abstractMap[i][j].getTileType());
							abstractMap[i][j - 1] = new Anchor(
									abstractMap[i][j].getPropagationRadius() - 1,
									8, abstractMap[i][j].getTileType());
						}
						if (abstractMap[i][j].getDirection() == 8) {
							abstractMap[i][j - 1] = new Anchor(
									abstractMap[i][j].getPropagationRadius() - 1,
									8, abstractMap[i][j].getTileType());
						}
					}
				}
			}
		} while (advance);

		return abstractMap;
	}

	private void instantiateUnits() {
		int p1Start = (int) (Math.random() * 72) + (int) (Math.random() * 72)
				* 144;
		int p2Start = (int) (72 + Math.random() * 72)
				+ (int) ((Math.random() * 72) + 72) * 144;
		GenericUnit player1 = new NoviceMagician("Player 1", p1Start);
		GenericUnit player2 = new NoviceMagician("Player 2", p2Start);
		unitsPane.remove(p1Start);
		unitsPane.add(player1, p1Start);
		unitsPane.remove(p2Start);
		unitsPane.add(player2, p2Start);
		unitsPane.revalidate();
		unitsPane.repaint();
	}

	private void scrollWindowToUnit(int targetUnit) {
		int upLeftY = unitsPane.getLocation().y;
		revalidate();
		Point scrollDest = new Point(unitsPane.getComponent(targetUnit)
				.getLocation().x - (getWidth() / 2) + 12, unitsPane
				.getComponent(targetUnit).getLocation().y
				- ((getHeight() - infoPane.getHeight()) / 2) + 12);

		if (scrollDest.x < 0) {
			scrollDest.x = 0;
		}

		if (scrollDest.y < upLeftY) {
			scrollDest.y = upLeftY + 24;
		}

		jScrollPane.getViewport().setViewPosition(scrollDest);

		repaint();
		revalidate();
	}

	private void updateVision(int srcUnit) {
		int row = srcUnit / 144, col = srcUnit % 144, vision = ((GenericUnit) unitsPane
				.getComponent(srcUnit)).getVision();

		for (int r = row - vision; r <= row + vision; r++) {
			for (int c = col - vision; c <= col + vision; c++) {
				try {
					((GenericUnit) unitsPane.getComponent(r * 144 + c))
							.setVisible(true);
				} catch (Exception e) {
					// Do Nothing
				}
			}
		}
	}

	public class DownReleasedAction extends AbstractAction {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			downPressed = false;
		}

	}

	public class EscapeAction extends AbstractAction {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			escapeGame();
		}

	}

	public class LeftReleasedAction extends AbstractAction {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			leftPressed = false;
		}

	}

	public class RightReleasedAction extends AbstractAction {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			rightPressed = false;
		}

	}

	public class ScrollDownAction extends AbstractAction {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			downPressed = true;

			if (leftPressed) {
				scrollSouthWest();
			} else if (rightPressed) {
				scrollSouthEast();
			} else {
				scrollDown();
			}
		}
	}

	public class ScrollLeftAction extends AbstractAction {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			leftPressed = true;

			if (upPressed) {
				scrollNorthWest();
			} else if (downPressed) {
				scrollSouthWest();
			} else {
				scrollLeft();
			}
		}
	}

	public class ScrollRightAction extends AbstractAction {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			rightPressed = true;

			if (upPressed) {
				scrollNorthEast();
			} else if (downPressed) {
				scrollSouthEast();
			} else {
				scrollRight();
			}
		}
	}

	public class ScrollUpAction extends AbstractAction {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			upPressed = true;

			if (leftPressed) {
				scrollNorthWest();
			} else if (rightPressed) {
				scrollNorthEast();
			} else {
				scrollUp();
			}
		}
	}

	public class TransparentOverlay extends JPanel {

		Timer quickDelay = new Timer(10, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int x = MouseInfo.getPointerInfo().getLocation().x, y = MouseInfo
						.getPointerInfo().getLocation().y;

				if (x < 5 && y < 5) {
					scrollNorthWest();

				}

				if (x < 5 && y > 5 && y < screenSize.height - 5) {
					scrollLeft();
				}

				if (x < 1 && y > screenSize.height - 1) {
					scrollSouthWest();
				}

				if (x > 5 && x < screenSize.width - 5
						&& y > screenSize.height - 5) {
					scrollDown();
				}

				if (x > screenSize.width - 5 && y > screenSize.height - 5) {
					scrollSouthEast();
				}

				if (x > screenSize.width - 5 && y > 5
						&& y < screenSize.height - 5) {
					scrollRight();
				}

				if (x > screenSize.width - 5 && y < 5) {
					scrollNorthEast();
				}

				if (x > 5 && x < screenSize.width - 5 && y < 5) {
					scrollUp();
				}
			}
		});

		public TransparentOverlay() {
			super();
			setSize(screenSize);
			quickDelay.start();
			pack();
		}
	}

	public class TurnTimer extends JLabel {

		private int turnLength, percent;
		private final ImageIcon[] clockFaces = {
				new ImageIcon("images/clock_0.png"),
				new ImageIcon("images/clock_1.png"),
				new ImageIcon("images/clock_2.png"),
				new ImageIcon("images/clock_3.png"),
				new ImageIcon("images/clock_4.png"),
				new ImageIcon("images/clock_5.png"),
				new ImageIcon("images/clock_6.png"),
				new ImageIcon("images/clock_7.png"),
				new ImageIcon("images/clock_8.png"),
				new ImageIcon("images/clock_9.png"),
				new ImageIcon("images/clock_10.png") };
		Timer turnTimer = new Timer(0, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				if (percent == 10) {
					turnTimer.setRepeats(false);
				}
				if (percent == 11) {
					percent = 0;
					endTurn();
				}
				setIcon(clockFaces[percent]);
				revalidate();
				repaint();
				percent++;
			}
		});

		public TurnTimer() {
			super();
			setIcon(clockFaces[0]);
			turnLength = 10 * 1000;
			percent = 0;
		}

		public void start(boolean b) {
			if (b) {
				turnLength += 10 * 1000;
			}
			startTurnTimer();
		}

		public void startTurnTimer() {
			if (turnTimer.isRunning()) {
				turnTimer.stop();
				percent = 0;
				endTurn();
				setIcon(clockFaces[percent]);
				revalidate();
				repaint();
			}
			turnTimer.setDelay(turnLength / 10);
			turnTimer.setRepeats(true);
			turnTimer.start();
		}
	}

	public class UpReleasedAction extends AbstractAction {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			upPressed = false;
		}

	}
}
