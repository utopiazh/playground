package com.play.cache;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

public class GuavaCache {
	
	public static void main(String[] args) {
		LoadingCache<Integer, String> cache =CacheBuilder.newBuilder()
				.maximumSize(10)
				.expireAfterAccess(10, TimeUnit.SECONDS)
				.build(new CacheLoader<Integer, String>() {

			@Override
			public String load(Integer key) throws Exception {
				System.out.println("load " + key);
				return Integer.toString(key);
			}
			
		});
		
		System.out.println(cache);
		for(int i = 0; i < 100; ++ i) {
			try {
				System.out.println(cache.get(i % 10));
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		try {
			TimeUnit.SECONDS.sleep(10);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println(cache.stats());
	}
}
