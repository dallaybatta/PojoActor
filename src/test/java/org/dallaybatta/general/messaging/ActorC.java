package org.dallaybatta.general.messaging;

import org.dallaybatta.pactor.ActorContext;
import org.dallaybatta.pactor.annotation.AContext;
import org.dallaybatta.pactor.annotation.CallBack;
import org.dallaybatta.pactor.annotation.JActor;
import org.dallaybatta.pactor.annotation.Send;
import org.dallaybatta.pactor.proxy.invoker.RequestEndPoint;
import org.dallaybatta.pactor.util.Utility;
@JActor
public class ActorC {

	@AContext
	public ActorContext context;
	
	@Send(cbtype = "", name = "callBackToc")
	public void c() {
		String failFlag = (String) context.getRequestOriginator().getInData();
		if("C".equals(failFlag)){
			context.getRequestOriginator().setInData("FAILIED AT C");
			throw new RuntimeException("Failing at C");
		}
	}
	
	@CallBack(name = "")
	public void callBackToc(RequestEndPoint rep){
		Utility.syslog(" "+(rep.getOutputData()==null));
		Utility.syslog(" "+(rep.getInData()));
	}

}
