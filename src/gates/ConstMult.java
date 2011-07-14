package gates;

import java.util.ArrayList;
import crypto.SecretShare;

public class ConstMult extends Gate {

	// multiplication with constant;
	
	private int constatnt;
	public ConstMult(ArrayList<GateIO> input, int constant) {
		super(input);
		this.constatnt = constant;
	}

	@Override
	public void compute() {
		this.result = new ArrayList<GateIO>();
		for (GateIO in : this.input) {
			//local computation for each party(input)
			GateIO out = new GateIO(in.getIndex());
			SecretShare outValue = new SecretShare(modField(in.value.get(0).x* constatnt),
					modField(in.value.get(0).y* constatnt));
		
			out.value.add(outValue);
			this.result.add(out);			
		}
	}
	
	
}
