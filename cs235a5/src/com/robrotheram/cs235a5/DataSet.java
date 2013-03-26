package com.robrotheram.cs235a5;
/**
 * The MS_DataSet Class stores a structure of the entire data 
 * @author Robert
 */
public class DataSet {
   
    /**
     * Creates the 2 dimensional array of DataCells and sets the Width
     * and Height
     * @param int width
     * @param int height
     * @return boolean true if set correctly 
     */
    
    public boolean SetDataSet(int width, int height){
        m_numCols = width;
        m_numRows = height;
        m_dataSet = new DataCell [height][width];
        return true;
    }
    /**
     * Set the Column names in this class
     * @param String[] colNames 
     * @return boolean true
     * 
     */
     
    public boolean SetHeader(String[] colNames ){
        m_Header = colNames;
        return true;
    }
    /**
     * sets in the data array a MS_DataAtributs at position x ,y 
     * @param setDataCell mDA the cell of data
     * @param int x:  Position x in the 2 dimensional array;
     * @param int y:  Position y in the 2 dimensional array;
     * @return Boolean true
     */
    public boolean SetDataCell(DataCell mDA, int x, int y){
        m_dataSet[y][x] = mDA;
        return true;
    }
    /**
     * set the entire dataset
     * @param DataCell[][] the representation of the table data
     * @return Boolean true if run. 
     */
    public boolean SetDataSet(DataCell[][] data){
        m_dataSet = data;
        return true;
    }
    
    /**
     * Returns the dataAtribute and position x,y
     * @param int x:  Position x in the 2 dimensional array;
     * @param int y:  Position y in the 2 dimensional array;
     * @return MS_DataSet single cell of data
     */
    
    public DataCell GetCell(int x, int y){
        return m_dataSet[y][x];
    }
    
    /**
     * Outputs the entire dataset
     * @return MS_DataSet[][]  The entire dataset
     */
    public DataCell[][] GetDataSet(){
        return m_dataSet;
    }
    /**
     * Returns an Array representing a row in the Set at position y
     * 
     * @param y
     * @return MS_DataSet[]
     */
    public DataCell[] GetRow(int y){
        return m_dataSet[y];
    }
    
    /**
     * Returns the array of the column names
     * @return String[]
     */
    public String[] GetHeader(){
        return m_Header;
    }
    /**
     * Gets the a single column name at position i in the array
     * @param i
     * @return 
     */
    public String GetAColumnName(int i){
        return m_Header[i];
    }
    /**
     * Returns the number of columns in the dataset
     * @return int
     */
    public int GetNumOfColumns(){
        return m_numCols;
    }
    /**
     * Returns the number of rows that the dataset represents. 
     * @return Int
     */
    public int GetNumOfRows(){
        return m_numRows;
    }
    
    private DataCell[][] m_dataSet;
    private String[] m_Header;
    private int m_numCols, m_numRows;
    
   
    
}
