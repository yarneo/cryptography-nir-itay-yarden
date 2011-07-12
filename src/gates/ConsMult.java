package gates;

import java.util.ArrayList;

import party.Party;

import crypto.SecretShare;

public class ConsMult extends Gate {

	// multiplication with constant;
	
	private int constatnt;
	public ConsMult(ArrayList<GateInput> input, int constat) {
		super(input);
		this.constatnt = constat;
	}

	@Override
	public void compute() {
		this.result = new SecretShare(0, 0);
		this.result.x = modField(input.get(0).getValue().x * constatnt);
		this.result.y = modField(input.get(0).getValue().y * constatnt);
		
		System.out.println("result of const multiplication : x = "
				+ this.result.x + ", y = " + this.result.y );
	}

}
