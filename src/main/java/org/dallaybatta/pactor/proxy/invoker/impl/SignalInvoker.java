/**
 * 
 */
package org.dallaybatta.pactor.proxy.invoker.impl;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.agilewiki.jactor.api.Mailbox;
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
public class SignalInvoker implements Invoker {

	private final static Logger logger = Logger.getLogger(SignalInvoker.class .getName());
	private Mailbox mailbox;//
	// We might require the InvokerUtil pool (TBD)
	InvokerUtil iutil = new InvokerUtil();
	@Override
	public <RETURN_TYPE> RETURN_TYPE invoke(InvocationObject invocationObject) throws Exception {

		String methodName = invocationObject.getM().getName();
		Class<?> invokeSuperClass = invocationObject.getInvokee().getClass().getSuperclass();
		iutil.createMailBox();
		InternalRequest ir = iutil.createRequest(invocationObject.getProceed().getGenericReturnType().getClass(),invokeSuperClass);	
		//setMailBox(iutil.getMailBox());
		try {
			ir.setMethodName(methodName);
			ir.setActorClassName(invokeSuperClass.getName());
			Class methodParam[] = invocationObject.getProceed().getParameterTypes();
			Object methodParamValue[] = invocationObject.getMethodArgs();
			ir.setMethodParam(methodParam);
			ir.setMethodParamValue(methodParamValue);
			// This is syn operation as it makes the client to wait till the call is over.
			ir.signal();				 
			logger.info(Utility.HASH_CHARACTER_SET+"Done with Invocation of method "+methodName+" via proxy "+invocationObject.getInvokee().getClass());
		} catch (Exception e) {
			e.printStackTrace();
			logger.log(Level.SEVERE," Failure fro the SignalInvoker::invoke "+e);
			//throw new InternalProxyException(methodName+" failed with exception "+e.getMessage());
			throw e;
		}		
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
