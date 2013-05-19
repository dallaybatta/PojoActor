package org.dallaybatta.pactor.proxy.invoker;

import java.util.logging.Logger;

public class InvokerFactory {
	
	private final static Logger logger = Logger.getLogger(InvokerFactory.class .getName());
	
	public static String CALL = "org.dallaybatta.pactor.proxy.invoker.impl.CallInvoker";
	public static String SIGNAL = "org.dallaybatta.pactor.proxy.invoker.impl.SignalInvoker";
	public static String SEND = "org.dallaybatta.pactor.proxy.invoker.impl.SendInvoker";
	
	public static Invoker getInstance(String invokerClassName) {
		logger.info("loading the class: "+invokerClassName);
		Class<?> clazz = null;
		try {
			clazz = Class.forName(invokerClassName);
			return (Invoker)clazz.newInstance();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}catch (InstantiationException e) {
			e.printStackTrace();
		}catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		logger.info("Done with loading the class "+invokerClassName);
		return null;
	}

}
