package io.dataprep.app;

import java.util.Arrays;
import java.util.Comparator;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

public class ColumnParse {

	private final static String INT_REGEX = "[+-]?\\d+";
	private final static String FLOAT_REGEX = "[+-]?[0-9]+(\\.[0-9]+)?([Ee][+-]?[0-9]+)?";
	private final static String AMPM_TIME_REGEX = "(1[012]|[1-9]):[0-5][0-9](\\s)?(?i)(am|pm)"; // 12 hour format
	private final static String TIME_REGEX = "([01]?[0-9]|2[0-3]):[0-5][0-9]"; //24 hour format
	private final static String DURATION_REGEX = "\\d[:\\d\\.]+"; 
	
	
	public static DataType determineColType(String[] col) {
		DataType dType = DataType.dpSTRING; // default type is dpSTRING
		ConcurrentMap<Object, Integer> uniq = Arrays.asList(col).parallelStream()
				.collect(Collectors.toConcurrentMap(w -> w, w -> 1, Integer::sum));
		uniq.keySet().stream().forEach(k -> {
			System.out.println(k + " = " + uniq.get(k));
		});
		
		Integer basicType = (Integer) uniq.keySet().stream().map(k->getBasicType((String) k)).max(Comparator.comparing(Integer::valueOf)).get();
		
		switch(basicType) {
		case 1:
			dType = DataType.dpINTEGER;
			break;
		case 2:
			dType = DataType.dpDOUBLE;
			break;
		case 3:
		default:
			dType = DataType.dpSTRING;
			break;
		}
		
		return dType;
	}
	
	
	private static Integer getBasicType(String s) {
		if( s.matches(INT_REGEX) ) {
			return DataType.dpINTEGER.getId();
		}
		if( s.matches(FLOAT_REGEX)) {
			return DataType.dpDOUBLE.getId();
		}
		
		
		return DataType.dpSTRING.getId();
	}
	
	
}
