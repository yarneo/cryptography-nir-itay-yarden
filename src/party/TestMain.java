package party;

import java.util.ArrayList;

import utils.Polynomial;

import crypto.SecretShare;
import gates.AdditionGate;
import gates.ConsMult;
import gates.Gate;
import gates.GateInput;

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
		ArrayList<GateInput> input = new ArrayList<GateInput>();
		input.add(new GateInput(1, s1));
		input.add(new GateInput(2, s2));
		Gate add = new AdditionGate(input);
		add.compute(); // should be - result of addition : x = 5, y = 0

		// check multiplication with constant gate
		ArrayList<GateInput> input2 = new ArrayList<GateInput>();
		input2.add(new GateInput(1, s1));
		Gate con = new ConsMult(input2, -2);
		con.compute(); // should be - result of const multiplication : x = 1, y = 6
		
		ArrayList<GateInput> input3 = new ArrayList<GateInput>();
		input3.add(new GateInput(1, add.getResult()));
		Gate conAfterAdd = new ConsMult(input3, 2);
		conAfterAdd.compute(); // should be - result of const multiplication : x = 3, y = 0
		*/
		
		//test polynomial creation out of secret
		Polynomial p = Polynomial.create(5);
		p.printPolynoial();
		
		
		
	}

}
