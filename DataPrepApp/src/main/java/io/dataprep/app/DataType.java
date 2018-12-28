package io.dataprep.app;

import java.time.Instant;

/**
 * The DataPrep predefined data types. Columns must be one of the included types (unknown will be labeled dpSTRING)
 * @author Steve Cook
 *
 */
public enum DataType {
	BOOLEAN(1,0, "Boolean","(?i)true|false|0|1"),
	INTEGER(1, 0, "Integer","[+-]?\\d+"),
	DOUBLE(2, 0, "Real Numbers","[+-]?[0-9]+(\\.[0-9]+)?([Ee][+-]?[0-9]+)?"), // double float - aka CONTINUOUS
	TIME(10, 0, "Time", "((1[012]|[1-9]):[0-5][0-9](\\s)?(?i)(am|pm))|(([01]?[0-9]|2[0-3]):[0-5][0-9])"),
	DURATION(11, 0, "Duration", "\\d[:\\d\\.]+"),
	DATE(11, 1, "Date",".*"),
	DATETIME(12, 1, "Date/Time",".*"),
	STRING(100, 0, "Text", ".*");
	// The following will be sub-types or modifiers for the above types.
	//dpCATEGORICAL(50, 2,".*"), // ex:  male, female  (aka NOMINAL)
	//dpORDINAL(60, 3,".*"); // values can matter. ex: 1 - small, 2 - medium, 3 - large
	
	/**
	 * The typeId is just the reference value for the data type.
	 */
	private final int typeId;
	
	/**
	 * The type of function that can be used to determine the data type. This can be REGEX (0), DATE/TIME/DATETIME(1), CATEGORICAL(2), or ORDINAL(3) 
	 */
	private final int func;  
	
	private final String readable; 
	/**
	 * If the type is a regex function, then this is the regex pattern for matching.
	 */
	private final String regex;
	
	private DataType(int tid, int func, String readable, String reg) {
		typeId = tid;
		this.func = func;
		this.readable = readable;
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
	
	public String getReadable() {
		return this.readable;
	}
	
	public String getRegex() {
		return this.regex;
	}
}
