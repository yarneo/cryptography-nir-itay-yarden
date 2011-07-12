package gates;

import crypto.SecretShare;
import party.Party;

public class GateInput {
	
	// the input is indexed by entry number (1, .. , n)
	private int index;
	// the input value
	private SecretShare value;
	
	public GateInput(int index, SecretShare value){
		if(index <= 0 || index >= Party.n)
			throw new IllegalArgumentException("index out of bounds");
		this.index = index;
		this.value = value;		
	}
	
	public int getIndex(){
		return index;
	}
	
	public SecretShare getValue(){
		return value;
	}

}
