package gates;

import java.util.ArrayList;

import party.Party;
import utils.LaGrange;
import utils.Polynomial;
import crypto.SecretShare;

public class MaxGate extends Gate {

	// if a>b return 1
	// else return 0
	public MaxGate(ArrayList<GateIO> input) {
		super(input);
	}

	// the input is secret shares of a and b! (two inputs)
	@Override
	public void compute() {
		// get a and b ordered by GateIO indices
		ArrayList<SecretShare> a = new ArrayList<SecretShare>();
		ArrayList<SecretShare> b = new ArrayList<SecretShare>();
		for (GateIO in : this.input) {
			a.add(in.getIndex(), in.getValue().get(0));
			b.add(in.getIndex(), in.getValue().get(1));
		}
		// print a,b
		System.out.println("a = " + Polynomial.computeSecret(a));
		System.out.println("b = " + Polynomial.computeSecret(b));
		
		// compute -b
		ArrayList<GateIO> minusB = new ArrayList<GateIO>();
		for (int i = 0; i < b.size(); i++) {
			ArrayList<SecretShare> tmpB = new ArrayList<SecretShare>();
			tmpB.add(b.get(i));
			minusB.add(new GateIO(i, tmpB));
		}
		ConstMult constGate = new ConstMult(minusB, -1);
		constGate.compute();

		// print -b
		ArrayList<SecretShare> mb = new ArrayList<SecretShare>();
		for (int i = 0; i < constGate.result.size(); i++) {
			mb.add(constGate.result.get(i).value.get(0));
		}
		System.out.println("-b = " + Polynomial.computeSecret(mb));
		
		// compute a-b
		ArrayList<GateIO> aMinusB = new ArrayList<GateIO>();
		for (int i = 0; i < a.size(); i++) {
			ArrayList<SecretShare> tmpAMinusB = new ArrayList<SecretShare>();
			tmpAMinusB.add(0, a.get(i));
			tmpAMinusB.add(1, constGate.getIOByIndex(i).value.get(0));
			aMinusB.add(new GateIO(i, tmpAMinusB));
		}
		AdditionGate addGate = new AdditionGate(aMinusB);
		addGate.compute();

		// print a-b
		ArrayList<SecretShare> anb = new ArrayList<SecretShare>();
		for (int i = 0; i < addGate.result.size(); i++) {
			anb.add(addGate.result.get(i).value.get(0));
		}
		System.out.println("a-b = " + Polynomial.computeSecret(anb));

		ArrayList<Integer> coefs = getMaxCoefs();
		// print the polynomial
		Polynomial p = new Polynomial(coefs);
		p.printPolynomial();
		
		PolynomialGate polyGate = new PolynomialGate(addGate.result, coefs);
		polyGate.compute();
		this.result = polyGate.result;
	}

	public ArrayList<Integer> getMaxCoefs() {
		// compute the max polynomial
		ArrayList<SecretShare> points = new ArrayList<SecretShare>();
		for (int i = -Party.n; i <= Party.n; i++) {
			if (i <= 0)
				points.add(new SecretShare(i, 0));
			else {
				points.add(new SecretShare(i, 1));
			}
		}
		LaGrange lagrange = new LaGrange();
		int[] lagrangeCoefs = lagrange.getPolyCoefs(points);
		ArrayList<Integer> coefs = new ArrayList<Integer>();
		for (int i = 0; i < lagrangeCoefs.length; i++) {
			coefs.add(i, lagrangeCoefs[i]);
		}
		return coefs;
	}

}
