package org.dallaybatta.general.exceptions;

import junit.framework.TestCase;
import org.dallaybatta.pactor.ActorBuilder;
import org.dallaybatta.pactor.proxy.invoker.RequestEndPoint;
import org.dallaybatta.pactor.util.Utility;

/**
 * Test code.
 */
public class Test1 extends TestCase {
	
    public void testI() throws Exception {          	
		ActorBuilder<ActorA> builder = new ActorBuilder<ActorA>(ActorA.class);
		ActorA proxy = builder.getProxy(ActorA.class);      
        try {
        	proxy.hi1();
        } catch (final SecurityException se) {
        	// Need to figure out how to get the close() operation ported.
        	//builder.getSharedMailBox().getMailboxFactory().close();
            // mailboxFactory.close();  
            return;
        }  
    }
    
    public void testHelloWithNullPointer(){
		RequestEndPoint<Object> reqEndPoint = new RequestEndPoint<Object>();
		ActorBuilder<ActorA> builder = new ActorBuilder<ActorA>(ActorA.class);
		builder.setRequestEndPoint(reqEndPoint);
		ActorA proxy = builder.getProxy(ActorA.class);  
        try {
        	proxy.helloWithNullPointer();
        } catch (final NullPointerException e) {
            return;
        }            	
    }
    
    // This is expected to fail
    public void testHelloWithRethrowRuntime(){
    	RequestEndPoint<Object> reqEndPoint = new RequestEndPoint<Object>();
		ActorBuilder<ActorA> builder = new ActorBuilder<ActorA>(ActorA.class);
		builder.setRequestEndPoint(reqEndPoint);
		ActorA proxy = builder.getProxy(ActorA.class);
		try {
        	proxy.helloWithRethrowRuntime();
        } catch (final NullPointerException e) {
            return;
        } 		
    }
    
    public void testThrowCustomException(){
		ActorBuilder<ActorA> builder = new ActorBuilder<ActorA>(ActorA.class);
		ActorA proxy = builder.getProxy(ActorA.class);      
        try {
        	proxy.throwCustomException();
        } catch (CustomException e) {
			return;
		}            	
    }

    public void testThrowCustomExceptionWithSend(){
    	RequestEndPoint<Object> reqEndPoint = new RequestEndPoint<Object>();
		ActorBuilder<ActorA> builder = new ActorBuilder<ActorA>(ActorA.class);
		builder.setRequestEndPoint(reqEndPoint);
		ActorA proxy = builder.getProxy(ActorA.class);      
        try {
        	proxy.throwCustomExceptionWithSend();
        } catch (CustomException e) {
			return;
		}            	
    }

    public void testThrowCustomExceptionChain(){
		ActorBuilder<ActorA> builder = new ActorBuilder<ActorA>(ActorA.class);
		ActorA proxy = builder.getProxy(ActorA.class);      
        try {
        	proxy.throwCustomExceptionChain();
        } catch (CustomException e) {
			return;
		}            	
    }
}
