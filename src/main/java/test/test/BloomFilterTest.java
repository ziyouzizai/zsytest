package test.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.Charset;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;

public class BloomFilterTest {
//	private static String paramTemp = "{\"media_id\": \"fec-test\",\"camp_ids\":[ \"903a5d67d20d9bbe\",\"93e560696e2082e6\"],\"device_id\": \"%s\",\"deviceid_type\": 1}";
//  返回:{"camp_ids":[],"code":0}	
	@SuppressWarnings("rawtypes")
	public static void main(String[] args) throws IOException {
		File file = new File("");
		FileInputStream fin = new FileInputStream(file);
		BloomFilter bf = BloomFilter.readFrom(fin,Funnels.stringFunnel(Charset.forName("utf-8")));
		
		
	}
}
