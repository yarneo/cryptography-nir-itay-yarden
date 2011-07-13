package gates;

import java.util.ArrayList;

import party.Party;

import crypto.SecretShare;

public class MultiplicationGate extends Gate {

	public MultiplicationGate(ArrayList<GateInput> input) {
		super(input);
	}

	// compute multiplication between two secret shares
	@Override
	public void compute() {
		SecretShare s1 = this.input.get(0).getValue();
		SecretShare s2 = this.input.get(1).getValue();
		
		SecretShare localMult = 
			new SecretShare(s1.x, Gate.modField(s1.y * s2.y),  s1.key + " * " + s2.key);
		
		
		
	}
	
	// compute multiplication of(-j/[i-j]) for each j!=i
	private int computeZi(int i){
		int counter = 1;
		for (int j = 1; j <= Party.n; j++) {
			if(j!=i){
				counter = counter * (-j/ (i-j));
			}
		}
		return Gate.modField(counter);
	}

}
