package org.dallaybatta;

import org.dallaybatta.pactor.annotation.AsynCall;
import org.dallaybatta.pactor.annotation.JActor;

@JActor
public class ActorWithoutContext {
	
	@AsynCall
	public String hello(){
		return "I am without a context";
	}
}
