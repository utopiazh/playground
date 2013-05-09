package com.play.string;

public class StringOps {

	static final String src = "abcdefghigklmnopqrstu12312312412355vwxyz_+(*&*^^%%$%$#(*()))_";
	
	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		for (int i = 0; i < Integer.MAX_VALUE; ++i) {
			src.indexOf('_');
		}
		long end = System.currentTimeMillis();
		System.out.println("Time: " + (end - start));
		
		start = System.currentTimeMillis();
		for (int i = 0; i < Integer.MAX_VALUE; ++i) {
			src.indexOf("_");
		}
		end = System.currentTimeMillis();
		System.out.println("Time: " + (end - start));
	}
	
}
