package com.play.joy;

/**
 * Calculate leap year.
 * 
 * TODO : use bit operation.
 * 
 * @author zhouhang
 *
 */
public class LeapYear {

	public static boolean isLeapYear(int year) {
		if(year % 4 == 0) {
			if(year % 100 == 0) {
				if(year % 400 == 0) {
					return true;
				}
				return false;
			}
			return true;
		}
		return false;
	}
	
	
	public static void main(String[] args) {
		System.out.println(">>> isLeapYear");
		int[] years = {1996, 2001, 2000, 1900};
		for(int year : years) {
			System.out.println(year + " : " + isLeapYear(year));
		}

	}
}
