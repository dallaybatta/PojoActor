package org.dallaybatta.pactor.proxy.invoker;

import org.agilewiki.jactor.api.Mailbox;
import org.agilewiki.jactor.api.MailboxFactory;
import org.agilewiki.jactor.impl.DefaultMailboxFactoryImpl;
import org.dallaybatta.pactor.proxy.InternalRequest;

public class InvokerUtil {

	private MailboxFactory mailboxFactory = new DefaultMailboxFactoryImpl(); 
	//private Mailbox mailbox = mailboxFactory.createMailbox(false);
	
	private Mailbox mailbox = null;
		/*
		mailboxFactory.createThreadBoundMailbox(new Runnable() {
        @Override
        public void run() {
        	mailbox.run();       
            try {
                mailboxFactory.close();
            } catch (final Throwable x) {
            	x.printStackTrace();
            }            
        }
    	});
		*/
    	
	@SuppressWarnings("unchecked")
	public  <RT,A>  InternalRequest createRequest(Class RETURN_TYPE, Class ACTOR){
		RT r = (RT)RETURN_TYPE;
		A a = (A)ACTOR;
		InternalRequest<RT,A> internalRequest = new InternalRequest<RT,A>(mailbox);		
		internalRequest.setActorClassName(ACTOR.getCanonicalName());
		return internalRequest;
	}
	
	public Mailbox getMailBox(){
		return mailbox;
	}
	
	public void setMailBox(Mailbox mailbox){
		this.mailbox = mailbox;
	}
	
	public void createMailBox(){
		mailbox = mailboxFactory.createThreadBoundMailbox(new Runnable() {
	        @Override
	        public void run() {
	        	mailbox.run();       
	            try {
	                mailboxFactory.close();
	            } catch (final Throwable x) {
	            	x.printStackTrace();
	            }            
	        }
	    });
	}
}
