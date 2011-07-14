package party;

import java.util.ArrayList;
import utils.Polynomial;
import crypto.SecretShare;
import gates.AdditionGate;
import gates.ConsMult;
import gates.Gate;
import gates.GateIO;
import gates.MultiplicationGate;

public class TestMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		
		System.out.println("field = " + Party.field);
		Party p1 = new Party(3);
		Party p2 = new Party(2);
		ArrayList<SecretShare> p1SecretShares = Polynomial.createShareSecret(3);
		ArrayList<SecretShare> p2SecretShares = Polynomial.createShareSecret(2);
		
		ArrayList<GateIO> input = new ArrayList<GateIO>();
		GateIO forP1 = new GateIO(0);
		GateIO forP2 = new GateIO(1);
		forP1.value.add(p1SecretShares.get(0));
		forP1.value.add(p2SecretShares.get(0));
		forP2.value.add(p1SecretShares.get(1));
		forP2.value.add(p2SecretShares.get(1));
		
		input.add(forP1);
		input.add(forP2);
		
		MultiplicationGate gate = new MultiplicationGate(input);
		
		gate.compute();
		
	}

}
