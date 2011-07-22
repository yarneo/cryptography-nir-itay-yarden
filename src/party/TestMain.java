package party;

import java.util.ArrayList;
import java.util.Vector;

import circuit.Circuit;

import utils.Polynomial;
import crypto.SecretShare;
import functions.Average;
import functions.GlobalAgreement;
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
		//testCircuit();
		//testAverage();
		//testGlobalAgreement();
		testSubstract();
	}
	
	public static void testFrequency() {
		int numOfContestents = 2;
		ArrayList<Integer> vote = new ArrayList<Integer>();
		vote.add(0);
		vote.add(1);
		Party p1 = new Party(vote);
		Party p2 = new Party(vote);
		Party p3 = new Party(vote);
		vote.set(0, 1);
		vote.set(1, 0);
		Party p4 = new Party(vote);
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