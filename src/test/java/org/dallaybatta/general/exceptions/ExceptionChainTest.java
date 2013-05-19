package org.dallaybatta.general.exceptions;

import junit.framework.TestCase;
import org.dallaybatta.pactor.ActorBuilder;
import org.dallaybatta.pactor.proxy.invoker.RequestEndPoint;
import org.dallaybatta.pactor.util.Utility;

/**
 * Test code, this needs to be worked yet.
 */
public class ExceptionChainTest extends TestCase {
	
    
	// This needs to be fixed.
	/*
    public void testCallChain() {          	
		ActorBuilder<ExceptionA> builder = new ActorBuilder<ExceptionA>(ExceptionA.class);
		ExceptionA proxy = builder.getProxy(ExceptionA.class);      
        try {
        	proxy.throwExceptioWithCallChain();
        } catch (CustomException e) {
        	//Utility.syslog(" "+e);
            return;
        }  
    }
    */
	
    public void testSendChain() throws Exception {
    	RequestEndPoint<Object> reqEndPoint = new RequestEndPoint<Object>();
		ActorBuilder<ExceptionA> builder = new ActorBuilder<ExceptionA>(ExceptionA.class);
		builder.setRequestEndPoint(reqEndPoint);
		ExceptionA proxy = builder.getProxy(ExceptionA.class);      		
        try {
        	proxy.throwExceptioWithSendChain();
        } catch (CustomException e) {
            return;
        }   
    }
 }
