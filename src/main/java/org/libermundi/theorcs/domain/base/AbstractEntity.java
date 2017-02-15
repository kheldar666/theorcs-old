package org.libermundi.theorcs.domain.base;

import java.io.Serializable;

import javax.persistence.MappedSuperclass;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * Abstract entity with equals/hashCode is calculated based on Identifiable.getId()
 *
 */
@MappedSuperclass
public abstract class AbstractEntity<T extends Serializable> implements Identifiable<T> {
	private static final long serialVersionUID = 757533802301350140L;

	
	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getId()).toHashCode();
	}

	@Override
    public boolean equals(Object obj) {
        
        if (this == obj) {
            return true;
        }
        
        if (obj == null) {
            return false;
        }
        
        if (getClass() != obj.getClass()) {
            return false;
        }
        
        if (obj instanceof Identifiable) {
            @SuppressWarnings("unchecked")
			Identifiable<T> other = (Identifiable<T>) obj;
            return new EqualsBuilder().append(getId(), other.getId()).isEquals();        
        }

        return false;
    }

	@Override
	public String toString() {
		return "AbstractEntity [getClass()=" + getClass() + ", hashCode()="
				+ hashCode() + ", toString()=" + super.toString() + "]";
	}
}
