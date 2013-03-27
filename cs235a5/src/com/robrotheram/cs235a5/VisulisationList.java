package com.robrotheram.cs235a5;

import java.util.Vector;

import android.content.Context;
import android.view.View;

public class VisulisationList {
	private Vector<View> m_pages;
	private DataSet m_db;
	private Context m_cnt;
	
	
	public  VisulisationList(Context context,DataSet db){
		m_pages = new Vector<View>();
		m_db = db;
		m_cnt = context;
	}
	
	
	public Vector<View> GetViews(){
		return m_pages;
	}
	public boolean addView(View v){
		m_pages.add(v);
		return true;
	}
	public boolean removeView(int id){
		m_pages.remove(id);
		return true;
	}
	
	
}
