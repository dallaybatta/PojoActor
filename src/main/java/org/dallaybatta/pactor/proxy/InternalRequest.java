package org.dallaybatta.pactor.proxy;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.agilewiki.jactor.api.Mailbox;
import org.agilewiki.jactor.api.RequestBase;
import org.agilewiki.jactor.api.Transport;
import org.dallaybatta.pactor.ActorContext;
import org.dallaybatta.pactor.proxy.invoker.impl.CallBack;
import org.dallaybatta.pactor.util.Utility;

/*
 * The InternalRequest Object is being used by the Actor Proxy.
 */
public class InternalRequest<RESPONSE_TYPE,REAL_ACTOR_TYPE> extends RequestBase<RESPONSE_TYPE>{
	
	private final static Logger logger = Logger.getLogger(InternalRequest.class .getName());

	private String actorClassName;
	private ActorContext actorContext;	
	private	Method method;
	private String methodName; 	
	private Class methodParameter[];
	private Object methodParamValue[];
	

	public InternalRequest(Mailbox mailbox){
		super(mailbox);
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.agilewiki.jactor.api.Request#processRequest(org.agilewiki.jactor.api.Transport)
	 * The InternalRequest should 
	 * 1) set the ActorContext object on the Pojo Actor.
	 * 2) Invoke the bussiness method in the Pojo Actor. The bussiness method of the Pojo Actor
	 *    can access the ActorContext as set in the step1.
	 */
	@Override
	public void processRequest(Transport<RESPONSE_TYPE> arg0) throws Exception {
		logger.info(" starting: processRequest by thread "+Thread.currentThread().getId()+" "+actorClassName);
		// The Instance should be obtained from the Object Pool, TBD next.
		Object actorRealObject = Class.forName(actorClassName).newInstance();
		logger.info(" actorRealObject "+actorRealObject);
		// Set the ActorContext for the Pojo Actor. The InvocationContext will be removed soon.
		//actorRealObject.getClass().getDeclaredField(Configurations.CONTEXT_NAME).set(actorRealObject, context);
		try{			
			Field actorContextField = Utility.getFieldBasedOnType(actorRealObject, Utility.ACTOR_CONTEXT_INTERFACE,false);
			logger.log(Level.INFO," ActorContextField is "+actorContextField);
			// Also check if the Invocation Annotation is set on the ActorContext.
			actorContextField.set(actorRealObject, actorContext);
			logger.log(Level.INFO,"The ActorContext "+actorContext+" is set on the Actor Object "+actorRealObject);
		}
		catch(NullPointerException e){
			logger.log(Level.WARNING,"Class "+actorClassName+" does not contain the ActorContext  "+e);
		}
		// This is actor POJO method invocation.
		Object result = Utility.invokeMethod(actorRealObject,methodName,methodParameter,methodParamValue); 
		// Need not to set the Response Processor if there is not return type
		logger.info(" PojoActor(Class Name) : "+actorClassName+" Method Name "+methodName+" invocation results in "+result); 
		if(result instanceof Throwable){
			logger.log(Level.INFO," Response is of type Exception "+result);
			throw (Exception)result;
		}
		
		try{
			logger.info(" arg0 "+arg0);
			//arg0 is Callback for CallInvoker and MailBoxImpl for SendInvoker( Fix this) 
			arg0.processResponse((RESPONSE_TYPE)result);
		}
		catch(Exception e){
			logger.log(Level.WARNING," PActor invocation failed "+e.getMessage());
		}
	}
	
	// Getter and Setters
	public ActorContext getActorContext() {
		return actorContext;
	}
	public void setActorContext(ActorContext actorContext) {
		this.actorContext = actorContext;
	}

	public void setActorClassName(String actorClassName){
		this.actorClassName = actorClassName;
	}
	public void setMethodName(String methodName){
		this.methodName = methodName;
	}
	public Method getMethod() {
		return method;
	}
	public String getMethodName() {
		return methodName;
	}

	public void setMethodParam(Class[] methodParam) {
		this.methodParameter = methodParam;		
	}
	
	public void setMethodParamValue(Object[] methodParamValue) {
		this.methodParamValue = methodParamValue;		
	}
}