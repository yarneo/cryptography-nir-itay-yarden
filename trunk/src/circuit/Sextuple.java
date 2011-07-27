package circuit;

import gates.Gate;

import java.util.ArrayList;

import crypto.SecretShare;

public class Sextuple {
	public Gate gate;
	public ArrayList<ArrayList<SecretShare>> secretShares;
	public int index;
	public int next;
	public int type; // 0 = addition, 1 = multiplication, 2 = constmult, 3 =
						// maximum
	public ArrayList<SecretShare> output;
	public int constant;

	public Sextuple(ArrayList<ArrayList<SecretShare>> secretShares, int index,
			int next, int type) {
		this.secretShares = secretShares;
		this.index = index;
		this.next = next;
		this.type = type;
	}

	public Sextuple(ArrayList<ArrayList<SecretShare>> secretShares, int index,
			int next, int type, int constant) {
		this.secretShares = secretShares;
		this.index = index;
		this.next = next;
		this.type = type;
		this.constant = constant;
	}

	public void changeNext(int next) {
		this.next = next;
	}

}