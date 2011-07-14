package party;

import java.util.ArrayList;
import java.util.Vector;

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
		Party p3 = new Party(5);
		
		ArrayList<GateIO> input = new ArrayList<GateIO>();
		GateIO forP1 = new GateIO(0);
		GateIO forP2 = new GateIO(1);
		GateIO forP3 = new GateIO(2);
		
		forP1.value.add(new SecretShare(1, -4));
		forP1.value.add(new SecretShare(1, 10));
		forP2.value.add(new SecretShare(2, -11));
		forP2.value.add(new SecretShare(2, 18));
		forP3.value.add(new SecretShare(3, -18));
		forP3.value.add(new SecretShare(3, 26));
		
		input.add(forP1);
		input.add(forP2);
		input.add(forP3);
		
		MultiplicationGate gate = new MultiplicationGate(input);
		
		gate.compute();
//		Vector<Integer> coef = new Vector<Integer>();
//		coef.add(0, -40);
//		coef.add(1, 2);
//		Polynomial p = new Polynomial(coef);
//		p.computeCoef(0);
//		System.out.println(p.computeCoef(0));
//		System.out.println(p.computeCoef(1));
//		System.out.println(p.computeCoef(2));
//		System.out.println(p.computeCoef(3));
		
	}

}
