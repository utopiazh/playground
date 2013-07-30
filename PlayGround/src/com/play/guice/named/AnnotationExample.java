package com.play.guice.named;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.google.inject.AbstractModule;
import com.google.inject.BindingAnnotation;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.name.Named;
import com.google.inject.name.Names;

/**
 * FIXME: doesn't work
 * @author zhouhang
 *
 */
public class AnnotationExample {
	
	@Retention(RetentionPolicy.RUNTIME)
	@Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.METHOD, ElementType.LOCAL_VARIABLE})
	@BindingAnnotation
	        @interface AT {
	}
	
	@Retention(RetentionPolicy.RUNTIME)
	@Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.METHOD, ElementType.LOCAL_VARIABLE})
	@BindingAnnotation
	        @interface BT {
	}
	
	public interface ITest {
		public void test();
	}
	
	public static class ATest implements ITest {
		@Inject public ATest(){
			
		}
		
		@Override
		public void test() {
			System.out.println(this.getClass());			
		}
		
	}
	
	public static class BTest implements ITest {
		
		@Inject public BTest(){
			
		}
		
		@Override
		public void test() {
			System.out.println(this.getClass());			
		} 
	}
	
	public static class ABTestModule extends AbstractModule {

		@Override
		protected void configure() {
			bind(ITest.class).annotatedWith(AT.class).to(ATest.class);
			bind(ITest.class).annotatedWith(BT.class).to(BTest.class);
		}
		
	}
	
	public static void main(String[] args) {
		Injector injector = Guice.createInjector(new ABTestModule());
		@AT ITest a = (ITest) injector.getInstance(ITest.class);
		a.test();
		@BT ITest b = (ITest) injector.getInstance(ITest.class);
		b.test();
	}
}
