package com.play.convert;

import java.util.HashMap;
import java.util.Map;

public class NullConvertTest {

	public static void main(String[] args) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		try{
		
			Long x = (Long)map.get("Long");
			System.out.println("Long" + (x == 0));

			
			Boolean y = (Boolean)map.get("Boolean" );
			System.out.println("Boolean" + (y && true));
			
			
			Map<String, String> m = (Map<String, String>)map.get("Map");
			System.out.println("Map" + m);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
