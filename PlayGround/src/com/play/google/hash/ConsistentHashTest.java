package com.play.google.hash;

import java.util.ArrayList;
import java.util.List;

import com.google.common.hash.Hashing;

public class ConsistentHashTest {

	
	
	public static void main(String[] args) {
		

		List<String> checkers = new ArrayList<String>();

		checkers.add("D50");
		checkers.add("promtion");
		checkers.add("test");
		
		List<String> ips = new ArrayList<String>();
		ips.add("10.4.4.97");
		ips.add("10.4.6.146");
		ips.add("10.4.6.142");
		
		
		for(String ip : ips) {
			System.out.println(ip + " : " + Hashing.consistentHash(ip.hashCode(), 3));
		}
		
		for(String checker : checkers) {
			System.out.println(checker + " : " +Hashing.consistentHash(checker.hashCode(), 3));
		}
	}
}
