/**
 * 
 */
package org.dallaybatta.pactor.proxy;

import org.dallaybatta.pactor.proxy.interceptor.InvocationObject;

/**
 * @author vickykak
 *
 *	This is the MethodInterceptor and not the Interceptor Chain, the Chain is 
 *	misnorm. It should be removed.
 */
public interface InterceptorChain {

	void intercept(InvocationObject invocationObject) throws Exception;

}
