package com.reyun.dmp.entity;

public class FecInfo {
	String u;
	String y;
	String z;
	String a;

	public String getFec_ver() {
		return this.z;
	}

	public void setFec_ver(String fec_ver) {
		this.z = fec_ver;
	}

	public String getFec_id() {
		return this.u;
	}

	public void setFec_id(String fec_id) {
		this.u = fec_id;
	}

	public String getFec_url() {
		return this.y;
	}

	public void setFec_url(String fec_url) {
		this.y = fec_url;
	}

	public String getMedia_id() {
		return this.a;
	}

	public void setMedia_id(String media_id) {
		this.a = media_id;
	}
}