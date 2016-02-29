package com.play.java.string;

public class SubString {
	public static void main(String[] args) {
		String type = "ENTITY_TYPE-1";
		String typeStr = type;
		if(type.startsWith("ENTITY_TYPE")) {
			typeStr = type.substring(type.indexOf('-') + 1);
		};
		
		System.out.println(Integer.parseInt(typeStr));

	}
}
