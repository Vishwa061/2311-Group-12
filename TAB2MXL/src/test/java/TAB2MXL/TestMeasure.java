package TAB2MXL;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;


public class TestMeasure {
	private final String PATH = "src/test/resources/";

	@Test
	void testSortArray() {
		TabReader test2 = new TabReader(new File(PATH + "StairwayHeaven.txt"));

		for (Measure m : test2.getMeasures()) {
			m.sortArray();
			ArrayList<Note> Notes = m.getNotes() ;

			//			for(Note n:Notes) {
			//				System.out.println(n.charIndex);
			for(int i =1; i < Notes.size(); i++) {
				//System.out.println(Notes.get(i).charIndex);
				assertTrue(Notes.get(i-1).charIndex <= (Notes.get(i).charIndex));
			}
			//			}
			//			System.out.println();
		}
	}

	@Test
	void testSetAttributes() {
		// TODO
	}

	@Test
	void testAddNote() {
		// TODO
	}

	@Test
	void testToString() {
		// TODO
	}

	@Test
	void testGetNote() {
		// TODO
	}

	@Test
	void testSize() {
		// TODO
	}

	@Test
	void testGetNotes() {
		// TODO
	}

}