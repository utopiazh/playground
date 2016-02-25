package com.play.math;

public class Million {
	public static void main(String[] args) {
		int docs = 10000000;
		int dims = 10;
		double[][] factors= new double[docs][dims];
		double[] weights = new double[dims];
		for(int i = 0; i < dims; ++i) {
			weights[i] = Math.random();
			
		}
		for(int j = 0; j < docs; ++j) {
			factors[j] = new double[dims];
			for(int i = 0; i < dims; ++i) {
				factors[j][i] = Math.random();
			}
		}
		
		double[] scores = new double[docs];
		
		try {
			Thread.sleep(10000l);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		long start = System.currentTimeMillis();
		for(int j = 0; j < docs; ++j) {
			for(int i = 0; i < dims; ++i) {
				scores[j] += weights[i]*factors[j][i];
			}
		}
		long end = System.currentTimeMillis();
		System.out.println("Elaspsed time: " + (end - start));
		
		
	}
}
