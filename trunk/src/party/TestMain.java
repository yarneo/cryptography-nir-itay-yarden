package party;

import java.util.ArrayList;

import crypto.SecretShare;
import gates.AdditionGate;
import gates.ConsMult;
import gates.Gate;
import gates.GateInput;

public class TestMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		System.out.println("field = " + Party.field);
		// check addition gate
		SecretShare s1 = new SecretShare(3, 4);
		SecretShare s2 = new SecretShare(2, 3);
		ArrayList<GateInput> input = new ArrayList<GateInput>();
		input.add(new GateInput(1, s1));
		input.add(new GateInput(2, s2));
		Gate add = new AdditionGate(input);
		add.compute();

		// check multiplication with constant gate
		input.clear();
		input.add(new GateInput(1, s1));
		Gate con = new ConsMult(input, -2);
		con.compute();
	}

}
