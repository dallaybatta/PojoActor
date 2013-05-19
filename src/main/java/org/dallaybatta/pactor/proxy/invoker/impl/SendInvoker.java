/**
 * 
 */
package org.dallaybatta.pactor.proxy.invoker.impl;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.agilewiki.jactor.api.Mailbox;
import org.dallaybatta.pactor.ActorContext;
import org.dallaybatta.pactor.proxy.InternalRequest;
import org.dallaybatta.pactor.proxy.interceptor.InvocationObject;
import org.dallaybatta.pactor.proxy.invoker.Invoker;
import org.dallaybatta.pactor.proxy.invoker.InvokerUtil;
import org.dallaybatta.pactor.proxy.invoker.RequestEndPoint;
import org.dallaybatta.pactor.util.Utility;

/**
 * @author vickykak
 *
 */
public class SendInvoker implements Invoker {

	private final static Logger logger = Logger.getLogger(SignalInvoker.class .getName());

	InvokerUtil iutil = new InvokerUtil();
	//private Mailbox mailbox;

	@Override
	// The <RETURN_TYPE> need not to be here although the Interface(Invoker) definition 
	// has it. Understand the rational for it.
	public <RETURN_TYPE> RETURN_TYPE invoke(InvocationObject invocationObject) throws Exception {
		
		String methodName = invocationObject.getM().getName();
		Class<?> invokeSuperClass = invocationObject.getInvokee().getClass().getSuperclass();
		// InvocationContext is not required now.
		//InvocationContext invocationConext = new InvocationContext();
		ActorContext actorContext = invocationObject.getActorContext();
		if(actorContext == null){
			iutil.createMailBox();
			try {
					actorContext = (ActorContext)Class.forName(Utility.DEFAULT_ACTORCONTEXT_IMPL).newInstance();
					//actorContext.setRequestOriginator(invocationObject.)
			} catch (InstantiationException e1) {
				e1.printStackTrace();
			} catch (IllegalAccessException e1) {
				e1.printStackTrace();
			} catch (ClassNotFoundException e1) {
				e1.printStackTrace();
			}
		}
		else{
			iutil.setMailBox(actorContext.getSharedMailBox());
		}
		
		//setMailBox(iutil.getMailBox());
		//invocationObject.setMailBox(iutil.getMailBox());
		//ir.setInvocationContext(invocationObject.getContext());
		// The ActorContext set here would be made visible in the InternalRequest::processRequest. The 
		// processRequest would set the ActorContext to the PojoActor. The PojoActor will use the ActorContext
		// to create the Actor proxies that are invoked in chain. The first Actor Proxy needs to be created
		// using the ActorBuilder, the subsequent would be created via ActorContext in the Actor Chain use case.	
		Mailbox mailbox = actorContext.getSharedMailBox();
		if(mailbox == null){
			iutil.createMailBox();
			mailbox = iutil.getMailBox();
		}
		//mailbox = (mailbox==null)?(iutil.createMailBox();iutil.getMailBox()):mailbox;
		InternalRequest ir = iutil.createRequest(invocationObject.getProceed().getGenericReturnType().getClass(),invokeSuperClass);	// Need to change the String class.
		actorContext.setSharedMailBox(mailbox);
		ir.setActorContext(actorContext);
		ir.setMethodName(methodName);
		ir.setMethodParam(invocationObject.getM().getParameterTypes());
		ir.setMethodParamValue(invocationObject.getMethodArgs());
		RequestEndPoint rep = ir.getActorContext().getRequestOriginator();
		logger.log(Level.INFO," ActorContext reference is  "+ir.getActorContext());
		logger.log(Level.INFO," RequestEndPoint is "+rep);
		// Check if this condition would be required.
		if(ir.getActorContext().getRequestOriginator() != null){
			CallBack genericCallback = (CallBack)ir.getActorContext().getRequestOriginator().getTransport();		
			if(genericCallback == null)
			{
				// The CallBack should come from the pool, need to be done.
				genericCallback = new CallBack(); 
				genericCallback.setActorClassName(invokeSuperClass.getName());
				genericCallback.setMethodName(methodName);
				genericCallback.setMethod(invocationObject.getM());
				ir.getActorContext().getRequestOriginator().setTransport(genericCallback);
			}
			try {
				// The actorClassName and methodName could be already set in the if block(Fix it)
				genericCallback.setActorClassName(invokeSuperClass.getName());
				genericCallback.setMethodName(methodName);
				genericCallback.setEndPointReference(rep);
				genericCallback.setMethod(invocationObject.getM());
				ir.send(mailbox, genericCallback);
			} catch (Exception e1) {
				logger.log(Level.SEVERE," Real send failed with exception "+e1);
				e1.printStackTrace();
				throw e1;
			}
		}
		else{			
			throw new RuntimeException("RequestEndPoint needs to be configured for the send invocation");
		}
		/*
		try {
			 ir.setMethodName(methodName);
			 ir.setActorClassName(invokeSuperClass.getName());
			 Class<?> methodParam[] = invocationObject.getProceed().getParameterTypes();
			 Object methodParamValue[] = invocationObject.getMethodArgs();
			 // Can we smartly find the RequestEndPoint in the method Parameter and not rely on the order.
			 // If the RequestEndPoint in not found then throw the Exception
			 RequestEndPoint<?> reqEndPoint = (RequestEndPoint<?>)methodParamValue[0];
			 logger.log(Level.INFO," RequestEndPoint is "+reqEndPoint);		 
			 ir.setMethodParam(methodParam);		 
			 ir.setMethodParamValue(methodParamValue);
			 CallBack cb = (CallBack) reqEndPoint.getTransport();
			 // TBD( Document properly)
			 if(cb == null){
				 //@Send(name="GenericTransport",cbtype="java.lang.String")
				 // Read the cbtype annotation for getting the type below.
				 cb = new CallBack();
				 cb.setInvocationContext(invocationConext);
				 cb.setActorClassName(invokeSuperClass.getName());
				 invocationObject.setCallBack(cb);
			 }		
			 // Will the circular dependency between the EndPointReference and Callback cause the leak, investigate this later.
			 cb.setEndPointReference(reqEndPoint);
			 reqEndPoint.setTransport(cb);		 
			 logger.log(Level.INFO,"Mailbox is "+invocationObject.getMailBox());
			 logger.log(Level.INFO,"Callback is "+(ResponseProcessor)cb);
			 // Here is the delegation of send to the Mailbox..... (Very Important to Note)
			 ir.send(invocationObject.getMailBox(), cb);
			 logger.info(Utility.HASH_CHARACTER_SET+"Done with Invocation of method "+methodName+" via proxy "+invocationObject.getInvokee().getClass());
		} catch (Exception e) {
			e.printStackTrace();
			throw new InternalProxyException(methodName+" failed with exception "+e.getMessage());
		}	
		*/
		// Ideally it should be void.		
		return null;
	}
	
	/*
	@Override
	public Mailbox getMailBox() {
		return mailbox;		
	}

	@Override
	public void setMailBox(Mailbox box) {
		this.mailbox = box;
	}
	*/
}
