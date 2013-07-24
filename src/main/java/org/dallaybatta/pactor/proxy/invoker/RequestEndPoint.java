package org.dallaybatta.pactor.proxy.invoker;

import java.io.Serializable;

import org.agilewiki.jactor2.api.Transport;

public class RequestEndPoint<RESPONSE_TYPE> implements Serializable{

	private Transport<RESPONSE_TYPE> transport;
	private Object outputData;
	private String callBackHandler;
	private String callBackInvoker;
	private Object incomingData;

	public Transport<RESPONSE_TYPE> getTransport() {
		return transport;
	}

	public void setTransport(Transport<RESPONSE_TYPE> transport) {
		this.transport = transport;
	}

	public void setOutputData(Object outputData) {
		this.outputData = outputData;
	}
	
	public Object getOutputData(){
		return outputData;
	}

	public void setCBHandler(String callBackHandler) {
		this.callBackHandler = callBackHandler;		
	}

	public String getCBHandler() {
		return callBackHandler;		
	}
	
	public void setCBInvoker(String callBackInvoker) {
		this.callBackInvoker = callBackInvoker;		
	}
	
	public String getCBInvoker(){
		return callBackInvoker;
	}

	/*
	 * The end user data that should be passed to the Actor
	 * or ActorChain for processing.
	 */
	public void setInData(Object incomingData) {
		this.incomingData = incomingData;		
	}
	
	public Object getInData(){
		return incomingData;
	}
}
