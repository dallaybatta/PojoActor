package org.dallaybatta.pactor;

import org.dallaybatta.pactor.proxy.invoker.RequestEndPoint;

public class ActorFactory {
	
	public static <ACTOR> ACTOR getActor(Class<?> pojoClass) {
		ActorBuilder<ACTOR> builder = new ActorBuilder<ACTOR>(pojoClass);
		ACTOR proxy = builder.getProxy(pojoClass);
		return proxy;
	}
	
	public static <ACTOR> ACTOR getActorWithREP(Class<?> pojoClass) {
		ActorBuilder<ACTOR> builder = new ActorBuilder<ACTOR>(pojoClass);
		builder.setRequestEndPoint(new RequestEndPoint<Object>());
		ACTOR proxy = builder.getProxy(pojoClass);
		return proxy;
	}
	public static <ACTOR> ACTOR getActor(Class<?> pojoClass,ActorContext context) {
		ActorBuilder<ACTOR> builder = new ActorBuilder<ACTOR>(pojoClass);
		ACTOR proxy = builder.getProxy(pojoClass,context);
		return proxy;
	}
}
