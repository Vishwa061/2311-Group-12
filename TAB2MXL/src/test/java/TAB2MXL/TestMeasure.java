package TAB2MXL;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;


public class TestMeasure {
	
private final String PATH = "src/test/resources/";
	

	
	@Test
	public void test1() {
		TabReader test2 = new TabReader(new File(PATH + "StairwayHeaven.txt"));
		
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

	
	
	


