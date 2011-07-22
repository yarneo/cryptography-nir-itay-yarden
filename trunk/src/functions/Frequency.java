package functions;

import java.util.ArrayList;

import party.Party;
import circuit.Circuit;
import crypto.SecretShare;

public class Frequency {

	public static ArrayList<SecretShare> frequency(ArrayList<Party> parties, int contestents) {
		ArrayList<Integer> outputsindex = new ArrayList<Integer>();
		Circuit circuit = new Circuit();
		int ind=-1;
		int ind2=-1;
		if(parties.size() >= 2) {
			for(int j=0;j<contestents;j++) {
				ind = circuit.addAdditionGate(-1,parties.get(0).shareSecret());
				circuit.addSecretShare(ind, parties.get(1).shareSecret());
				for(int i=2;i<parties.size();i++) {
					ind2 = circuit.addAdditionGate(ind, parties.get(i).shareSecret());
					ind = ind2;
				}
				outputsindex.add(ind);
			}
		}
		circuit.computeCircuit();
		ArrayList<ArrayList<SecretShare>> outputs = new ArrayList<ArrayList<SecretShare>>();
		for(Integer index : outputsindex) {
			outputs.add(circuit.getOutput(index));
		}
		return null;
	}

}
