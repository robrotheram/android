package com.robrotheram.cs235a5;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle; 
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

public class Setting extends Activity {
private ProgressBar pb;
private EditText et;
private TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
        et = (EditText)findViewById(R.id.editText1);
        tv = (TextView)findViewById(R.id.prog);
        tv.setText("0 %");
        et.setText("http://robrotheram.com/uni/csv.csv");
        Button remove = (Button) findViewById(R.id.downloadButton);
        pb = (ProgressBar)findViewById(R.id.progressBar1);
        remove.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	new DownloadFileFromURL().execute(et.getText().toString().trim());
            	String url = et.getText().toString().trim();
            	Log.d("settings","Url = "+url);
            }
        });
    
    }
    

    /**
     * Background Async Task to download file
     * */
    class DownloadFileFromURL extends AsyncTask<String, String, String> {
 
        /**
         * Before starting background thread
         * Show Progress Bar Dialog
         * */
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
 
                // Output stream
                OutputStream output = new FileOutputStream(Environment.getExternalStorageDirectory().getPath()+"/csv.csv");
 
                byte data[] = new byte[1024];
 
                long total = 0;
 
                while ((count = input.read(data)) != -1) {
                    total += count;
                    // publishing the progress....
                    // After this onProgressUpdate will be called
                    publishProgress(""+(int)((total*100)/lenghtOfFile));
 
                    // writing data to file
                    output.write(data, 0, count);
                }
 
                // flushing output
                output.flush();
 
                // closing streams
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
        protected void onProgressUpdate(String... progress) {
            // setting progress percentage
            pb.setProgress(Integer.parseInt(progress[0]));
            tv.setText(progress[0]+" %");
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