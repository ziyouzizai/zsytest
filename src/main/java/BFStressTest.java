

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import redis.clients.jedis.Jedis;
import test.myutils.FileUtil;

public class BFStressTest {
	public static void main(String[] args) {
		
		
	}
	
	public static void stress() {
		String bfDir = "";
		Map<String,RedisBloomFilter> map = load(getJedis(),bfDir);
		
	}
	
	public static Jedis getJedis() {
		//TODO 
		return null;
	}
	
	public static Map<String,RedisBloomFilter> load(Jedis jedis,String srcDir){
		Map<String,RedisBloomFilter> result = new HashMap<>();
		File dir = new File(srcDir);
		File[] files = dir.listFiles();
		for(File file : files) {
			String path = file.getAbsolutePath();
			String redisKey = file.getName();
			String ciphertext = FileUtil.file2Str(path);
			RedisBloomFilter rbf = RedisBloomFilter.recovery(jedis, redisKey, ciphertext);
			result.put(redisKey,rbf);
		}
		return result;
	}
}
