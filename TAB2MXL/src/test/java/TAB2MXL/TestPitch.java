package TAB2MXL;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class TestPitch {
	// new Note(stringNumber, tuning, fret, charIndex)
	
	@Test
	void testStep_Normal() {
		Pitch p = (new Note(1, "G", 5, 0).getPitch());
		String expected = "C";
		
		assertEquals(expected, p.getStep());
	}
	
	@Test
	void testStep_Sharp() {
		Pitch p = (new Note(1, "G#", 5, 0).getPitch());
		String expected = "C";
		
		assertEquals(expected, p.getStep());
	}

	@Test
	void testAlter_Normal() {
		Pitch p = (new Note(1, "G", 5, 0).getPitch());
		int expected = 0;
		
		assertEquals(expected, p.getAlter());
	}
	
	@Test
	void testAlter_Sharp() {
		Pitch p = (new Note(1, "G#", 5, 0).getPitch());
		int expected = 1;
		
		assertEquals(expected, p.getAlter());
	}

	@Test
	void testOctave_Normal() {
		Pitch p = (new Note(1, "G", 5, 0).getPitch());
		int expected = 5;
		
		assertEquals(expected, p.getOctave());
	}
	
	@Test
	void testOctave_Sharp() {
		Pitch p = (new Note(1, "G#", 5, 0).getPitch());
		int expected = 5;
		
		assertEquals(expected, p.getOctave());
	}
}
