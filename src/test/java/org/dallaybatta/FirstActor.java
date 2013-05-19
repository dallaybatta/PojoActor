/**
 * 
 */
package org.dallaybatta;

import java.util.logging.Logger;

import org.dallaybatta.pactor.annotation.AsynCall;
import org.dallaybatta.pactor.annotation.JActor;
import org.dallaybatta.pactor.annotation.Send;
import org.dallaybatta.pactor.annotation.Signal;


/**
 * @author vickykak
 *
 */
@JActor
public class FirstActor {

	private final static Logger logger = Logger.getLogger(FirstActor.class.getName());
	
	@AsynCall
	public String testStuff(){
		logger.info("Inside the testStuff");
		return "Hello World";
	}
	
	@Signal
	public void signalTest(){
		logger.info("INVOKING signalTest .........");
	}
	
	@Send(cbtype = "", name = "")
	public void sendTest(){
		logger.info("INVOKING sendTest .........");		
	}
}
