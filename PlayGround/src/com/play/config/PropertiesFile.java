package com.play.config;

import java.io.IOException;
import java.util.Properties;

public class PropertiesFile {
	
	public static void main(String[] args) {
		Properties prop = new Properties();
		String file1 = "test.properties";
		String file2 = "test2.properties";
		try {
			prop.load(PropertiesFile.class.getClassLoader().getResourceAsStream(file1));
			System.out.println(prop.toString());
			prop.load(PropertiesFile.class.getClassLoader().getResourceAsStream(file2));
			System.out.println(prop.toString());

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
