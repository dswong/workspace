package warmage;

import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class GenericUnit extends JButton implements MouseListener {

	private Boolean pressed = false, canMove = true;
	protected ImageIcon unitIcon, unitHL;
	protected int positionIndex, unitType;
	protected String unitName, playerName;
	protected int hitPoints, attack, defense, mana, damage, speed, vision, intelligenceAtt, pierceAtt, pierceDam, intelligenceDef, soulAffinity,
	hoardingAptitude, healthRegeneration, calmingAura, disillusionment, holy, dark, spellStrength, affinity, bravery, earthResist, 
	constructionGrade, heatThreshold, liquidThreshold, nullification, strength, agility, stability, padding, shell, reinforcement, health, 
	magicPoints, numberOfAttacks, sightRadius, movementSpeed, productionCost, range, squaresOccupied;
	protected JButton moveButton = new JButton("Move");
	public GenericUnit(GenericUnit toCopy) {
		super();
		addMouseListener(this);
		
		positionIndex = toCopy.getPositionIndex();
		unitName = toCopy.getUnitName();
		unitIcon = toCopy.getUnitIcon();
		unitHL = toCopy.getUnitHL();
		moveButton = toCopy.getMoveButton();
		setIcon(unitIcon);
		setMargin(new Insets(-2, -2, -2, -2));
		setContentAreaFilled(false);
		setOpaque(false);
	}

	public GenericUnit(int pi, String pN) {
		super();
		addMouseListener(this);
		positionIndex = pi;
		setUnitType();
		setUnitName();
		unitIcon = new ImageIcon("images/" + unitName + pN + ".png");
		unitHL = new ImageIcon("images/" + unitName + "HL" + pN + ".png");
		setIcon(unitIcon);
		setMoveButton();
		setMargin(new Insets(-2, -2, -2, -2));
		setContentAreaFilled(false);
		setOpaque(false);
	}

	public int getAttack() {
		return attack;
	}

	public int getDamage() {
		return damage;
	}

	public int getDefense() {
		return defense;
	}

	public int getHitPoints() {
		return hitPoints;
	}

	public int getMana() {
		return mana;
	}

	public String getPlayerName() {
		return playerName;
	}

	public int getPositionIndex() {
		return positionIndex;
	}

	public int getSpeed() {
		return speed;
	}

	public String getUnitName() {
		return unitName;
	}

	public int getUnitType() {
		return unitType;
	}

	public int getVision() {
		return vision;
	}

	public int getRange() {
			return range;
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		if (unitType > 0) {
			MainFrame.displayInformation(positionIndex);
		} else {
			MainFrame.displayTerrainInformation(positionIndex);
		}
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		if (canMove && unitType > 0) {
			MainFrame.generateHighlight(positionIndex, speed);
		}
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		if (!pressed && canMove && unitType > 0) {
			MainFrame.removeHighlight(positionIndex, speed);
		}
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		if (unitType > 0) {
			pressed = true;
		}
		mouseClicked(arg0);
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		if (unitType > 0) {
			pressed = false;
			if (canMove) {
				MainFrame.tryMove(arg0, positionIndex, speed);
			}
			MainFrame.removeHighlight(positionIndex, speed);
		}
	}

	public void removeHighlight() {
		if (unitName == "Blank") {
			setIcon(unitIcon);
		}
	}

	public void setCanMove(Boolean moveable) {
		canMove = moveable;
	}

	public void setPositionIndex(int pi) {
		positionIndex = pi;
	}

	public void updateHighlight() {
		if (unitName == "Blank") {
			setIcon(unitHL);
		}
	}

	private ImageIcon getUnitHL() {
		return unitHL;
	}

	private ImageIcon getUnitIcon() {
		return unitIcon;
	}

	protected void setUnitName() {
		unitName = "Blank";
	}

	protected void setUnitType() {
		unitType = 0;
	}

	protected void setMoveButton() {
		moveButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// Do Nothing
			}
		});
	}
	
	protected JButton getMoveButton() {
		return moveButton;
	}
}