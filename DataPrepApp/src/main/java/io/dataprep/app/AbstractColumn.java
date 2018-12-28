package io.dataprep.app;

import lombok.Data;

@Data
public abstract class AbstractColumn implements Column{

	private DataType dpType;
	private String name; // from the header
	private int columnNumber;
	private long numBlank;
	
	
	public DataType getDpType() {
		return dpType;
	}
	
	public void setDpType(DataType dt) {
		this.dpType = dt;
	}
	
	public void setName(String s) {
		this.name = s;
	}
	
	public void setNumBlank(int n) {
		this.numBlank = n;	
	}
}
