package TAB2MXL;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TestUnpitch {
	
	@BeforeEach
	void setup() {
		TabReader.instrument = "Drumset";
	}

	@Test
	void test() {
		Unpitch up = new Unpitch("CC", "x");
		System.out.println(up);
	}
	
	@Test
	void testStep1() {
		Unpitch p = new Unpitch("HT","o");
		String expected = "E";

		assertEquals(expected, p.getStep());
	}
	
	@Test
	void testStep2() {
		Unpitch p = new Unpitch("MT","o");
		String expected = "D";

		assertEquals(expected, p.getStep());
	}
	@Test
	void testStep3() {
		Unpitch p = new Unpitch("HH","x");
		String expected = "G";

		assertEquals(expected, p.getStep());
	}
	@Test
	void testStep4() {
		Unpitch p = new Unpitch("CC","x");
		String expected = "A";

		assertEquals(expected, p.getStep());
	}
	
//	@Test
//	void testOcrave1() {
//		Unpitch p = new Unpitch("CC","x");
//		String expected = "A";
//
//		assertEquals(expected, p.getStep());
//	}
//	@Test
//	void testOctave2() {
//		Unpitch p = new Unpitch("CC","x");
//		String expected = "A";
//
//		assertEquals(expected, p.getStep());
//	}
//	@Test
//	void testOctave3() {
//		Unpitch p = new Unpitch("BD", 5);
//		int expected = 5;
//
//		assertEquals(expected, p.getOctave());
//	}
//	
	
	
	

}
