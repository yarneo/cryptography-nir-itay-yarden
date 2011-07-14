package gates;

import java.util.ArrayList;
import java.util.Vector;

import party.Party;
import utils.Polynomial;

import crypto.SecretShare;

public class MultiplicationGate extends Gate {

	public MultiplicationGate(ArrayList<GateIO> input) {
		super(input);
	}

	// compute multiplication between two secret shares
	@Override
	public void compute() {
		
		//compute s1, s2 secret shares for all the parties
		//s1s2[i] - pair(s1 SecretShare, s2 SecretShare) for party i
		Vector<Vector<SecretShare>> s1s2 = new Vector<Vector<SecretShare>>();
		for (GateIO in : this.input) {
			Vector<SecretShare> theShares = new Vector<SecretShare>();
			theShares.add(0, in.getValue().get(0)); // s1
			theShares.add(1, in.getValue().get(1)); // s2
			s1s2.add(in.getIndex(), theShares);
		}
		
		//compute locally - s1*s2
		Vector<SecretShare> localMults = new Vector<SecretShare>();
		for (int i = 0; i < s1s2.size(); i++) {
			localMults.add(i, new SecretShare(s1s2.get(i).get(0).x,
					modField(s1s2.get(i).get(0).y * s1s2.get(i).get(1).y)));			
		}
		
		// share the local multiplications between the parties
		Vector<Vector<SecretShare>> localMultsShare = new Vector<Vector<SecretShare>>();
		for (int i = 0; i < localMultsShare.size(); i++) {
			localMultsShare.add(i, new Vector<SecretShare>());			
		}
		for (SecretShare mult : localMults) {
			Vector<SecretShare> yiShares = Polynomial.shareSecret(mult.y);
			for (int i = 0; i < yiShares.size(); i++) {
				Vector<SecretShare> tmp = localMultsShare.get(i);
				tmp.add(yiShares.get(i));
				localMultsShare.set(i, tmp);
			}
		}
		
		//compute the outPut = Yi * Zi
		this.result = new ArrayList<GateIO>();
		for (int i = 0; i < localMultsShare.size(); i++) {
			GateIO out = new GateIO(0);
			int outYValue = 0;
			for (int j = 0; j < localMultsShare.get(i).size(); j++) {
				outYValue += computeZi(j) * ((localMultsShare.get(i)).get(j)).y;
			}
			out.value.add(new SecretShare(i, modField(outYValue)));
		}
		
		
	}
	
	// compute multiplication of(-j/[i-j]) for each j!=i
	private int computeZi(int i){
		int counter = 1;
		for (int j = 1; j <= Party.n; j++) {
			if(j!=i){
				counter = counter * (-j/ (i-j));
			}
		}
		return Gate.modField(counter);
	}

}
