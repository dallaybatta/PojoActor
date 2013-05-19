package org.dallaybatta.general.exceptions;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.dallaybatta.general.messaging.ActorB;
import org.dallaybatta.pactor.ActorBuilder;
import org.dallaybatta.pactor.ActorContext;
import org.dallaybatta.pactor.annotation.AContext;
import org.dallaybatta.pactor.annotation.AsynCall;
import org.dallaybatta.pactor.annotation.JActor;
import org.dallaybatta.pactor.annotation.Send;

@JActor
public class ActorA {
	
	private final static Logger logger = Logger.getLogger(ActorA.class .getName());
	@AContext
	public ActorContext context;
	
	@AsynCall
	public void hi1(){
		throw new SecurityException("thrown on request");
	}
	
	@AsynCall
	public void throwCustomException() throws CustomException{
		throw new CustomException("thrown on request");
	}
	
	@Send(cbtype = "", name = "")
	public void throwCustomExceptionWithSend() throws CustomException{
		throw new CustomException("thrown on request");
	}
	
	@AsynCall
	public void throwCustomExceptionChain() throws CustomException{
		ActorB proxy = context.getActor(ActorB.class);
		proxy.throwCustomExceptionChain();
	}
	
	@Send(cbtype = "", name = "")
	public void helloWithNullPointer(){
		String test = null;
		test.toLowerCase();
		/*
		try{
			test.toLowerCase();
		}
		catch(Exception e){
			logger.log(Level.SEVERE,"Failed with exception "+e);
			throw new RuntimeException("Test");	
		}
		*/		
	}
	
	@Send(cbtype = "", name = "")
	public void helloWithRethrowRuntime(){
		String test = null;		
		try{
			test.toLowerCase();
		}
		catch(Exception e){
			logger.log(Level.SEVERE,"Failed with exception "+e);
			throw new CustomRuntime("Test");	
		}		
	}
}
