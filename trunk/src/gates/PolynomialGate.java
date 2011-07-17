package gates;

import java.util.ArrayList;

import utils.Polynomial;

import crypto.SecretShare;

public class PolynomialGate extends Gate {

	private ArrayList<Integer> coef;

	public PolynomialGate(ArrayList<GateIO> input, ArrayList<Integer> coefs) {
		super(input);
		this.coef = coef;
	}

	// input - secretShares of x (the variable)
	// output - secretShares of the result ( P(x) )
	@Override
	public void compute() {
		
		// get the input(x) into comfortable data structure
		ArrayList<SecretShare> x = new ArrayList<SecretShare>();
		for (GateIO in: this.input) {
			x.add(in.getIndex(), in.getValue().get(0));			
		}
		
		ArrayList<SecretShare> output = Polynomial.createShareSecret(coef.get(0));		
		//compute the polynomial[x]
		for (int i = 1; i < coef.size(); i++) {		
			//x^i shares
			ArrayList<GateIO> xInI = getxInIPower(i, x);
			ArrayList<SecretShare> xInIShares = new ArrayList<SecretShare>();
			for (GateIO gateIO : xInI) {
				xInIShares.add(gateIO.getIndex(), gateIO.value.get(0));
			}
			
			// coef[i] shares
			ArrayList<SecretShare> coefIShares = Polynomial.createShareSecret(coef.get(i));
			
			// create multInput
			ArrayList<GateIO> multInput = new ArrayList<GateIO>();
			for (int j = 0; j < xInIShares.size(); j++) {
				ArrayList<SecretShare> multPartShares = new ArrayList<SecretShare>();
				multPartShares.add(0, xInIShares.get(j));
				multPartShares.add(1, coefIShares.get(j));
				GateIO multPart = new GateIO(j, multPartShares);
				multInput.add(j, multPart);
			}
			Gate mult = new MultiplicationGate(multInput);
			
			// coefs[i] * x^i
			mult.compute();

			// add to total sum
			ArrayList<GateIO> addInput = new ArrayList<GateIO>();
			for (int j = 0; j < mult.result.size(); j++) {
				ArrayList<SecretShare> addShares = new ArrayList<SecretShare>();
				// the current
				addShares.add(mult.getIOByIndex(j).value.get(0));
				//  the sum so far
				addShares.add(output.get(j));
				GateIO gateParty = new GateIO(j, addShares);
				
				addInput.add(j, gateParty);
				Gate add = new AdditionGate(addInput);
				add.compute();
				//update output
				for (GateIO gateIo : add.result) {
					output.set(gateIo.getIndex(), gateIo.value.get(0));
				}
			}
			
			// update result
			result = new ArrayList<GateIO>();
			for (int j = 0; j < output.size(); j++) {
				ArrayList<SecretShare> resJ = new ArrayList<SecretShare>();
				resJ.add(output.get(j));
				GateIO g = new GateIO(j, resJ);
				result.add(j, g);
			}
		}
	
	}
	
	// get x^i for i >=1
	private ArrayList<GateIO> getxInIPower(int i, ArrayList<SecretShare> x){
		if ( i <= 1){
			ArrayList<GateIO> result = new ArrayList<GateIO>();
			for (int j = 0; j < x.size(); j++) {
				SecretShare sj = x.get(j);
				ArrayList<SecretShare> sjList = new ArrayList<SecretShare>();
				sjList.add(sj);
				result.add(j, new GateIO(j, sjList));				
			}
			return result;
		}		
		//x^(i-1)
		ArrayList<GateIO> xInIMinusOne = getxInIPower(i-1, x);
		// x^1
		ArrayList<GateIO> xInOne = getxInIPower(1, x);
		
		//what each client has (his share of x^1 and his share of x^(i-1))
		ArrayList<GateIO> clientParts = new ArrayList<GateIO>();
		for (int j = 0; j < xInIMinusOne.size(); j++) {
			ArrayList<SecretShare> clientShares = new ArrayList<SecretShare>();
			clientShares.add(0, xInIMinusOne.get(j).value.get(0));
			clientShares.add(1, xInOne.get(j).value.get(0));
			GateIO clientPart = new GateIO(j, clientShares);
			clientParts.add(j, clientPart);
		}	
		Gate mult = new MultiplicationGate(clientParts);
		mult.compute();
		return mult.result;
	}

}
