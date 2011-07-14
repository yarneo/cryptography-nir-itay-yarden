package party;

import java.util.ArrayList;
import utils.Polynomial;
import crypto.SecretShare;
import gates.AdditionGate;
import gates.ConsMult;
import gates.Gate;
import gates.GateIO;

public class TestMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		System.out.println("field = " + Party.field);
		
		/*
		// check addition gate
		SecretShare s1 = new SecretShare(3, 4, null);
		SecretShare s2 = new SecretShare(2, 3, null);
		ArrayList<GateIO> input = new ArrayList<GateIO>();
		input.add(new GateIO(1, s1));
		input.add(new GateIO(2, s2));
		Gate add = new AdditionGate(input);
		add.compute(); // should be - result of addition : x = 5, y = 0

		// check multiplication with constant gate
		ArrayList<GateIO> input2 = new ArrayList<GateIO>();
		input2.add(new GateIO(1, s1));
		Gate con = new ConsMult(input2, -2);
		con.compute(); // should be - result of const multiplication : x = 1, y = 6
		
		ArrayList<GateIO> input3 = new ArrayList<GateIO>();
		input3.add(new GateIO(1, add.getResult()));
		Gate conAfterAdd = new ConsMult(input3, 2);
		conAfterAdd.compute(); // should be - result of const multiplication : x = 3, y = 0
		*/
		
		//test polynomial creation out of secret
		//Polynomial p = Polynomial.create(5);
		//p.printPolynomial();
		
		
	}

}
