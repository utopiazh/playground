package com.play.ifstmt;

public class Condition {

	public static void condExec() {
		//(1 == 0) ? System.out.println("True") : System.out.println("True"); 
	}
	
	public static void condVar() {
		System.out.println(1 ==  0 ? "True" : "False");
	}
	
	public static void main(String[] args) {
		condExec();
		condVar();
	}
	
	
}
