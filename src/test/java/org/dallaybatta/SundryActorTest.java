/**
 * 
 */
package org.dallaybatta;

import java.util.logging.Logger;

import org.dallaybatta.pactor.ActorBuilder;
import org.dallaybatta.pactor.util.Utility;

import junit.framework.TestCase;

/**
 * @author vickykak
 *
 */
public class SundryActorTest extends  TestCase {
	
	private final static Logger logger = Logger.getLogger(SundryActorTest.class .getName());
	
	public void testActorWithoutActorContext(){
		ActorBuilder<ActorWithoutContext> builder = new ActorBuilder<ActorWithoutContext>(ActorWithoutContext.class);
		ActorWithoutContext proxy = builder.getProxy(ActorWithoutContext.class);
		assertEquals(proxy.hello(),"I am without a context");
	}	
	
	public void testActorWithActorContext(){
		ActorBuilder<ActorWithContext> builder = new ActorBuilder<ActorWithContext>(ActorWithContext.class);
		ActorWithContext proxy = builder.getProxy(ActorWithContext.class);
		proxy.displayContextInfo();
	}	
	
	public void testActorWithOutJActorAnnotation(){
		ActorBuilder<ActorWithOutJActorAnnotation> builder = new ActorBuilder<ActorWithOutJActorAnnotation>(ActorWithOutJActorAnnotation.class);
		ActorWithOutJActorAnnotation proxy = builder.getProxy(ActorWithOutJActorAnnotation.class);
		proxy.displayContextInfo();
	}	
}
