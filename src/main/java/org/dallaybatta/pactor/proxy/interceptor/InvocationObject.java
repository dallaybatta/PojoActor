/**
 * 
 */
package org.dallaybatta.pactor.proxy.interceptor;

import java.lang.reflect.Method;
import java.util.logging.Logger;

import org.agilewiki.jactor.api.Mailbox;
import org.dallaybatta.pactor.ActorContext;
import org.dallaybatta.pactor.InvocationContext;
import org.dallaybatta.pactor.proxy.invoker.impl.CallBack;

/**
 * @author vickykak
 *
 */
public class InvocationObject {

	private final static Logger logger = Logger.getLogger(InvocationObject.class .getName());
	private Object invokee;
	private Method m;
	private Method proceed;
	private Object[] methodArgs;	
	private Object returnValue;
	private Mailbox mailbox;
	private InvocationContext invocationContext;
	private CallBack cb;
	
	public Object getReturnValue() {
		return returnValue;
	}
	public void setReturnValue(Object returnValue) {
		this.returnValue = returnValue;
	}
	public InvocationObject(Object self, Method m, Method proceed, Object[] methodArgs) {
		this.invokee = self;
		this.m = m;
		this.proceed = proceed;
		this.methodArgs = methodArgs;		
	}
	public Object getInvokee() {
		return invokee;
	}
	public void setInvokee(Object invokee) {
		this.invokee = invokee;
	}
	public Method getM() {
		return m;
	}
	public void setM(Method m) {
		this.m = m;
	}
	public Method getProceed() {
		return proceed;
	}
	public void setProceed(Method proceed) {
		this.proceed = proceed;
	}
	public Object[] getMethodArgs() {
		return methodArgs;
	}
	public void setMethodArgs(Object[] methodArgs) {
		this.methodArgs = methodArgs;
	}
	public static Logger getLogger() {
		return logger;
	}
	public Mailbox getMailBox() {
		// TODO Auto-generated method stub
		return mailbox;
	}
	public void setMailBox(Mailbox mailbox) {
		// TODO Auto-generated method stub
		this.mailbox = mailbox;
	}
	public Object getCallBack() {
		return cb;
	}
	public void setCallBack(CallBack cb) {
		this.cb = cb;		
	}
	public void setContext(InvocationContext invocationContext) {
		this.invocationContext = invocationContext;		
	}
	public InvocationContext getContext() {
		return invocationContext;
	}
	public ActorContext actorContext;
	public ActorContext getActorContext() {
		return actorContext;
	}
	public void setActorContext(ActorContext actorContext){
		this.actorContext = actorContext;
	}
}
