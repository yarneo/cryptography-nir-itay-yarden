package gates;

import java.util.ArrayList;

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
			new SecretShare(s1.x, Gate.modField(s1.y * s2.y),  s1.secret + " * " + s2.secret);
		
		
	}

}
