package functions;

import gates.GateIO;
import gates.MaxGate;

import java.util.ArrayList;

import party.Party;
import utils.Polynomial;
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
				ind = circuit.addAdditionGate(-1,parties.get(0).shareVote(j));
				circuit.addSecretShare(ind, parties.get(1).shareVote(j));
				for(int i=2;i<parties.size();i++) {
					ind2 = circuit.addAdditionGate(ind, parties.get(i).shareVote(j));
					ind = ind2;
				}
				outputsindex.add(ind);
			}
		}
		circuit.computeCircuit();
		ArrayList<ArrayList<SecretShare>> outputs = new ArrayList<ArrayList<SecretShare>>();
		for(Integer index : outputsindex) {
			outputs.add(circuit.getOutput(index));
			System.out.println(Polynomial.computeSecret(circuit.getOutput(index)));
		}
		Circuit circuit2;
		ind=-1;
		ind2=-1;
		int ind3 = -1;
		ArrayList<SecretShare> outy = new ArrayList<SecretShare>();
		if(outputs.size() >= 2) {
			circuit2 = new Circuit();
			ind = circuit2.addMaxGate(-1,outputs.get(0));
			circuit2.addSecretShare(ind, outputs.get(1)); //Z
			circuit2.computeCircuit();
			ArrayList<SecretShare> Z = circuit2.getOutput(ind);
			System.out.println(Polynomial.computeSecret(Z));
			circuit2 = new Circuit();
			ind = circuit2.addConstGate(Z, -1); //(1-Z)
			ArrayList<SecretShare> oneConst = Polynomial.createShareSecret(1);
			ind2 = circuit2.addAdditionGate(ind, oneConst);
			ind = circuit2.addMulGate(outputs.get(0)); //A*Z
			circuit2.addSecretShare(ind, Z);
			ind3 = circuit2.addMulGate(outputs.get(1)); //B*(1-Z)
			circuit2.setNext(ind2, ind3);
			ind2 = circuit2.addAdditionGate(ind,null);
			circuit2.setNext(ind3, ind2);
			circuit2.computeCircuit();
			System.out.println(Polynomial.computeSecret(circuit2.getOutput(ind2)));
			outy = circuit2.getOutput((ind2));

			for(int i=2;i<outputs.size();i++) {
				circuit2 = new Circuit();
				ind = circuit2.addMaxGate(-1,outy);
				circuit2.addSecretShare(ind, outputs.get(i)); //Z
				circuit2.computeCircuit();
				Z = circuit2.getOutput(ind);
				System.out.println(Polynomial.computeSecret(Z));
				circuit2 = new Circuit();
				ind = circuit2.addConstGate(Z, -1); //(1-Z)
				oneConst = Polynomial.createShareSecret(1);
				ind2 = circuit2.addAdditionGate(ind, oneConst);
				ind = circuit2.addMulGate(outy); //A*Z
				circuit2.addSecretShare(ind, Z);
				ind3 = circuit2.addMulGate(outputs.get(i)); //B*(1-Z)
				circuit2.setNext(ind2, ind3);
				ind2 = circuit2.addAdditionGate(ind,null);
				circuit2.setNext(ind3, ind2);
				circuit2.computeCircuit();
				System.out.println(Polynomial.computeSecret(circuit2.getOutput(ind2)));
				outy = circuit2.getOutput((ind2));
			}
		}
		return outy;
	}

}
