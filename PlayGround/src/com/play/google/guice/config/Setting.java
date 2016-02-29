package com.play.google.guice.config;

import java.util.Properties;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.name.Named;
import com.google.inject.name.Names;

public class Setting {
	
	public interface Consumer {
		
	}
	
	public static class RealConsumer implements Consumer {
		String base;
		String zk;
		@Inject public RealConsumer(@Named("zk") String zk,
				@Named("base") String base) {
			this.base = base;
			this.zk = zk;
		}
		@Override
		public String toString() {
			return "RealConsumer [base=" + base + ", zk=" + zk + "]";
		}		
		
	}
	
	public static class ConsumerModule extends AbstractModule {

		@Override
		protected void configure() {
			bind(Consumer.class).to(RealConsumer.class);			
		}
		
	}
	
	public  static void main(String[] args) {
		final Properties config = new Properties();
		config.setProperty("zk", "zk");
		config.setProperty("base", "root");
		
		Module settings = new AbstractModule() {
			@Override
			protected void configure() {
				Names.bindProperties(binder(), config);				
			}			
		};
		
		Injector injector = Guice.createInjector(settings, new ConsumerModule());
		Consumer consumer = injector.getInstance(Consumer.class);
		System.out.println(consumer.toString());
		
	}
}
