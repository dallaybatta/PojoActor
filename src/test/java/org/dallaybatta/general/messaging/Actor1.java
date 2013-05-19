package org.dallaybatta.general.messaging;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.dallaybatta.pactor.ActorBuilder;
import org.dallaybatta.pactor.ActorContext;
import org.dallaybatta.pactor.annotation.AContext;
import org.dallaybatta.pactor.annotation.AsynCall;
import org.dallaybatta.pactor.annotation.JActor;
import org.dallaybatta.pactor.annotation.Send;
import org.dallaybatta.pactor.proxy.invoker.RequestEndPoint;

@JActor
public class Actor1 {
	
	private final static Logger logger = Logger.getLogger(Actor1.class .getName());
	
	@AContext
	public ActorContext context;
	
	@AsynCall
	public String hi1(){
		return "Hello world!";
	}
	
	// The annotated Send must have first method parameter of type RequestEndPoint
	@Send(name="GenericTransport",cbtype="java.lang.String")
	public void requestChain(String chain){
		chain = chain + getClass().getName();
		ActorBuilder<Actor2> builder = new ActorBuilder<Actor2>(Actor2.class);
		Actor2 proxy = builder.getProxy(Actor2.class);
		proxy.requestChain(chain); 
	}

	@Send(cbtype = "", name = "")
	public void requestChain(RequestEndPoint<?> reqEndPoint, String chain) {
		chain = chain + getClass().getName();
		logger.log(Level.INFO," chain is "+chain+" from "+getClass().getName()+" context "+context);
		Actor2 proxy = context.getActor(Actor2.class,context);
		proxy.requestChain(reqEndPoint,chain);
		/*
		ActorBuilder<Actor2> builder = new ActorBuilder<Actor2>(Actor2.class);
		Actor2 proxy = builder.getProxy(Actor2.class);		
		proxy.requestChain(reqEndPoint,chain); 		
		*/
		
	}	 
}
