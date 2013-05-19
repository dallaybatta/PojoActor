package org.dallaybatta.general.messaging;

import org.dallaybatta.pactor.ActorBuilder;
import org.dallaybatta.pactor.ActorContext;
import org.dallaybatta.pactor.ActorFactory;
import org.dallaybatta.pactor.annotation.AContext;
import org.dallaybatta.pactor.annotation.AsynCall;
import org.dallaybatta.pactor.annotation.JActor;

@JActor
public class Actor4 {
	
	public ActorContext context;
	
	@AsynCall
	public void hi4(){
		ActorBuilder<Actor1> builder = new ActorBuilder<Actor1>(Actor1.class);
		Actor1 proxy = builder.getProxy(Actor1.class);
		proxy.hi1();
	}
	
	@AsynCall
	public void hi5(){
		Actor1 proxy = ActorFactory.getActor(Actor1.class);
		proxy.hi1();
	}
}
