package com.reyun.dmp.entity;

import java.util.List;
import java.util.Map;

public class CampInfo {
	String k;
	int status;
	String r;
	List<Map<String, Object>> s;

	public String getCamp_id() {
		return this.k;
	}

	public void setCamp_id(String camp_id) {
		this.k = camp_id;
	}

	public int getStatus() {
		return this.status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getLast_date() {
		return this.r;
	}

	public void setLast_date(String last_date) {
		this.r = last_date;
	}

	public List<Map<String, Object>> getAudiences() {
		return this.s;
	}

	public void setAudiences(List<Map<String, Object>> audiences) {
		this.s = audiences;
	}
}
