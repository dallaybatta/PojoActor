package org.dallaybatta.general.messaging;

import org.dallaybatta.general.exceptions.CustomException;
import org.dallaybatta.pactor.ActorContext;
import org.dallaybatta.pactor.annotation.AContext;
import org.dallaybatta.pactor.annotation.CallBack;
import org.dallaybatta.pactor.annotation.JActor;
import org.dallaybatta.pactor.annotation.Send;
import org.dallaybatta.pactor.proxy.invoker.RequestEndPoint;
import org.dallaybatta.pactor.util.Utility;

@JActor
public class ActorB {

	@AContext
	public ActorContext context;
	
	@Send(cbtype = "", name = "bcallback")
	public void b(){
		ActorC proxy = context.getActor(ActorC.class);
		String failFlag = (String) context.getRequestOriginator().getInData();
		if("B".equals(failFlag)){
			context.getRequestOriginator().setInData("FAILIED AT B");
			throw new RuntimeException("Failing at B");
		}
		proxy.c();
	}
	
	@CallBack(name = "")
	public void bcallback(RequestEndPoint rep){
		Utility.syslog(" "+rep.getInData());
	}

	public void throwCustomExceptionChain() throws CustomException{
		throw new CustomException("thrown on request");
	}
}
