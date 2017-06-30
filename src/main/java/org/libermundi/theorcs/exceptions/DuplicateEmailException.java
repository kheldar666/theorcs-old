package org.libermundi.theorcs.exceptions;

public class DuplicateEmailException extends Exception {

	private static final long serialVersionUID = 6556402290667194062L;

	public DuplicateEmailException(String message) {
        super(message);
    }
}