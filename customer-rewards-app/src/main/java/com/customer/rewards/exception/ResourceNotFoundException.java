package com.customer.rewards.exception;

import java.io.Serial;

/**
 * Exception thrown when a requested resource is not found.
 */
public class ResourceNotFoundException extends RuntimeException {

	@Serial
	private static final long serialVersionUID = -6642533818139021006L; // For serialization compatibility

	/**
	 * Constructs a new ResourceNotFoundException with the specified detail message.
	 *
	 * @param message the detail message
	 */
	public ResourceNotFoundException(String message) {
		super(message);
	}

	/**
	 * Constructs a new ResourceNotFoundException with the specified detail message and cause.
	 *
	 * @param message the detail message
	 * @param cause   the cause of the exception
	 */
	public ResourceNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}
}
