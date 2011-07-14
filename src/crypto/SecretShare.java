package crypto;

import party.Party;

public class SecretShare {
	public int x;
	public int y;
	
	public SecretShare(int x, int y){
		this.x = x % Party.field;
		this.y = y % Party.field;
	}
	
	// for debug
	public String toString() {
		return "(" + Integer.toString(x) + " , " + Integer.toString(y) + ")";
	}

}
