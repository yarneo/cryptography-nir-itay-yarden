package utils;

import java.util.ArrayList;

import crypto.SecretShare;
import gates.Gate;

public class LaGrange {

	public LaGrange() {
	}

	// LaGrange computation - Adapter Pattern
	public int computeLagrange(ArrayList<SecretShare> shares, int value) {
		double[] y = new double[shares.size()];
		double[] x = new double[shares.size()];
		boolean allZero = true;
	//	boolean allOne = true;
		
		for (int i = 0; i < shares.size(); i++) {
			x[i] = shares.get(i).x;
			y[i] = shares.get(i).y;
			allZero = allZero && (x[i]== 0.0);
		//	allOne = allOne && (x[i]== 0.0) && (y[i]== 1.0);
		}
		
		// cant interpolate a zero input
		// if the input is zero (can happen in max gate result)
		// then the output is zero
		if(allZero){
		//	return 0;
		}
//		if (allOne){
//			return 1;
//		}
		
		//System.out.println("in lagrange");
//		for (int i = 0; i < x.length; i++) {
//			System.out.println(x[i]);
//		}
//		for (int i = 0; i < y.length; i++) {
//			System.out.println(y[i]);
//		}
//		System.out.println("in lagrange--");
		PolynomialFunctionLagrangeForm p = new PolynomialFunctionLagrangeForm(
				x, y);
		return Gate.modField((int) p.value(value));
	}

	// LaGrange computation - Adapter Pattern
	public int[] getPolyCoefs(ArrayList<SecretShare> shares) {
		double[] y = new double[shares.size()];
		double[] x = new double[shares.size()];
		boolean allZero = true;
		boolean allOne = true;

		for (int i = 0; i < shares.size(); i++) {
			x[i] = shares.get(i).x;
			y[i] = shares.get(i).y;
			allZero = allZero && (x[i]== 0.0);
			//allOne = allOne && (x[i]== 0.0) && (y[i]== 1.0);
		}
		
		// cant interpolate a zero input
		// if the input is zero (can happen in max gate result)
		// then the output is zero
		if(allZero){
			int[] res = new int[x.length];
			for (int i = 0; i < x.length; i++) {
				res[i] = 0;
			}
			System.out.println("all zero!");
			return res;
		}
//		if (allOne){
//			int[] res = new int[x.length];
//			for (int i = 0; i < x.length; i++) {
//				res[i] = 1;
//			}
//			return res;
//		}
		
		PolynomialFunctionLagrangeForm p = new PolynomialFunctionLagrangeForm(
				x, y);
		double[] pRes = p.getCoefficients();
		int[] res = new int[pRes.length];
		for (int i = 0; i < pRes.length; i++) {
			res[i] = Gate.modField((int) pRes[i]);
		}
		return res;
	}

}
