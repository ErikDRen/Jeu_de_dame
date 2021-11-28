package game;

public class Coordonee {
	int x,y;

	
	@Override
	public String toString() {
		return "Coordonee [x=" + x + ", y=" + y + "]";
	}

	public Coordonee(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}
	public String getStringX() {
		return Integer.toString(x);
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public String getStringY() {
		return Integer.toString(y);
	}
	
	public void setY(int y) {
		this.y = y;
	}
}
