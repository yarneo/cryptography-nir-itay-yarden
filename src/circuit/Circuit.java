package circuit;

import gates.AdditionGate;
import gates.ConstMult;
import gates.GateIO;
import gates.MultiplicationGate;

import java.util.ArrayList;
import crypto.SecretShare;

public class Circuit {

	public static int gateIndex = 0;
	public ArrayList<Sextuple> gates;


	public Circuit() {
		gates = new ArrayList<Sextuple>();
	}

	public void setNext(int me, int next) {
		for(Sextuple gate : gates) {
			if(gate.index == me) {
				gate.next = next; 
			}
		}
	}
	
	public ArrayList<SecretShare> getOutput(int gateIndex) {
		for(Sextuple gate : gates) {
			if(gate.index == gateIndex) {
				return gate.output;
			}
		}
		return null;
	}
	
	public void addSecretShare(int index,ArrayList<SecretShare> share) {
		for(Sextuple gate : gates) {
			if(gate.index == index) {
				gate.secretShares.add(share);
			}
		}
	}
	
	public int addMulGate(int indexfrom,ArrayList<SecretShare> share) {
		ArrayList<ArrayList<SecretShare>> al = new ArrayList<ArrayList<SecretShare>>();
		if(share != null)
		al.add(share);
		gateIndex++;
		for(Sextuple Sextuple : gates) {
			if(Sextuple.index == indexfrom) {
				Sextuple.next = gateIndex;
			}
		}

		gates.add(new Sextuple(al,gateIndex,-1,1));
		return gateIndex;
	}

	public int addMulGate(ArrayList<SecretShare> share) {
		ArrayList<ArrayList<SecretShare>> al = new ArrayList<ArrayList<SecretShare>>();
		if(share != null)
		al.add(share);

		gates.add(new Sextuple(al,++gateIndex,-1,1));
		return gateIndex;
	}

	public int addAdditionGate(int indexfrom,ArrayList<SecretShare> share) {
		ArrayList<ArrayList<SecretShare>> al = new ArrayList<ArrayList<SecretShare>>();
		if(share != null)
		al.add(share);
		gateIndex++;
		for(Sextuple Sextuple : gates) {
			if(Sextuple.index == indexfrom) {
				Sextuple.next = gateIndex;
			}
		}

		gates.add(new Sextuple(al,gateIndex,-1,0));
		return gateIndex;
	}

	public int addAdditionGate(ArrayList<SecretShare> share) {
		ArrayList<ArrayList<SecretShare>> al = new ArrayList<ArrayList<SecretShare>>();
		if(share != null)
		al.add(share);

		gates.add(new Sextuple(al,++gateIndex,-1,0));
		return gateIndex;
	}

	public int addConstGate(int indexfrom,ArrayList<SecretShare> share,int constant) {
		ArrayList<ArrayList<SecretShare>> al = new ArrayList<ArrayList<SecretShare>>();
		if(share != null)
		al.add(share);
		gateIndex++;
		for(Sextuple Sextuple : gates) {
			if(Sextuple.index == indexfrom) {
				Sextuple.next = gateIndex;
			}
		}

		gates.add(new Sextuple(al,gateIndex,-1,2));
		return gateIndex;
	}

	public int addConstGate(ArrayList<SecretShare> share,int constant) {
		ArrayList<ArrayList<SecretShare>> al = new ArrayList<ArrayList<SecretShare>>();
		if(share != null)
		al.add(share);

		gates.add(new Sextuple(al,++gateIndex,-1,2,constant));
		return gateIndex;
	}

	public void computeCircuit() {
		for(Sextuple gate : gates) {
			switch(gate.type) {
			case 0: //addition
				ArrayList<GateIO> input = new ArrayList<GateIO>();
				for(int i=0;i<gate.secretShares.get(0).size();i++) {
					ArrayList<SecretShare> tmpparty = new ArrayList<SecretShare>();
					tmpparty.add(0,gate.secretShares.get(0).get(i));
					tmpparty.add(1,gate.secretShares.get(1).get(i));
					//	System.out.println("input secret shares for party" + i + " is:" + ss1.get(i) + " " + ss2.get(i));
					GateIO tmpgate = new GateIO(i,tmpparty);
					input.add(tmpgate);
				}
				gate.gate = new AdditionGate(input);
				gate.gate.compute();
				ArrayList<SecretShare> outcome = new ArrayList<SecretShare>();
				for(GateIO gio : gate.gate.getResult()) {
					//System.out.println(gio.getValue().size());
					outcome.add(gio.getValue().get(0));
				}
				gate.output = outcome;
				for(Sextuple gate2 : gates) {
					if(gate2.index == gate.next) {
						gate2.secretShares.add(outcome);
					}
				}			
				break;
			case 1: //multiplication
				ArrayList<GateIO> input2 = new ArrayList<GateIO>();
				for(int i=0;i<gate.secretShares.get(0).size();i++) {
					ArrayList<SecretShare> tmpparty2 = new ArrayList<SecretShare>();
					tmpparty2.add(0,gate.secretShares.get(0).get(i));
					tmpparty2.add(1,gate.secretShares.get(1).get(i));
					//	System.out.println("input secret shares for party" + i + " is:" + ss1.get(i) + " " + ss2.get(i));
					GateIO tmpgate2 = new GateIO(i,tmpparty2);
					input2.add(tmpgate2);
				}
				gate.gate = new MultiplicationGate(input2);
				gate.gate.compute();
				ArrayList<SecretShare> outcome2 = new ArrayList<SecretShare>();
				for(GateIO gio : gate.gate.getResult()) {
					//System.out.println(gio.getValue().size());
					outcome2.add(gio.getValue().get(0));
				}
				gate.output = outcome2;
				for(Sextuple gate2 : gates) {
					if(gate2.index == gate.next) {
						gate2.secretShares.add(outcome2);
					}
				}								
				break;
			case 2: //const
				ArrayList<GateIO> input3 = new ArrayList<GateIO>();
				for(int i=0;i<gate.secretShares.get(0).size();i++) {
					ArrayList<SecretShare> tmpparty3 = new ArrayList<SecretShare>();
					tmpparty3.add(0,gate.secretShares.get(0).get(i));
					//	System.out.println("input secret shares for party" + i + " is:" + ss1.get(i) + " " + ss2.get(i));
					GateIO tmpgate3 = new GateIO(i,tmpparty3);
					input3.add(tmpgate3);
				}
				gate.gate = new ConstMult(input3,gate.constant);
				gate.gate.compute();
				ArrayList<SecretShare> outcome3 = new ArrayList<SecretShare>();
				for(GateIO gio : gate.gate.getResult()) {
					//System.out.println(gio.getValue().size());
					outcome3.add(gio.getValue().get(0));
				}
				gate.output = outcome3;
				for(Sextuple gate3 : gates) {
					if(gate3.index == gate.next) {
						gate3.secretShares.add(outcome3);
					}
				}								
				break;
			default:
				break;
			}
		}
	}

}
