package com.robrotheram.cs235a5;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class TableView {
	private DataSet m_db;
	private Context m_cnt;
	
	public TableView(Context context, DataSet db) {
		m_cnt = context;
		m_db = db;
		// TODO Auto-generated constructor stub
	}
	
	@SuppressLint("DrawAllocation")
	public View getView(){

		TableLayout table = new TableLayout(m_cnt);
		FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(
		        ViewGroup.LayoutParams.MATCH_PARENT,
		        ViewGroup.LayoutParams.MATCH_PARENT);
		table.setLayoutParams(lp);
		
		for (int i = -1; i < (m_db.GetNumOfRows()-1); i++) {
			if(i == -1){
				TableRow row = new TableRow(m_cnt);
				for (int j = 0; j < (m_db.GetHeader().length); j++) {
					
				TextView cell = new TextView(m_cnt) {
				    @Override
				    protected void onDraw(Canvas canvas) {
				        super.onDraw(canvas);
				        Rect rect = new Rect();
				        Paint paint = new Paint();
				        paint.setStyle(Paint.Style.STROKE);
				        paint.setColor(Color.WHITE);
				        paint.setStrokeWidth(STROCKWIDTH);
				        getLocalVisibleRect(rect);
				        canvas.drawRect(rect, paint);       
				    }
	
				};
				cell.setText(m_db.GetAColumnName(j));
				cell.setPadding(CELLLEFT,CELLTOP,CELLRIGHT,CELLBOTTOM);
				row.addView(cell);
				
				
			}
				table.addView(row);
				table.setStretchAllColumns(true);
			}else{
				TableRow row = new TableRow(m_cnt);
				for (int j = 0; j < (m_db.GetNumOfColumns()); j++) {
					
				TextView cell = new TextView(m_cnt) {
				    @Override
				    protected void onDraw(Canvas canvas) {
				        super.onDraw(canvas);
				        Rect rect = new Rect();
				        Paint paint = new Paint();
				        paint.setStyle(Paint.Style.STROKE);
				        paint.setColor(Color.WHITE);
				        paint.setStrokeWidth(STROCKWIDTH);
				        getLocalVisibleRect(rect);
				        canvas.drawRect(rect, paint);       
				    }
	
				};
				cell.setText(m_db.GetCell(j,i).toString());
				cell.setPadding(CELLLEFT,CELLTOP,CELLRIGHT,CELLBOTTOM);
				row.addView(cell);
				
			}
			
			table.addView(row);
			table.setStretchAllColumns(true);
			}
		}
		return table;
		
		
	}

	private final int CELLLEFT = 6;
	private final int CELLTOP = 4;
	private final int CELLRIGHT = 6;
	private final int CELLBOTTOM = 4;
	private final int STROCKWIDTH = 2;
	

}
