/**
 * 
 */
package org.dallaybatta.pactor.proxy.interceptor;

import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.dallaybatta.pactor.config.Configurations;
import org.dallaybatta.pactor.proxy.InterceptorChain;
import org.dallaybatta.pactor.proxy.invoker.InvokerFactory;
import org.dallaybatta.pactor.util.Utility;

/**
 * @author vickykak
 *
 */
public class DefaultInterceptorChain implements InterceptorChain {

	private final static Logger logger = Logger.getLogger(DefaultInterceptorChain.class .getName());
	
	@Override
	public void intercept(InvocationObject invocationObject) throws Exception{
		Utility.syslog("Inside the DefaultInterceptorChain invoking method "+invocationObject.getM().getName());		
		Class<?> invokeSuperClass = invocationObject.getInvokee().getClass().getSuperclass();
		Utility.syslog("Invokee's Super Class "+invokeSuperClass);
		boolean containsJActor = Utility.hasClassAnnotation(invokeSuperClass,Utility.JACTOR_ANNOTATION_CLASS);
		Utility.syslog("containsJActor "+containsJActor);
		if(containsJActor){
			String actorInvocationType = "";
			logger.log(Level.INFO," Method Name is "+invocationObject.getM().getName());
			// This needs to be fixed......
			if(Utility.hasMethodAnnotation(invokeSuperClass, Utility.JACTOR_ASYNCALL_METHOD, invocationObject.getM())){
				actorInvocationType =Utility.JACTOR_ASYNCALL_METHOD.getCanonicalName();
				logger.info("actorInvocationType is "+actorInvocationType);		
				// Check how to construct the mailbox, 1) new 2) existing 3) specific.
				// Check the mailbox present in the invocation.
				invocationObject.setReturnValue(InvokerFactory.getInstance(InvokerFactory.CALL).invoke(invocationObject));
			}
			else if(Utility.hasMethodAnnotation(invokeSuperClass, Utility.JACTOR_SIGNAL_METHOD, invocationObject.getM())){
				actorInvocationType = Utility.JACTOR_SIGNAL_METHOD.getCanonicalName();
				logger.info("actorInvocationType is "+actorInvocationType);
				// TBD, check if the return value is to be set here.
				InvokerFactory.getInstance(InvokerFactory.SIGNAL).invoke(invocationObject);
			}
			else if(Utility.hasMethodAnnotation(invokeSuperClass, Utility.JACTOR_SEND_METHOD, invocationObject.getM())){
				actorInvocationType = Utility.JACTOR_SEND_METHOD.getCanonicalName();
				logger.info("actorInvocationType is "+actorInvocationType);
				// TBD, check if the return value is to be set here.
				// Set an Transport Object.
				InvokerFactory.getInstance(InvokerFactory.SEND).invoke(invocationObject);
			}
			else
			{
				logger.log(Level.WARNING," The POJO("+invokeSuperClass+") is actorized but the method "+invocationObject.getM().getName()+" is not annotated.");
				// Generate th runtime excpetion and rethrow it. This needs to be done in this release.
			}
			logger.info("InvokerFactory is possibly loaded");
		}
		else{			
			// Should be direct call if the configuration allows it.
			if(Configurations.OO_ENABLED){
				try {
					logger.info("Making OO+ call"); 
					// This needs to be reworked ......
					invocationObject.getProceed().invoke(invocationObject.getInvokee(),invocationObject.getMethodArgs());					
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
			}
			else{
				throw new RuntimeException("Actor Not Annotated");
			}			
		}
	}

}
