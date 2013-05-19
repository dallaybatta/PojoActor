/**
 * 
 */
package org.dallaybatta;

import java.util.logging.Logger;

import org.dallaybatta.pactor.ActorBuilder;
import org.dallaybatta.pactor.util.Utility;

import junit.framework.TestCase;

/**
 * @author vickykak
 *
 */
public class FirstActorTest extends  TestCase {
	
	private final static Logger logger = Logger.getLogger(FirstActorTest.class .getName());
	
	public void testFirstActor(){
		ActorBuilder<FirstActor> abuilder = new ActorBuilder<FirstActor>(FirstActor.class);
		FirstActor firstActor = abuilder.actorizePOJO();//abuilder.actorizePOJO("org.dallaybatta.FirstActor");
		//String returnValue = firstActor.testStuff();
		//logger.info(returnValue);
		//assertEquals("Hello World",returnValue);
		assertTrue(true);
		Utility.delay(2);
		firstActor.sendTest();
		Utility.delay(2);
		firstActor.signalTest();		
	}
	
	/*
	public void testOOCall(){
		FirstActor firstActor = new FirstActorProxy();
		firstActor.singalTest();
	}
	
	public void testDummy(){
		A a = new B();
		a.test();
	}
	*/
	/*
	public void testJavaAssistProxy(){

	   B b = new B();
	   assertTrue(b instanceof A);
	    
	   System.out.println("Generating proxy");
	   ProxyFactory f = new ProxyFactory();
	   f.setSuperclass(B.class);
	   f.setFilter(new MethodFilter() {
		@Override
		public boolean isHandled(Method arg) {
			if (arg.getName().equals("test"))
				System.out.println("Invoking handler");
			return true;
		}
	   });
	   MethodHandler mi = new MethodHandler() {
		     public Object invoke(Object self, Method m, Method proceed,
		                          Object[] args) throws Throwable {
		         System.out.println("Name: " + m.getName());
		         return proceed.invoke(self, args);  // execute the original method.
		     }
		 };
	   System.out.println("Create");
	   Class c = f.createClass();	   
	   System.out.println("Created");
	 
	   B bproxy = null;
	try {
		bproxy = (B) c.newInstance();
		((Proxy)bproxy).setHandler(mi);
	} catch (InstantiationException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IllegalAccessException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	   bproxy.test();
	}
	*/
	@SuppressWarnings("unchecked")
	public void testgetProxy(){
		//A a = new A();
		FirstActor a = new FirstActor();
		Class aClass = a.getClass();
		//ActorBuilder<A> builder = new ActorBuilder<A>(aClass);
		ActorBuilder<FirstActor> builder = new ActorBuilder<FirstActor>(aClass);
		// the logger seems to be getting kicked in aync mode. This needs to 
		// be investigate more.
		//logger.info(">>----------------");
		System.out.println(">>----------------");
		//A proxy = builder.getProxy(aClass);
		//FirstActor proxy = builder.getProxy(aClass);
		//proxy.test();
		//proxy.testStuff();
		//logger.info("<<----------------");
		System.out.println("<<----------------");
	}
	
	public void testProxyInvocations(){
		//invokeMethodOnProxy("org.dallaybatta.FirstActor","testStuff");
		//invokeMethodOnProxy("org.dallaybatta.FirstActor","signalTest");
		//invokeMethodOnProxy("org.dallaybatta.FirstActor","sendTest");
	}
	
	public <ANNOTATED_ACTOR> void invokeMethodOnProxy(String proxyClassName,String methodName){
		ANNOTATED_ACTOR pactor = (ANNOTATED_ACTOR)Utility.loadClassTest(proxyClassName);
		Class<?> pactorClass = pactor.getClass();
		ActorBuilder<ANNOTATED_ACTOR> builder = new ActorBuilder<ANNOTATED_ACTOR>(pactorClass);
		ANNOTATED_ACTOR proxy = builder.getProxy(pactorClass);
		Class[] methodParameter =  {};
		Object[] methodParamValue =  {};
		Utility.invokeMethod(proxy, methodName, methodParameter, methodParamValue);
	}
}

class A {
	public void test(){
		System.out.println("From test in A");
	}
}
class B extends A{
	public void test(){
		System.out.println("From test in B, overriden");
	}
}
