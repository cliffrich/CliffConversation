package com.cliff.flash;

public class QandA {
	String no;
	String q;
	String a;
	public String getQ() {
		return q;
	}
	public void setQ(String q) {
		this.q = q;
	}
	public String getA() {
		return a;
	}
	public void setA(String a) {
		this.a = a;
	}
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	@Override
	public String toString() {
		return "QandA [no=" + no + ", q=" + q + ", a=" + a + "]";
	}
}
