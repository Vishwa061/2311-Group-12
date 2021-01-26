package TAB2MXL;

import java.util.ArrayList;
import java.util.List;

public class ReadTab {
	
	public void splitMeasures() {
		ArrayList<String> split = new ArrayList<String>();
		for(String meas : TabArray) {
			for (String list : meas.split("\t")) {
				split.add(list);
			}
		}
		
	

}
	
	
	  
	
	public List<String> combineTab(){
		ArrayList<String> tab = new ArrayList<String>();
		 ArrayList<String> tabNote = new ArrayList<String>();
		 ArrayList<String> tabArr = this.getTabArray();
		 String correctLine = "^(\\||\\-|[0-9])([\\x5c\\|\\-\\s,*\\+<>0-9^\\(\\)hp=gSs%ex/]+)(\\s?)+"+")(\\||\\-|[0-9])" ;
		 for (int i = 0; i < tabArr.size();i++) {
			 int val = i;
			 boolean match = tabArr.get(val).matches(correctLine);
			 if (match) {
				 if( tabNote.size() == 6) {
				 tabNote.add(tabArr.get(val));
				 tab.addAll(tabNote);
				 String combine;
				((String) combine).join(match);
				 
				 
			 }
				 else {
					 //do nothing for now
				 }
		 }
		
		
		
	}
		 return tab;
}
	
	String correctLine = "^(\\||\\-|[0-9])([\\x5c\\|\\-\\s,*\\+<>0-9^\\(\\)hp=gSs%ex/]+)(\\s?)+"+")(\\||\\-|[0-9])" ;
	for ()
	
	
	
	
}
