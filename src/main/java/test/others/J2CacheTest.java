package test.others;

import java.util.Date;

import net.oschina.j2cache.CacheChannel;
import net.oschina.j2cache.J2Cache;

public class J2CacheTest {
	public static void main(String[] args) throws Exception {
	    final CacheChannel cache = J2Cache.getChannel();
	    //缓存操作
//	    cache.set("default", "1", "Hello J2Cache");
//	    System.out.println(cache.get("default", "1").asString());
//	    cache.evict("default", "1");
//	    System.out.println(cache.get("default", "1").asString());
//	    cache.evict("default", "1");
//	    System.out.println(cache.get("default", "1").asString());
	    
	    cache.set("test", "1", "Hello J2Cache");
	    cache.set("default", "1", "Hello J2Cache");
	    
	    for(int i=0;i<1000;i++) {
	    	cache.set("other", i+"", "Hello J2Cache" + i);
//	    	Thread.sleep(1L);
	    }
	    
	    new Thread(new Runnable() {
	    	private int min =0;
			@Override
			public void run() {
				while(true) {
					System.out.println((min++) + "s:");
					System.out.println(cache.keys("other"));
				    System.out.println(cache.keys("other").size());
				    System.out.println();
				    
				    if(cache.keys("other").size() == 0)
				    	return;
				    
				    try {
						Thread.sleep(1000L);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
	    }).start();
	}
}
