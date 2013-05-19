/**
 * 
 */
package org.dallaybatta.pactor.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.dallaybatta.pactor.ActorContext;
import org.dallaybatta.pactor.Messages;
import org.dallaybatta.pactor.annotation.AsynCall;
import org.dallaybatta.pactor.annotation.JActor;
import org.dallaybatta.pactor.annotation.Send;
import org.dallaybatta.pactor.annotation.Signal;
import org.dallaybatta.pactor.proxy.invoker.RequestEndPoint;

public class Utility {
	
	private final static Logger logger = Logger.getLogger(Utility.class .getName());
	
	public static final String PROXY_CLASS_EXTENSION = "Proxy";
	private static final int MILLI_SECONDS = 1000;
	public static final String DEFAULT_ACTORCONTEXT_IMPL = "org.dallaybatta.pactor.context.impl.ActorContextImpl";
	public static final Class<?> JACTOR_CONTEXT_FIELD = org.dallaybatta.pactor.annotation.AContext.class;

	public static final String CALLBACK_ANNOTATION_NAME = "name";

	public static final Object EMPTY_STRING = "";
	public static String HASH_CHARACTER_SET = "########  ";
	public static Class<JActor> JACTOR_ANNOTATION_CLASS = org.dallaybatta.pactor.annotation.JActor.class;
	public static Class<AsynCall> JACTOR_ASYNCALL_METHOD = org.dallaybatta.pactor.annotation.AsynCall.class;
	public static Class<Signal> JACTOR_SIGNAL_METHOD = org.dallaybatta.pactor.annotation.Signal.class;
	public static Class<Send> JACTOR_SEND_METHOD = org.dallaybatta.pactor.annotation.Send.class;
	public static Class<ActorContext> ACTOR_CONTEXT_INTERFACE = org.dallaybatta.pactor.ActorContext.class;
	
	public static boolean hasClassAnnotation(Class<?> clazz,Class<?> annotationClass) {
		Annotation[] annotations = clazz.getDeclaredAnnotations();		
		if(annotations.length>0){
			syslog(" size of annotations "+annotations.length+" for class "+clazz.getName());
			return true;
		}
		else{
			return false;
		}
	}

	public static boolean hasMethodAnnotation(Class<?> clazz,Class<?> annotationClass) {
		Method[] methods = clazz.getDeclaredMethods();
		for(Method method :methods){
			Annotation[] annotations = method.getAnnotations();
			// This is crap and needs to be cleaned.
			if(annotations.length > 0){
				syslog(" size of annotations "+annotations.length+" for method "+method.getName());
				return true;
			}
		}
		return false;
	}
	
	public static boolean hasMethodAnnotation(Class<?> clazz,Class<?> annotationClass,Method meth) {
		Method[] methods = clazz.getDeclaredMethods();
		for(Method method :methods){
			if(method.getName().equals(meth.getName())){
				Annotation[] annotations = method.getAnnotations();
				// This is crap and needs to be cleaned.
				for(Annotation annon:annotations){
					syslog(" --> "+annon.annotationType().getCanonicalName());
					if(annon.annotationType().getCanonicalName().equals(annotationClass.getCanonicalName())){
						syslog("Matchining "+annon.annotationType().getCanonicalName()+" and "+annotationClass.getCanonicalName());
						return true;
					}
				}
				if(annotations.length > 0){
					syslog(" size of annotations "+annotations.length+" for method "+method.getName());
					//return true;
				}
			}
		}
		return false;
	}
	public static boolean hasClassAnnotationWithSuper(Class<?> clazz,Class<?> annotationClass) {
		Annotation[] annotations = clazz.getDeclaredAnnotations();
		if(annotations.length>0){
			syslog(" size of annotations "+annotations.length+" for class "+clazz.getName());
			return true;
		}
		else{
			hasClassAnnotationWithSuper(clazz.getSuperclass(),annotationClass);
			return false;
		}
	}
	
	public static boolean hasFieldAnnotation(Class<?> clazz,Class<?> annotationClass,Field field) {
		Field[] fields = clazz.getDeclaredFields();
		for(Field field1 : fields){
			if(field1.getName().equals(field.getName())){
				Annotation[] annotations = field.getAnnotations();
				// This is crap and needs to be cleaned.
				for(Annotation annon:annotations){
					//syslog(" --> "+annon.annotationType().getCanonicalName());
					if(annon.annotationType().getCanonicalName().equals(annotationClass.getCanonicalName())){
						//syslog("Matchining "+annon.annotationType().getCanonicalName()+" and "+annotationClass.getCanonicalName());
						return true;
					}
				}
				if(annotations.length > 0){
					syslog(" size of annotations "+annotations.length+" for method "+field.getName());
					//return true;
				}
			}
		}
		return false;
	}
	
	
	public static Field getFieldBasedOnType(Object object,Class type,boolean checkSuperClass){
		Field fields[] = null;
		if(checkSuperClass){
			fields = object.getClass().getSuperclass().getDeclaredFields();
			//logger.log(Level.INFO," Field Lenght in "+object.getClass().getSuperclass().getCanonicalName()+" is "+fields.length);				
		}
		else{
			fields = object.getClass().getDeclaredFields();
			//logger.log(Level.INFO," Field Lenght in "+object.getClass().getCanonicalName()+" is "+fields.length);
		}		
		for(Field field:fields){
			Class<?> fieldType = field.getType();
			//logger.log(Level.INFO,"field name : "+field.getName()+" field type :"+field.getType()+"::");
			if(fieldType.equals(type)){
				return field;
			}
		}
		return null;
	}
	public static boolean hasNonVoidReturn() {
		// TODO Auto-generated method stub
		return false;
	}
	
	public static void syslog(String log){
		System.out.println(log);
	}
	public static void main(String ar[]){
		/*
		Utility util = new Utility();
		//util.test1();
		try{
		syslog(" "+util.hasClassAnnotationWithSuper(org.dallaybatta.FirstActor.class, org.dallaybatta.pactor.annotation.JActor.class));
		syslog(" "+util.hasClassAnnotationWithSuper(org.dallaybatta.FirstActorProxy.class, org.dallaybatta.pactor.annotation.JActor.class));
		syslog(" "+util.hasMethodAnnotation(org.dallaybatta.FirstActor.class, org.dallaybatta.pactor.annotation.AsynCall.class));
		}
		catch(Exception e){
			
		}
		Utility.syslog(" The main thread is being used "+Thread.currentThread().getId());
		ActorBuilder actorBuilder = new ActorBuilder();
		String result = "";
		FirstActor firstActor;
		try {
			firstActor = (FirstActor) actorBuilder.actorizePOJO(FirstActorProxy.class);
			result = firstActor.testStuff();
		} catch (ActorNotAnnotated e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // This should create the ActorProxyFA. 
		syslog("End !!! "+result);
		*/
	}
	
	// Here are the tests 
	/*
	public void test1(){
		syslog(renderAnnotations(FirstActorProxy.class));
		FirstActor fa = new FirstActor();
		syslog(renderAnnotations(fa.getClass()));
		syslog(renderAnnotations(FirstActor.class));
	}
	*/

	private String renderAnnotations(Class<?> type) {
		Annotation[] annotations = type.getDeclaredAnnotations();
		syslog("-"+annotations.length);
		return null;
	}

	public static Object invokeMethod(Object newInstance, String methodName,
			Class[] methodParameter, Object[] methodParamValue) {
		Throwable t = null;
		Class clazz = newInstance.getClass();
		logger.log(Level.INFO," Calling method "+methodName+" on class "+clazz);
		try {
			Method meth = clazz.getDeclaredMethod(methodName, methodParameter);
			return meth.invoke(newInstance, methodParamValue);
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} /*catch (SecurityException e) {
			// Rethrow the Exception.
			rethrowException(e.getMessage().toString());
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		*/catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) { // This exception is called when the method throws Exception.
			// TODO Auto-generated catch block
			logger.severe("-->"+e.getMessage()+"<--");
			logger.severe("-->"+e.getTargetException()+"<--");		
			// This is getting Hacky
			//SEVERE: -->java.lang.SecurityException: thrown on request<--			
			try {
				String targetException = ""+e.getTargetException();
				logger.log(Level.INFO,"Going to create the exception "+targetException);
				//t = (Throwable) Utility.loadPlainClass(Utility.retrieveException(e.getTargetException().toString())).newInstance();
				t = (Throwable) Utility.loadPlainClass(Utility.retrieveException(targetException)).newInstance();
				try {
					logger.log(Level.INFO,"Going to throw the exception "+t);
					throw t;
				} catch (Throwable e1) {
					logger.log(Level.SEVERE,"Throwable Exception is  "+e1);
					//e1.printStackTrace();
				}
			} catch (InstantiationException e1) {
				e1.printStackTrace();
			} catch (IllegalAccessException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();			
		}
		
		return t;
	}

	public static void rethrowException(String completeErrorMessage)
	{

		Throwable t = null;
		try {
			t = (Throwable) Utility.loadPlainClass(Utility.retrieveException(completeErrorMessage)).newInstance();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			logger.log(Level.INFO,"Going to throw the exception "+t);
			throw t;
		} catch (Throwable e1) {
			e1.printStackTrace();
		}
	
	}
	/*
	 * This returns the derived class instance.
	 */
	public static <POJO,PROXY_POJO extends POJO> PROXY_POJO loadClass(String proxyClassName) {
		Class clazz = null;
		PROXY_POJO pojoProxy = null;
		try {
			clazz = Class.forName(proxyClassName);			
			pojoProxy = (PROXY_POJO)clazz.newInstance();			
		} 
		catch (ClassNotFoundException e) {
			logger.info(Messages.LOAD_CLASS_ERROR+e.getMessage());
		}
		catch (InstantiationException e) {
			logger.info(Messages.LOAD_CLASS_ERROR+e.getMessage());
		} 
		catch (IllegalAccessException e) {
			logger.info(Messages.LOAD_CLASS_ERROR+e.getMessage());
		}
		return pojoProxy;
	}
	
	public static Class loadPlainClass(String className){
		try {
			return Class.forName(className);
		} catch (ClassNotFoundException e) {
			logger.log(Level.SEVERE," Unable to load the class "+e);
			//e.printStackTrace();
		}
		return null;
	}
	/*
	 * This is not required, it was giving the trouble earlier. This should be removed after few
	 * iterations. This seems to working as expected.
	 */
	public static <POJO> POJO loadClassTest(String proxyClassName) {
		Class clazz = null;
		POJO pojo = null;
		try {
			clazz = Class.forName(proxyClassName);			
			pojo = (POJO)clazz.newInstance();			
		} 
		catch (ClassNotFoundException e) {
			logger.info(Messages.LOAD_CLASS_ERROR+e.getMessage());
		}
		catch (InstantiationException e) {
			logger.info(Messages.LOAD_CLASS_ERROR+e.getMessage());
		} 
		catch (IllegalAccessException e) {
			logger.info(Messages.LOAD_CLASS_ERROR+e.getMessage());
		}
		return pojo;
	}

	public static void delay(int i) {
		try {
			Thread.sleep(i*MILLI_SECONDS);
		} catch (InterruptedException e) {
			// Igonore.
		}
	}

	//java.lang.SecurityException: thrown on request
	// This sounds to be hack.... Will look for efficient solution later.
	public static String retrieveException(String completeErrorMessage){
		String[] tokens = completeErrorMessage.split(":");
		logger.log(Level.INFO," Retrieved Exception from "+completeErrorMessage+" as "+tokens[0]+" :");
		return tokens[0];
	}

	// This needs to be reworked make more generic, next release....
	public static String getCallBackMethodName(String actorClassName,Method method, String name) {
		Class parameterTypes = RequestEndPoint.class;
		Annotation methodAnnotations[] = null;
		try {
			//Method method = Utility.loadPlainClass(actorClassName).getDeclaredMethod(methodName);
			methodAnnotations = method.getAnnotations();
			for(Annotation anon:methodAnnotations){
				Send sendClass = method.getAnnotation(Utility.JACTOR_SEND_METHOD);
				Utility.syslog(method.getName()+"--->"+sendClass.name());
				return sendClass.name();
			}
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		return null;
	}
}