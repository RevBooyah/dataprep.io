package io.dataprep.app;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

public class ColumnParse {

	private static final int FUNC_REGEX = 0;
	
	public static DataType determineColType(String[] col) {
		DataType dType = DataType.STRING; // default type is dpSTRING
		ConcurrentMap<Object, Integer> uniq = Arrays.asList(col)
				.parallelStream()
				.collect(Collectors.toConcurrentMap(w -> w, w -> 1, Integer::sum));
		
		// how many different values
		int numDifferent = uniq.size();
		System.out.println("THere are "+numDifferent+" different values.");
		
		uniq.keySet().stream().forEach(k -> {
			System.out.println(k + " = " + uniq.get(k));
		});
		
		// TODO(steve): filter empty string here or before?
		ConcurrentMap<DataType,Integer> thash = uniq.keySet().parallelStream().map(s->guessDataType((String) s)) 
				.collect(Collectors.toConcurrentMap(w -> w, w->1, Integer::sum));	
		dType = Collections.max(thash.entrySet(), Map.Entry.comparingByValue()).getKey();
		System.out.println("THASH: "+thash);
		//ConcurrentMap<DataType,Integer> thash = uniq.keySet().stream().map(k->getBasicType((String) k)).max(Comparator.comparing(Integer::valueOf)).get();
		return dType;
	}
	
	private static DataType guessDataType(String s) {
		DataType out = Arrays.stream(DataType.values()).filter(x->x.getFunc()==FUNC_REGEX).filter(x->s.matches(x.getRegex())).findFirst().get();
		//System.out.println("DT("+out.getId()+") = "+out.name());
		if(out == DataType.STRING) out = guessSpecificType(s);
		return out;
	}
	
	private static DataType guessSpecificType(String s) {
		DataType out = DataType.STRING;
		return out;
	}
	
	
	
}
