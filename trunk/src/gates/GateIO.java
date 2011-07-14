package gates;

import java.util.ArrayList;

import crypto.SecretShare;
import party.Party;

public class GateIO {
	
	// the I/O is indexed by entry number (1, .. , n)
	private int index;
	// for input - few secret shares
	// for output - one secret share
	public ArrayList<SecretShare> value;
	
	public GateIO(int index){
		if(index <= 0 || index >= Party.n)
			throw new IllegalArgumentException("index out of bounds");
		this.index = index;
		this.value = new ArrayList<SecretShare>();		
	}
	
	public int getIndex(){
		return index;
	}
	
	public ArrayList<SecretShare> getValue(){
		return value;
	}

}
