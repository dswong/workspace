package warmage;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class TorakEres extends GenericUnit {
	
	public TorakEres(String pN, int pi) {
		super(pi, pN);
		playerName = pN;
		hitPoints = 8;
		defense = 7;
		attack = 10;
		mana = 10;
		speed = 5;
		damage = 8;
		vision = 7;
		
		intelligenceAtt = 4;
		pierceAtt = 9;
		pierceDam = 4;
		intelligenceDef = 8;
		soulAffinity = 5;
		hoardingAptitude = 8;
		healthRegeneration = 4;
		calmingAura = 9;
		disillusionment = 5;
		holy = 9;
		dark = 8;
		spellStrength = 6;
		affinity = 3;
		bravery = 4;
		earthResist = 7; 
		constructionGrade = 7;
		heatThreshold = 7;
		liquidThreshold = 7;
		nullification = 6;
		strength = 6;
		agility = 6; 
		stability = 5;
		padding = 6;
		shell = 6;
		reinforcement = 6; 
		magicPoints = 9;
		numberOfAttacks = 3; 
		sightRadius = 6; 
		movementSpeed = 5;
		productionCost = 4;
		range = 1; 
		squaresOccupied = 1;
	}

	public TorakEres(TorakEres toCopy) {
		super(toCopy.getPositionIndex(), toCopy.getPlayerName());
		playerName = toCopy.getPlayerName();
		hitPoints = toCopy.getHitPoints();
		defense = toCopy.getDefense();
		attack = toCopy.getAttack();
		mana = toCopy.getMana();
		speed = toCopy.getSpeed();
		damage = toCopy.getDamage();
		vision = toCopy.getVision();

	}

	@Override
	protected void setUnitName() {
		unitName = "TorakEres";
	}

	@Override
	protected void setUnitType() {
		unitType = 1;
	}
	
	@Override
	protected void setMoveButton() {
		moveButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("You are moving!!!");
			}
		});
	}
}
