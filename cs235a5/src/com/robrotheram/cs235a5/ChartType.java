package com.robrotheram.cs235a5;

public enum ChartType {
	BARCHART(0),LINECHART(1),PIECHART(2),SCATTER(3),AREA(4),BUBBLE(5);
	private Integer chartType;
    
    private ChartType(int i) {
    	chartType = i;
    }
    public final Integer getValue() {
        return chartType;
    }
}
