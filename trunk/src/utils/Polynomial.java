package utils;

import gates.Gate;

import java.util.ArrayList;
import java.util.Vector;

import crypto.SecretShare;

import party.Party;

public class Polynomial {
	
	private Vector<Integer> coef;
	
	// change to private!!
	public Polynomial(Vector<Integer> coef){
		this.coef = coef;
	}
	
	public int computeCoef(int value) {
//		System.out.println("value = " + value);
		int ans = coef.get(0);
		for (int i = 1; i < coef.size(); i++) {
//			System.out.println("coef.get(i)" + coef.get(i));
//			System.out.println("value = " + value);
//			System.out.println("i = " + i);
//			System.out.println("(value ^ i)" + (value ^ i));
			//System.out.println(" i = " + i + " x = " + (coef.get(i) * (value ^ i)));
			ans = ans + (coef.get(i) * (int)(Math.pow((double)value, (double)i)));			
		}
//		System.out.println("result = " + ans);
		return Gate.modField(ans);
	}
	
	// create a polynomial with degree t
	public static Polynomial create(int secret){
		Vector<Integer> coefs = new Vector<Integer>();
		for (int i = 0; i <= Party.t; i++) {
			int coef = (int) (Math.random() * 100);
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
		}
		return result;
	}
	
}
