/**
 * 
 */
package org.dallaybatta.pactor.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


/**
 * JActor annotation will mark the POJO to be annotated. This annotation must
 * be present on the POJO for ActorBuilder to Actorize a POJO.  
 * @author vickykak
 * 
 */

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface JActor {

}
