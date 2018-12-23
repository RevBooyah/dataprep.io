package io.dataprep.app;

public enum DataType {
	dpSTRING,
	dpCATEGORICAL, // ex:  male, female  (aka NOMINAL)
	dpORDINAL, // values can matter. ex: 1 - small, 2 - medium, 3 - large
	dpDOUBLE, // double float - aka CONTINUOUS
	dpINTERVAL,
	dpINTEGER
}
