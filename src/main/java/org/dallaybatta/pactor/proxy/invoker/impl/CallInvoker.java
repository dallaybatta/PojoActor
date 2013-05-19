/**
 * 
 */
package org.dallaybatta.pactor.proxy.invoker.impl;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.agilewiki.jactor.api.Mailbox;
import org.dallaybatta.pactor.ActorContext;
import org.dallaybatta.pactor.InternalProxyException;
import org.dallaybatta.pactor.proxy.InternalRequest;
import org.dallaybatta.pactor.proxy.interceptor.InvocationObject;
import org.dallaybatta.pactor.proxy.invoker.Invoker;
import org.dallaybatta.pactor.proxy.invoker.InvokerUtil;
import org.dallaybatta.pactor.util.Utility;

/**
 * @author vickykak
 *
 */
public class CallInvoker implements Invoker {

private final static Logger logger = Logger.getLogger(CallInvoker.class .getName());
	
	InvokerUtil iutil = new InvokerUtil();
	
	@Override
	public <RETURN_TYPE> RETURN_TYPE invoke(InvocationObject invocationObject) throws Exception{		
		String methodName = invocationObject.getM().getName();
		Class<?> invokeSuperClass = invocationObject.getInvokee().getClass().getSuperclass();
		ActorContext actorContext = invocationObject.getActorContext();
		if(actorContext == null){
			iutil.createMailBox();
			try {
					actorContext = (ActorContext)Class.forName(Utility.DEFAULT_ACTORCONTEXT_IMPL).newInstance();				
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		else{
			iutil.setMailBox(actorContext.getSharedMailBox());
		}
		
		Mailbox mailbox = actorContext.getSharedMailBox();
		if(mailbox == null){
			iutil.createMailBox();
			mailbox = iutil.getMailBox();
		}		
		InternalRequest ir = iutil.createRequest(invocationObject.getProceed().getGenericReturnType().getClass(),invokeSuperClass);
		RETURN_TYPE RT= null;
		actorContext.setSharedMailBox(mailbox);
		ir.setActorContext(actorContext);			
		try {
			 ir.setMethodName(methodName);
			 ir.setActorClassName(invokeSuperClass.getName());
			 Class<?> methodParam[] = invocationObject.getProceed().getParameterTypes();
			 Object methodParamValue[] = invocationObject.getMethodArgs();
			 ir.setMethodParam(methodParam);
			 ir.setMethodParamValue(methodParamValue);		 
			 RT = (RETURN_TYPE) ir.call();
			 logger.log(Level.INFO," Response "+RT);
			 logger.info(Utility.HASH_CHARACTER_SET+"Done with Invocation of method "+methodName+" via proxy "+invocationObject.getInvokee().getClass());
		} catch (Exception e) {
			e.printStackTrace();
			//throw new InternalProxyException(methodName+" failed with exception "+e.getMessage());
			throw e;
		}
		logger.log(Level.INFO," CallInvoker::invoke return value is "+RT);
		return RT;
	}	
}
