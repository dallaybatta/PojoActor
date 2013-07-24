package org.dallaybatta.pactor;

import java.util.List;

import org.agilewiki.jactor2.api.Mailbox;
import org.dallaybatta.pactor.proxy.invoker.RequestEndPoint;

public interface ActorContext {
	/*
	 * This should create the ActorProxy
	 */
	public <ACTOR> ACTOR getActor(Class<?> pojoClass);
	public <ACTOR> ACTOR getActor(Class<?> pojoClass,ActorContext context);
	
	public void addToInvocationChain(String actorName);
	public List<String> getActorInvocationChain();
	
	public void setRequestOriginator(RequestEndPoint rep);
	public RequestEndPoint getRequestOriginator();
	
	public void setSharedMailBox(Mailbox mailbox);
	public Mailbox getSharedMailBox();
}
