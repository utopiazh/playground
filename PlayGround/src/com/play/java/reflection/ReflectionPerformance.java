package com.play.java.reflection;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
/**
 * Reflection performance:
 * 
 * 
 * 1. getFields becomes slow when public fields number increases.
 * 
 * @author zhouhang
 *
 */
public class ReflectionPerformance {
	public static List<Field> fields = new ArrayList();;
	public static void getFields(Class targetClass) {
		fields.addAll(Arrays.asList(targetClass.getFields()));
	}
	
	public static void main(String[] args) {
		final int COUNT = 1000000;
		ReflectionPerformance.getFields(TestClass.class);
		ReflectionPerformance.getFields(TestClass2.class);
		ReflectionPerformance.getFields(TestClass3.class);

		long s = System.currentTimeMillis();
		for(int i = 0; i < COUNT; ++i) {
			ReflectionPerformance.getFields(TestClass.class);
		}
		long e = System.currentTimeMillis();
		
		System.out.println("TestClass, cost "  + (e - s));
		
		s = System.currentTimeMillis();
		for(int i = 0; i < COUNT; ++i) {
			ReflectionPerformance.getFields(TestClass2.class);
		}
		e = System.currentTimeMillis();
		
		System.out.println("TestClass2, cost "  + (e - s));
		
		s = System.currentTimeMillis();
		for(int i = 0; i < COUNT; ++i) {
			ReflectionPerformance.getFields(TestClass3.class);
		}
		e = System.currentTimeMillis();
		
		System.out.println("TestClass3, cost "  + (e - s));
		
		s = System.currentTimeMillis();
		for(int i = 0; i < COUNT; ++i) {
			ReflectionPerformance.getFields(LinkedBlockingQueue.class);
		}
		e = System.currentTimeMillis();
		
		System.out.println("LinkedBlockingQueue, cost "  + (e - s));
	}
}
