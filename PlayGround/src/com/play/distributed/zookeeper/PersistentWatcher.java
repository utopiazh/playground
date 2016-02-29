package com.play.distributed.zookeeper;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;

public class PersistentWatcher implements Watcher {
	
	private CountDownLatch latch;
	
	private static final String dir = "/testPersistentWatcher";
	private ZooKeeper zk;
	private AtomicLong sn = new AtomicLong();
	public PersistentWatcher(){
		try {
			latch = new CountDownLatch(1);
			zk =  new ZooKeeper("localhost:2181", 5000, this);
			latch.await();
			System.out.println("Connected");
			if(null == zk.exists(dir, true)){
				zk.create(dir, new byte[0], Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (KeeperException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public void create(String path) {
		try {
			latch.await();
			// use exist to add watch first to receive NodeCreated event
			if(null == zk.exists(path, this)) {
				zk.create(dir, new byte[0], Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (KeeperException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}


	@Override
	public void process(WatchedEvent event) {
		
		if(event.getState().equals(KeeperState.SyncConnected)) {
			latch.countDown();
		}
		
		try {
			if(null != zk.exists(dir, this)) {
				List<String> children = zk.getChildren(dir, this);
				LinkedList<String> dfsQueue = new LinkedList<String> ();
				if(children != null) {
					for(String child : children) {
						dfsQueue.add(dir + "/" + child);
					}
				}
				while(dfsQueue != null && !dfsQueue.isEmpty()) {
					String child = dfsQueue.pop();					
					System.out.println(child);
					if(null !=  zk.exists(child, this)) {
						List<String> grands = zk.getChildren(child, this);
						if(grands != null) {
							for(String grand : grands) {
								dfsQueue.add(child + "/" + grand);
							}
						}
					}
					
				}
			}
		} catch (KeeperException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(sn.incrementAndGet() + ":" + event);
		System.out.println("================================");
	}
	
	public static void main(String[] args) {
		
		PersistentWatcher pw = new PersistentWatcher();
		try {
			Thread.sleep(1000 * 1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
