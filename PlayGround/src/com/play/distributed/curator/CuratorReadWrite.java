package com.play.distributed.curator;

import java.util.concurrent.TimeUnit;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryNTimes;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;

public class CuratorReadWrite {

	public static void main(String[] args) {
		  // This is the ZooKeeper path we're watching
	    final String MYWATCH_ZK_PATH = "/mywatch";

	    // Initialize the curator framework
	    final CuratorFramework cf = CuratorFrameworkFactory.builder()
	        .namespace("curatordemo")
	        .connectString("192.168.130.107:2181,192.168.130.108:2181,192.168.130.109:2181,10.161.144.126:2181,10.161.144.127:2181")
	        .retryPolicy(new RetryNTimes(5, 1000))
	         .connectionTimeoutMs(30000)
	         .build();
	    
	    cf.start();
	    
	    
	    final Watcher watcher = new Watcher() {
            public void process(WatchedEvent event) {
                try {
                    if(event.getType() == Event.EventType.NodeDataChanged) {
                        System.out.println(event.getPath());
                        byte[] data = cf.getData()
                                .forPath(MYWATCH_ZK_PATH);
                            System.out.println(new String(data));
                    }
                } catch(Exception e) {
                    System.out.println("An error occurred while sending notifications " +
                        "to change handler");
                } finally {
                    try {
                        cf.getData().usingWatcher(this)
                            .forPath(MYWATCH_ZK_PATH);
                    } catch(Exception e) {
                        System.out.println("Unable to reset watch on path /watchme");
                    }
                }
            }
        };
      
  
        try {
        	if(cf.checkExists().forPath(MYWATCH_ZK_PATH) == null) {
        		cf.create().forPath(MYWATCH_ZK_PATH);	
        	}
     	
  	
        	cf.getData().usingWatcher(watcher).forPath(MYWATCH_ZK_PATH);
            
			cf.setData().forPath(MYWATCH_ZK_PATH, 
			        "Hello World".getBytes());
	        TimeUnit.SECONDS.sleep(3);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}
}
