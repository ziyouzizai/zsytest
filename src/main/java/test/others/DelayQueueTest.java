package test.others;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class DelayQueueTest {
	
	public static void main(String[] args) throws InterruptedException {
		DelayQueue<DTask> queue = new DelayQueue<>();
		List<DTask> list = Arrays.asList(new DTask[] {
				new DTask(new Task(1,"task1"),3),
				new DTask(new Task(2,"task1"),1),
				new DTask(new Task(3,"task1"),4),
				new DTask(new Task(4,"task1"),5),
				new DTask(new Task(5,"task1"),7),
				new DTask(new Task(6,"task1"),6),
				new DTask(new Task(7,"task1"),2)
				
		});
		for(DTask dt : list) {
			queue.offer(dt);
		}
		
		DTask task = null;
		while(!queue.isEmpty() && (task = queue.take()) != null) {
			System.out.println(task);
		}
	}
	
}

class DTask implements Delayed{
	private Task task;
	private long excuteTime;
	
	public DTask(Task task,long delayTime) {
		this.task = task;
		this.excuteTime = TimeUnit.NANOSECONDS.convert(delayTime, TimeUnit.MILLISECONDS) + System.nanoTime();
	}
	@Override
	public int compareTo(Delayed delayed) {
		DTask msg = (DTask) delayed;
		return this.excuteTime > msg.excuteTime ? 1 : this.excuteTime < msg.excuteTime ? -1 : 0;
	}
	@Override
	public long getDelay(TimeUnit unit) {
		return unit.convert(this.excuteTime - System.nanoTime(), TimeUnit.NANOSECONDS);
	}
	public Task getTask() {
		return task;
	}
	public void setTask(Task task) {
		this.task = task;
	}
	public long getExcuteTime() {
		return excuteTime;
	}
	public void setExcuteTime(long excuteTime) {
		this.excuteTime = excuteTime;
	}
	@Override
	public String toString() {
		return "DTask [task=" + task + ", excuteTime=" + excuteTime + "]";
	}
}

class Task {
	private int id;
	private String desc;
	
	public Task() {}
	
	public Task(int id, String desc) {
		this.id = id;
		this.desc = desc;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}

	@Override
	public String toString() {
		return "Task [id=" + id + ", desc=" + desc + "]";
	}
}