package org.dallaybatta.general.messaging;

import org.dallaybatta.pactor.ActorContext;
import org.dallaybatta.pactor.annotation.AContext;
import org.dallaybatta.pactor.annotation.AsynCall;
import org.dallaybatta.pactor.annotation.CallBack;
import org.dallaybatta.pactor.annotation.JActor;
import org.dallaybatta.pactor.annotation.Send;
import org.dallaybatta.pactor.proxy.invoker.RequestEndPoint;
import org.dallaybatta.pactor.util.Utility;

@JActor
public class Actor3 {
			
	@AContext
	public ActorContext context;
	
	@AsynCall
	public void hi3(){
		System.out.println("Hello World");
	}
	
	// The annotated Send must have first method parameter of type RequestEndPoint
	@Send(name="GenericTransport",cbtype="java.lang.String")
	public void requestChain(String chain){	
		System.out.println(Utility.DEFAULT_ACTORCONTEXT_IMPL+" is "+context);
		chain = chain + getClass().getName();
		System.out.println("chain "+" data is : "+chain);
		// Finally set the output data in the transport, this needs to be done.
		//context.setOutPutData(chain);
		//context.setPojoActorTargetName(getClass().getSimpleName());
		//context.setCallBackMethodName("requestChain");
	}	
		
	@CallBack(name="requestChain")
	public void processResponse(Object outputData){
		String output = (String)outputData;
		System.out.println(output);
	}
	
	@Send(cbtype = "", name = "requestChainCallBack")
	public String requestChain(RequestEndPoint<?> reqEndPoint, String chain) {
		System.out.println(Utility.DEFAULT_ACTORCONTEXT_IMPL+" is "+context);
		chain = chain + this.getClass().getName();
		System.out.println("chain "+" data is : "+chain);
		reqEndPoint.setOutputData(chain);
		reqEndPoint.setCBHandler("org.dallaybatta.general.messaging.Actor3");
		reqEndPoint.setCBInvoker("requestChain");
		return chain;
	}
	
	@Send(cbtype = "", name = "helloCallBackNew")
	public String hello(){		
		System.out.println("Hello");
		return "hello world";
	}

	@Send(cbtype = "", name = "helloCallBackWithException")
	public String helloWithException(){
		throw new RuntimeException("helloWorldException");
	}

	@CallBack(name = "helloCallBack")
	public void helloCallBack(RequestEndPoint rep){
		// Here you should send the data back to the RequestOriginator and end the 
		// aysn call.
		String results = (String)rep.getOutputData();
		Utility.syslog("Going to send the results "+results+" to RequestEndPoint");
	}
	
	@CallBack(name = "helloCallBackNew")
	public void helloCallBackNew(RequestEndPoint rep){
		String results = (String)rep.getOutputData();
		Utility.syslog("Going to send the results "+results+" to RequestEndPoint using new callback");
	}
	
	// This doesn't work, should be considered/evaulated in next release.
	@CallBack(name = "helloCallBackNew1")
	public void helloCallBackNew1(RequestEndPoint rep){
		String results = (String)context.getRequestOriginator().getOutputData();
		Utility.syslog("Going to send the results "+results+" to RequestEndPoint using new callback1");
	}
	
	@CallBack(name = "requestChainCallBack")
	public void requestChainCallBack(RequestEndPoint rep){
		Utility.syslog(" requestChainCallBack output data "+rep.getOutputData());
	}
}
