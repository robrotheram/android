package com.robrotheram.cs235a5;



import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.Locale;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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

import com.robrotheram.charts.MakeAreaChart;
import com.robrotheram.charts.MakeBarChart;
import com.robrotheram.charts.MakeBubbleChart;
import com.robrotheram.charts.MakeLineChart;
import com.robrotheram.charts.MakePieChart;
import com.robrotheram.charts.MakeScatterChart;

public class MainActivity extends Activity implements MyListFragment.
OnItemSelectedListener{ 
	

	private ViewPager myPager; 
	private ChartPageadapter cpa;
	private DataSet db;
	private VisulisationList  lD = new VisulisationList();
	private final String filePath = Environment.getExternalStorageDirectory().toString() + "/csv.csv";
	private boolean newdb;
	private boolean isEmpty = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		newdb = false;
		setContentView(R.layout.activity_main);
		Log.d("Dataset ouptput",("Setting up data"));
		init(filePath);
		this.getWindow().setSoftInputMode(WindowManager.LayoutParams.
				SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		
		Button button = (Button) findViewById(R.id.button1);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
			public void onClick(View v) {
            	if(isEmpty){
            		addChart();
            	}else{
            		Toast.makeText(getApplicationContext(),"No Data try importing a csv file", Toast.LENGTH_LONG).show();
            	}
            }
        });
        
        Button remove = (Button) findViewById(R.id.buttonRemove);
        remove.setOnClickListener(new View.OnClickListener() {
            @Override
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
	@Override
	public boolean onOptionsItemSelected(MenuItem item) { 
	    // Handle item selection       
	    switch (item.getItemId()) {   
	    case R.id.menu_Cloud:           
	        Log.d("settings","munupressed");
	        
	        Intent intent = new Intent(this, Setting.class);
	        newdb = true;
	        startActivity(intent);
	        
	        return true;           
	    case R.id.menu_FileSystem:
	    	showFileChooser();
	    default:              
	        return super.onOptionsItemSelected(item);    
	    }
	}
	
	public void init(String imagePath){
		 Log.d("Dataset ouptput",(imagePath+"/n"));
         File f = new File(imagePath);
         if(!f.exists()){
        	 db = new DataSet();
	         db.SetNullDataSet(4,10);
	         db.SetHeader(new String[]{"Title A","Title B","Title C","Title D"});
	         Log.d("on create","Data");
         }else{
    	 isEmpty = true;
         db = new DataSet();
         CSVReader parser = new CSVReader(db,f,",");
         Spinner spinner = new Spinner(this);
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
         	
         	ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                    this, android.R.layout.simple_spinner_item,db.GetHeader());
         	spinnerArrayAdapter.setDropDownViewResource(
           		 android.R.layout.simple_spinner_dropdown_item );

            spinner = (Spinner) findViewById( R.id.spinner1 );
            spinner.setAdapter(spinnerArrayAdapter);
            
            spinner = (Spinner) findViewById( R.id.spinner2);
            spinner.setAdapter(spinnerArrayAdapter);
       
         }
         

         
         ScrollView sv = new ScrollView(this);
         sv.setLayoutParams(new LayoutParams
        		 (LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));
         sv.addView(new TableView(this, db).getView());
         lD.addView(sv);
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
		
	
		ChartType chart = ChartType.valueOf
				(whichChart.getSelectedItem().toString().toUpperCase
						(Locale.getDefault()));
		switch(chart){
			case BARCHART: 
				
			    Log.d("Test",ll1.toString());
			    ll1.setLayoutParams(new LayoutParams
			    		(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));
			    ll1.addView(new MakeBarChart(this, db).GetChartView
			    		(_title,xpos,ypos));
			    lD.addView(ll1);
			    cpa = new  ChartPageadapter(this, lD.GetViews());
		        Log.d("Test",myPager.toString());
				myPager.setAdapter(cpa);  	
				myPager.setCurrentItem(cpa.getCount(), true);
				break;
				
			case LINECHART: 
		     
			    Log.d("Test",ll1.toString());
			    ll1.setLayoutParams(new LayoutParams
			    		(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));
			    ll1.addView(new MakeLineChart(this, db).GetChartView
			    		(_title,xpos,ypos));
			    lD.addView(ll1);
			    cpa = new  ChartPageadapter(this, lD.GetViews());
		        Log.d("Test",myPager.toString());
				myPager.setAdapter(cpa);  	
				myPager.setCurrentItem(cpa.getCount(), true);
				break;
				
			case PIECHART:
				
				Log.d("Test",ll1.toString());
			    ll1.setLayoutParams(new LayoutParams
			    		(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));
			    ll1.addView(new MakePieChart(this, db).GetChartView
			    		(_title,xpos,ypos));
			    lD.addView(ll1);
			    cpa = new  ChartPageadapter(this, lD.GetViews());
		        Log.d("Test",myPager.toString());
				myPager.setAdapter(cpa);  	
				myPager.setCurrentItem(cpa.getCount(), true);
				break;
				
			case SCATTER:
				 Log.d("Test",ll1.toString());
				 ll1.setLayoutParams(new LayoutParams
						 (LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));
				 ll1.addView(new MakeScatterChart(this, db).GetChartView
						 (_title,xpos,ypos));
				 lD.addView(ll1);
				 cpa = new  ChartPageadapter(this, lD.GetViews());
				 Log.d("Test",myPager.toString());
				 myPager.setAdapter(cpa);  	
				 myPager.setCurrentItem(cpa.getCount(), true);
				 break;
			case AREA:
			    Log.d("Test",ll1.toString());
			    ll1.setLayoutParams(new LayoutParams
			    		(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));
			    ll1.addView(new MakeAreaChart(this, db).GetChartView
			    		(_title,xpos,ypos));
			    lD.addView(ll1);
			    cpa = new  ChartPageadapter(this, lD.GetViews());
		        Log.d("Test",myPager.toString());
				myPager.setAdapter(cpa);  	
				myPager.setCurrentItem(cpa.getCount(), true);
				break;
			case BUBBLE:
			    Log.d("Test",ll1.toString());
			    ll1.setLayoutParams(new LayoutParams
			    		(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));
			    ll1.addView(new MakeBubbleChart(this, db).GetChartView
			    		(_title,xpos,ypos));
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
			Toast.makeText(getApplicationContext(),
					"Can not delete the Table", Toast.LENGTH_LONG).show();
		}else{
			lD.removeView(pos);
			cpa = new  ChartPageadapter(this, lD.GetViews());
			Log.d("Test",myPager.toString());
			myPager.setAdapter(cpa);  	
			myPager.setCurrentItem(cpa.getCount(), true);
		}
		
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		if(newdb){
		Log.d("rebuild","rebild");
		lD.removeView(0);
		 ScrollView sv = new ScrollView(this);
         sv.setLayoutParams(new LayoutParams
        		 (LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));
         sv.addView(new TableView(this, db).getView());
         lD.addView(sv,0);
         
 		cpa = new  ChartPageadapter(this, lD.GetViews());
 		Log.d("Test",myPager.toString());
 		myPager.setAdapter(cpa);  	
 		myPager.setCurrentItem(cpa.getCount(), true);
 		newdb = false;
		}
    }
	
	
    private static final int FILE_SELECT_CODE = 0;
	private static final String TAG = null;

    private void showFileChooser() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT); 
        intent.setType("*/*"); 
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        try {
            startActivityForResult(
                    Intent.createChooser(intent, "Select a File to Upload"),
                    FILE_SELECT_CODE);
        } catch (android.content.ActivityNotFoundException ex) {
            // Potentially direct the user to the Market with a Dialog
            Toast.makeText(this, "Please install a File Manager.", 
                    Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case FILE_SELECT_CODE:      
            if (resultCode == RESULT_OK) {  
                // Get the Uri of the selected file 
                Uri uri = data.getData();
                Log.d(TAG, "File Uri: " + uri.toString());
                // Get the path
                File f = new File(uri.getPath());
                try {
					coppyFile(f,new File(filePath));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
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
                lD.removeView(0);
       		 ScrollView sv = new ScrollView(this);
                sv.setLayoutParams(new LayoutParams
               		 (LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));
                sv.addView(new TableView(this, db).getView());
                lD.addView(sv,0);
                
        		cpa = new  ChartPageadapter(this, lD.GetViews());
        		Log.d("Test",myPager.toString());
        		myPager.setAdapter(cpa);  	
        		myPager.setCurrentItem(cpa.getCount(), true);
        		newdb = false;
                // Get the file instance
                // File file = new File(path);
                // Initiate the upload
            }           
            break;
        }
    super.onActivityResult(requestCode, resultCode, data);
    }
    
    public void coppyFile(File sf, File df) throws IOException{
    	if(!df.exists()) {
            try {
				df.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }

        FileChannel source = null;
        FileChannel destination = null;

        try {
            source = new FileInputStream(sf).getChannel();
            destination = new FileOutputStream(df).getChannel();
            destination.transferFrom(source, 0, source.size());
        } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        finally {
            if(source != null) {
                try {
					source.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
            if(destination != null) {
                destination.close();
            }
        }
    }
	
	

}
