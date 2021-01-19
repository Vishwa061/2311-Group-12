package TAB2MXL;

import java.util.*;
import java.io.*;

public class TabReader {
	
	public List<String> tabArray = new ArrayList<String>();
	public File inputTabFile;
	public String outputXMLFile;
	
	public TabReader(String fileLocation) {
		inputTabFile = new File(fileLocation);
		outputXMLFile = "<?xml version = 1.0 encoding = UTF-8 standalone = no?>";
		tabArray = new ArrayList<String>();
		this.readFile();
	}

	
	
	public void readFile() {
		Scanner sc = null;
		try {
			sc = new Scanner(inputTabFile);
			while(sc.hasNextLine()) {
				tabArray.add(sc.nextLine());
			}
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		finally {
			sc.close();
		}
	}
		
		public ArrayList<String> getTabArray(){
			ArrayList<String> tabArr = new ArrayList<String>();
			for (String line:this.tabArray) {
				tabArr.add(line);
			}
			
			return tabArr;
			
		}
		
		public void XMLTextBuilder() {
			// parse and build text file 
		}
	}


