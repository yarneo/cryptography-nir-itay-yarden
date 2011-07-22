package utils;

import gates.Gate;
import gates.GateIO;

import java.util.ArrayList;

import crypto.SecretShare;

import party.Party;

public class Polynomial {

	private ArrayList<Integer> coef;

	public Polynomial() {
	}

	// change to private!!
	public Polynomial(ArrayList<Integer> coef) {
		this.coef = coef;
	}

	// get val at point value
	public int computeCoef(int value) {
		int ans = coef.get(0);
		for (int i = 1; i < coef.size(); i++) {
			ans += (coef.get(i) * (int) (Math.pow((double) value, (double) i)));
		}
		// System.out.println("result = " + ans);
		return Gate.modField(ans);
	}

	// create a polynomial with degree t
	public static Polynomial create(int secret) {
		ArrayList<Integer> coefs = new ArrayList<Integer>();
		for (int i = 0; i <= Party.t; i++) {
			int coef = (int) (Math.random() * Party.field);
			coefs.add(i, Gate.modField(coef));
			// System.out.println("coef " + i + ": " + coef);
		}
		coefs.set(0, secret);
		// demand that the final coef is not zero
		while (coefs.get(Party.t - 1) == 0) {
			if ((Party.t == 1) && (secret == 0))
				break;
			coefs.set(Party.t - 1, Gate.modField((int) (Math.random() * 100)));
		}
		return new Polynomial(coefs);
	}

	// for debug
	public void printPolynomial() {
		for (int i = 0; i < this.coef.size(); i++) {
			System.out.println("coef " + i + ": " + this.coef.get(i));
		}
	}

	// share the local secret with the other parties
	public static ArrayList<SecretShare> createShareSecret(int secret) {
		ArrayList<SecretShare> result = new ArrayList<SecretShare>();
		Polynomial poly = Polynomial.create(secret);
		for (Party p : Party.parties) {
			int valueInP = poly.computeCoef(p.getIndex());
			SecretShare s = new SecretShare(p.getIndex(), valueInP);
			result.add(p.getIndex() - 1, s);
			// p.addSecretShare(s);
		}
		return result;
	}

	// LaGrange computation (in 0)
	public static int computeSecret(ArrayList<SecretShare> shares) {
		return new LaGrange().computeLagrange(shares, 0);
	}

	// return array [d, a, b] such that d = gcd(p, q), ap + bq = d
	public static int[] extendedGcd(int p, int q) {
		if (q == 0)
			return new int[] { p, 1, 0 };
		int[] vals = extendedGcd(q, p % q);
		int d = vals[0];
		int a = vals[2];
		int b = vals[1] - (p / q) * vals[2];
		return new int[] { d, a, b };
	}

	// division of first and second above the field (first/second)mod FIELD
	// first we get the a coef from extended gcd (a*x + b*FIELD)
	// that equal to a*x = 1 (b*FIELD = 0) = thus a=x^-1
	// return a*first
	public static int fieldDiv(int first, int second) {
		// get coef a for div 1 and the field
		int a = extendedGcd(Gate.modField(second), Party.field)[1];
		if (Gate.modField(second) == 0){
			throw new ArithmeticException("in fieldDiv, division by zero");
		}
		return Gate.modField(a * Gate.modField(first));
	}

}
