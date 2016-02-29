package com.play.google.json;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Multimap;

public class TestClass {
	private Map<String, String> map;
	private List<String> list;
	private int i;
	private double d;
	private Multimap<String, String> mmap;
	
	public TestClass() {		
	}
	
	public Map<String, String> getMap() {
		return map;
	}
	public void setMap(Map<String, String> map) {
		this.map = map;
	}
	public List<String> getList() {
		return list;
	}
	public void setList(List<String> list) {
		this.list = list;
	}
	public int getI() {
		return i;
	}
	public void setI(int i) {
		this.i = i;
	}
	public double getD() {
		return d;
	}
	public void setD(double d) {
		this.d = d;
	}
	public Multimap<String, String> getMmap() {
		return mmap;
	}
	public void setMmap(Multimap<String, String> mmap) {
		this.mmap = mmap;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof TestClass)) {
			return false;
		}
		
		TestClass other = (TestClass)obj;
		if(this.i != other.i) {
			return false;
		}
		
		if(this.d != other.d) {
			return false;
		}
		
		if(this.list == null ? other.list != null : !this.list.equals(other.list)) {
			return false;
		}
		if(this.map == null ? other.map != null : !this.map.equals(other.map)) {
			return false;
		}
		if(this.mmap == null ? other.mmap != null : !this.mmap.equals(other.mmap)) {
			return false;
		}
		return true;
		
	}
}
