package party;

import gates.Gate;
import java.util.ArrayList;
import java.util.Vector;

import utils.Polynomial;
import crypto.SecretShare;

public class Party {

	public final static int t = 1; // number of Affiliates rivals
	public final static int n = 2 * t + 1; // number of parties
	// NOTICE - the parties are indexed by 1, .. , n (not by zero)
	//public final static int field = 2 * n + 1;
	public final static int field = 10000; // just for testing;


	public static ArrayList<Party> parties = new ArrayList<Party>();
	private static int PartyIndex = 1;

	private int secret;
	private int index;
	private ArrayList<SecretShare> shares;

	public Party() {
		this.secret = -1; //no secret
		this.index = PartyIndex;
		PartyIndex++;
		parties.add(this);
		shares = new ArrayList<SecretShare>();
	}
	
	public Party(int secret) {
		this.secret = secret;
		this.index = PartyIndex;
		PartyIndex++;
		parties.add(this);
		shares = new ArrayList<SecretShare>();
	}

	// share the local secret with the other parties
	public ArrayList<SecretShare> shareSecret() {
		// initial secret sharing
		return Polynomial.createShareSecret(this.secret);
	}

	public void addSecretShare(SecretShare s) {
		this.shares.add(s);		
	}
	
	public int getIndex(){
		return index;
	}
	
	public ArrayList<SecretShare> getShares() {
		return shares;
	}

}
