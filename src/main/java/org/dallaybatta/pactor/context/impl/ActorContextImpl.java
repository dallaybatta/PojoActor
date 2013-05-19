package org.dallaybatta.pactor.context.impl;

import java.util.ArrayList;
import java.util.List;

import org.agilewiki.jactor.api.Mailbox;
import org.dallaybatta.pactor.ActorContext;
import org.dallaybatta.pactor.ActorFactory;
import org.dallaybatta.pactor.proxy.invoker.RequestEndPoint;

public class ActorContextImpl implements ActorContext {

	private List<String> actorInvocationChain = new ArrayList<String>();
	private RequestEndPoint requestOriginator;
	private Mailbox sharedMailBox;	

	@Override
	public List<String> getActorInvocationChain() {
		return actorInvocationChain;
	}

	@Override
	public RequestEndPoint getRequestOriginator() {
		return requestOriginator;
	}

	@Override
	public void setRequestOriginator(RequestEndPoint requestOriginator) {
		this.requestOriginator = requestOriginator;
	}

	@Override
	public void addToInvocationChain(String actorName) {
		actorInvocationChain.add(actorName);
	}

	@Override
	public Mailbox getSharedMailBox() {
		return sharedMailBox;
	}

	@Override
	public void setSharedMailBox(Mailbox sharedMailBox) {
		this.sharedMailBox = sharedMailBox;
	}

	@Override
	public <ACTOR> ACTOR getActor(Class<?> pojoClass, ActorContext context) {
		return ActorFactory.getActor(pojoClass,context);		
	}

	@Override
	public <ACTOR> ACTOR getActor(Class<?> pojoClass) {
		return getActor(pojoClass,this);
	}
	
}

