package crypto;

import party.Party;

public class SecretShare {
	public String secret;
	public int x;
	public int y;
	
	public SecretShare(int x, int y, String secret){
		this.x = x % Party.field;
		this.y = y % Party.field;
		this.secret = secret;
		//System.out.println("x = " + this.x + " y = " + this.y);
	}

}
