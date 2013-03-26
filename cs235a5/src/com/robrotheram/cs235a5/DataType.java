package com.robrotheram.cs235a5;

/**
 *
 * @author Robert
 * \brief Small class that defines the datatypes in a enum
 */
public enum DataType {
    
    STRING("S"),INTEGER("I"),DOUBLE("B");
    
    private String dataType;
    
    private DataType(String s) {
        dataType = s;
    }
    public String getValue() {
        return dataType;
    }
}
