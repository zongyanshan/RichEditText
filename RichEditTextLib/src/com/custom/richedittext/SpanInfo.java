package com.custom.richedittext;

public class SpanInfo {
	
	public int start;
	
	public int count;
	
	public int spannedType;

	public boolean isCurrent =false;
	
	public SpanInfo(int start, int count, int spannedType) {
		super();
		this.start = start;
		this.count = count;
		this.spannedType = spannedType;
	}

	public SpanInfo(int start, int count, int spannedType, boolean isCurrent) {
		super();
		this.start = start;
		this.count = count;
		this.spannedType = spannedType;
		this.isCurrent = isCurrent;
	}
	
	
	
	
}
