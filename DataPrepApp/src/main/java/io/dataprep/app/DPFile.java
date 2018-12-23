package io.dataprep.app;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.BufferedInputStream;
import java.io.FileInputStream;

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
	private String fullFileName; // includes full path
	private String fileSize;
	private long numLines;
	
	private String[] headers;
	private String[] colTypes;
	
	public DPFile(String full) {
		fullFileName = full;
	}
	
	public void readFullFile() throws IOException {
		@SuppressWarnings("resource")
		CSVReader reader = new CSVReader(new FileReader(fullFileName));
	    String [] nextLine;
	    
	    headers = reader.readNext();
	    if(headers==null || headers.length<1) {
	    	System.out.println("Fatal error.  Header line is required but is not available.");
			return;
	    }
	    parseHeadline();
	    numLines = countLines();
	    System.out.println("Total Lines = "+numLines);
	    while ((nextLine = reader.readNext()) != null) {
	        // nextLine[] is an array of values from the line
	    	String fullLine = String.join(", ", nextLine);
	        System.out.println(fullLine );
	    }
	    /*
	     * try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
        		stream.forEach(System.out::println);
			}
	     */
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
