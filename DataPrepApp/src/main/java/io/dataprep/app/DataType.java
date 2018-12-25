package io.dataprep.app;

import lombok.Data;

public enum DataType {
	dpINTEGER(1),
	dpDOUBLE(2), // double float - aka CONTINUOUS
	dpTIME(3),
	dpDATE(4),
	dpDATETIME(5),
	dpDURATION(6),
	dpCATEGORICAL(20), // ex:  male, female  (aka NOMINAL)
	dpORDINAL(30), // values can matter. ex: 1 - small, 2 - medium, 3 - large
	dpSTRING(100);
	
	private final int typeId;
	
	private DataType(int tid) {
		typeId = tid;
	}
	
	public int getId() {
		return this.typeId;
	}
}
