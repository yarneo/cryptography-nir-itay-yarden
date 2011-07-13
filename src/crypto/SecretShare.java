package crypto;

import party.Party;

public class SecretShare {
	public String key;
	public int x;
	public int y;
	
	public SecretShare(int x, int y, String key){
		this.x = x % Party.field;
		this.y = y % Party.field;
		this.key = key;
		//System.out.println("x = " + this.x + " y = " + this.y);
	}

}
