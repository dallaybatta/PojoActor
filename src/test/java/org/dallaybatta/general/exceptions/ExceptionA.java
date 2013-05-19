package org.dallaybatta.general.exceptions;

import org.dallaybatta.general.messaging.ActorB;
import org.dallaybatta.pactor.ActorContext;
import org.dallaybatta.pactor.annotation.AContext;
import org.dallaybatta.pactor.annotation.AsynCall;
import org.dallaybatta.pactor.annotation.CallBack;
import org.dallaybatta.pactor.annotation.JActor;
import org.dallaybatta.pactor.annotation.Send;
import org.dallaybatta.pactor.annotation.Signal;
import org.dallaybatta.pactor.proxy.invoker.RequestEndPoint;
import org.dallaybatta.pactor.util.Utility;

@JActor
public class ExceptionA {
	
	@AContext
	public ActorContext context;
	
	// Signal use case is wrong, it can't have a return type and is always void. 
	// We may think of passing the RequestEndPoint to the Signal like Send. ( TBD)
	@Signal
	public void throwExceptioWithSignal() throws CustomException {
		throw new CustomException("Signal Exception");
	}

	@AsynCall
	public void throwExceptioWithCall() throws CustomException {
		throw new CustomException("Call Exception");
		
	}
	
	@Send(cbtype = "", name = "sendExceptionHandler")
	public void throwExceptioWithSend() throws CustomException {
		throw new CustomException("Send Exception");		
	}

	// This doesn't get called.
	@CallBack(name = "")
	public void sendExceptionHandler(RequestEndPoint rep){
		Utility.syslog(" ------ "+rep.getOutputData());
	}

	@AsynCall
	public void throwExceptioWithCallChain() throws CustomException {
		try{
			ExceptionB proxy = context.getActor(ExceptionB.class);		
			proxy.throwExceptioWithCallChain();
		}
		catch(CustomChainedException e){
			throw new CustomException("Chained Call Exception");
		}
	}
	
	@Send(cbtype = "", name = "")
	public void throwExceptioWithSendChain() throws CustomException{
		try{
			ExceptionB proxy = context.getActor(ExceptionB.class);		
			proxy.throwExceptioWithSendChain();
		}
		catch(CustomChainedException e){
			throw new CustomException("Chained Call Exception");
		}	
	}
}
// Guidelines
// 1) If there is a Send method in the POJO then there should be ActorContext too.
