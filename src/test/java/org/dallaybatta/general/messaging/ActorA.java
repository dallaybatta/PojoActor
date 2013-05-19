package org.dallaybatta.general.messaging;

import org.dallaybatta.pactor.ActorContext;
import org.dallaybatta.pactor.annotation.CallBack;
import org.dallaybatta.pactor.annotation.JActor;
import org.dallaybatta.pactor.annotation.AContext;
import org.dallaybatta.pactor.annotation.Send;
import org.dallaybatta.pactor.proxy.invoker.RequestEndPoint;
import org.dallaybatta.pactor.util.Utility;

@JActor
public class ActorA {

	@AContext
	public ActorContext context;
	
	@Send(cbtype = "", name = "")
	public void a(){
		ActorB proxy = context.getActor(ActorB.class);		
		proxy.b();
	}
	
	@Send(cbtype = "", name = "a1Callback")
	public void a1(){
		ActorB proxy = context.getActor(ActorB.class);	
		if(context.getRequestOriginator().getInData() == null)
			throw new RuntimeException("Failing at A");
		proxy.b();
	}
	
	@CallBack(name = "")
	public void a1Callback(RequestEndPoint rep){
		Utility.syslog("rep "+rep+" output data "+rep.getOutputData());
	}
}
