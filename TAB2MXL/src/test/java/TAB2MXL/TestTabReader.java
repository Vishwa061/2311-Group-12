package TAB2MXL;
import static org.junit.jupiter.api.Assertions.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

public class TestTabReader {
	private final String PATH = "src/test/resources/";
	
	@Test
	void testReadFile() {
		TabReader reader = new TabReader(new File(PATH + "test_tabs_reading.txt"));
		
		List<String> expected = new ArrayList<String>();
		expected.add("e|-------------0-0-0-0-0-0-----0-------0-0-0-0-0---|");
		expected.add("B|-------------1-1-1-1-1-1-1h3p1p0h1-----1-1-1-1-1-|");
		expected.add("G|-----0h2-----2-2-2-2-2-2-----2-------2-2-2-2-2---|");
		expected.add("D|-0h2-------2-2-2-2-2-2-2-----2-----2-2-2-2-2-2---|");
		expected.add("A|---------0---0-0-0-0-0-----------0---0-0-0-0-0---|");
		expected.add("E|-------------------------------------------------|");
		
		List<String> actual = reader.readFile(new File(PATH + "test_tabs_reading.txt"));
		
		assertEquals(expected, actual);
	}
	
	@Test
	void testSplitMeasure() {
		TabReader reader = new TabReader(new File(PATH + "TestSplitMeasure.txt"));
//		List<ArrayList<String>> expected = new ArrayList<ArrayList<String>>();
//		ArrayList<String> expMeasure1 = new ArrayList<String>();
//		expMeasure1.add("--0-----------------------");
//		expMeasure1.add("------------------3-----5-");
//		expMeasure1.add("------------------3-------");
//		expMeasure1.add("------------------5-------");
//		expMeasure1.add("--------------------------");
//		expMeasure1.add("--------------------------");
//		expected.add(expMeasure1);
//		ArrayList<String> expMeasure2 = new ArrayList<String>();
//		expMeasure2.add("-------------------------");
//		expMeasure2.add("-2-----------------------");
//		expMeasure2.add("-2-----------------------");
//		expMeasure2.add("-2-----------------------");
//		expMeasure2.add("-0-----------------------");
//		expMeasure2.add("-------------------------");
//		expected.add(expMeasure2);
//		
//		List<ArrayList<String>> actual = reader.splitMeasure();
//		assertEquals(expected, actual);
	}
	
	@Test
	void testCountBars() {
		TabReader test2 = new TabReader(new File(PATH + "countBar.txt"));
		//test2.countBars();
	System.out.println(test2.countBars());
		
		
		
	}
	
	@Test
	void testLineHasTabs() {
		// TODO
	}
	
	@Test
	void testGetMeasures() {
		// TODO
	}
	
	@Test
	void testGetTuning() {
		// TODO
	}
	
	@Test
	void testGetTitle() {
		// TODO
	}
	
	@Test
	void testMakeNotes() {
		// TODO
	}
	
	@Test
	void testGetInstrument() {
		// TODO
	}
	
	@Test
	void testToMXL() {
		// TODO
	}

}
