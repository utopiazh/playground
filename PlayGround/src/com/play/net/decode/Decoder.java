package com.play.net.decode;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class Decoder {

	public static void main(String[] args) {
		String str = "%E9%9B%80%E5%B7%A2%E5%92%96%E5%95%A1";
		try {
			str = URLDecoder.decode(str, "utf-8");
			System.out.println(str);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
	}
}
