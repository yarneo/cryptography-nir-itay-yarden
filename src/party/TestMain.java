package party;

import java.util.ArrayList;
import java.util.Vector;

import utils.Polynomial;
import crypto.SecretShare;
import gates.AdditionGate;
import gates.ConstMult;
import gates.GateIO;
import gates.MultiplicationGate;
import gates.PolynomialGate;

public class TestMain {

	/**
	 * @param args
	 */
	public static ArrayList<GateIO> input = new ArrayList<GateIO>();

	public static void main(String[] args) {
		Party p1 = new Party(1);
		Party p2 = new Party(2);
		Party p3 = new Party(3);
		System.out.println("field = " + Party.field);
		
	}

	// create 3 parties, need t+1 coefs, for any x in field
	private static void testPolynom(ArrayList<Integer> coefs, int x) {
		ArrayList<SecretShare> shares = Polynomial.createShareSecret(x);
		ArrayList<GateIO> input = new ArrayList<GateIO>();

		// 0
		ArrayList<SecretShare> g0Secrests = new ArrayList<SecretShare>();
		g0Secrests.add(0, shares.get(0));
		GateIO g0 = new GateIO(0, g0Secrests);

		// 1
		ArrayList<SecretShare> g1Secrests = new ArrayList<SecretShare>();
		g1Secrests.add(0, shares.get(1));
		GateIO g1 = new GateIO(0, g1Secrests);

		// 2
		ArrayList<SecretShare> g2Secrests = new ArrayList<SecretShare>();
		g2Secrests.add(0, shares.get(2));
		GateIO g2 = new GateIO(0, g2Secrests);

		input.add(g0);
		input.add(g1);
		input.add(g2);

		PolynomialGate polyGate = new PolynomialGate(input, coefs);
		polyGate.compute();
		ArrayList<SecretShare> res = new ArrayList<SecretShare>();
		for (int i = 0; i < polyGate.result.size(); i++) {
			res.add(i, polyGate.result.get(i).value.get(0));
			System.out.print(polyGate.result.get(i).value.get(0) + ", ");
		}
	}

	public static void testMultiplicationGate() {
		MultiplicationGate gate = new MultiplicationGate(input);
		gate.compute();
	}

	public static void testConstCompute() {
		Party p1 = new Party(3); // create p1 with secert 3
		GateIO forP1 = new GateIO(0);
		int constant = 54;

		forP1.value.add(new SecretShare(1, -4));
		forP1.value.add(new SecretShare(1, 10));
		ArrayList<GateIO> arr = new ArrayList<GateIO>();
		arr.add(forP1);
		ConstMult mult = new ConstMult(arr, constant);
		mult.compute();

		/*
		 * ArrayList<GateIO> input = new ArrayList<GateIO>(); int constant = 5;
		 * int secret = 10; Party p1 = new Party(1); Party p2 = new Party(10);
		 * ArrayList<SecretShare> initInput = new ArrayList<SecretShare>();
		 * Polynomial poly = Polynomial.create(secret);
		 * System.out.println("**********************");
		 * System.out.println("The polynom coefficients are:");
		 * poly.printPolynomial();
		 * System.out.println("**********************\n"); for (Party p :
		 * Party.parties) { int valueInP = poly.computeCoef(p.getIndex());
		 * SecretShare s = new SecretShare(p.getIndex(), valueInP);
		 * initInput.add(p.getIndex()-1, s); }
		 * System.out.println("**********************");
		 * System.out.println("The init input is:"); for (int i = 0; i <
		 * initInput.size(); i++) { System.out.println("secret share " + (i+1) +
		 * " is: " + initInput.get(i).toString()); }
		 * System.out.println("**********************\n"); ConstMult mult = new
		 * ConstMult(input, constant);
		 */
	}

	public static void testAdditionCompute() {
		Party p1 = new Party(3); // create p1 with secert 3
		Party p2 = new Party(2); // create p2 with secert 2

		ArrayList<SecretShare> arr1 = new ArrayList<SecretShare>();
		arr1.add(new SecretShare(1, -4));
		arr1.add(new SecretShare(1, 10));

		ArrayList<SecretShare> arr2 = new ArrayList<SecretShare>();
		arr2.add(new SecretShare(2, -11));
		arr2.add(new SecretShare(2, 18));

		GateIO forP1 = new GateIO(0, arr1);
		GateIO forP2 = new GateIO(1, arr2);
		/*
		 * forP1.value.add(new SecretShare(1, -4)); forP1.value.add(new
		 * SecretShare(1, 10)); forP2.value.add(new SecretShare(2, -11));
		 * forP2.value.add(new SecretShare(2, 18));
		 */
		ArrayList<GateIO> arr = new ArrayList<GateIO>();
		arr.add(forP1);
		arr.add(forP2);

		AdditionGate add = new AdditionGate(arr);
		add.compute();
		ArrayList<SecretShare> outcome = new ArrayList<SecretShare>();

		for (GateIO gio : add.getResult()) {
			outcome.add(gio.getIndex(), gio.getValue().get(0));
		}
		System.out.println(Polynomial.computeSecret(outcome));

	}

	public static void setup() {
		Party p1 = new Party(3); // create p1 with secert 3
		Party p2 = new Party(2); // create p2 with secert 2
		Party p3 = new Party(5); // create p3 with secert 5

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