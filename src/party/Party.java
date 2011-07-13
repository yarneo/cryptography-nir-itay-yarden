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
		// initial secret sharing
		// key is Party PartyIndex
		Polynomial.shareSecret("Party " + Integer.toString(this.getIndex()), this.secret);
	}

	public void addSecret(SecretShare s) {
		this.shares.add(s);		
	}
	
	public int getIndex(){
		return index;
	}
	
	public SecretShare getSecret(String key){
		for (SecretShare s : this.shares) {
			if(s.key.equals(key))
				return s;
		}
		
		return null;
	}

}
