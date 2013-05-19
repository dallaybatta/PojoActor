package org.dallaybatta;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.dallaybatta.pactor.ActorContext;
import org.dallaybatta.pactor.annotation.AContext;
import org.dallaybatta.pactor.annotation.AsynCall;
import org.dallaybatta.pactor.annotation.JActor;

@JActor
public class ActorWithContext {
	
	private final static Logger logger = Logger.getLogger(ActorWithContext.class .getName());
	
	@AContext
	public ActorContext context;
	
	@AsynCall
	public void displayContextInfo(){
		logger.log(Level.INFO," Context is "+context);
	}
}
