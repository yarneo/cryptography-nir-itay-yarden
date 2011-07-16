package party;

import java.util.ArrayList;
import utils.Polynomial;
import crypto.SecretShare;
import gates.ConstMult;
import gates.GateIO;
import gates.MultiplicationGate;

public class TestMain {

	/**
	 * @param args
	 */
	public static ArrayList<GateIO> input = new ArrayList<GateIO>(); 
	public static void main(String[] args) {
//		setup();
		testMultiplicationGate();
//		testConstCompute();	
//		System.out.println("field = " + Party.field);		
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
	public static void testMultiplicationGate()
	{
		Party p1 = new Party(6);
		Party p2 = new Party(21);
		Party p3 = new Party(3);
		ArrayList<SecretShare> ss1 = p1.shareSecret();
		ArrayList<SecretShare> ss2 = p2.shareSecret();
		ArrayList<GateIO> input2 = new ArrayList<GateIO>(); 
		for(int i=0;i<ss1.size();i++) {
			ArrayList<SecretShare> tmpparty = new ArrayList<SecretShare>();
			tmpparty.add(0,ss1.get(i));
			tmpparty.add(1,ss2.get(i));
			System.out.println("input secret shares for party" + i + " is:" + ss1.get(i) + " " + ss2.get(i));
			GateIO tmpgate = new GateIO(i,tmpparty);
			input2.add(tmpgate);
		}
		MultiplicationGate gate = new MultiplicationGate(input2);
		gate.compute();
		ArrayList<SecretShare> outcome = new ArrayList<SecretShare>();
		for(GateIO gio : gate.getResult()) {
			//System.out.println(gio.getValue().size());
			outcome.add(gio.getValue().get(0));
		}
		System.out.println(Polynomial.computeSecret(outcome));
		input2 = new ArrayList<GateIO>(); 
		ArrayList<SecretShare> ss3 = p3.shareSecret();
		for(int i=0;i<ss3.size();i++) {
			ArrayList<SecretShare> tmpparty = new ArrayList<SecretShare>();
			tmpparty.add(0,ss3.get(i));
			tmpparty.add(1,outcome.get(i));
			System.out.println("input secret shares for party" + i + " is:" + ss3.get(i) + " " + outcome.get(i));
			GateIO tmpgate = new GateIO(i,tmpparty);
			input2.add(tmpgate);
		}
		MultiplicationGate gate2 = new MultiplicationGate(input2);
		gate2.compute();
		ArrayList<SecretShare> outcome2 = new ArrayList<SecretShare>();
		for(GateIO gio : gate2.getResult()) {
			//System.out.println(gio.getValue().size());
			outcome2.add(gio.getValue().get(0));
		}
		System.out.println(Polynomial.computeSecret(outcome2));
	}
	public static void testConstCompute()
	{	
		int constant = 10;
		ConstMult mult = new ConstMult(input, constant);
		mult.compute();
		ArrayList<SecretShare> outcome = new ArrayList<SecretShare>();
		for(GateIO gio : mult.getResult()) {
			outcome.add(gio.getValue().get(0));
		}
		System.out.println(Polynomial.computeSecret(outcome));
		
		/*ArrayList<GateIO> input = new ArrayList<GateIO>();
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
		for (int i = 0; i < initInput.size(); i++)		{
			System.out.println("secret share " + (i+1) + " is: " + initInput.get(i).toString());
		}
		System.out.println("**********************\n");	
		ConstMult mult = new ConstMult(input, constant);
		*/
	}

	public static void setup()
	{
		
		Party p1 = new Party(3);
		Party p2 = new Party(2);
		Party p3 = new Party(5);		
		
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
	}
}