package party;

import java.util.ArrayList;
import java.util.Vector;

import utils.Polynomial;
import crypto.SecretShare;
import gates.AdditionGate;
import gates.ConstMult;
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
		testConstCompute();		
		
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
	public static void testConstCompute()
	{
		ArrayList<GateIO> input = new ArrayList<GateIO>();
		int constant = 5;
		int secret = 10;
		Party p1 = new Party(1);
		Party p2 = new Party(10);
		ArrayList<SecretShare> initInput = new ArrayList<SecretShare>();
		Polynomial poly = Polynomial.create(secret);
		System.out.println("**********************");
		System.out.println("The polynom coefficients are:");
		poly.printPolynomial();
		System.out.println("**********************\n");
		for (Party p : Party.parties) {
			int valueInP = poly.computeCoef(p.getIndex());
			SecretShare s = new SecretShare(p.getIndex(), valueInP);
			initInput.add(p.getIndex()-1, s);
		}
		System.out.println("**********************");
		System.out.println("The init input is:");
		
		for (int i = 0; i < initInput.size(); i++)
		{
			System.out.println("secret share " + (i+1) + " is: " + initInput.get(i).toString());
		}
		System.out.println("**********************\n");
		
		
		ConstMult mult = new ConstMult(input, constant);
		
		
	}

}
