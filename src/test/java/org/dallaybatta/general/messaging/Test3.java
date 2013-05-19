package org.dallaybatta.general.messaging;

import junit.framework.TestCase;

import org.dallaybatta.pactor.ActorBuilder;
import org.dallaybatta.pactor.proxy.invoker.RequestEndPoint;

public class Test3 extends TestCase {
	
	public void testb() throws Exception {		
		ActorBuilder<Actor3> builder = new ActorBuilder<Actor3>(Actor3.class);
		Actor3 proxy = builder.getProxy(Actor3.class);
		proxy.hi3();		
	}
	
	public void testChain() throws Exception {		
		RequestEndPoint<Object> reqEndPoint = new RequestEndPoint<Object>();
		ActorBuilder<Actor1> builder = new ActorBuilder<Actor1>(Actor1.class);
		builder.setRequestEndPoint(reqEndPoint);
		Actor1 proxy = builder.getProxy(Actor1.class);		
		String chain = "";
		proxy.requestChain(reqEndPoint,chain);
	}
	
	public void testInvocationContextSet() throws Exception {		
		RequestEndPoint<Object> reqEndPoint = new RequestEndPoint<Object>();
		ActorBuilder<Actor3> builder = new ActorBuilder<Actor3>(Actor3.class);
		builder.setRequestEndPoint(reqEndPoint);
		Actor3 proxy = builder.getProxy(Actor3.class);
		String chain = "1244";
		proxy.requestChain(reqEndPoint,chain);		
	}
	
	public void testChainWithInvocation() throws Exception {		
		RequestEndPoint<Object> reqEndPoint = new RequestEndPoint<Object>();
		ActorBuilder<Actor1> builder = new ActorBuilder<Actor1>(Actor1.class);
		builder.setRequestEndPoint(reqEndPoint);
		Actor1 proxy = builder.getProxy(Actor1.class);
		String chain = "1234444";
		proxy.requestChain(reqEndPoint,chain);		
	}

	// WIP
	public void testChainWithTransport() throws Exception {		
		RequestEndPoint<Object> reqEndPoint = new RequestEndPoint<Object>();		
		ActorBuilder<Actor1> builder = new ActorBuilder<Actor1>(Actor1.class);
		builder.setRequestEndPoint(reqEndPoint);
		Actor1 proxy = builder.getProxy(Actor1.class);		
		String chain = "";
		proxy.requestChain(reqEndPoint,chain);
	}
	
	public void testHello() throws Exception {		
		RequestEndPoint<Object> reqEndPoint = new RequestEndPoint<Object>();
		ActorBuilder<Actor3> builder = new ActorBuilder<Actor3>(Actor3.class);
		builder.setRequestEndPoint(reqEndPoint);
		Actor3 proxy = builder.getProxy(Actor3.class);				
		proxy.hello();
	}
	
	public void testHelloWithException() throws Exception {		
		RequestEndPoint<Object> reqEndPoint = new RequestEndPoint<Object>();
		ActorBuilder<Actor3> builder = new ActorBuilder<Actor3>(Actor3.class);
		builder.setRequestEndPoint(reqEndPoint);
		Actor3 proxy = builder.getProxy(Actor3.class);				
		proxy.helloWithException();
	}
}


