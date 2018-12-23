package io.dataprep.app;

import java.io.FileReader;
import java.io.IOException;

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
	
	private String[] headers;
	private String[] colTypes;
	
	public DPFile(String full) {
		fullFileName = full;
	}
	
	public void readFullFile() throws IOException {
		CSVReader reader = new CSVReader(new FileReader(fullFileName));
	    String [] nextLine;
	    
	    headers = reader.readNext();
	    parseHeadline();
	    
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
		if(headers.length<1) {
			System.out.println("Fatal error.  Header line is required but is not available.");
			return;
		}
		
	}
	
}
