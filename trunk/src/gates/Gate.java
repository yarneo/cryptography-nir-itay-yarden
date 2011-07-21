package gates;

import java.util.ArrayList;

import party.Party;

import crypto.SecretShare;

public abstract class Gate {
	
	public static int forId = 1;
	public int id;
	protected ArrayList<GateIO> input;
	//change to protected
	public ArrayList<GateIO> result;
	//protected ArrayList<Gate> output; // output gates
	
	public Gate(ArrayList<GateIO> input/*, ArrayList<Gate> output*/){
		result = null;
		this.input = input;
		id = forId;
		forId++;
		/*this.output = output;
		if(output == null)
			this.output = new ArrayList<Gate>();
			*/
	}
	
	public abstract void compute();	
	
	// modulo %, but results a non negative number
	public static int modField(int x){
		int ans = (x % Party.field);
		while(ans<0){
			ans += Party.field;
		}
		return ans;		
		//return x; // just for testing
	}
	
	public ArrayList<GateIO> getResult(){
		return result;
	}
	
	public GateIO getIOByIndex(int i){
		for (int j = 0; j < result.size(); j++) {
			if(result.get(j).getIndex() == i)
				return result.get(j);
		}
		return null;
	}

}
