package org.dallaybatta.general.messaging;

import junit.framework.TestCase;

import org.dallaybatta.pactor.ActorBuilder;
import org.dallaybatta.pactor.ActorFactory;
import org.dallaybatta.pactor.proxy.invoker.RequestEndPoint;

public class Test4 extends TestCase {
	
	public void testb() throws Exception {		
		RequestEndPoint<Object> reqEndPoint = new RequestEndPoint<Object>();
		ActorBuilder<Actor4> builder = new ActorBuilder<Actor4>(Actor4.class);
		builder.setRequestEndPoint(reqEndPoint);
		Actor4 proxy = builder.getProxy(Actor4.class);
		proxy.hi4();
		
		// This is two line, make it more simple.
		Actor4 proxy1 = (new ActorBuilder<Actor4>(Actor4.class)).getProxy(Actor4.class);
		proxy1.hi4();

	}
	
	public void testbWithActorFactory(){
		Actor4 proxy = ActorFactory.getActor(Actor4.class);
		proxy.hi5();
	}
	
	public void testABCChainInvocation(){
		ActorA proxy = ActorFactory.getActorWithREP(ActorA.class);
		proxy.a();
	}
	
	public void testABCChainInvocationWithFailAtA(){
		ActorA proxy = ActorFactory.getActorWithREP(ActorA.class);
		proxy.a1();		
	}
	
	
	public void testABCChainInvocationWithFailAtB(){
		RequestEndPoint<Object> reqEndPoint = new RequestEndPoint<Object>();
		reqEndPoint.setInData("B");
		ActorBuilder<ActorA> builder = new ActorBuilder<ActorA>(ActorA.class);
		builder.setRequestEndPoint(reqEndPoint);
		ActorA proxy = builder.getProxy(ActorA.class);
		proxy.a();		
	}
	
	public void testABCChainInvocationWithFailAtC(){
		RequestEndPoint<Object> reqEndPoint = new RequestEndPoint<Object>();
		reqEndPoint.setInData("C");
		ActorBuilder<ActorA> builder = new ActorBuilder<ActorA>(ActorA.class);
		builder.setRequestEndPoint(reqEndPoint);
		ActorA proxy = builder.getProxy(ActorA.class);
		proxy.a();		
	}
}
