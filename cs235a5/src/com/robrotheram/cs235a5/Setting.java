package com.robrotheram.cs235a5;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle; 
import android.os.Environment;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.view.View;

import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;


public class Setting extends Activity {
private EditText et , pt;
private final CloudIO CLOUD = new CloudIO();

private String[][] m_data;

private String m_sid;
private ListView m_listview;
private Context cnt;
private View preview;
private int m_dataPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
      
        cnt = this;
        preview = new View(cnt);
        StrictMode.ThreadPolicy policy = new StrictMode.
        		ThreadPolicy.Builder().permitAll().build();
        		StrictMode.setThreadPolicy(policy); 
        
        
        setContentView(R.layout.settings);
        
        et = (EditText)findViewById(R.id.editText1);
        pt = (EditText)findViewById(R.id.editText2);
        Button remove = (Button) findViewById(R.id.downloadButton);
        Button downlaod = (Button) findViewById(R.id.down);
        Button upload = (Button) findViewById(R.id.uploads);
        m_listview = (ListView) findViewById(R.id.ListView1);
     
        
        remove.setOnClickListener(new View.OnClickListener() {
            @Override
			public void onClick(View v) {
            	String user = et.getText().toString().trim();
            	String pass = pt.getText().toString().trim();
            	m_sid = CLOUD.login(user,pass);
            	if(m_sid!=null){
            		Log.d("Login message","Login is sucssful sid ="+m_sid);
            		m_data = CLOUD.List(m_sid);
            		final ArrayList<String> list = new ArrayList<String>();
                    for (int i = 0; i < m_data.length; ++i) {
                      list.add(m_data[i][1]);
                    }
                    final ArrayAdapter<String> adapter = 
                    		new ArrayAdapter<String>
                    (cnt, R.layout.rowitemlayout, R.id.textid, list);
                    m_listview.setAdapter(adapter);
                    m_listview.setOnItemClickListener
                    (new AdapterView.OnItemClickListener() {

                        @Override
                        public void onItemClick(AdapterView<?> 
                        parent, final View view,
                            int position, long id) {
                        	preview.setBackgroundColor(Color.WHITE);
                        	m_dataPosition = position;
                          final String item = (String) parent.
                        		  getItemAtPosition(position);
                          Toast.makeText(cnt,"Item seleted = "
                        		  +item,Toast.LENGTH_SHORT).show();
                          view.setBackgroundColor(Color.parseColor("#00E5FF"));
                          preview = view;
                        }

                      });	
                    
                    m_listview.setOnScrollListener(new OnScrollListener() {

            		

						@Override
            			public void onScroll(AbsListView view, 
            					int firstVisibleItem,
            					int visibleItemCount, int totalItemCount) {
            				// TODO Auto-generated method stub
            				
            			}

            			@Override
            			public void onScrollStateChanged(AbsListView view,
            					int scrollState) {
            				if (scrollState != 0){
            					 
            				}else {
            					  
            					  ((BaseAdapter) m_listview.getAdapter())
            					  .notifyDataSetChanged();
            					}
            				
            			}
                    
            		});
            		
            	}
            }
        });
        
        
        downlaod.setOnClickListener(new View.OnClickListener() {
            @Override
			public void onClick(View v) {
				
            	String fileid = m_data[m_dataPosition][0];      	
            	String file_url= CLOUD.GetServerAddres()+"../"
            	+CLOUD.getFilePath(m_sid, fileid);
            	new DownloadFileFromURL().execute(file_url);
            	
            }
        });
        
        
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
			public void onClick(View v) {
				File f = new File((Environment.
						getExternalStorageDirectory().getPath()+"/csv.csv"));
				String up = CLOUD.upload(m_sid, f);
				Toast.makeText(cnt,"Item seleted = "+
				up,Toast.LENGTH_SHORT).show();
            }
        });   		
        
    }
    
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    /**
     * Background Async Task to download file
     * */
    class DownloadFileFromURL extends AsyncTask<String, String, String> {
 
        /**
         * Before starting background thread
         * Show Progress Bar Dialog
         * */
        @Override
		protected void onPreExecute() {
            super.onPreExecute();
            
        }
 
        /**
         * Downloading file in background thread
         * */
        @Override
        protected String doInBackground(String... f_url) {
            int count;
            try {
                URL url = new URL(f_url[0]);
                URLConnection conection = url.openConnection();
                conection.connect();
                // this will be useful so that you can show a tipical 0-100% progress bar
                int lenghtOfFile = conection.getContentLength();
 
                // download the file
                InputStream input = new BufferedInputStream(url.openStream(), 8192);
                OutputStream output = new FileOutputStream(Environment.getExternalStorageDirectory().getPath()+"/csv.csv");
                byte data[] = new byte[1024];
                long total = 0;
                while ((count = input.read(data)) != -1) {
                    total += count;
                    publishProgress(""+(int)((total*100)/lenghtOfFile));
                    output.write(data, 0, count);
                }
                output.flush();
                output.close();
                input.close();
 
            } catch (Exception e) {
                Log.e("Error: ", e.getMessage());
            }
 
            return null;
        }
 
        /**
         * Updating progress bar
         * */
        @Override
		protected void onProgressUpdate(String... progress) {
          
       }
 
        /**
         * After completing background task
         * Dismiss the progress dialog
         * **/
        @Override
        protected void onPostExecute(String file_url) {
            // dismiss the dialog after the file was downloaded
        	Toast.makeText(getApplicationContext(),"File Download Sucsfully", Toast.LENGTH_LONG).show();
        	Log.d("settings","Url = "+(Environment.getExternalStorageDirectory().getPath()+"/csv.csv"));
        }
    }
    
    
    

}