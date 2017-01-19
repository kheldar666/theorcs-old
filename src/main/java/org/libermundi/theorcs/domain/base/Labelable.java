package org.libermundi.theorcs.domain.base;

public interface Labelable {
	/**
	 * Used as an alternative to the {@link Object#toString()} method when
	 * it comes to render this object in a GUI 
	 * @return a 'human readable' label
	 */
	String printLabel();
}
