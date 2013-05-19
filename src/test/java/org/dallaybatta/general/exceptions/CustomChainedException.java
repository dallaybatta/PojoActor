package org.dallaybatta.general.exceptions;

public class CustomChainedException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CustomChainedException(){
		
	}
	
	public CustomChainedException(String message){
		super(message);
	}
}
