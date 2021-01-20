package com.yrx.squirrel.nut.rpc.client.zk;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * Created by r.x on 2020/10/26.
 */
public class ZkClient {

    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
        ZooKeeper zk = connect();

        //获取节点信息
        // byte[] data = zk.getData("/rpc", false, null);
        // System.out.println("new String(data) = " + new String(data));

        //遍历节点下的所有数据
        List<String> nodeChildName = zk.getChildren("/rpc", false);
        for (String child : nodeChildName) {
            System.out.println("child = " + child);

            byte[] data = zk.getData("/rpc/" + child, false, null);
            String node = new String(data);
            System.out.println("node = " + node);

            List<String> children = zk.getChildren("/rpc/" + child, false);
            for (String interfaces : children) {
                System.out.println("interfaces = " + interfaces);
            }
        }

    }

    public static ZooKeeper connect() throws InterruptedException, IOException {
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
