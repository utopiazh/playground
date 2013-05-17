package com.play.zookeeper;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.apache.zookeeper.AsyncCallback;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Stat;

/**
 * ZooKeeper Wrapper which handles connectionLossException in the background, 
 * while provides sync interfaces.
 * 
 * Limitations:
 * - watchers MUST NOT call any zookeeper data functions during process(), 
 *   otherwise it'll block the async callback.
 * 
 * @author zhouhang
 *
 */
public class ReliableZooKeeper extends ZooKeeper {

	private static Logger log = Logger.getLogger(ReliableZooKeeper.class);

	int retryLimit = 3;
	long waitInterval = 1000;

	public ReliableZooKeeper(String connectString, int sessionTimeout,
			Watcher watcher, int retryLimit) throws IOException {
		super(connectString, sessionTimeout, watcher);
		this.retryLimit = retryLimit;
	}
	
	@Override
	public Stat exists(final String path, final Watcher watcher) {
		CountDownLatch latch = new CountDownLatch(1);
		final Object ctx = null;
		RetryStatCallback cb = new RetryStatCallback(latch, ctx) {
			@Override
			protected void retry() {
				ReliableZooKeeper.super.exists(path, watcher, this, ctx);
			}
		};
		try {
			super.exists(path, watcher, cb, ctx);
			latch.await(10, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			log.error(e);
		}
		return cb.stat;
	}

	@Override
	public List<String> getChildren(final String path, final Watcher watcher) {
		CountDownLatch latch = new CountDownLatch(1);
		final Object ctx = null;
		RetryChildrenCallback cb = new RetryChildrenCallback(latch, ctx) {
			@Override
			protected void retry() {
				ReliableZooKeeper.super.getChildren(path, watcher, this, ctx);
			}
		};

		try {
			super.getChildren(path, watcher, cb, ctx);
			latch.await(10, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			log.error(e);
		}
		return cb.children;
	}

	// This function could not change to async.
	// @Override
	// public List<String> getChildren(final String path, final Watcher watcher,
	// final Stat stat)
	// throws KeeperException, InterruptedException {
	// CountDownLatch latch = new CountDownLatch(1);
	// final Object ctx = null;
	// RetryChildrenCallback cb = new RetryChildrenCallback(latch, ctx) {
	// @Override
	// protected void retry() {
	// ReliableZooKeeper.super.getChildren(path, watcher, this, ctx);
	// }
	// };
	//
	// try {
	// super.getChildren(path, watcher, cb, ctx);
	// latch.await(10, TimeUnit.SECONDS);
	// } catch (InterruptedException e) {
	// log.error(e);
	// }
	// return cb.children;
	// }

	@Override
	public String create(final String path, final byte[] data,
			final List<ACL> acl, final CreateMode createMode)
			throws KeeperException, InterruptedException {
		CountDownLatch latch = new CountDownLatch(1);
		final Object ctx = null;
		RetryStringCallback cb = new RetryStringCallback(latch, ctx) {
			@Override
			protected void retry() {
				ReliableZooKeeper.super.create(path, data, acl, createMode,
						this, ctx);
			}
		};

		try {
			super.create(path, data, acl, createMode, cb, ctx);
			latch.await(10, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			log.error(e);
		}
		return cb.name;
	}

	@Override
	public void delete(final String path, final int version)
			throws InterruptedException, KeeperException {
		CountDownLatch latch = new CountDownLatch(1);
		final Object ctx = null;
		RetryVoidCallback cb = new RetryVoidCallback(latch, ctx) {
			@Override
			protected void retry() {
				ReliableZooKeeper.super.delete(path, version, this, ctx);
			}
		};

		try {
			super.delete(path, version, cb, ctx);
			latch.await(10, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			log.error(e);
		}
		return;
	}

	@Override
	public byte[] getData(final String path, final Watcher watcher,
			final Stat stat) throws KeeperException, InterruptedException {
		CountDownLatch latch = new CountDownLatch(1);
		final Object ctx = null;
		RetryDataCallback cb = new RetryDataCallback(latch, ctx) {
			@Override
			protected void retry() {
				ReliableZooKeeper.super.getData(path, watcher, this, ctx);
			}
		};

		try {
			super.getData(path, watcher, cb, ctx);
			latch.await(10, TimeUnit.SECONDS);

		} catch (InterruptedException e) {
			log.error(e);
		}
		return cb.data;
	}

	@Override
	public Stat setData(final String path, final byte[] data, final int version)
			throws KeeperException, InterruptedException {
		CountDownLatch latch = new CountDownLatch(1);
		final Object ctx = null;
		RetryStatCallback cb = new RetryStatCallback(latch, ctx) {
			@Override
			protected void retry() {
				ReliableZooKeeper.super.setData(path, data, version, this, ctx);
			}
		};

		try {
			super.setData(path, data, version, cb, ctx);
			latch.await(10, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			log.error(e);
		}
		return cb.stat;
	}

	@Override
	public List<ACL> getACL(final String path, final Stat stat)
			throws KeeperException, InterruptedException {
		CountDownLatch latch = new CountDownLatch(1);
		final Object ctx = null;
		RetryACLCallback cb = new RetryACLCallback(latch, ctx) {
			@Override
			protected void retry() {
				ReliableZooKeeper.super.getACL(path, stat, this, ctx);
			}
		};
		try {
			super.getACL(path, stat, cb, ctx);
			latch.await(10, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			log.error(e);
		}
		return cb.acl;
	}

	@Override
	public Stat setACL(final String path, final List<ACL> acl, final int version)
			throws KeeperException, InterruptedException {
		CountDownLatch latch = new CountDownLatch(1);
		final Object ctx = null;
		RetryStatCallback cb = new RetryStatCallback(latch, ctx) {
			@Override
			protected void retry() {
				ReliableZooKeeper.super.setACL(path, acl, version, this, ctx);
			}
		};

		try {
			super.setACL(path, acl, version, cb, ctx);
			latch.await(10, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			log.error(e);
		}
		return cb.stat;
	}

	// ///////////////////////////////////////////////////////
	// ZooKeeper callback overrides
	// ///////////////////////////////////////////////////////
	/*
	 * Retrying callback for zk
	 */
	private abstract class ZkRetryCallback {
		private CountDownLatch latch;
		private Object ctx;
		private int retryCount;
		private long interval;

		public ZkRetryCallback(final CountDownLatch latch, final Object ctx) {
			this.latch = latch;
			this.ctx = ctx;
			this.retryCount = ReliableZooKeeper.this.retryLimit;
			this.interval = waitInterval;
		}

		public void finish() {
			this.latch.countDown();
		}

		public void retryLimitedTimes(String path) {
			if (retryCount-- > 0) {
				interval = interval * 2;
				try {
					Thread.sleep(interval);
				} catch (InterruptedException e) {
					log.error(e);
				}
				retry();
			} else {
				log.error("Operation on " + path
						+ " failed after retrying for " + retryLimit + " times");
				finish();
			}
		}

		public void processResult(String path, int rc) {
			KeeperException.Code result = KeeperException.Code.get(rc);
			if (result != KeeperException.Code.CONNECTIONLOSS) {
				finish();
			} else {
				retryLimitedTimes(path);
			}
		}

		protected abstract void retry();
	}

	private abstract class RetryStatCallback extends ZkRetryCallback implements
			AsyncCallback.StatCallback {
		private Stat stat;

		public RetryStatCallback(final CountDownLatch latch, final Object ctx) {
			super(latch, ctx);
			this.stat = null;
		}

		@Override
		public void processResult(int rc, String path, Object ctx, Stat stat) {
			this.stat = stat;
			processResult(path, rc);
		}
	}

	private abstract class RetryDataCallback extends ZkRetryCallback implements
			AsyncCallback.DataCallback {
		private byte data[];

		public RetryDataCallback(final CountDownLatch latch, final Object ctx) {
			super(latch, ctx);
			this.data = null;
		}

		@Override
		public void processResult(int rc, String path, Object ctx, byte data[],
				Stat stat) {
			this.data = data;
			processResult(path, rc);
		}
	}

	private abstract class RetryACLCallback extends ZkRetryCallback implements
			AsyncCallback.ACLCallback {
		private Stat stat;
		private List<ACL> acl;

		public RetryACLCallback(final CountDownLatch latch, final Object ctx) {
			super(latch, ctx);
			this.stat = null;
			this.acl = null;
		}

		@Override
		public void processResult(int rc, String path, Object ctx,
				List<ACL> acl, Stat stat) {
			this.stat = stat;
			this.acl = acl;
			processResult(path, rc);
		}
	}

	private abstract class RetryChildrenCallback extends ZkRetryCallback
			implements AsyncCallback.ChildrenCallback {
		private List<String> children;

		public RetryChildrenCallback(final CountDownLatch latch,
				final Object ctx) {
			super(latch, ctx);
			this.children = null;
		}

		@Override
		public void processResult(int rc, String path, Object ctx,
				List<String> children) {
			this.children = children;
			processResult(path, rc);
		}
	}

	private abstract class RetryChildren2Callback extends ZkRetryCallback
			implements AsyncCallback.Children2Callback {
		private Stat stat;
		private List<String> children;

		public RetryChildren2Callback(final CountDownLatch latch,
				final Object ctx) {
			super(latch, ctx);
			this.stat = null;
			this.children = null;
		}

		@Override
		public void processResult(int rc, String path, Object ctx,
				List<String> children, Stat stat) {
			this.stat = stat;
			this.children = children;
			processResult(path, rc);
		}
	}

	private abstract class RetryStringCallback extends ZkRetryCallback
			implements AsyncCallback.StringCallback {
		private String name;

		public RetryStringCallback(final CountDownLatch latch, final Object ctx) {
			super(latch, ctx);
			this.name = null;
		}

		@Override
		public void processResult(int rc, String path, Object ctx, String name) {
			this.name = name;
			processResult(path, rc);
		}
	}

	private abstract class RetryVoidCallback extends ZkRetryCallback implements
			AsyncCallback.VoidCallback {

		public RetryVoidCallback(final CountDownLatch latch, final Object ctx) {
			super(latch, ctx);
		}

		@Override
		public void processResult(int rc, String path, Object ctx) {
			processResult(path, rc);
		}
	}
}
