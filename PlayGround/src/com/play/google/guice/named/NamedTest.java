package com.play.google.guice.named;

import com.google.inject.AbstractModule;
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
public class NamedTest {

	private static @Named("ATest") ITest a;
	private static @Named("BTest") ITest b;
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
	
	public static class TestModule extends AbstractModule {

		@Override
		protected void configure() {
			bind(ITest.class).annotatedWith(Names.named("ATest")).to(ATest.class);
			bind(ITest.class).annotatedWith(Names.named("BTest")).to(BTest.class);
		}
		
	}
	
	public static void main(String[] args) {
		Injector injector = Guice.createInjector(new TestModule());
		a = (ITest) injector.getInstance(ITest.class);
		a.test();
		b = (ITest) injector.getInstance(ITest.class);
		b.test();
	}
}
