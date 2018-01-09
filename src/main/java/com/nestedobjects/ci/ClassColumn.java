package com.nestedobjects.ci;

public class ClassColumn {
	private Class objectClass;
	private String name;
	public ClassColumn(Class clazz, String name){
		this.objectClass = clazz;
		this.name = name;
	}
	public Class getObjectClass(){ return this.objectClass; }
	public String getName() { return this.name; }
}
