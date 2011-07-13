package gates;

import java.util.ArrayList;

import party.Party;

import crypto.SecretShare;

public abstract class Gate {
	
	public static int forId = 1;
	public int id;
	protected SecretShare result;
	protected ArrayList<GateInput> input;
	//protected ArrayList<Gate> output; // output gates
	
	public Gate(ArrayList<GateInput> input/*, ArrayList<Gate> output*/){
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
	}
	
	public SecretShare getResult(){
		return result;
	}

}