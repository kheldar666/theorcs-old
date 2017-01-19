// [LICENCE-HEADER]
//
package org.libermundi.theorcs.domain.base;

import java.util.Date;

/**
 * Interface marks class which can be historical 
 *
 */
public interface Timestampable {
    
    /**
     * Property which represents createdDate.
     */
    static final String PROP_CREATED_DATE = "createdDate";
    
    /**
     * Property which represents modifiedDate.
     */
    static final String PROP_MODIFIED_DATE = "modifiedDate";
    

    
    Date getCreatedDate();
    
    void setCreatedDate(Date createdDate);
    
    Date getModifiedDate();
    
    void setModifiedDate(Date modifiedDate);

}
