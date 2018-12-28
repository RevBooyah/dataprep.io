package io.dataprep.app;

public interface Column {
	
	DataType getDpType();
	void setDpType(DataType dt);
	
	String getName();
	void setName(String name);
	
	int getColumnNumber();
	void setColumnNumber(int idx);
	
	long getNumBlank();
	void setNumBlank(int idx);
	
}
