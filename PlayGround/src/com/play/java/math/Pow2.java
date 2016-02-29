package com.play.java.math;

public class Pow2 {

	public static void main(String[] args) {
		int n = 121;
		
		if((n & (n-1)) == 0) {
			System.out.println("is pow2: " + n);
		} else {
			while ((n & (n-1)) != 0) {
				System.out.println("isnot pow2 yet: " + n + ", bin: " + Integer.toBinaryString(n));
				n = n & (n-1);
			}
			n = n << 1;
			System.out.println("roundup pow2: " + n);
		}
	}
}
