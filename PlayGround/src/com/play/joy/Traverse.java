package com.play.joy;

public class Traverse {

	public static void main(String[] args) {
		int length = 8;
		byte[] data = new byte[length];
		int start = 113 * 31 + 111;
		int inc = (start >> 8 + start) | 1;
		System.out.println("start: " + start + ", inc: " + inc);
		int count = 0;
		while (true) {
			count++;
			int pos = start & (length - 1);
			data[pos] = 1;
			start = start + inc;

			StringBuilder sb = new StringBuilder();
			boolean ok = true;
			for (int i = 0; i < length; ++i) {
				sb.append(data[i]);
				if (data[i] != 1) {
					ok = false;
				}
			}
			System.out.println(count + ". pos: " + pos + ", data:"
					+ sb.toString());
			if (ok) {
				break;
			}
		}
	}

	/**
	 * 其中的原理为：
	 * 1. inc 可以保证生成为奇数，因此inc与length互质。
	 * 2. start 并不重要。
	 * 3. (inc * x) mod length 构成一个[0, length -1] 到[0, length -1]的全映射，所以只需要length次就可以遍历完这个数组。 
	 * 
	 * start: 3614, inc: 57 
	 * 1. pos: 6, data:00000010 
	 * 2. pos: 7, data:00000011 
	 * 3. pos: 0, data:10000011 
	 * 4. pos: 1, data:11000011 
	 * 5. pos: 2, data:11100011
	 * 6. pos: 3, data:11110011 
	 * 7. pos: 4, data:11111011 
	 * 8. pos: 5, data:11111111
	 */
}
