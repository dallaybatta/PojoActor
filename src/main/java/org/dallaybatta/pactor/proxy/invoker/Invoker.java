package org.dallaybatta.pactor.proxy.invoker;

import org.dallaybatta.pactor.proxy.interceptor.InvocationObject;

public interface Invoker {	
	//public void setMailBox(Mailbox box);
	//public Mailbox getMailBox();
	public <RETURN_TYPE> RETURN_TYPE invoke(InvocationObject invocationObject) throws Exception;
	
}
