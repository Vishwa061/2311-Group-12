package TAB2MXL;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
		
		public void parser() {
			String correctLine = "^(\\||\\-|[0-9])([\\x5c\\|\\-\\s,*\\+<>0-9^\\(\\)hp=gSs%ex/]+)(\\s?)+"+")(\\||\\-|[0-9])" ;
			//need to update the correctLine string since not entirely sure how to declare the required regex in java
			//
			String measureSeparators = "|";
			ArrayList<String> tabArr = this.getTabArray();
			int count = 0;
			
			 for (int i = 0; i < tabArr.size(); i++ ) {      
			      boolean match = tabArr.get(i).matches(correctLine);
			      //breaking the tablature into 6 lines a piece
			      if (match) {
			        count++;                
			        if (count == 6) {
			          count = 0;
			        }
			      }
			      else { 
			        count = 0;
			        }    
			      //recombine conveniently to convert into what we actually need
			      //to do
			  }
		}
		
		
		
		public void XMLTextBuilder() {
			// parse and build text file 
		}
	}


