package org.dallaybatta.general.messaging;

import junit.framework.TestCase;

import org.dallaybatta.pactor.ActorBuilder;

public class Test1 extends TestCase {
	
	public void testa() throws Exception {
		ActorBuilder<Actor1> builder = new ActorBuilder<Actor1>(Actor1.class);
		Actor1 proxy = builder.getProxy(Actor1.class);
		String result = proxy.hi1();
		assertEquals("Hello world!", result);
	}
}
