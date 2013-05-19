package org.dallaybatta.pactor;

import org.dallaybatta.pactor.proxy.interceptor.InvocationObject;

/*
 * The IvocationContext instance would be passed across the Actor invocation sequence.
 * Assuming the user/application request is send to the Actor Based System, the request
 * needs to be processed by PActor1, PActor2, ... PActorN. The InvocationContext instance
 * should be passed from original Request to PActor1 then to PActor2 and so on.
 * The InvocationContext flows till the Request is served fully by the Actor System or error 
 * occurs. We should consider the following for InvocationContext 
 * - Should have a reference of InvocationObject which will further have the Mailbox.
 * - Should hold the Actor Chain information.
 * - Should support timeout in case of deadlocks appears, due to wrong application code.
 * 
 * 
 * This is no longer required and is replaced by ActorContext.
 */
public class InvocationContext {

	private InvocationObject invocation;
	private Object output;
	
	// Getters and Setters
	public InvocationObject getInvocation() {
		return invocation;
	}
	public void setInvocation(InvocationObject invocation) {
		this.invocation = invocation;
	}

	// Do we require it????
	public void setOutPutData(Object output) {
		this.output = output;
	}

	public Object getOutPutData() {
		return output;
	}
		
	public String getPojoActorTargetName() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setPojoActorTargetName(String name) {
		// TODO Auto-generated method stub
		
	}

	public void setCallBackMethodName(String string) {
		// TODO Auto-generated method stub
		
	}

	public String getCallBackMethodName() {
		// TODO Auto-generated method stub
		return null;
	}	
}
