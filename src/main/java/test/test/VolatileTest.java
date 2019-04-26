package test.test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

public class VolatileTest {
	public static AtomicInteger ai = new AtomicInteger(0);
	public static void main(String[] args) {
		int TASK_NUM = 10000000;
		int THREAD_NUM = 50;
		ExecutorService executor = Executors.newFixedThreadPool(THREAD_NUM);
		List<Future<? extends Object>> futureList = new ArrayList<>();
		for(int i=0;i<TASK_NUM;i++) {
			futureList.add(executor.submit(new T1()));
		}
		
		for(Future<? extends Object> f : futureList) {
			try {
				f.get();
			} catch (Exception e) {
				e.printStackTrace();
			} 
		}
		System.out.println("result = " + ai.get());
		
		//结果仍然没有看到不一致,所以，搞不清楚valatile的作用(明白原理，但是所以需要用volatile的地方，不用，似乎都没有看得见的现象，很奇怪 )
		
		//测试的初衷，是基于下面这段话:
		//内存屏障本身不是一种优化方式, 而是你使用lock-free(CAS)的时候, 必须要配合使用内存屏障，
		//因为CPU和memory之间有多级cache, CPU core只会更新cache-line, 而cache-line什么时候
		//flush到memory, 这个是有一定延时的 ,在这个延时当中, 其他CPU core是无法得知你的更新的, 
		//因为只有把cache-line flush到memory后, 其他core中的相应的cache-line才会被置为过期数据,
		//所以如果要保证使用CAS能保证线程间互斥, 即乐观锁, 必须当一个core发生更新后, 其他所有core立刻
		//知道并把相应的cache-line设为过期, 否则在这些core上执行CAS读到的都是过期数据.
		//内存屏障 = “立刻将cache-line flush到memory, 没有延时”
		//注:可参考java中volatile的原理，同样实现了内存屏障。
	}
}

class T1 implements Runnable{
	@Override
	public void run() {
		VolatileTest.ai.incrementAndGet();
	}
}
