package gates;

import java.util.ArrayList;

import party.Party;

import crypto.SecretShare;

public class AdditionGate extends Gate {

	public AdditionGate(ArrayList<GateInput> input/*, ArrayList<Gate> output*/) {
		super(input/*, output*/);
	}

	// each client locally adds his secret share
	// result - secret share of the S1+S2+..+Sn
	@Override
	public void compute() {
		//this.result.add(e);
		this.result = new SecretShare(0, 0);
		for (GateInput in : input) {
			this.result.x = modField(this.result.x +  in.getValue().x);
			this.result.y = modField(this.result.y +  in.getValue().y);
		}
		System.out.println("result of addition : x = " + this.result.x +
				", y = " + this.result.y );
	}

}
