package test.redis;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class JedisPoolHolder {
	private static JedisPool jedisPool = null;
	
	private JedisPoolHolder() {}
	
	private static void init() {
		JedisPoolConfig config = new JedisPoolConfig(); 
        config.setMaxTotal(10);
        config.setMaxIdle(5);
        config.setMaxWaitMillis(1000L);
        config.setTestOnBorrow(false);
        jedisPool = new JedisPool(config,"127.0.0.1",6379);
	}
	
	public static JedisPool getJedisPool() {
		if(jedisPool == null) {
			synchronized (JedisPoolHolder.class) {
				if(jedisPool == null) {
					init();
				}
			}
		}
		return jedisPool;
	}
	
	public static boolean closePool() {
		if(jedisPool != null && !jedisPool.isClosed()) {
			jedisPool.close();
		}
		return true;
	}
}
