package io.dataprep.types;

import io.dataprep.app.AbstractColumn;
import io.dataprep.app.Column;
import lombok.Data;
import lombok.ToString;

@ToString(callSuper = true)
@Data
public class DpString extends AbstractColumn implements Column {

	@Override
	public void fullColumnParse(String[] fullRawColumn) {

		System.out.println(this);
	}

	@Override
	public void convertColumn(String[] rawCol) {
		// TODO Auto-generated method stub
		
	}

}
