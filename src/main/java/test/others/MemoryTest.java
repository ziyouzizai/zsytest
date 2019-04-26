package test.others;

import java.util.LinkedList;

/**
* 类说明 
*
* @author zhangsy
* @date 2018年6月28日
*/
public class MemoryTest {
	
	public static void main(String[] args){ 
		LinkedList<Dog> xttblog=new LinkedList<Dog>();//作为GC Root 
		long index = 0;
		while(true){ 
			index++;
			xttblog.add(new Dog("name"+index,"color"+index,index));//疯狂创建对象 
		} 
	}
}

class Dog {
	private String name;
	private String color;
	private long age;
	public Dog(String name,String color,long age) {
		this.name = name;this.color = color;this.age = age;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public long getAge() {
		return age;
	}
	public void setAge(long age) {
		this.age = age;
	}
}
