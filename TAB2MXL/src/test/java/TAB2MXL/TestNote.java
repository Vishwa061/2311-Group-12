package TAB2MXL;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TestNote {

	@BeforeEach
	void setup() {
		TabReader.instrument = "Classical Guitar";
	}
	
	@Test
	void testCompareTo_Negative() {
		Note n1 = new Note(1, "C", 0, 4);
		Note n2 = new Note(1, "C", 0, 5);
		
		assertTrue(n1.compareTo(n2) < 0);
	}
	
	@Test
	void testCompareTo_Positive() {
		Note n1 = new Note(1, "C", 0, 6);
		Note n2 = new Note(1, "C", 0, 5);
		
		assertTrue(n1.compareTo(n2) > 0);
	}
	
	@Test
	void testCompareTo_Equals() {
		Note n1 = new Note(1, "C", 0, 5);
		Note n2 = new Note(1, "C", 0, 5);
		
		assertTrue(n1.compareTo(n2) == 0);
	}
	
	@Test
	void testGetPitch() {
		Pitch actual = new Note(1, "E", 0, 0).getPitch();
		Pitch expected = new Pitch(1, "E", 0);
		
		assertEquals(expected, actual);
	}
	
	@Test
	void testToString() {
		// TODO
	}
}