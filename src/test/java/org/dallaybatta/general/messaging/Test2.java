package org.dallaybatta.general.messaging;

import junit.framework.TestCase;

import org.dallaybatta.pactor.ActorBuilder;

public class Test2 extends TestCase {
	
	public void testa() throws Exception {		
		ActorBuilder<Actor2> builder = new ActorBuilder<Actor2>(Actor2.class);
		Actor2 proxy = builder.getProxy(Actor2.class);
		String result = proxy.hi2();
		assertEquals("Hello world!", result);
	}
}
