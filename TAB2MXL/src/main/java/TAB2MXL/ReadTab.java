package TAB2MXL;

import java.util.ArrayList;

public class ReadTab {
	
	public void splitMeasures() {
		ArrayList<String> split = new ArrayList<String>();
		for(String meas : TabArray) {
			for (String list : meas.split("\t")) {
				split.add(list);
			}
		}
		
	

}
