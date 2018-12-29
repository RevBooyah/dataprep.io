package io.dataprep.app;

import java.util.HashMap;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public abstract class AbstractColumn implements Column{

	private DataType dpType;
	private String name; // from the header
	private int columnNumber;
	private long numBlank;
	private int numUnique;
	private int numRows;
	
	public HashMap<String, Object> details;
	
	
	public int getNumUnique() {
		return numUnique;
	}
	
	public HashMap<String, Object> getDetails() {
		return details;
	}
	
	//public DataType getDpType() {
	//	return dpType;
	//}
	
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
