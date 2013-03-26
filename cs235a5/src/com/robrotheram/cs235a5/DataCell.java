package com.robrotheram.cs235a5;

import java.util.regex.Pattern;

/**
 *
 * @author Robert
 */
public class DataCell {
    
    public DataCell(String input){
       if(SetData(input)){
           //add toast here
       }
    }
    
    public boolean SetData(String input){
        if(Pattern.matches(DOUBLEPATTERN, input)){
            m_Double = Double.parseDouble(input);
            m_String = input;
            m_DataType = DataType.DOUBLE;
            return true;
       }else if(Pattern.matches(INTPATTERN, input)){
           m_Integer = Integer.parseInt(input);
           m_String = input;
           m_DataType = DataType.INTEGER;
            return true;
       }else if(Pattern.matches(STRINGPATTERN, input)) {
           m_String = input;
           m_DataType = DataType.STRING;
            return true;
       }else{
    	   System.err.println("fail ajdnaldnasldnasd aksjd aosdn;skda'dlkasdalmdl;ald;smdaskdlkasndpoaskdja fuw ajsjsisdb ");
           return false;
       }
        
    }
    
    
    public int GetInteger(){
        return m_Integer;
    }
    
    public double GetDouble(){
        return m_Double;  
    }
    
   
    public String GetString(){
        return m_String;  
    }
    public DataType GetDataType(){
        
        return m_DataType;
    }
    
    @Override
    public String toString(){
        return m_String;
    }
    
    
    
    
    private String m_String;
    private Integer m_Integer;
    private Double m_Double;
    private DataType m_DataType;
    private final String DOUBLEPATTERN = "([0-9]*)\\.([0-9]*)";  
    private final String INTPATTERN = "([0-9]*)";
    private final String STRINGPATTERN = "/^[a-zA-Z]+$/";
    
    
}
