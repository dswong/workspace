package warmage;

public class Anchor {

	private final int propagationRadius, direction, tileType;
	private boolean propagated;

	public Anchor(int pR, int d, int tT) {
		propagationRadius = pR;
		direction = d;
		tileType = tT;
		propagated = false;
	}

	public int getDirection() {
		return direction;
	}

	public int getPropagationRadius() {
		return propagationRadius;
	}

	public int getTileType() {
		return tileType;
	}

	public int getType() {
		return tileType;
	}

	public boolean hasPropagated() {
		return propagated;
	}

	public void setPropagated() {
		propagated = true;
	}

}
