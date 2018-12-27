package io.dataprep.app;

import java.time.Instant;

/**
 * The DataPrep predefined data types. Columns must be one of the included types (unknown will be labeled dpSTRING)
 * @author Steve Cook
 *
 */
public enum DataType {
	dpINTEGER(1, 0,"[+-]?\\d+"),
	dpDOUBLE(2, 0,"[+-]?[0-9]+(\\.[0-9]+)?([Ee][+-]?[0-9]+)?"), // double float - aka CONTINUOUS
	dpTIME(10, 0,"((1[012]|[1-9]):[0-5][0-9](\\s)?(?i)(am|pm))|(([01]?[0-9]|2[0-3]):[0-5][0-9])"),
	dpDURATION(11, 0, "\\d[:\\d\\.]+"),
	dpDATE(11, 1,".*"),
	dpDATETIME(12, 1,".*"),
	dpSTRING(100, 0, ".*"),
	dpCATEGORICAL(50, 2,".*"), // ex:  male, female  (aka NOMINAL)
	dpORDINAL(60, 3,".*"); // values can matter. ex: 1 - small, 2 - medium, 3 - large
	
	/**
	 * The typeId is just the reference value for the data type.
	 */
	private final int typeId;
	
	/**
	 * The type of function that can be used to determine the data type. This can be REGEX (0), DATE/TIME/DATETIME(1), CATEGORICAL(2), or ORDINAL(3) 
	 */
	private final int func;  
	
	/**
	 * If the type is a regex function, then this is the regex pattern for matching.
	 */
	private final String regex;
	
	private DataType(int tid, int func, String reg) {
		typeId = tid;
		this.func = func;
		regex = reg;
	}
	
	public Instant datetime(String s) {
		//String timestampString = "2013-10-27T13:00:00.325234Z";
	    Instant inst = Instant.parse(s);
	    return inst;
	}
	
	public int getId() {
		return this.typeId;
	}
	
	public int getFunc() {
		return this.func;
	}
	
	public String getRegex() {
		return this.regex;
	}
}
