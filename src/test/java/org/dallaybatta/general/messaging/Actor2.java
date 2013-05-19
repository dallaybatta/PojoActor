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
public class Actor2 {
	
	private final static Logger logger = Logger.getLogger(Actor2.class .getName());

	@AContext
	public ActorContext context;
	
	@AsynCall
	public String hi2(){
		ActorBuilder<Actor1> builder = new ActorBuilder<Actor1>(Actor1.class);
		Actor1 proxy = builder.getProxy(Actor1.class);
		String result = proxy.hi1();
		return result;
	}
	
	// The annotated Send must have first method parameter of type RequestEndPoint
	@Send(name="GenericTransport",cbtype="java.lang.String")
	public void requestChain(String chain){
		chain = chain + this.getClass().getName();
		ActorBuilder<Actor3> builder = new ActorBuilder<Actor3>(Actor3.class);
		Actor3 proxy = builder.getProxy(Actor3.class);
		proxy.requestChain(chain);
	}
	
	@Send(cbtype = "", name = "")
	public void requestChain(RequestEndPoint<?> reqEndPoint, String chain) {
		logger.log(Level.INFO," chain is "+chain+" from "+getClass().getName()+" context "+context);
		chain = chain + getClass().getName();		
		Actor3 proxy = context.getActor(Actor3.class,context);
		/*
		ActorBuilder<Actor3> builder = new ActorBuilder<Actor3>(Actor3.class);
		Actor3 proxy = builder.getProxy(Actor3.class);
		*/
		proxy.requestChain(reqEndPoint,chain);
	}	
}
