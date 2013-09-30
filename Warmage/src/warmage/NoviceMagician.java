package warmage;

public class NoviceMagician extends GenericUnit {

	public NoviceMagician(String pN, int pi) {
		super(pi, pN);
		playerName = pN;
		hitPoints = 8;
		defense = 7;
		attack = 10;
		mana = 10;
		speed = 5;
		damage = 8;
		vision = 7;
	}

	public NoviceMagician(NoviceMagician toCopy) {
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
		unitName = "WarMage";
	}

	@Override
	protected void setUnitType() {
		unitType = 1;
	}
}
