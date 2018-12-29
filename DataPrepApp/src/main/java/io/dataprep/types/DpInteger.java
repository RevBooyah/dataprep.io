package io.dataprep.types;

import java.util.Arrays;
import java.util.HashMap;
import java.util.IntSummaryStatistics;
import java.util.stream.Collectors;

import io.dataprep.app.AbstractColumn;
import io.dataprep.app.Column;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=true)
public class DpInteger extends AbstractColumn implements Column {

	private Integer[] values;
	private IntSummaryStatistics stats;
	
	@Override
	public void fullColumnParse(String[] fullRawColumn) {
		convertColumn(fullRawColumn);
		genStats();
		details = new HashMap<>();
		details.put("Min", stats.getMin());
		details.put("Max", stats.getMax());
		details.put("Avg", stats.getAverage());
		details.put("Sum", stats.getSum());
		details.put("numBlank", (fullRawColumn.length - values.length));
	}
	
	
	public void convertColumn(String[] rawCol) {
		values = Arrays.stream(rawCol).filter(k->k!=null && k!="").map(k->Integer.parseInt(k)).toArray(Integer[]::new);
	}
	
	private void genStats() {
		stats = Arrays.stream(values).collect(Collectors.summarizingInt(Integer::intValue));
	}
}
