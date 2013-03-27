package com.robrotheram.cs235a5;

import java.util.ArrayList;
import java.util.Random;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.model.CategorySeries;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;


public class MakePieChart {
	
	 private GraphicalView m_ChartView;
	 private DataSet m_db;
	 private final int COUNT=5;
	 private int[] m_colors = new int[] { Color.RED, Color.DKGRAY, Color.BLUE, Color.parseColor("#800080"),Color.parseColor("#008000"),Color.GRAY };
	 private Context m_cnt;
	 private ArrayList<String> foundElements;
	 private int size;
	 
	 public MakePieChart(Context context, DataSet db){
		 m_cnt = context;
		 m_db = db;
		 
	 }
	
	 public View GetChartView(String title, int xPosition, int yPosition) {
	  
	  CategorySeries cs = new CategorySeries(title);
	  
      int sum = 0;
      int pos = 0;
      int j= 0;

      DataCell preVal = m_db.GetCell(0, 0);
      foundElements  = new ArrayList<String>();
      
      
      for(int i= 0; i < m_db.GetNumOfRows()-1; i++ ){
          
          if(!isUnique(m_db.GetCell(xPosition, i).toString())){
              for(j = pos; j < m_db.GetNumOfRows()-1; j++ ){
                  if(preVal.toString().equals(m_db.GetCell(xPosition, j).toString())){
                      
                      
                      System.out.println("DatatType= :"+m_db.GetCell(xPosition, j).GetDataType());
                      //Check if datatype is a number
                      if(m_db.GetCell(xPosition, j).GetDataType()==DataType.INTEGER){
                          sum += m_db.GetCell(yPosition, j).GetInteger();  
                      }else if(m_db.GetCell(xPosition, j).GetDataType()==DataType.DOUBLE){
                          sum += m_db.GetCell(yPosition, j).GetDouble(); 
                      }
                  }
              }
              foundElements.add(m_db.GetCell(xPosition, i).toString());
              
              //Add to chart dataSet
              //String[] temp = {m_db.GetCell(xPosition, i).toString(),Integer.toString(sum)};
              cs.add(m_db.GetCell(xPosition, i).toString(),(sum)); 
              
              
              System.out.println("Make Chart"+("j = "+m_db.GetCell(xPosition, i).toString()));    
              preVal = m_db.GetCell(xPosition, i++);
              sum=0;//
              pos++;	
              size++;
              }
          }
        
	        
	     int[] colors = new int[size];
	  	  for(int i=0;i<size;i++)
	  	  {
	  	   colors[i]=m_colors[(i%5)];
	  	  }
	  	  DefaultRenderer renderer = buildCategoryRenderer(colors);
	  	   renderer.setPanEnabled(false);// Disable User Interaction
	  	   renderer.setLabelsColor(Color.BLACK);
	  	   renderer.setShowLabels(true);
	  	   renderer.setShowLegend(false);
	  	   
	  	  renderer.setChartTitle(title);
	  	  renderer.setLabelsTextSize(20);      
	  
	 
	  //parent.addView(mChartView2);
	  
	  //return ChartFactory.getPieChartIntent(context, categorySeries, renderer,null);
	  return ChartFactory.getPieChartView(m_cnt, cs,renderer);
	 }
	 
	 private boolean isUnique(String val){
		 boolean found = false;
		 for (int i = 0; i<foundElements.size(); i++){
			 if(foundElements.size() != 0){
			 if(val.equals(foundElements.get(i))){
				 found = true;
				 break;
			 }
			 }
		 }
		 return found;
	 }
	 
	 
	 
	 protected DefaultRenderer buildCategoryRenderer(int[] colors) {
	  DefaultRenderer renderer = new DefaultRenderer();
	  for (int color : colors) {
	  SimpleSeriesRenderer r = new SimpleSeriesRenderer();
	  r.setColor(color);
	  renderer.addSeriesRenderer(r);
	  
	  }
	  return renderer;
	  }
	}
