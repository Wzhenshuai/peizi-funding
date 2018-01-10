package com.icaopan.framework.sync;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * <P></P>
 * User: <a href="mailto:xuhm@gudaiquan.com">许昊旻</a>
 * Date: 2017/6/16
 * Time: 上午10:43
 */
public abstract class SyncLock {

    private final Map<String, ReentrantLock> lockMap = new HashMap<String, ReentrantLock>();

    public void lock(String id) {
        getLogger().debug("开始【{SyncLock.lock}】" + id);
        ReentrantLock lock = null;
        lock = getLock(id);
        try {
            lock.lockInterruptibly();
        } catch (InterruptedException e) {
            getLogger().error("同步锁被中断", e);
        }
        getLogger().debug("结束【{SyncLock.lock}】");
    }

    public void lockAll(List<String> ids) {
        getLogger().debug("开始【{SyncLock.lockAll}】" + StringUtils.join(ids, ","));
        try {
            doLockAll(ids, 5);
        } catch (InterruptedException e) {
            getLogger().error("同步锁被中断", e);
        }
        getLogger().debug("结束【{SyncLock.lockAll}】");
    }

    private void doLockAll(List<String> ids, int tryTimes) throws InterruptedException {
        List<ReentrantLock> locks = getLocks(ids);
        for (ReentrantLock lock : locks) {
            boolean isLock = lock.tryLock() || lock.tryLock(150, TimeUnit.MILLISECONDS);
            if (isLock == false) {
                unlockAll(ids);
                if (tryTimes == 0) {
                    throw new RuntimeException("无法取得所需全部同步锁,请稍后重试");
                } else {
                    Thread.sleep((int) (Math.random() * 300));
                    doLockAll(ids, tryTimes - 1);
                    return;
                }
            }
        }
    }

    private List<ReentrantLock> getLocks(List<String> ids) {
        List<ReentrantLock> locks = new ArrayList<ReentrantLock>(ids.size());
        for (String id : ids) {
            locks.add(getLock(id));
        }
        return locks;
    }

    private ReentrantLock getLock(String id) {
        ReentrantLock lock;
        synchronized (this) {
            lock = lockMap.get(getPrefix() + id);
            if (lock == null) {
                lock = new ReentrantLock(true);
                lockMap.put(getPrefix() + id, lock);
            }
        }
        return lock;
    }

    public void unlock(String id) {
        getLogger().debug("开始【{SyncLock.unlock}】" + id);
        synchronized (this) {
            doUnlock(id);
        }
        getLogger().debug("结束【{SyncLock.unlock}】");
    }

    public void unlockAll(List<String> ids) {
        getLogger().debug("开始【{SyncLock.unlockAll}】" + StringUtils.join(ids, ","));
        synchronized (this) {
            for (String id : ids) {
                doUnlock(id);
            }
        }
        getLogger().debug("结束【{SyncLock.unlockAll}】");
    }

    private void doUnlock(String id) {
        ReentrantLock lock = lockMap.get(getPrefix() + id);
        if (lock == null) {
            return;
        }
        try {
            lock.unlock();
        } catch (IllegalMonitorStateException e) {
            // 忽略释放非本身持有锁的异常
        }
        if (lock.isLocked() == false && lock.hasQueuedThreads() == false) {
            lockMap.remove(id);
        }
    }

    public abstract String getPrefix();

    public abstract Logger getLogger();
}
