package io.dataprep.app;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

import com.opencsv.CSVReader;

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
	private String[] colTypes;
	
	
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
	    
	    /*
	     * try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
        		stream.forEach(System.out::println);
			}
	     */
	}
	

	public void parseColumn(int idx) throws IOException {
		@SuppressWarnings("resource")
		CSVReader reader = new CSVReader(new FileReader(fullFileName));
	    String [] nextLine;
	    long lcnt = numLines -1;
	    if(lcnt > Integer.MAX_VALUE) {
	    	System.out.println("Too many rows for current DataPrep version. Unlimited coming soon...");
	    	return;	    	
	    }
	    int numSamples = (lcnt<SAMPLE_ROWS)?(int)lcnt:SAMPLE_ROWS;
	    
	    String[] col = new String[numSamples];
	    
	    reader.readNext(); // ignore header.
	    
	    //TODO: if I keep with just sampling, add random space between samples instead of a "head -SAMPLE_SIZE"
	    int x=0;
	    while ((nextLine = reader.readNext()) != null) {
	    	col[x++] = nextLine[idx].strip();
	    	String fullLine = String.join(", ", nextLine);
	        //System.out.println(fullLine );
	    }
		System.out.println("Column "+idx+" contains: "+String.join(" | ", col));
		
		ConcurrentMap<Object, Integer> uniq = Arrays.asList(col).parallelStream().
			            collect(Collectors.toConcurrentMap(
			                w -> w, w -> 1, Integer::sum));
		uniq.keySet().stream().forEach(k-> {
			System.out.println(k + " = "+uniq.get(k));
		});
		int numInt = 0;
		int numFloat = 0;
		int numString = 0;
		
		
				
		
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
	        while (readChars == 1024) {
	            for (int i=0; i<1024;) {
	                if (c[i++] == '\n') {
	                    ++count;
	                }
	            }
	            readChars = is.read(c);
	        }

	        // count remaining characters
	        while (readChars != -1) {
	            System.out.println(readChars);
	            for (int i=0; i<readChars; ++i) {
	                if (c[i] == '\n') {
	                    ++count;
	                }
	            }
	            readChars = is.read(c);
	        }

	        return count == 0 ? 1 : count;
	    } finally {
	        is.close();
	    }
	}
}
