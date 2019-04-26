package test.others;

import com.alibaba.fastjson.JSON;

public class Cat {
	private String name;
	private int age;
	private String color;
	
	private int legNum;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	
	public int getLegNum() {
		return legNum;
	}
	public void setLegNum(int legNum) {
		this.legNum = legNum;
	}
	
	public Cat() {}
	
	
	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}
	
}
