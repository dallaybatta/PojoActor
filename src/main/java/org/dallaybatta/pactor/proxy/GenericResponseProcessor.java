package org.dallaybatta.pactor.proxy;

import java.util.concurrent.Semaphore;
import java.util.logging.Logger;

import org.agilewiki.jactor.api.ResponseProcessor;

// I don't think this is required,need to check and then delete....
public class GenericResponseProcessor<RESPONSE_TYPE> implements ResponseProcessor<RESPONSE_TYPE>{
	
	private final static Logger logger = Logger.getLogger(GenericResponseProcessor.class .getName());
	
	private Semaphore semaphore;
	
	public Semaphore getSemaphore() {
		return semaphore;
	}

	public void setSemaphore(Semaphore semaphore) {
		this.semaphore = semaphore;
	}

	public RESPONSE_TYPE getMethodReturnValue() {
		return methodReturnValue;
	}

	public void setMethodReturnValue(RESPONSE_TYPE methodReturnValue) {
		this.methodReturnValue = methodReturnValue;
	}

	private RESPONSE_TYPE methodReturnValue;
	
	public void processResponse(RESPONSE_TYPE response) throws Exception {
		logger.info("Response with "+response);
		methodReturnValue = response;
		getSemaphore().release();
	}	
}