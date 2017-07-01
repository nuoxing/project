package com.jyzx.model;


import org.apache.commons.lang3.builder.ToStringBuilder;


/**
 * 
 * @author LinDongHai
 *
 * @version 1.0, 2012-12-29
 */
public abstract class AbstractModel implements java.io.Serializable {
    
    private static final long serialVersionUID = 2035013017939483936L;


    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
    

}
