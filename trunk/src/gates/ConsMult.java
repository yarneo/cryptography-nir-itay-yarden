package gates;

import java.util.ArrayList;
import crypto.SecretShare;

public class ConsMult extends Gate {

	// multiplication with constant;
	
	private int constatnt;
	public ConsMult(ArrayList<GateIO> input, int constat) {
		super(input);
		this.constatnt = constat;
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
