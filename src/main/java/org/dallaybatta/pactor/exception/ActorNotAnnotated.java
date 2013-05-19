/**
 * 
 */
package org.dallaybatta.pactor.exception;

/**
 * @author vickykak
 *
 */
public class ActorNotAnnotated extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ActorNotAnnotated(String pojoNotActorized) {
		super(pojoNotActorized);
	}

}
