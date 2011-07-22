package functions;

import java.util.ArrayList;

import circuit.Circuit;

import party.Party;

import crypto.SecretShare;

public class Average {

	public static ArrayList<SecretShare> average(ArrayList<Party> parties) {
		Circuit circuit = new Circuit();
		int ind=-1;
		int ind2=-1;
		if(parties.size() >= 2) {
			ind = circuit.addAdditionGate(-1,parties.get(0).shareSecret());
			circuit.addSecretShare(ind, parties.get(1).shareSecret());
			for(int i=2;i<parties.size();i++) {
				ind2 = circuit.addAdditionGate(ind, parties.get(i).shareSecret());
				ind = ind2;
			}
		}
		circuit.computeCircuit();
		return circuit.getOutput(ind);
	}



}
