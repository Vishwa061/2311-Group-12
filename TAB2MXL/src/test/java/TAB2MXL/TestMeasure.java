package TAB2MXL;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class TestMeasure {
	
private final String PATH = "src/test/resources/";
	

	
	@Test
	public void test1() {
		MainV2 test2 = new MainV2("src/main/java/TAB2MXL/StairwayHeaven.txt");
		test2.setTuning(test2.tabArray);
		test2.makeMeasures(test2.tabArray);
		test2.makeNotes();
		
		for (Measure m : test2.measureElements) {
			m.sortArray();
			ArrayList<Note> Notes = m.getNotes() ;
			
			for(Note n:Notes) {
				System.out.println(n.charIndex);
			for(int i =1; i < Notes.size(); i++) {
				//System.out.println(Notes.get(i).charIndex);
				assertTrue(Notes.get(i-1).charIndex <= (Notes.get(i).charIndex));
				
				
			}
			

			}
			System.out.println();
			
		}
		
		
	}

}

	
	
	


