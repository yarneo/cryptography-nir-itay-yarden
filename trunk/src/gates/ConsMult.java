package gates;

import java.util.ArrayList;
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
		this.result =new SecretShare(modField(input.get(0).getValue().x * constatnt), 
				modField(input.get(0).getValue().y * constatnt), 
				Integer.toString(this.id)) ;
	}

}
