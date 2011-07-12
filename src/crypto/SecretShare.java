package crypto;

import party.Party;

public class SecretShare {
	public int x;
	public int y;
	
	public SecretShare(int x, int y){
		this.x = x % Party.field;
		this.y = y % Party.field;
		//System.out.println("x = " + this.x + " y = " + this.y);
	}

}
