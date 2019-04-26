import java.nio.charset.Charset;
import java.util.Base64;

import com.google.common.hash.Funnels;
import com.google.common.hash.Hashing;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;
import test.myutils.BitUtil;

/**
 * Redis版本布隆过滤器
 * 
 * 
 * @author zhangsy
 */
public class RedisBloomFilter {
	// 预计插入量
	private long expectedInsertions = 10000;
	// 可接受的错误率
	private double fpp = 0.001F;
	private long numBits = -1L;
	private int numHashFunctions = -1;
	private static String rediskeyPrefix = "rbf:";
	private String redisKey;
	private Jedis jedis = null;

	public RedisBloomFilter(Jedis jedis,String redisKey,long expectedInsertions,double fpp) {
		this.redisKey = redisKey;
		this.expectedInsertions = expectedInsertions;
		this.fpp = fpp;
		this.jedis = jedis;
		// bit数组长度
		numBits = optimalNumOfBits(expectedInsertions, fpp);
		// hash函数数量
		numHashFunctions = optimalNumOfHashFunctions(expectedInsertions, numBits);
	}
	
	/**
	 * 判断keys是否存在于集合where中
	 */
	public boolean isExist(String value) {
		long[] indexs = getIndexs(value);
		boolean result;
		// 这里使用了Redis管道来降低过滤器运行当中访问Redis次数 降低Redis并发量
		Pipeline pipeline = jedis.pipelined();
		try {
			for (long index : indexs) {
				pipeline.getbit(rediskeyPrefix + redisKey, index);
			}
			result = !pipeline.syncAndReturnAll().contains(false);
		} finally {
			pipeline.close();
		}
		return result;
	}

	/**
	 * 将key存入redis bitmap
	 */
	public void put(String value) {
		long[] indexs = getIndexs(value);
		// 这里使用了Redis管道来降低过滤器运行当中访问Redis次数 降低Redis并发量
		Pipeline pipeline = jedis.pipelined();
		try {
			for (long index : indexs) {
				pipeline.setbit(rediskeyPrefix + redisKey, index, true);
			}
			pipeline.sync();
		} finally {
			pipeline.close();
		}
	}

	/**
	 * 根据key获取bitmap下标 方法来自guava
	 */
	private long[] getIndexs(String value) {
		long hash1 = hash(value);
		long hash2 = hash1 >>> 16;
		long[] result = new long[numHashFunctions];
		for (int i = 0; i < numHashFunctions; i++) {
			long combinedHash = hash1 + i * hash2;
			if (combinedHash < 0) {
				combinedHash = ~combinedHash;
			}
			result[i] = combinedHash % numBits;
		}
		return result;
	}

	// 计算hash函数个数 方法来自guava
	private int optimalNumOfHashFunctions(long n, long m) {
		return Math.max(1, (int) Math.round((double) m / n * Math.log(2)));
	}

	// 计算bit数组长度 方法来自guava
	private long optimalNumOfBits(long n, double p) {
		if (p == 0) {
			p = Double.MIN_VALUE;
		}
		return (long) (-n * Math.log(p) / (Math.log(2) * Math.log(2)));
	}
	
	/**
	 * 获取一个hash值 方法来自guava
	 */
	private long hash(String key) {
		Charset charset = Charset.forName("UTF-8");
		return Hashing.murmur3_128().hashObject(key, Funnels.stringFunnel(charset)).asLong();
	}
	
	public Jedis getJedis() {
		return jedis;
	}
	public long getExpectedInsertions() {
		return expectedInsertions;
	}
	public double getFpp() {
		return fpp;
	}
	public String getRedisKey() {
		return redisKey;
	}
	
	/**
	 * 备份布隆过滤器
	 * 
	 * @param rbf
	 * @return
	 */
	public static String dump(RedisBloomFilter rbf) {
		String rkey = rediskeyPrefix + rbf.getRedisKey();
		byte[] bs = rbf.getJedis().get(rkey.getBytes());
		byte[] expectedInsertionsBytes = BitUtil.long2Bytes(rbf.getExpectedInsertions());
		byte[] fppBytes = BitUtil.double2Bytes(rbf.getFpp());
		
		byte[] bytes = new byte[Long.SIZE/Byte.SIZE + Double.SIZE/Byte.SIZE + bs.length];
		System.arraycopy(expectedInsertionsBytes, 0, bytes,0,Long.SIZE/Byte.SIZE);
		System.arraycopy(fppBytes,0,bytes,Long.SIZE/Byte.SIZE,Double.SIZE/Byte.SIZE);
		System.arraycopy(bs, 0, bytes,Long.SIZE/Byte.SIZE + Double.SIZE/Byte.SIZE,bs.length);
		String ciphertext = Base64.getEncoder().encodeToString(bytes);
		return ciphertext;
	}
	
	/**
	 * 根据备份还原布隆过滤器
	 * 
	 * @param jedis
	 * @param redisKey
	 * @param ciphertext
	 * @return
	 */
	public static RedisBloomFilter recovery(Jedis jedis,String redisKey,String ciphertext) {
		byte[] bytes = Base64.getDecoder().decode(ciphertext);
		int longsize = Long.SIZE/Byte.SIZE;
		int doublesize = Double.SIZE/Byte.SIZE;
		byte[] expectedInsertionsBytes = new byte[longsize];
		byte[] fppBytes = new byte[doublesize];
		byte[] bs = new byte[bytes.length - longsize - doublesize];
		System.arraycopy(bytes,0, expectedInsertionsBytes, 0,longsize);
		System.arraycopy(bytes,longsize, fppBytes, 0,doublesize);
		System.arraycopy(bytes,longsize+doublesize, bs, 0,bs.length);
		long expectedInsertions = BitUtil.bytes2Long(expectedInsertionsBytes);
		double fpp = BitUtil.bytes2Double(fppBytes);
		String rkey = rediskeyPrefix + redisKey;
		jedis.set(rkey.getBytes(),bs);
		return new RedisBloomFilter(jedis,redisKey,expectedInsertions,fpp);
	}
}
