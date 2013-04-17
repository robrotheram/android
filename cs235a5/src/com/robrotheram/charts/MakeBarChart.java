package com.robrotheram.charts;

import java.util.ArrayList;


import org.achartengine.ChartFactory;
import org.achartengine.chart.BarChart.Type;
import org.achartengine.chart.PointStyle;

import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;

import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import com.robrotheram.cs235a5.DataCell;
import com.robrotheram.cs235a5.DataSet;
import com.robrotheram.cs235a5.DataType;

import android.content.Context;

import android.graphics.Color;

import android.view.View;



public class MakeBarChart {
	
	
	 private DataSet m_db;

	 private int[] m_colors = new int[] {Color.parseColor("#33b5e5"), Color.DKGRAY, Color.BLUE, Color.parseColor("#33b5e5"),Color.parseColor("#008000"),Color.GRAY };
	 private Context m_cnt;
	 private ArrayList<String> foundElements;
	 public MakeBarChart(Context context, DataSet db){
		 m_cnt = context;
		 m_db = db;
		 
	 }
	
	 public View GetChartView(String title, int xPosition, int yPosition ) {
	  
		 XYSeries cs = new XYSeries(title);
	      int sum = 0;
	      int pos = 0;
	      int j= 0;

	      DataCell preVal = m_db.GetCell(0, 0);
	      foundElements  = new ArrayList<String>();
	      
	      
	      for(int i= 0; i < m_db.GetNumOfRows()-1; i++ ){
	          
	          if(!isUnique(m_db.GetCell(xPosition, i).toString())){
	              for(j = pos; j < m_db.GetNumOfRows()-1; j++ ){
	                  if(preVal.toString().equals(m_db.GetCell(xPosition, j)
	                		  .toString())){
	                      
	                      
	                      System.out.println("DatatType= :"+m_db
	                    		  .GetCell(xPosition, j).GetDataType());
	                      //Check if datatype is a number
	                      if(m_db.GetCell(xPosition, j).GetDataType()
	                    		  ==DataType.INTEGER){
	                          sum += m_db.GetCell(yPosition, j).GetInteger();  
	                      }else if(m_db.GetCell(xPosition, j).GetDataType()
	                    		  ==DataType.DOUBLE){
	                          sum += m_db.GetCell(yPosition, j).GetDouble(); 
	                      }
	                  }
	              }
	              foundElements.add(m_db.GetCell(xPosition, i).toString());
	              

	              cs.add(m_db.GetCell(xPosition, i).GetDouble(),(sum)); 
	              
	              
	              System.out.println("Make Chart"+("j = "+
	              m_db.GetCell(xPosition, i).toString()));    
	              preVal = m_db.GetCell(xPosition, i++);
	              sum=0;//
	              pos++;	
	              }
	          }
	        
		        
		     int[] colors = new int[1];
		  	  for(int i=0;i<1;i++)
		  	  {
		  	   colors[i]=m_colors[(i%5)];
		  	  }
		  	   PointStyle[] styles = new PointStyle[] { PointStyle.CIRCLE};
		  	   XYMultipleSeriesRenderer renderer = buildRenderer(colors, styles);
		  	   renderer.setPanEnabled(false,false);// Disable User Interaction
		  	   renderer.setAxesColor(Color.BLACK); 
		  	   renderer.setLabelsColor(Color.BLACK);
		  	   renderer.setApplyBackgroundColor(true);
		  	   renderer.setBackgroundColor(Color.parseColor("#fff3f3f3"));
		  	   renderer.setMarginsColor(Color.parseColor("#fff3f3f3"));
		  	   renderer.setBarSpacing(1);
		  	   for(int i =0; i<foundElements.size();i++){
		  		 renderer.addXTextLabel((i+1),foundElements.get(i));
		  	   }
		  	   
		  	   renderer.setShowLabels(true);
		  	   renderer.setShowLegend(false);
		  	   renderer.setChartTitle(title);
		  	   renderer.setLabelsTextSize(TEXTSIZE);      
		  
		 
		  	XYMultipleSeriesDataset data = new XYMultipleSeriesDataset();
		  	data.addSeries(cs); 
	  return ChartFactory.getBarChartView(m_cnt,data,renderer,Type.DEFAULT);
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
	 protected XYMultipleSeriesRenderer buildRenderer(int[] colors, 
			 PointStyle[] styles) {
		    XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
		    renderer.setAxisTitleTextSize(16);
		    renderer.setChartTitleTextSize(20);
		    renderer.setLabelsTextSize(15);
		    renderer.setLegendTextSize(15);
		    renderer.setPointSize(5f);
		    renderer.setMargins(MARGIN);
		    int length = colors.length;
		    for (int i = 0; i < length; i++) {
		      XYSeriesRenderer r = new XYSeriesRenderer();
		      r.setColor(colors[i]);
		      r.setPointStyle(styles[i]);
		      renderer.addSeriesRenderer(r);
		    }
		    return renderer;
		  }
	 
	 private final int TEXTSIZE = 20;
	 private final int[] MARGIN = { 20, 30, 15, 0 };
	}