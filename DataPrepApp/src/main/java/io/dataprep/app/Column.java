package io.dataprep.app;

import lombok.Data;

@Data
public class Column {

	private DataType dpType;
	private String name; // from the header
	private int columnNumber;
	private long numBlank;
	
	// each type will extend this type.
	
}
