package io.dataprep.types;

import java.util.Arrays;
import java.util.IntSummaryStatistics;
import java.util.stream.Collectors;

import io.dataprep.app.AbstractColumn;
import io.dataprep.app.Column;

public class DpInteger extends AbstractColumn implements Column {

	String[] detailList = {"Min", "Max"};
	
	Integer[] values;
	IntSummaryStatistics stats;
	
	@Override
	public void fullColumnParse(String[] fullRawColumn) {
		convertColumn(fullRawColumn);
		genStats();
		System.out.println(this);
		details.put("Min", stats.getMin());
		details.put("Max", stats.getMax());
		details.put("Avg", stats.getAverage());
		details.put("Sum", stats.getSum());
	}
	
	
	public void convertColumn(String[] rawCol) {
		values = (Integer[]) Arrays.stream(rawCol).map(k->Integer.parseInt(k)).toArray();
	}
	
	private void genStats() {
		stats = Arrays.stream(values).collect(Collectors.summarizingInt(Integer::intValue));
	}
}
