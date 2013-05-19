package org.dallaybatta.general.exceptions;

import org.dallaybatta.pactor.ActorContext;
import org.dallaybatta.pactor.annotation.AContext;
import org.dallaybatta.pactor.annotation.AsynCall;
import org.dallaybatta.pactor.annotation.JActor;
import org.dallaybatta.pactor.annotation.Send;

@JActor
public class ExceptionB {
	
	@AContext
	public ActorContext context;
	
	@AsynCall
	public void throwExceptioWithCallChain() throws CustomChainedException {
		throw new CustomChainedException("Chained Exception");
	}
	
	@Send(cbtype = "", name = "")
	public void throwExceptioWithSendChain() throws CustomChainedException {
		throw new CustomChainedException("Send Exception");		
	}

}
