/**
 * 
 */
package org.dallaybatta;

import java.util.concurrent.Semaphore;
import java.util.logging.Logger;

import org.agilewiki.jactor2.api.Mailbox;
import org.agilewiki.jactor2.api.MailboxFactory;
import org.agilewiki.jactor2.api.ResponseProcessor;
import org.agilewiki.jactor2.impl.DefaultMailboxFactoryImpl;
import org.dallaybatta.pactor.InternalProxyException;
import org.dallaybatta.pactor.proxy.InternalRequest;
import org.dallaybatta.pactor.util.Utility;

/**
 * @author vickykak
 * This should be generated code, should act as the template.
 * The Proxy naming convention should be <POJO-NAME>Proxy e.g. FirstActorProxy is the proxy for FirstActor.
 * 
 */
public class FirstActorProxy extends FirstActor{
	
	private final static Logger logger = Logger.getLogger(FirstActorProxy.class .getName());
	
	private Semaphore sem = new Semaphore(0);		
	private MailboxFactory mailboxFactory = new DefaultMailboxFactoryImpl(); 
	//private Mailbox mailbox = mailboxFactory.createMailbox(false);
	private Mailbox mailbox = mailboxFactory.createThreadBoundMailbox(new Runnable() {
        @Override
        public void run() {
        	mailbox.run();
            try {
                mailboxFactory.close();
            } catch (final Throwable x) {
            }
        }
    });
	
	private Object testMethodReturnValue;
	private Object sendTestReturnValue;
	
	
	public String testStuff(){
		String methodName = "testStuff";		
		logger.info(Utility.HASH_CHARACTER_SET+"Invoking "+methodName+" via proxy of "+getClass().getSuperclass().getName()+" proxy is "+getClass());
		if(Utility.hasClassAnnotation(getClass().getSuperclass(),Utility.JACTOR_ANNOTATION_CLASS)) 
		{
			logger.info("Making a async call ");
			String actorInvocationType = "";
			if(Utility.hasMethodAnnotation(getClass().getSuperclass(), Utility.JACTOR_ASYNCALL_METHOD)){
				actorInvocationType = Utility.JACTOR_ASYNCALL_METHOD.getCanonicalName();
				logger.info("actorInvocationType is "+actorInvocationType);				
			}
			else if(Utility.hasMethodAnnotation(getClass().getSuperclass(), Utility.JACTOR_SIGNAL_METHOD)){
				actorInvocationType = Utility.JACTOR_ASYNCALL_METHOD.getCanonicalName();
				logger.info("actorInvocationType is "+actorInvocationType);
			}
			else if(Utility.hasMethodAnnotation(getClass().getSuperclass(), Utility.JACTOR_SEND_METHOD)){
				actorInvocationType = Utility.JACTOR_ASYNCALL_METHOD.getCanonicalName();
				logger.info("actorInvocationType is "+actorInvocationType);
			}
				
			InternalRequest ir = createRequest(String.class,getClass().getSuperclass());	// Need to change the String class.			
			try {
			 ir.setMethodName(methodName);
			 ir.setActorClassName(getClass().getSuperclass().getName());
			 Class methodParam[] = {};
			 Object methodParamValue[] = {};
			 ir.setMethodParam(methodParam);
			 ir.setMethodParamValue(methodParamValue);
			 // This is syn operation as it makes the client to wait till the call is over.
			 ir.send(mailbox,new ResponseProcessor<String>(){				 // Need to change <String> based on the method return type
				 public void processResponse(String responseValue){	
					 logger.info("Response with "+responseValue);
					 testMethodReturnValue = responseValue;
					 sem.release();
				 }
			 });
			 sem.acquire();
			 logger.info(Utility.HASH_CHARACTER_SET+"Done with Invocation of method "+methodName+" via proxy "+getClass());
			} catch (Exception e) {
				e.printStackTrace();
				throw new InternalProxyException(methodName+" failed with exception "+e.getMessage());
			}		
			//mailbox.shutdown();
		}
		else
		{
			logger.info("current thread "+Thread.currentThread().getId());
			logger.info("Making an OO+ call for "+ methodName);
			return super.testStuff();
		}
		return (String)testMethodReturnValue;
	}

	public void signalTest(){
		String methodName = "signalTest";		
		logger.info(Utility.HASH_CHARACTER_SET+"Invoking "+methodName+" via proxy of "+getClass().getSuperclass().getName()+" proxy is "+getClass());
		if(Utility.hasClassAnnotation(getClass().getSuperclass(),Utility.JACTOR_ANNOTATION_CLASS)) 
		{
			logger.info("Making a async call for "+methodName);
			String actorInvocationType = "";
			if(Utility.hasMethodAnnotation(getClass().getSuperclass(), Utility.JACTOR_ASYNCALL_METHOD)){
				actorInvocationType = Utility.JACTOR_ASYNCALL_METHOD.getCanonicalName();
				logger.info("actorInvocationType is "+actorInvocationType);				
			}
			else if(Utility.hasMethodAnnotation(getClass().getSuperclass(), Utility.JACTOR_SIGNAL_METHOD)){
				actorInvocationType = Utility.JACTOR_ASYNCALL_METHOD.getCanonicalName();
				logger.info("actorInvocationType is "+actorInvocationType);
			}
			else if(Utility.hasMethodAnnotation(getClass().getSuperclass(), Utility.JACTOR_SEND_METHOD)){
				actorInvocationType = Utility.JACTOR_ASYNCALL_METHOD.getCanonicalName();
				logger.info("actorInvocationType is "+actorInvocationType);
			}
				
			InternalRequest ir = createRequest(String.class,getClass().getSuperclass());	// Need to change the String class to Void			
			try {
			 ir.setMethodName(methodName);
			 ir.setActorClassName(getClass().getSuperclass().getName());
			 Class methodParam[] = {};
			 Object methodParamValue[] = {};
			 ir.setMethodParam(methodParam);
			 ir.setMethodParamValue(methodParamValue);
			 // This is syn operation as it makes the client to wait till the call is over.
			 ir.signal(mailbox);
			 logger.info(Utility.HASH_CHARACTER_SET+"Done with Invocation of method "+methodName+" via proxy "+getClass());
			} catch (Exception e) {
				e.printStackTrace();
				throw new InternalProxyException(methodName+" failed with exception "+e.getMessage());
			}		
			//mailbox.shutdown();
		}
		else
		{
			logger.info("current thread "+Thread.currentThread().getId());
			logger.info("Making an OO+ call for "+ methodName);
			super.signalTest();			
		}
	}

	public void sendTest(){
		String methodName = "sendTest";		
		logger.info(Utility.HASH_CHARACTER_SET+"Invoking "+methodName+" via proxy of "+getClass().getSuperclass().getName()+" proxy is "+getClass());
		if(Utility.hasClassAnnotation(getClass().getSuperclass(),Utility.JACTOR_ANNOTATION_CLASS)) 
		{
			logger.info("Making a async call for "+methodName);
			String actorInvocationType = "";
			if(Utility.hasMethodAnnotation(getClass().getSuperclass(), Utility.JACTOR_ASYNCALL_METHOD)){
				actorInvocationType = Utility.JACTOR_ASYNCALL_METHOD.getCanonicalName();
				logger.info("actorInvocationType is "+actorInvocationType);				
			}
			else if(Utility.hasMethodAnnotation(getClass().getSuperclass(), Utility.JACTOR_SIGNAL_METHOD)){
				actorInvocationType = Utility.JACTOR_ASYNCALL_METHOD.getCanonicalName();
				logger.info("actorInvocationType is "+actorInvocationType);
			}
			else if(Utility.hasMethodAnnotation(getClass().getSuperclass(), Utility.JACTOR_SEND_METHOD)){
				actorInvocationType = Utility.JACTOR_ASYNCALL_METHOD.getCanonicalName();
				logger.info("actorInvocationType is "+actorInvocationType);
			}
				
			InternalRequest ir = createRequest(String.class,getClass().getSuperclass());	// Need to change the String class to Void			
			try {
			 ir.setMethodName(methodName);
			 ir.setActorClassName(getClass().getSuperclass().getName());
			 Class methodParam[] = {};
			 Object methodParamValue[] = {};
			 ir.setMethodParam(methodParam);
			 ir.setMethodParamValue(methodParamValue);
			 // This is syn operation as it makes the client to wait till the call is over.
			 ir.send(mailbox,new ResponseProcessor<String>(){				 // Need to change <String> based on the method return type
				 public void processResponse(String responseValue){	
					 logger.info("Response with "+responseValue);
					 sendTestReturnValue = responseValue;					
				 }
			 });
			 logger.info(Utility.HASH_CHARACTER_SET+"Done with Invocation of method "+methodName+" via proxy "+getClass());
			} catch (Exception e) {
				e.printStackTrace();
				throw new InternalProxyException(methodName+" failed with exception "+e.getMessage());
			}		
			//mailbox.shutdown();
		}
		else
		{
			logger.info("current thread "+Thread.currentThread().getId());
			logger.info("Making an OO+ call for "+ methodName);
			super.sendTest();			
		}
		//return (String)sendTestReturnValue;
		return;
	}
	
	@SuppressWarnings("unchecked")
	public <RT,A> InternalRequest createRequest(Class RETURN_TYPE, Class ACTOR){
		//RT rt = (RT) RETURN_TYPE;
		//A actor = (A)ACTOR;
		InternalRequest<RT,A> internalRequest = new InternalRequest<RT,A>(mailbox);		
		return internalRequest;
	}

}
