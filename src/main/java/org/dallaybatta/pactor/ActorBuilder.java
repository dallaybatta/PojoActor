/**
 * 
 */
package org.dallaybatta.pactor;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;

import javassist.util.proxy.MethodHandler;
import javassist.util.proxy.Proxy;
import javassist.util.proxy.ProxyFactory;

import org.agilewiki.jactor.api.Mailbox;
import org.dallaybatta.pactor.exception.ActorNotAnnotated;
import org.dallaybatta.pactor.Messages;
import org.dallaybatta.pactor.proxy.InterceptorChain;
import org.dallaybatta.pactor.proxy.interceptor.DefaultInterceptorChain;
import org.dallaybatta.pactor.proxy.interceptor.InvocationObject;
import org.dallaybatta.pactor.proxy.invoker.RequestEndPoint;
import org.dallaybatta.pactor.util.Utility;

/**
 * This ActorBuilder will create a Actorized Proxy for the POJO, the POJO should be 
 * annotated for ActorBuilder to create an Actorized Proxy.
 * 
 * @author vickykak
 *
 */
public class ActorBuilder<POJO> {
	
	private final static Logger logger = Logger.getLogger(ActorBuilder.class .getName());
		
	private POJO genericPojo;
	private String className;
	private RequestEndPoint requestEndPoint;
	private Mailbox sharedMailBox;
	
	@SuppressWarnings("unchecked")
	public ActorBuilder(Class<?> clazz){
		className = clazz.getCanonicalName();
		logger.info("Class Name is "+className);
		genericPojo = (POJO) clazz;
	}
	
	/*
	 * This creates a POJO Actor Proxy based on the annotations.
	 */
	public POJO actorizePOJO(Class<?> pojoClass) throws ActorNotAnnotated{	
		checkActorAnnotations(pojoClass);
		POJO actorizedPojoProxy = (POJO)generateProxy(pojoClass);
		return actorizedPojoProxy;
	}

	public <PROXY_POJO extends POJO> POJO actorizePOJO(String pojoClassName){
		String proxyClassName = pojoClassName + Utility.PROXY_CLASS_EXTENSION;
		PROXY_POJO proxy_pojo = Utility.loadClass(proxyClassName);
		return proxy_pojo;
	}
	
	public POJO actorizePOJO(){
		String proxyClassName = className + Utility.PROXY_CLASS_EXTENSION;		
		logger.info("Fully Proxy Class Name "+proxyClassName);
		POJO pojo = Utility.loadClassTest(proxyClassName);
		return pojo;
	}
	
	public POJO actorizePOJOWithConfiguration(Class<?> pojoClass){
		// This should be done in next release.
		return null;
	}
	
	/*
	 * This method will check if the POJO contains the appropriate annotations
	 * which would be required by the ActorBuilder to create a Actor Proxy. The 
	 * POJO should be annotated with @JActor,@AsynCall etc ( Yet to be defined).
	 * 
	 */
	private void checkActorAnnotations(Class<?> pojoClass) throws ActorNotAnnotated {
		throw new ActorNotAnnotated(Messages.POJO_NOT_ACTORIZED);
	}

	/*
	 * Will take a look at 
	 * http://asm.ow2.org/doc/tutorial-asm-2.0.html
	 * http://stackoverflow.com/questions/10664182/what-is-the-difference-between-jdk-dynamic-proxy-and-cglib
	 */
	private POJO generateProxy(Class<?> pojoClass) {
		// This is for next release.
		return null;
	}	
	
	/*
	 * pojoClass should have been to Class.forName(pojoClassName)
	 */
	@SuppressWarnings("unchecked")
	public <POJO_PROXY> POJO_PROXY getProxy(Class<?> pojoClass){
		POJO_PROXY pojoProxy = null;
		ProxyFactory pf = new ProxyFactory();
		pf.setSuperclass(pojoClass);
		Class proxyClass = pf.createClass();
		try {
			pojoProxy = (POJO_PROXY)proxyClass.newInstance();
			// Setting the ActorContext if found.
			logger.log(Level.INFO, " pojoProxy Object "+pojoProxy.toString());
			Field actorContextfield = Utility.getFieldBasedOnType(pojoProxy, Utility.ACTOR_CONTEXT_INTERFACE, true);
			// Check if the ActorContext field had AContext annotation, then it must be considered for instantiating.
			logger.log(Level.INFO, " actorContextfield "+actorContextfield);
			// ActorContext field is not defined.
			if(actorContextfield == null){
				logger.log(Level.WARNING," ActorContext variable is not defined in "+pojoProxy.toString()+" , are you sure that you don't require it !!!! ");
			}			
			else{
				boolean isActorContextFieldAnnotated = Utility.hasFieldAnnotation(pojoClass, Utility.JACTOR_CONTEXT_FIELD, actorContextfield); 
				if(!isActorContextFieldAnnotated){
					logger.log(Level.WARNING,"ActorContext is not annotated with "+Utility.JACTOR_CONTEXT_FIELD+" so would not be set.....");
				}
				else{
					ActorContext actorContext = (ActorContext) actorContextfield.get(pojoProxy);
					if(actorContext == null){
						logger.log(Level.INFO, " We got null actorContext "+pojoProxy.toString());
						actorContext = (ActorContext) Class.forName(Utility.DEFAULT_ACTORCONTEXT_IMPL).newInstance();
						
						if(getRequestEndPoint() == null){
							logger.log(Level.WARNING," The RequestEndPoint is "+null+"!!!! ");
						}
						
						actorContext.setRequestOriginator(getRequestEndPoint());
						// set back the ActorContext with value finally.
						actorContextfield.set(pojoProxy, actorContext);
					}
					else{
						actorContextfield.set(pojoProxy, actorContext);
					}
					logger.log(Level.INFO, " We have set the ActorContext(field) "+actorContextfield.get(pojoProxy));
				}
			}
			// Make the MethodHandler pluggbale, next release.
			Jactor2MethodHandler jactorMethodHandler = new Jactor2MethodHandler();
			jactorMethodHandler.setProxyObj(pojoProxy);		
			jactorMethodHandler.setRep(getRequestEndPoint());
			((Proxy)pojoProxy).setHandler(jactorMethodHandler);
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return pojoProxy;
	}
	
	/*
	 * This should be used with the Send + Actor chaining where the Context/RequestEndPoint
	 * needs to be passed to all the actors in a chain.
	 */
	@SuppressWarnings("unchecked")
	public <POJO_PROXY> POJO_PROXY getProxy(Class<?> pojoClass, ActorContext incomingActorContext){
		POJO_PROXY pojoProxy = null;
		ProxyFactory pf = new ProxyFactory();
		pf.setSuperclass(pojoClass);
		Class<?> proxyClass = pf.createClass();
		try {
			pojoProxy = (POJO_PROXY)proxyClass.newInstance();		
			// Set the ActorContext on the pojoProxy
			Field actorContextfield = Utility.getFieldBasedOnType(pojoProxy, Utility.ACTOR_CONTEXT_INTERFACE, true);
			actorContextfield.set(pojoProxy, incomingActorContext);
			Jactor2MethodHandler jactorMethodHandler = new Jactor2MethodHandler();
			jactorMethodHandler.setProxyObj(pojoProxy);
			jactorMethodHandler.setRep(incomingActorContext.getRequestOriginator());			
			((Proxy)pojoProxy).setHandler(jactorMethodHandler);
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return pojoProxy;
	}
	
	// Getter Setters
	public RequestEndPoint getRequestEndPoint() {
		return requestEndPoint;
	}

	public void setRequestEndPoint(RequestEndPoint requestEndPoint) {
		this.requestEndPoint = requestEndPoint;
	}

	public Mailbox getSharedMailBox() {
		return sharedMailBox;
	}

	public void setSharedMailBox(Mailbox sharedMailBox) {
		this.sharedMailBox = sharedMailBox;
	}
}

class Jactor2MethodHandler implements MethodHandler{
	private final static Logger logger = Logger.getLogger(Jactor2MethodHandler.class .getName());

	private Object proxyObj;
	private RequestEndPoint rep;
	
	public RequestEndPoint getRep() {
		return rep;
	}

	public void setRep(RequestEndPoint rep) {
		this.rep = rep;
	}

	public Object getProxyObj() {
		return proxyObj;
	}

	public void setProxyObj(Object proxyObj) {
		this.proxyObj = proxyObj;
	}

	@Override
	public Object invoke(Object self, Method m, Method proceed,
            Object[] args) throws Exception{
		// The InterceptorChain and the Invocation object should be configurable.
		InterceptorChain interceptorChain = new DefaultInterceptorChain();		
		InvocationObject invocation = new InvocationObject(proxyObj,m,proceed,args);
		try {
			// Inject Actor Container Details.
			Field actorContextField = Utility.getFieldBasedOnType(proxyObj, Utility.ACTOR_CONTEXT_INTERFACE, true);
			
			//if(!Utility.hasFieldAnnotation(pojoClass, Utility.JACTOR_CONTEXT_FIELD, actorContextfield)){
			//logger.log(Level.WARNING,"ActorContext is not annoated with "+Utility.JACTOR_CONTEXT_FIELD+" so would not be set.....");
			//}
			
			logger.log(Level.WARNING,"proxyObj.getClass() "+proxyObj.getClass());
			boolean isActorContextAnnotated = Utility.hasFieldAnnotation(proxyObj.getClass().getSuperclass(), Utility.JACTOR_CONTEXT_FIELD, actorContextField);
			if(actorContextField != null && isActorContextAnnotated){						
				ActorContext context = (ActorContext)actorContextField.get(proxyObj);
				context.setRequestOriginator(getRep());
				invocation.setActorContext(context);
				logger.log(Level.INFO,"ActorContext is "+context);
			}
			else{
				logger.log(Level.INFO,"actorContextField is "+actorContextField+" isActorContextAnnotated "+isActorContextAnnotated);
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} 
		
		try{
			interceptorChain.intercept(invocation);
		}
		catch(Exception e){
			e.printStackTrace();
			logger.log(Level.SEVERE,"Failed in the interceptor Chain "+e.getMessage());
			throw e;
		}
		return invocation.getReturnValue();
	}
}
