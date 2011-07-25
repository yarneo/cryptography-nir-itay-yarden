package party;

import java.util.ArrayList;
import java.util.Vector;

import circuit.Circuit;

import utils.LaGrange;
import utils.Polynomial;
import utils.PolynomialFunctionLagrangeForm;
import crypto.SecretShare;
import functions.Average;
import functions.Frequency;
import functions.GlobalAgreement;
import gates.AdditionGate;
import gates.ConstMult;
import gates.GateIO;
import gates.MaxGate;
import gates.MultiplicationGate;
import gates.PolynomialGate;

public class TestMain {

	/**
	 * @param args
	 */
	public static ArrayList<GateIO> input = new ArrayList<GateIO>();

	public static void main(String[] args) {
	}
	
	public static void testFrequency() {
		int numOfContestents = 3;
		ArrayList<Integer> vote = new ArrayList<Integer>();
		vote.add(0);
		vote.add(1);
		vote.add(0);
		Party p1 = new Party(vote);
		ArrayList<Integer> vote2 = new ArrayList<Integer>();
		vote2.add(1);
		vote2.add(0);
		vote2.add(0);
		Party p2 = new Party(vote2);
		ArrayList<Integer> vote3 = new ArrayList<Integer>();
		vote3.add(0);
		vote3.add(0);
		vote3.add(1);
		Party p3 = new Party(vote3);
		Party p4 = new Party(vote3);
		Party p5 = new Party(vote3);
		ArrayList<Party> parties = new ArrayList<Party>();
		parties.add(p1);
		parties.add(p2);
		parties.add(p3);
		parties.add(p4);
		parties.add(p5);
		System.out.println(Polynomial.computeSecret(Frequency.frequency(parties, numOfContestents)));	
	}


	public static void testSubstract() {
		Party p1 = new Party(7);
		Party p2 = new Party(4);
		Circuit circuit = new Circuit();

		int ret = circuit.addConstGate(p2.shareSecret(), -1);
		int ret2 = circuit.addAdditionGate(ret, p1.shareSecret());

		circuit.computeCircuit();
		ArrayList<SecretShare> out = circuit.getOutput(ret2);
		System.out.println(Polynomial.computeSecret(out));
	}

	public static void testAverage() {
		Party p1 = new Party(1);
		Party p2 = new Party(3);
		Party p3 = new Party(2);
		Party p4 = new Party(2);
		ArrayList<Party> parties = new ArrayList<Party>();
		parties.add(p1);
		parties.add(p2);
		parties.add(p3);
		parties.add(p4);
		System.out.println((Polynomial.computeSecret(Average.average(parties)))/parties.size());
	}

	public static void testGlobalAgreement() {
		Party p1 = new Party(1);
		Party p2 = new Party(1);
		Party p3 = new Party(1);
		Party p4 = new Party(1);
		ArrayList<Party> parties = new ArrayList<Party>();
		parties.add(p1);
		parties.add(p2);
		parties.add(p3);
		parties.add(p4);
		System.out.println((Polynomial.computeSecret(GlobalAgreement.globalAgreement(parties))));
	}

	public static void testCircuit() {
		Party p1 = new Party(1);
		Party p2 = new Party(3);
		Party p3 = new Party(2);
		Party p4 = new Party(2);
		Circuit circuit = new Circuit();

		int ret = circuit.addMulGate(p1.shareSecret()); //1*3
		circuit.addSecretShare(ret, p2.shareSecret());

		int ret2 = circuit.addMulGate(p3.shareSecret()); //2*2
		circuit.addSecretShare(ret2, p4.shareSecret());

		int ret3 = circuit.addAdditionGate(ret, null); //(1*3)+(2*2)
		circuit.setNext(ret2, ret3);

		circuit.computeCircuit();
		ArrayList<SecretShare> out = circuit.getOutput(ret3);
		System.out.println(Polynomial.computeSecret(out));
	}

	private static void testMax() {
		Party p1 = new Party(1);
		Party p2 = new Party(2);
		Party p3 = new Party(3);
		Party p4 = new Party(2);
		Party p5 = new Party(2);
		Party p6 = new Party(1);
		Party p7 = new Party(3);
		Party p8 = new Party(1);
		System.out.println("field = " + Party.field);

		// if a>b return 1
		// else return 0
		int a = 1;
		int b = 3;
		ArrayList<SecretShare> aShares = Polynomial.createShareSecret(a);
		ArrayList<SecretShare> bShares = Polynomial.createShareSecret(b);
		ArrayList<GateIO> input = new ArrayList<GateIO>();

		// 0
		ArrayList<SecretShare> g0Secrests = new ArrayList<SecretShare>();
		g0Secrests.add(0, aShares.get(0));
		g0Secrests.add(1, bShares.get(0));
		GateIO g0 = new GateIO(0, g0Secrests);

		// 1
		ArrayList<SecretShare> g1Secrests = new ArrayList<SecretShare>();
		g1Secrests.add(0, aShares.get(1));
		g1Secrests.add(1, bShares.get(1));
		GateIO g1 = new GateIO(1, g1Secrests);

		// 2
		ArrayList<SecretShare> g2Secrests = new ArrayList<SecretShare>();
		g2Secrests.add(0, aShares.get(2));
		g2Secrests.add(1, bShares.get(2));
		GateIO g2 = new GateIO(2, g2Secrests);
		
		ArrayList<SecretShare> g3Secrests = new ArrayList<SecretShare>();
		g3Secrests.add(0, aShares.get(3));
		g3Secrests.add(1, bShares.get(3));
		GateIO g3 = new GateIO(3, g3Secrests);
		
		ArrayList<SecretShare> g4Secrests = new ArrayList<SecretShare>();
		g4Secrests.add(0, aShares.get(4));
		g4Secrests.add(1, bShares.get(4));
		GateIO g4 = new GateIO(4, g4Secrests);
		
		ArrayList<SecretShare> g5Secrests = new ArrayList<SecretShare>();
		g5Secrests.add(0, aShares.get(5));
		g5Secrests.add(1, bShares.get(5));
		GateIO g5 = new GateIO(5, g5Secrests);
		
		ArrayList<SecretShare> g6Secrests = new ArrayList<SecretShare>();
		g6Secrests.add(0, aShares.get(6));
		g6Secrests.add(1, bShares.get(6));
		GateIO g6 = new GateIO(6, g6Secrests);
		
		ArrayList<SecretShare> g7Secrests = new ArrayList<SecretShare>();
		g7Secrests.add(0, aShares.get(7));
		g7Secrests.add(1, bShares.get(7));
		GateIO g7 = new GateIO(7, g7Secrests);
		
		input.add(0, g0);
		input.add(1, g1);
		input.add(2, g2);
		input.add(3, g3);
		input.add(4, g4);
		input.add(5, g5);
		input.add(6, g6);
		input.add(7, g7);

		MaxGate max = new MaxGate(input);
		max.compute();
		ArrayList<SecretShare> res = new ArrayList<SecretShare>();
		for (int i = 0; i < max.result.size(); i++) {
			res.add(i, max.getIOByIndex(i).value.get(0));
		}
		System.out.println("the max(need to be 0) = "
				+ Polynomial.computeSecret(res));

//		int c = 2;
//		int d = 1;
//
//		ArrayList<SecretShare> cShares = Polynomial.createShareSecret(c);
//		ArrayList<SecretShare> dShares = Polynomial.createShareSecret(d);
//		ArrayList<GateIO> cdinput = new ArrayList<GateIO>();
//		for (int i = 0; i < 3; i++) {
//			ArrayList<SecretShare> cdShares = new ArrayList<SecretShare>();
//			cdShares.add(0, cShares.get(i));
//			cdShares.add(1, dShares.get(i));
//			GateIO cdIOi = new GateIO(i, cdShares);
//			cdinput.add(i, cdIOi);
//		}
//
//		MaxGate cdMax = new MaxGate(cdinput);
//		cdMax.compute();
//		ArrayList<SecretShare> cdRes = new ArrayList<SecretShare>();
//		for (int i = 0; i < cdMax.getResult().size(); i++) {
//			cdRes.add(i, cdMax.getIOByIndex(i).value.get(0));
//		}
//		System.out.println("the max (need to be 1) = "
//				+ Polynomial.computeSecret(cdRes));
	}

	private static void testMaxPolynomial() {
		Party p1 = new Party(1);
		Party p2 = new Party(2);
		Party p3 = new Party(3);
		System.out.println("field = " + Party.field);

		int x = -3;
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

		ArrayList<Integer> coefs = new ArrayList<Integer>();
		coefs.add(0, 0);
		coefs.add(1, 4);
		coefs.add(2, 0);
		coefs.add(3, 6);
		coefs.add(4, 0);
		coefs.add(5, 1);
		coefs.add(6, 4);

		PolynomialGate polyGate = new PolynomialGate(input, coefs);
		polyGate.compute();
		ArrayList<SecretShare> res = new ArrayList<SecretShare>();
		for (int i = 0; i < polyGate.result.size(); i++) {
			res.add(i, polyGate.result.get(i).value.get(0));
			System.out.print(polyGate.result.get(i).value.get(0) + ", ");
		}
		System.out.println();
		System.out.println("result = " + Polynomial.computeSecret(res));
	}

	// need t+1 coefs, for any x in field
	private static void testPolynom(ArrayList<Integer> coefs, int x) {
		Party p1 = new Party(1);
		Party p2 = new Party(2);
		Party p3 = new Party(3);
		System.out.println("field = " + Party.field);

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