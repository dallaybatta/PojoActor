package org.dallaybatta.pactor.proxy.invoker.impl;

import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.agilewiki.jactor.api.MailboxFactory;
import org.agilewiki.jactor.api.Transport;
import org.agilewiki.jactor.impl.DefaultMailboxFactoryImpl;
import org.dallaybatta.pactor.ActorContext;
import org.dallaybatta.pactor.proxy.invoker.RequestEndPoint;
import org.dallaybatta.pactor.util.Utility;

// This is generic CallBack class.
public class CallBack<RESPONSE_TYPE> implements Transport<RESPONSE_TYPE>{

	private final static Logger logger = Logger.getLogger(CallBack.class .getName());

	private String actorClassName;
	//private InvocationContext invocationConext;
	
	// Should have reference to the ActorContext
	private ActorContext actorContext;
	
	public ActorContext getActorContext() {
		return actorContext;
	}

	public void setActorContext(ActorContext actorContext) {
		this.actorContext = actorContext;
	}

	private RequestEndPoint<?> reqEndPoint;
	@Override
	public MailboxFactory getMailboxFactory() {
		return new DefaultMailboxFactoryImpl();
	}

	public void processResponse(RESPONSE_TYPE arg0) throws Exception {
		logger.log(Level.INFO," actorClassName "+actorClassName+" methodName "+getMethodName());		
		logger.log(Level.INFO," arg0 "+arg0+" reqEndPoint "+reqEndPoint+" transport "+reqEndPoint.getTransport());
		// Set the Response on RequestEndPoint
		reqEndPoint.setOutputData(arg0);
		// From the actorClassName find the method with Callback annotation in  
		// actorClassName. The method should take the RequestEndpoint as parameter.
		Object actorRealObject = Utility.loadPlainClass(actorClassName).newInstance();
		
		Class[] methodParameter = {RequestEndPoint.class};
		Object[] methodParamValue = {reqEndPoint};
		// helloCallBack is hardCoded and will be fixed, WIP.
		String actorCBProcessingMethodName = Utility.getCallBackMethodName(actorClassName,getMethod(),Utility.CALLBACK_ANNOTATION_NAME);
		actorCBProcessingMethodName = (actorCBProcessingMethodName==null)?"":actorCBProcessingMethodName;
		if(actorCBProcessingMethodName.equals(Utility.EMPTY_STRING)){
			logger.log(Level.WARNING,"The Callback is not annotated well for processing method");
			return;
		}
		Object result = Utility.invokeMethod(actorRealObject,actorCBProcessingMethodName,methodParameter,methodParamValue);		
		if(arg0 instanceof Exception){
			// Don't know if this works.			
		}
	}

	public void setActorClassName(String actorClassName) {
		this.actorClassName = actorClassName;		
	}
	/*
	public void setInvocationContext(InvocationContext invocationConext) {
		this.invocationConext = invocationConext;
	}
	*/

	public void setEndPointReference(RequestEndPoint<?> reqEndPoint) {
		this.reqEndPoint = reqEndPoint;		
	}

	public RequestEndPoint getEndPointReference() {
		return reqEndPoint;		
	}

	@Override
	public void processException(Exception arg0) throws Exception {		
		logger.log(Level.INFO," Exception "+arg0);		
	}

	private String methodName;

	private Method method;
	
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	public String getMethodName(){
		return methodName;
	}

	public void setMethod(Method method) {
		this.method = method;
	}
	
	public Method getMethod(){
		return method;
	}
}
