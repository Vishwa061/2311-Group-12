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
		TabReader reader = new TabReader();
		
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
		TabReader reader = new TabReader();
		List<ArrayList<String>> expected = new ArrayList<ArrayList<String>>();
		ArrayList<String> expMeasure1 = new ArrayList<String>();
		expMeasure1.add("--0-----------------------");
		expMeasure1.add("------------------3-----5-");
		expMeasure1.add("------------------3-------");
		expMeasure1.add("------------------5-------");
		expMeasure1.add("--------------------------");
		expMeasure1.add("--------------------------");
		expected.add(expMeasure1);
		ArrayList<String> expMeasure2 = new ArrayList<String>();
		expMeasure2.add("-------------------------");
		expMeasure2.add("-2-----------------------");
		expMeasure2.add("-2-----------------------");
		expMeasure2.add("-2-----------------------");
		expMeasure2.add("-0-----------------------");
		expMeasure2.add("-------------------------");
		expected.add(expMeasure2);
		
		List<String> tabArray = reader.readFile(new File(PATH + "TestSplitMeasure.txt"));
		List<ArrayList<String>> actual = reader.splitMeasure(tabArray, tabArray.size());
		assertEquals(expected, actual);
	}
	
//	@Test
//	void testSplitMeasureDrum() {
//		TabReader reader = new TabReader();
//		List<ArrayList<String>> expected = new ArrayList<ArrayList<String>>();
//		ArrayList<String> expMeasure1 = new ArrayList<String>();
//		expMeasure1.add("CC|x---------------|");
//		expMeasure1.add("HH|--x-x-x-x-x-x-x-|");
//		expMeasure1.add("SD|----o-------o---|");
//		expMeasure1.add("HT|----------------|");
//		expMeasure1.add("MT|----------------|");
//		expMeasure1.add("BD|o-------o-------|");
//		expected.add(expMeasure1);
////		ArrayList<String> expMeasure2 = new ArrayList<String>();
////		expMeasure2.add("-------------------------");
////		expMeasure2.add("-2-----------------------");
////		expMeasure2.add("-2-----------------------");
////		expMeasure2.add("-2-----------------------");
////		expMeasure2.add("-0-----------------------");
////		expMeasure2.add("-------------------------");
////		expected.add(expMeasure2);
//		
//		List<String> tabArray = reader.readFile(new File(PATH + "SplitDrum.txt"));
//		List<ArrayList<String>> actual = reader.splitMeasure(tabArray, tabArray.size());
//		assertEquals(expected, actual);
//	}
//	
	
	@Test
	void testCountBars() {
		TabReader test2 = new TabReader();
		test2.setInput(new File(PATH + "countBar.txt"));
		test2.convertTabs();
		System.out.println(test2.countBars());
		// TODO
	}
	
	@Test
	void testCompileMeasures() {
		TabReader reader = new TabReader();
		reader.setInput(new File(PATH + "CompileMeasures_Input.txt"));
		reader.convertTabs();
		// TODO
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
