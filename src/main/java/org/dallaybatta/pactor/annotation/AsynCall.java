/**
 * 
 */
package org.dallaybatta.pactor.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.dallaybatta.pactor.config.Configurations;

/**
 * This annotation will be used at the POJO method to identify if the 
 * signal/send/call should be done for a specific method.
 * The AyncCall is pointing to the blocking Call.
 * @author vickykak
 *
 */

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AsynCall {
	 String mailbox() default Configurations.EXISTING;
}
