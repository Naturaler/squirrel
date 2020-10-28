package com.yrx.squirrel.nut.rpc.server.zp;

import org.apache.zookeeper.*;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * Created by r.x on 2020/10/26.
 */
public class ZkServer {

    public static void main(String[] args) throws KeeperException, InterruptedException, IOException {
        ZkServer client = new ZkServer();
        ZooKeeper zk = client.connect();

        String s = zk.create("/client", "java".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        System.out.println("s = " + s);
    }

    public ZooKeeper connect() throws InterruptedException, IOException {
        CountDownLatch connectedSemaphore = new CountDownLatch(1);
        //创建zookeeper连接
        String CONNECT_ADDR = "127.0.0.1:2181";
        int SESSION_OUTTIME = 3000;
        ZooKeeper zk = new ZooKeeper(CONNECT_ADDR, SESSION_OUTTIME, new Watcher() {
            @Override
            public void process(WatchedEvent event) {
                //连接状态
                Event.KeeperState keeperState = event.getState();

                //事件类型
                Event.EventType eventType = event.getType();

                //如果是建立连接
                if (Event.KeeperState.SyncConnected == keeperState) {

                    if (Event.EventType.None == eventType) {

                        //如果建立连接成功，则发送信号量，让后续阻塞程序向下执行
                        connectedSemaphore.countDown();
                        System.out.println("zk 建立连接");

                    }
                }
            }
        });
        //进行阻塞
        connectedSemaphore.await();
        return zk;
    }
}
