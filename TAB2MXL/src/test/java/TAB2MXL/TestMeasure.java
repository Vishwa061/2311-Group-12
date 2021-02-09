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
		TabReader test2 = new TabReader("src/main/java/TAB2MXL/StairwayHeaven.txt");
		
		for (Measure m : test2.getMeasures()) {
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

	
	
	


