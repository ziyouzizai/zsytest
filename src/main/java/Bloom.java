import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
 
public class Bloom {
     
    public static void main(String[] args) {
    	test();
    }
 
    public static void test() {
    	BloomFilter<String> bf = BloomFilter.create(Funnels.stringFunnel(Charset.forName("utf-8")), 1000000, 0.000001);
    	
    	List<String> list = new ArrayList<>();
		List<String> testList = new ArrayList<>();
		for(int i=1;i<=1000000;i++) {
			String idfa = UUID.randomUUID().toString();
			list.add(idfa);
			if(i % 100 == 0) {
				testList.add(idfa);
			}
		}
		for(int i=0;i<100;i++) {
			String idfa = UUID.randomUUID().toString();
			testList.add(idfa);
		}
		long start = System.currentTimeMillis();
		for(int i=0;i<list.size();i++) {
			bf.put(list.get(i));
		}
		long end = System.currentTimeMillis();
		System.out.println(String.format("cost %d ms",(end - start)));
		
		for(int i=0;i<testList.size();i++) {
			boolean flag = bf.mightContain(testList.get(i));
			if(!flag) {
				System.out.println(String.format("%d,%s",i,testList.get(i)));
			}
		}
		long end2 = System.currentTimeMillis();
		System.out.println("check cost " + (end2 - end) + "ms");
    }
    
    public static void old() {
    	BloomFilter<String> b = BloomFilter.create(Funnels.stringFunnel(Charset.forName("utf-8")), 1000, 0.000001);
        b.put("121");
        b.put("122");
        b.put("123");
        System.out.println(b.mightContain("12321"));
        BloomFilter<String> b1 = BloomFilter.create(Funnels.stringFunnel(Charset.forName("utf-8")), 1000, 0.000001);
        b1.put("aba");
        b1.put("abb");
        b1.put("abc");
        b1.putAll(b);
        System.out.println(b1.mightContain("123"));
    }
}