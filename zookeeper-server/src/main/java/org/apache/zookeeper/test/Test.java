package org.apache.zookeeper.test;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.apache.zookeeper.AddWatchMode.PERSISTENT;

public class Test {

    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
        ZooKeeper zooKeeper = new ZooKeeper("127.0.0.1:2181", 200000, new Watcher() {
            @Override
            public void process(WatchedEvent event) {
                System.out.println("默认的:"+event);
            }
        });
        List<String> children = zooKeeper.getChildren("/dubbo", false);
        //System.out.println(children);

        byte[] data = zooKeeper.getData("/dubbo/com.hhd.merchant.facade.service.HhdLendRecordFacade", false, new Stat());
        System.out.println(new String(data));
       zooKeeper.addWatch("/dubbo/lkp/b", new Watcher() {
           @Override
           public void process(WatchedEvent event) {
               System.out.println(event);
           }
       }, PERSISTENT);
        ArrayList<ACL> objects = new ArrayList<>();
        objects.add(new ACL());

        String s = zooKeeper.create(
                "/dubbo/lkp/b", "1".getBytes(),
                ZooDefs.Ids.OPEN_ACL_UNSAFE,
                CreateMode.PERSISTENT);
        System.out.println(s);

        String s1 = zooKeeper.create(
                "/dubbo/lkp/c", "1".getBytes(),
                ZooDefs.Ids.OPEN_ACL_UNSAFE,
                CreateMode.PERSISTENT);
        System.out.println(s1);

        TimeUnit.DAYS.sleep(1);
    }
}
