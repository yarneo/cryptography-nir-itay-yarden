package party;

import gates.Gate;
import java.util.ArrayList;
import java.util.Vector;
import crypto.SecretShare;

public class Party {

	public final static int t = 1; // number of Affiliates rivals
	public final static int n = 2 * t + 1; // number of parties
	// NOTICE - the parties are indexed by 1, .. , n (not by zero)
	public final static int field = 2 * n + 1;
	public static ArrayList<Party> parties = new ArrayList<Party>();
	private static int PartyIndex = 1;

	private int secret;
	private int index;
	private ArrayList<SecretShare> shares;

	public Party(int secret) {
		this.secret = secret;
		this.index = PartyIndex;
		PartyIndex++;
		parties.add(this);
	}

	// share the local secret with the other parties
	public void shareSecret() {
		Vector<Integer> coef = this.createPolynomial(this.secret);
		

	}

	// create a polynomial with degree t
	public Vector<Integer> createPolynomial(int secret) {
		Vector<Integer> coefs = new Vector<Integer>();
		for (int i = 0; i < Party.t; i++) {
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
		return coefs;
	}

	// for debug
	public void printPolynoial(Vector<Integer> p) {
		for (int i = 0; i < p.size(); i++) {
			System.out.println("coef " + i + ": " + p.get(i));
		}
	}

}
