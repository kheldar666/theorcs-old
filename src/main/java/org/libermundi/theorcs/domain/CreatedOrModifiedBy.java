package org.libermundi.theorcs.domain;


public interface CreatedOrModifiedBy {
    /**
     * Property which represents createdBy.
     */
    String PROP_CREATED_BY = "createdBy";
    
    /**
     * Property which represents modifiedBy.
     */
    String PROP_MODIFIED_BY = "modifiedBy";
    
    String getCreatedBy();
    
    void setCreatedBy(String createdBy);
    
    String getModifiedBy();
    
    void setModifiedBy(String modifiedBy);    
}
