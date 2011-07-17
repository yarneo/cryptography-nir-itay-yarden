package crypto;

import gates.Gate;

public class SecretShare {
	public int x;
	public int y;
	
	public SecretShare(int x, int y){
		this.x = Gate.modField(x);
		this.y = Gate.modField(y);
		// debug
		/*this.x = x;// Gate.modField(x);
		this.y = y;//Gate.modField(y);*/
	}
	
	// for debug
	public String toString() {
		return "[" + Integer.toString(x) + " , " + Integer.toString(y) + "]";
	}

}
