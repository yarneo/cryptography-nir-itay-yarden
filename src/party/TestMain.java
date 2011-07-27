package party;

import java.util.ArrayList;
import java.util.Vector;
import circuit.Circuit;
import functions.Average;
import functions.Frequency;
import functions.GlobalAgreement;
import utils.LaGrange;
import utils.Polynomial;
import utils.PolynomialFunctionLagrangeForm;
import crypto.SecretShare;
import gates.AdditionGate;
import gates.ConstMult;
import gates.GateIO;
import gates.MaxGate;
import gates.MultiplicationGate;
import gates.PolynomialGate;

public class TestMain {

	public static ArrayList<GateIO> input = new ArrayList<GateIO>();

	/*
	 * Comments: 
	 * 1. for every test we used different amount of parties. if you
	 * run some test, there is a need to look at the number of parties and the
	 * the Party.n variable at class Party.
	 * 
	 * 2. each test need to run alone from other tests ( means, don't do:
	 * testFrequncy(); testGlobalAgreement(); )
	 */

	public static void main(String[] args) {
		// testFrequency(); - what it does?, put comments
		// testSubstract();
		// testAverage(); - not working
		// testGlobalAgreement();
		// testCircuit();
		testMax();
		
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
		System.out.println(Polynomial.computeSecret(Frequency.frequency(
				parties, numOfContestents)));
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
		Party p2 = new Party(1);
		Party p3 = new Party(7);
		Party p4 = new Party(7);
		System.out.println("field = " + Party.field);
		
		ArrayList<Party> parties = new ArrayList<Party>();
		parties.add(p1);
		parties.add(p2);
		parties.add(p3);
		parties.add(p4);
		System.out.println((Polynomial.computeSecret(Average.average(parties)))
				/ parties.size());
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
		System.out.println((Polynomial.computeSecret(GlobalAgreement
				.globalAgreement(parties))));
	}

	public static void testCircuit() {
		Party p1 = new Party(1);
		Party p2 = new Party(3);
		Party p3 = new Party(2);
		Party p4 = new Party(2);
		Circuit circuit = new Circuit();

		int ret = circuit.addMulGate(p1.shareSecret()); // 1*3
		circuit.addSecretShare(ret, p2.shareSecret());

		int ret2 = circuit.addMulGate(p3.shareSecret()); // 2*2
		circuit.addSecretShare(ret2, p4.shareSecret());

		int ret3 = circuit.addAdditionGate(ret, null); // (1*3)+(2*2)
		circuit.setNext(ret2, ret3);

		circuit.computeCircuit();
		ArrayList<SecretShare> out = circuit.getOutput(ret3);
		System.out.println(Polynomial.computeSecret(out));
	}

	private static void testMax() {
		Party p1 = new Party(1);
		Party p2 = new Party(2);
		Party p3 = new Party(3);
		System.out.println("field = " + Party.field);

		// if a>b return 1
		// else return 0
		int a = 2;
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

		input.add(0, g0);
		input.add(1, g1);
		input.add(2, g2);

		MaxGate max = new MaxGate(input);
		max.compute();
		ArrayList<SecretShare> res = new ArrayList<SecretShare>();
		for (int i = 0; i < max.result.size(); i++) {
			res.add(i, max.getIOByIndex(i).value.get(0));
		}
		System.out.println("the max(need to be 0) = "
				+ Polynomial.computeSecret(res));

		int c = 2;
		int d = 1;

		ArrayList<SecretShare> cShares = Polynomial.createShareSecret(c);
		ArrayList<SecretShare> dShares = Polynomial.createShareSecret(d);
		ArrayList<GateIO> cdinput = new ArrayList<GateIO>();
		for (int i = 0; i < 3; i++) {
			ArrayList<SecretShare> cdShares = new ArrayList<SecretShare>();
			cdShares.add(0, cShares.get(i));
			cdShares.add(1, dShares.get(i));
			GateIO cdIOi = new GateIO(i, cdShares);
			cdinput.add(i, cdIOi);
		}

		MaxGate cdMax = new MaxGate(cdinput);
		cdMax.compute();
		ArrayList<SecretShare> cdRes = new ArrayList<SecretShare>();
		for (int i = 0; i < cdMax.getResult().size(); i++) {
			cdRes.add(i, cdMax.getIOByIndex(i).value.get(0));
		}
		System.out.println("the max (need to be 1) = "
				+ Polynomial.computeSecret(cdRes));
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

}