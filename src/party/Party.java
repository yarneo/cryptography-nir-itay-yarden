package party;

import gates.Gate;
import java.util.ArrayList;
import java.util.Vector;

import utils.Polynomial;
import crypto.SecretShare;

public class Party {

	public final static int t = 1; // number of Affiliates rivals
	public final static int n = 3; // number of parties
	// NOTICE - the parties are indexed by 1, .. , n (not by zero)
	public static int field = 0;

	public static ArrayList<Party> parties = new ArrayList<Party>();
	private static int PartyIndex = 1;

	private int secret;
	private int index;
	private ArrayList<Integer> vote;
	private ArrayList<SecretShare> shares;

	public Party() {
		this.secret = -1; // no secret
		this.index = PartyIndex;
		PartyIndex++;
		parties.add(this);
		shares = new ArrayList<SecretShare>();

		if (field == 0) {
			field = 2 * n + 1;
			while (!isPrime(field)) {
				field++;
			}
		}
	}

	public Party(int secret) {
		this.secret = secret;
		this.index = PartyIndex;
		PartyIndex++;
		parties.add(this);
		shares = new ArrayList<SecretShare>();

		if (field == 0) {
			field = 2 * n + 1;
			while (!isPrime(field)) {
				field++;
			}
		}
	}

	public Party(ArrayList<Integer> vote) {
		this.vote = vote;
		this.index = PartyIndex;
		PartyIndex++;
		parties.add(this);
		shares = new ArrayList<SecretShare>();

		if (field == 0) {
			field = 2 * n + 1;
			while (!isPrime(field)) {
				field++;
			}
		}
	}

	// share the local secret with the other parties
	public ArrayList<SecretShare> shareSecret() {
		// initial secret sharing
		return Polynomial.createShareSecret(this.secret);
	}

	public ArrayList<SecretShare> shareVote(int index) {
		// initial secret sharing
		return Polynomial.createShareSecret(this.vote.get(index));
	}
	
	
	public void addSecretShare(SecretShare s) {
		this.shares.add(s);
	}

	public int getIndex() {
		return index;
	}

	public ArrayList<SecretShare> getShares() {
		return shares;
	}

	private boolean isPrime(int n) {
		if (n <= 1) {
			return false;
		}
		if (n == 2) {
			return true;
		}
		if (n % 2 == 0) {
			return false;
		}
		for (int i = 3; i <= Math.sqrt(n) + 1; i = i + 2) {
			if (n % i == 0) {
				return false;
			}
		}
		return true;
	}

}
