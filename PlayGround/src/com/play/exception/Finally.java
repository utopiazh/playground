package com.play.exception;

public class Finally {

	public static void main(String[] args) {
		int i = 0;

		try {
			try {
				i = 1;
				throw new Exception("1");
			} catch (Exception e) {
				i = 2;
				throw new Exception("2");
			} finally {
				i = 3;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println(i);

	}

}
