package crypto;

import gates.Gate;

public class SecretShare {
	public int x;
	public int y;
	public double dx;
	public double dy;
	public boolean is_int;
	
	public SecretShare(int x, int y){
		this.x = Gate.modField(x);
		this.y = Gate.modField(y);
		is_int = true;
		// debug
		/*this.x = x;// Gate.modField(x);
		this.y = y;//Gate.modField(y);*/
	}
	
	public SecretShare(double dx, double dy){
		this.dx = dx;
		this.dy = dy;
		this.x = -1;
		this.y = -1;
		is_int = false;
		// debug
		/*this.x = x;// Gate.modField(x);
		this.y = y;//Gate.modField(y);*/
	}
	
	
	// for debug
	public String toString() {
		return "[" + Integer.toString(x) + " , " + Integer.toString(y) + "]";
	}

}
