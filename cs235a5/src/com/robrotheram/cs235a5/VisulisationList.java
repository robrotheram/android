package com.robrotheram.cs235a5;

import java.util.Vector;
import android.view.View;

public class VisulisationList {
	private Vector<View> m_pages;

	
	
	public  VisulisationList(){
		m_pages = new Vector<View>();

	}
	
	
	public Vector<View> GetViews(){
		return m_pages;
	}
	public boolean addView(View v){
		m_pages.add(v);
		return true;
	}
	public boolean addView(View v, int i){
		m_pages.add(i,v);
		return true;
	}
	public boolean removeView(int id){
		m_pages.remove(id);
		return true;
	}
	
	
}
