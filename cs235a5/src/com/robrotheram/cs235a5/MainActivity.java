package com.robrotheram.cs235a5;



import java.io.File;
import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.content.Context;

import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends Activity implements MyListFragment.OnItemSelectedListener{ 
	
	private Context m_cnt;
	private ViewPager myPager; 
	private ChartPageadapter cpa;
	private View cached;
	private VisulisationList lD;
	private DataSet db;
	
	private int[][] scheem = {
			{   Color.rgb(2,89,89),
				Color.rgb(0,175,181),
				Color.rgb(189,51,103),
				Color.rgb(242,206,22),
				Color.rgb(204,135,4)
			},
			{   Color.rgb(8,89,89),
				Color.rgb(8,89,89),
				Color.rgb(8,89,89),
				Color.rgb(8,89,89),
				Color.rgb(8,89,89)
			},
			{   Color.rgb(8,89,89),
				Color.rgb(8,89,89),
				Color.rgb(8,89,89),
				Color.rgb(8,89,89),
				Color.rgb(8,89,89)
			},
			{   Color.rgb(8,89,89),
				Color.rgb(8,89,89),
				Color.rgb(8,89,89),
				Color.rgb(8,89,89),
				Color.rgb(8,89,89)
			},
			{   Color.rgb(8,89,89),
				Color.rgb(8,89,89),
				Color.rgb(8,89,89),
				Color.rgb(8,89,89),
				Color.rgb(8,89,89)
			},
			

	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		m_cnt= this;
		db = new DataSet();
		Log.d("Dataset ouptput",("Setting up data"));
		init();
		this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		
		Button button = (Button) findViewById(R.id.button1);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	addChart();
            }
        });
        
        Button remove = (Button) findViewById(R.id.buttonRemove);
        remove.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	removeChart();
            }
        });
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	
	public void init(){
		

		 //import file
		 String imagePath = Environment.getExternalStorageDirectory().toString() + "/csv.csv";
		 Log.d("Dataset ouptput",(imagePath+"/n"));
         File f = new File(imagePath);
         CSVReader parser = new CSVReader(db,f,",");
         if(parser.ParseFile()){
         	//Toast.makeText(this,"import yay!!!!",Toast.LENGTH_LONG).show();
         	for(int j = 0; j<(db.GetNumOfColumns()-1);j++){
         		String out ="";
         		for(int i = 0; i<(db.GetNumOfRows()-1);i++){
         			out += "| " +db.GetCell(j,i);	
         		
         		}
         		Log.d("Dataset ouptput",(out+"/n"));
         	}
         }
         Spinner spinner = new Spinner(this);
         String[] spinnerArray;
		ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                 this, android.R.layout.simple_spinner_item,db.GetHeader());
         spinnerArrayAdapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );

         spinner = (Spinner) findViewById( R.id.spinner1 );
         spinner.setAdapter(spinnerArrayAdapter);
         
         spinner = (Spinner) findViewById( R.id.spinner2);
         spinner.setAdapter(spinnerArrayAdapter);
         
      
         
         lD = new VisulisationList(this, db);
         
         ScrollView sv = new ScrollView(this);
         
         sv.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));
         sv.addView(new TableView(this, db).getView());
         lD.addView(sv);
         /*
         LinearLayout ll =  new LinearLayout(this);
         Log.d("Test",ll.toString());
         ll.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));
         ll.addView(new MakeScatterChart(this, db).GetChartView());
         lD.UpdateViews(ll);
         
         LinearLayout ll1 =  new LinearLayout(this);
         Log.d("Test",ll1.toString());
         ll1.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));
         ll1.addView(new MakePieChart(this, db).GetChartView());
         lD.UpdateViews(ll1);
         
         LinearLayout ll2 =  new LinearLayout(this);
         Log.d("Test",ll2.toString());
         ll2.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));
         ll2.addView(new MakeLineChart(this, db).GetChartView());
         lD.UpdateViews(ll2);
         
         LinearLayout ll3 =  new LinearLayout(this);
         Log.d("Test",ll3.toString());
         ll3.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));
         ll3.addView(new MakeBarChart(this, db).GetChartView());
         lD.UpdateViews(ll3);
         
         */
         myPager = (ViewPager) this.findViewById(R.id.myfivepanelpager); 
         cpa = new  ChartPageadapter(this, lD.GetViews());
         Log.d("Test",myPager.toString());
		 myPager.setAdapter(cpa);  	  
	}

	@Override
	public void onRssItemSelected(String link) {
		 DetailFragment fragment = (DetailFragment) getFragmentManager()
	            .findFragmentById(R.id.VisulisationFragment);
	        if (fragment != null && fragment.isInLayout()) {
	          fragment.setText(link);
	        } 
		
	}
	
	private boolean addChart(){
		
		Spinner whichChart = (Spinner) findViewById(R.id.spinner3);
		Spinner xAxis = (Spinner) findViewById(R.id.spinner1);
		Spinner yAxis = (Spinner) findViewById(R.id.spinner2);
		EditText title = (EditText)findViewById(R.id.editText1);
		
		String _title  =title.getText().toString().trim();
		int xpos = xAxis.getSelectedItemPosition();
		int ypos = yAxis.getSelectedItemPosition();
		LinearLayout ll1 =  new LinearLayout(this);
		
	
		ChartType chart = ChartType.valueOf(whichChart.getSelectedItem().toString().toUpperCase());
		switch(chart){
			case BARCHART: 
				
			    Log.d("Test",ll1.toString());
			    ll1.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));
			    ll1.addView(new MakeBarChart(this, db).GetChartView(_title,xpos,ypos));
			    lD.addView(ll1);
			    cpa = new  ChartPageadapter(this, lD.GetViews());
		        Log.d("Test",myPager.toString());
				myPager.setAdapter(cpa);  	
				myPager.setCurrentItem(cpa.getCount(), true);
				break;
				
			case LINECHART: 
		     
			    Log.d("Test",ll1.toString());
			    ll1.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));
			    ll1.addView(new MakeLineChart(this, db).GetChartView(_title,xpos,ypos));
			    lD.addView(ll1);
			    cpa = new  ChartPageadapter(this, lD.GetViews());
		        Log.d("Test",myPager.toString());
				myPager.setAdapter(cpa);  	
				myPager.setCurrentItem(cpa.getCount(), true);
				break;
				
			case PIECHART:
				
				Log.d("Test",ll1.toString());
			    ll1.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));
			    ll1.addView(new MakePieChart(this, db).GetChartView(_title,xpos,ypos));
			    lD.addView(ll1);
			    cpa = new  ChartPageadapter(this, lD.GetViews());
		        Log.d("Test",myPager.toString());
				myPager.setAdapter(cpa);  	
				myPager.setCurrentItem(cpa.getCount(), true);
				break;
				
			case SCATTER:
				 Log.d("Test",ll1.toString());
				 ll1.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));
				 ll1.addView(new MakeScatterChart(this, db).GetChartView(_title,xpos,ypos));
				 lD.addView(ll1);
				 cpa = new  ChartPageadapter(this, lD.GetViews());
				 Log.d("Test",myPager.toString());
				 myPager.setAdapter(cpa);  	
				 myPager.setCurrentItem(cpa.getCount(), true);
				 break;
		}
		return true;
	}
	
	private void removeChart(){
		int pos =myPager.getCurrentItem();
		Log.d("page selected = ","page id:"+pos);
		if(pos == 0){
			Toast.makeText(getApplicationContext(),"Can not delete the Table", Toast.LENGTH_LONG).show();
		}else{
			lD.removeView(pos);
			cpa = new  ChartPageadapter(this, lD.GetViews());
			Log.d("Test",myPager.toString());
			myPager.setAdapter(cpa);  	
			myPager.setCurrentItem(cpa.getCount(), true);
		}
		
	}
	
	
	
	

}
