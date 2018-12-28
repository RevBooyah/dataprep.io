package io.dataprep.app;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;

import com.opencsv.CSVReader;

import io.dataprep.types.DpDouble;
import io.dataprep.types.DpInteger;
import io.dataprep.types.DpString;
import lombok.Data;

/**
 * DataPrep File - contains all the meta details and file pointers for the 
 * file being prepped.
 * @author Stephen Cook
 *
 */

@Data
public class DPFile {

	private String fileName;
	private final String fullFileName; // includes full path
	private String fileSize;
	private long numLines;
	private int numColumns;
	
	private String[] headers;
	private Column[] columns;
	
	
	private final static int SAMPLE_ROWS=1000;
	
	public DPFile(String full) {
		fullFileName = full;
	}
	
	public void readBasicFileInfo() throws IOException {
		@SuppressWarnings("resource")
		CSVReader reader = new CSVReader(new FileReader(fullFileName));
	    
	    headers = reader.readNext();
	    if(headers==null || headers.length<1) {
	    	System.out.println("Fatal error.  Header line is required but is not available.");
			return;
	    }
	    numColumns = headers.length;
	    parseHeadline();
	    numLines = countLines();
	    columns = new Column[numColumns];
	    
	    /*
	     * try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
        		stream.forEach(System.out::println);
			}
	     */
	}
	

	public Column parseColumn(int idx) throws IOException {
		@SuppressWarnings("resource")
		CSVReader reader = new CSVReader(new FileReader(fullFileName));
	    String [] line;
	    long lcnt = numLines -1;
	    if(lcnt > Integer.MAX_VALUE) {
	    	System.out.println("Too many rows for current DataPrep version. Unlimited coming soon...");
	    	return null;	    	
	    }
	    int numSamples = (lcnt<SAMPLE_ROWS)?(int)lcnt:SAMPLE_ROWS;
	    
	    String[] col = new String[numSamples];
	    System.out.println("Num samples: "+numSamples);
	    reader.readNext(); // ignore header.
	    
	    //TODO: if I keep with just sampling, add random space between samples instead of a "head -SAMPLE_SIZE"
	    int x=0;
	    while ((line = reader.readNext()) != null) {
	    	//System.out.println("Next Line: "+nextLine);
	    	col[x] = line[idx].trim();
	    	//String fullLine = String.join(", ", nextLine);
	        //System.out.println(x+" "+fullLine );
	        x++;
	    }
		System.out.println("Column "+idx+" contains: "+String.join(" | ", col));
		
		DataType dType = ColumnParse.determineColType(col);
		
		Column cls = new DpString();
		//cls = Class.forName(dType.getClassName()).newInstance();
		switch(dType) {
			case STRING:
				cls = new DpString();
				break;
			case INTEGER:
				cls = new DpInteger();
				break;
			case DOUBLE:
				cls = new DpDouble();
				
			default:
				cls = new DpString();
				break;
		}
		
		cls.setDpType(dType);
		cls.setColumnNumber(idx);
		cls.setName(headers[idx]);
		cls.setNumBlank(0);
		
		columns[idx] = cls;
		System.out.println("DATA TYPE FOR COL "+idx+" is "+dType);
			
		
		return columns[idx];
		
	}
	
	
	private void parseHeadline() {
		System.out.println(String.join(" , ", headers));
		
	}
	
	private long countLines() throws IOException {
	    InputStream is = new BufferedInputStream(new FileInputStream(fullFileName));
	    try {
	        byte[] c = new byte[1024];

	        int readChars = is.read(c);
	        if (readChars == -1) {
	            // bail out if nothing to read
	            return 0;
	        }

	        // make it easy for the optimizer to tune this loop
	        long count = 0;
	        int tmpcnt = 0;
	        while (readChars == 1024) {
	            for (int i=0; i<1024;) {
	                if (c[i++] == '\n') {
	                    ++count;
	                    //tmpcnt=0;
	                } else {
	                	//tmpcnt++;
	                }
	                
	            }
	            readChars = is.read(c);
	        }
	        
	        // count remaining characters
	        tmpcnt=0;
	        while (readChars != -1) {
	            //System.out.println("READCHARS: "+readChars);
	            for (int i=0; i<readChars; ++i) {
	                if (c[i] == '\n') {
	                    ++count;
	                    tmpcnt=0;
	                } else {
	                	tmpcnt++;
	                }
	            }
	            readChars = is.read(c);
	        }
	        if(tmpcnt>0) count++;
	        return count == 0 ? 1 : count;
	    } finally {
	        is.close();
	    }
	}
	
	/*
	public static String ucFirst(String s) {
		if (s == null || s.length() <= 0) return s;
		char[] chars = new char[1];
		s.toLowerCase().getChars(0, 1, chars, 0);
   
	    StringBuilder sb = new StringBuilder(s.length());
	    sb.append(Character.toUpperCase(chars[0]));
	    sb.append(s.toCharArray(), 1, s.length()-1);
	    return sb.toString();
	} */
}
