package org.dallaybatta.general.exceptions;

import junit.framework.TestCase;
import org.dallaybatta.pactor.ActorBuilder;
import org.dallaybatta.pactor.proxy.invoker.RequestEndPoint;
import org.dallaybatta.pactor.util.Utility;

/**
 * Test code.
 */
public class BasicExceptionTest extends TestCase {
	
	// This test is not valid as the signal are pure asyn invocations.
	// The signal should not have the return type.
    public void testSignal() throws Exception {          	
		ActorBuilder<ExceptionA> builder = new ActorBuilder<ExceptionA>(ExceptionA.class);
		ExceptionA proxy = builder.getProxy(ExceptionA.class);      		
        try {
        	proxy.throwExceptioWithSignal();
        } catch (SecurityException e) {
            return;
        }         
    }
    
    public void testCall() throws Exception {          	
		ActorBuilder<ExceptionA> builder = new ActorBuilder<ExceptionA>(ExceptionA.class);
		ExceptionA proxy = builder.getProxy(ExceptionA.class);      
        try {
        	proxy.throwExceptioWithCall();
        } catch (CustomException se) {
            return;
        }  
    }
    
    public void testSend() throws Exception {
    	RequestEndPoint<Object> reqEndPoint = new RequestEndPoint<Object>();
		ActorBuilder<ExceptionA> builder = new ActorBuilder<ExceptionA>(ExceptionA.class);
		builder.setRequestEndPoint(reqEndPoint);
		ExceptionA proxy = builder.getProxy(ExceptionA.class);      		
        try {
        	proxy.throwExceptioWithSend();
        } catch (SecurityException e) {
        	// Need to figure out how to get the close() operation ported.
        	//builder.getSharedMailBox().getMailboxFactory().close();
            // mailboxFactory.close();  
            return;
        }  
    }
 }
