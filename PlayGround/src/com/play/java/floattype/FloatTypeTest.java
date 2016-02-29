package com.play.java.floattype;

public class FloatTypeTest {

	// after the test, you'll know the precision of float is very low
	public static void main(String[] args) {
		float x = 1.11111111f;
		double y = 1.1111111d;
		for(int i = 1; i < 10000; i *=2) {
			System.out.println(x * i);
			System.out.println(y * i);
		}
		
	}
}
