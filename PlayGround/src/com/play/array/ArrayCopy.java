package com.play.array;

public class ArrayCopy {
	
	private static void printArray(int[] arr) {
		
		StringBuilder sb = new StringBuilder();
		if(arr != null) {
			for(int i = 0; i < arr.length; ++ i) {
				sb.append(arr[i] + ", ");
			}
		}
		System.out.println(sb.toString());
	}

	public static void main(String[] args) {
		int[] arr = new int[] {1, 2, 3};
		
		int[] copy = arr;
		
		printArray(copy);
		
		arr[1] = 12;
		
		printArray(copy);
		
		arr = null;
		
		printArray(copy);
	}
}
