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
		details.put("Empty Rows", (fullRawColumn.length - values.length));
		details.put("Std. Dev.", stdev());
	}
	
	
	public void convertColumn(String[] rawCol) {
		values = Arrays.stream(rawCol).filter(k->k!=null && k!="").map(k->Integer.parseInt(k)).toArray(Integer[]::new);
	}
	
	private void genStats() {
		stats = Arrays.stream(values).collect(Collectors.summarizingInt(Integer::intValue));
	} 
	
	private double stdev(){
        double mean = 0.0;
        double num=0.0;
        double numi = 0.0;
        if(stats==null | stats.getSum()==0) genStats();
        if(values.length<1) return 0.0; // no div by zero
        mean = stats.getSum()/(double)values.length;
        for (Integer i : values) {
            numi = Math.pow(((double) i - mean), 2);
            num = num + numi;
        }
        return Math.sqrt(num/values.length);
    }
}
