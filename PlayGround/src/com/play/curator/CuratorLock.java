package com.play.curator;

import java.util.concurrent.TimeUnit;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessLock;
import org.apache.curator.framework.recipes.locks.InterProcessSemaphoreMutex;
import org.apache.curator.retry.RetryNTimes;

public class CuratorLock {
	public static void main(String[] args) {
		  // This is the ZooKeeper path we're watching
	    final String lockPath = "/mylock";

	    // Initialize the curator framework
	    final CuratorFramework cf = CuratorFrameworkFactory.builder()
	        .namespace("curatordemo")
	        .connectString("192.168.130.107:2181,192.168.130.108:2181,192.168.130.109:2181,10.161.144.126:2181,10.161.144.127:2181")
	        .retryPolicy(new RetryNTimes(5, 1000))
	         .connectionTimeoutMs(30000)
	         .build();
	    
	    cf.start();
	    
	    InterProcessLock lock = new InterProcessSemaphoreMutex(cf, lockPath);
	    
	    try {
			lock.acquire(5, TimeUnit.MINUTES);
			System.out.println("Acquired lock");
			lock.release();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	    cf.close();
	}
}
