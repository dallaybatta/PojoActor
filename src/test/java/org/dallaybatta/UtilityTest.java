package org.dallaybatta;

import junit.framework.TestCase;

import org.dallaybatta.general.messaging.Actor3;
import org.dallaybatta.pactor.ActorBuilder;
import org.dallaybatta.pactor.proxy.invoker.RequestEndPoint;
import org.dallaybatta.pactor.util.Utility;

public class UtilityTest extends TestCase {
	
	public void testFieldBasedOnType() throws Exception {
		Actor3 actor3 = new Actor3();
		Utility.getFieldBasedOnType(actor3, Utility.ACTOR_CONTEXT_INTERFACE,false);
	}
	
	public void testRetrieveException(){
		assertEquals(Utility.retrieveException("java.lang.SecurityException: thrown on request"),"java.lang.SecurityException");
	}
}
