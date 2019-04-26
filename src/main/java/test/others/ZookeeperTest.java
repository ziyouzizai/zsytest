package test.others;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooKeeper;

/**
 * 类说明
 *
 * @author zhangsy
 * @date 2018年7月10日
 */
public class ZookeeperTest {
	private static String connectString = "10.3.20.80:2181,10.3.20.81:2181,10.3.20.82:2181";
	
	public static void main(String[] args) {
		test1();
	}

	public static void test1() {
		String zpath = "/";
		List<String> zooChildren = new ArrayList<String>();
		ZooKeeper zk = null;
		try {
			zk = new ZooKeeper(connectString, 2000, null);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		if (zk != null) {
			try {
				zooChildren = zk.getChildren(zpath, false);
				System.out.println("Znodes of '/': ");
				for (String child : zooChildren) {
					// print the children
					System.out.println(child);
				}
			} catch (KeeperException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
