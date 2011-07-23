package gates;

import java.util.ArrayList;
import crypto.SecretShare;

public class AdditionGate extends Gate {

	public AdditionGate(ArrayList<GateIO> input/* , ArrayList<Gate> output */) {
		super(input/* , output */);
	}

	// each client locally adds his secret share
	// result - secret share of the S1+S2+..+Sn, for each client
	@Override
	public void compute() {
		this.result = new ArrayList<GateIO>();
		for (GateIO in : this.input) {
			// local computation for each party(input)
			GateIO out = new GateIO(in.getIndex());
			SecretShare outValue = new SecretShare(0, 0);
			for (SecretShare sIn : in.value) {
				outValue.y = modField(outValue.y + sIn.y);
			}
			outValue.x = modField(in.value.get(0).x);
			out.value.add(outValue);
			// System.out.println("out value is: (" + outValue.x + ","+
			// outValue.y + ")");
			this.result.add(out);
		}
	}

}
