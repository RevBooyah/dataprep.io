package io.dataprep.app;

import java.util.HashMap;

public interface Column {
	
	DataType getDpType();
	void setDpType(DataType dt);
	
	String getName();
	void setName(String name);
	int getNumUnique();
	HashMap<String, Object> getDetails();
	void convertColumn(String[] rawCol);
	int getColumnNumber();
	void setColumnNumber(int idx);
	
	long getNumBlank();
	void setNumBlank(int idx);
	void fullColumnParse(String[] fullRawColumn);
	void setNumUnique(int numUnique);
	
}
