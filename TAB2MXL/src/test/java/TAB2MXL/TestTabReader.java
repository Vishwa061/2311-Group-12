package TAB2MXL;

import static org.junit.jupiter.api.Assertions.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestTabReader {
	private final String PATH = "src/test/resources/";
	private TabReader reader;

	@BeforeEach
	void setup() {
		reader = new TabReader();
	}

	@Test
	void testReadFile() {
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

	// @Test
	// void testSplitMeasureDrum() {
	// TabReader reader = new TabReader();
	// List<ArrayList<String>> expected = new ArrayList<ArrayList<String>>();
	// ArrayList<String> expMeasure1 = new ArrayList<String>();
	// expMeasure1.add("CC|x---------------|");
	// expMeasure1.add("HH|--x-x-x-x-x-x-x-|");
	// expMeasure1.add("SD|----o-------o---|");
	// expMeasure1.add("HT|----------------|");
	// expMeasure1.add("MT|----------------|");
	// expMeasure1.add("BD|o-------o-------|");
	// expected.add(expMeasure1);
	//// ArrayList<String> expMeasure2 = new ArrayList<String>();
	//// expMeasure2.add("-------------------------");
	//// expMeasure2.add("-2-----------------------");
	//// expMeasure2.add("-2-----------------------");
	//// expMeasure2.add("-2-----------------------");
	//// expMeasure2.add("-0-----------------------");
	//// expMeasure2.add("-------------------------");
	//// expected.add(expMeasure2);
	//
	// List<String> tabArray = reader.readFile(new File(PATH + "SplitDrum.txt"));
	// List<ArrayList<String>> actual = reader.splitMeasure(tabArray,
	// tabArray.size());
	// assertEquals(expected, actual);
	// }
	//

//	@Test
//	void testCountBars() {
//		ArrayList<Integer> expected = new ArrayList<Integer>();
//		expected.add(2);
//		expected.add(3);
//		expected.add(4);
//
//		reader.setInput(new File(PATH + "countBar.txt"));
//		reader.convertTabs();
//
//		assertEquals(expected, reader.countBars());
//	}

//	@Test
//	void testCompileMeasures() {
//		reader.setInput(new File(PATH + "CompileMeasures_Input.txt"));
//		List<ArrayList<String>> expected = new ArrayList<ArrayList<String>>();
//
//		ArrayList<String> m1 = new ArrayList<String>();
//		m1.add("--0-----------------------");
//		m1.add("------------------3-----5-");
//		m1.add("------------------3-------");
//		m1.add("------------------5-------");
//		m1.add("--------------------------");
//		m1.add("--------------------------");
//		ArrayList<String> m2 = new ArrayList<String>();
//		m2.add("-------------------------");
//		m2.add("-2-----------------------");
//		m2.add("-2-----------------------");
//		m2.add("-2-----------------------");
//		m2.add("-0-----------------------");
//		m2.add("-------------------------");
//		ArrayList<String> m3 = new ArrayList<String>();
//		m3.add("----15-7-----5-7-");
//		m3.add("-----5-----5-----");
//		m3.add("---5---------5---");
//		m3.add("-7-------6-------");
//		m3.add("-----------------");
//		m3.add("-----------------");
//		ArrayList<String> m4 = new ArrayList<String>();
//		m4.add("-8----------8------------2-----2-");
//		m4.add("---5-----------------------3-----");
//		m4.add("-----5------------------2--------");
//		m4.add("-5-------4-----------------------");
//		m4.add("---------------------------------");
//		m4.add("---------------------------------");
//		ArrayList<String> m5 = new ArrayList<String>();
//		m5.add("-0---0-----");
//		m5.add("--1--1-1---");
//		m5.add("-----2---2-");
//		m5.add("-3---------");
//		m5.add("-----------");
//		m5.add("-----------");
//		ArrayList<String> m6 = new ArrayList<String>();
//		m6.add("--2-----------------------");
//		m6.add("------------------7-----1-");
//		m6.add("--------------------------");
//		m6.add("--------------------------");
//		ArrayList<String> m7 = new ArrayList<String>();
//		m7.add("-------------------------");
//		m7.add("-9-----------------------");
//		m7.add("-8-----------------------");
//		m7.add("-------------------------");
//
//		expected.add(m1);
//		expected.add(m2);
//		expected.add(m3);
//		expected.add(m4);
//		expected.add(m5);
//		expected.add(m6);
//		expected.add(m7);
//System.out.println(reader.compileMeasures());
//		assertEquals(expected, reader.compileMeasures());
//	}

	@Test
	void testLineHasTabs() {
		assertTrue(reader.lineHasTabs("|--|"));
		assertFalse(reader.lineHasTabs("|--"));
		assertFalse(reader.lineHasTabs("|-|"));
	}

	@Test
	void testGetTuning() {
		reader.setInput(new File(PATH + "test_tabs_reading.txt"));

		List<String> expected = new ArrayList<String>();
		expected.add("E");
		expected.add("B");
		expected.add("G");
		expected.add("D");
		expected.add("A");
		expected.add("E");

		assertEquals(expected, reader.getTuning());
	}

	@Test
	void testGetTitle() {
		assertEquals("Title", reader.getTitle());

		reader.setInput(new File(PATH + "test_tabs_reading.txt"));
		assertEquals("test_tabs_reading", reader.getTitle());
	}

	@Test
	void testGetInstrument() {
		reader.setInput(new File(PATH + "test_tabs_reading.txt"));
		assertEquals("Classical Guitar", reader.getInstrument());

		reader.setInput(new File(PATH + "SplitDrum.txt"));
		assertEquals("Drumset", reader.getInstrument());
	}

	@Test
	void testLastCharacter() {
		TabReader test = new TabReader();
		test.setInput(new File("src/test/resources/LastCharTest.txt"));
		test.convertTabs();
		test.makeNotes();

		Measure m = test.getMeasures().get(0);

		int measureLength = m.getIndexTotal();
		int noteCharIndex = m.getNotes().get(m.size() - 1).charIndex;

		assertTrue(measureLength - 1 == noteCharIndex);

	}

	@Test
	void testLastCharacterTechnique() {
		TabReader test = new TabReader();
		test.setInput(new File("src/test/resources/LastCharTechnique.txt"));
		test.convertTabs();
		test.makeNotes();
		boolean[] actuals = new boolean[5];
		boolean[] expecteds = new boolean[5];
		Arrays.fill(expecteds, true);

		for (int i = 0; i < test.getMeasures().size(); i = i + 2) {
			Measure m = test.getMeasures().get(i);
			Note note = m.getNotes().get(m.size() - 1);
			boolean techStart = false;
			if (note.release || note.pullStart || note.hammerStart || note.slideStart || note.bend)
				techStart = true;
			actuals[i / 2] = techStart;
		}

		assertArrayEquals(expecteds, actuals);

	}

	@Test
	void testguitarTechniques() {
		TabReader test = new TabReader();
		test.setInput(new File("src/test/resources/guitarTechniques.txt"));
		test.convertTabs();
		test.makeNotes();
		boolean[] actuals = new boolean[8];
		boolean[] expecteds = new boolean[8];
		Arrays.fill(expecteds, true);
		int i = 0;
		for (Measure m : test.getMeasures()) {
			m.sortArray();
			Note note1 = m.getNotes().get(0);
			Note note2 = m.getNotes().get(1);
			boolean techStart = false;
			boolean techStop = false;
			if (note1.release || note1.bend) {
				actuals[i] = true;
				i++;
				continue;
			}
			if (note1.hammerStart || note1.slideStart || note1.pullStart)
				techStart = true;
			if (note2.pullStop || note2.hammerStop || note2.slideStop)
				techStop = true;

			actuals[i] = techStart && techStop;
			i++;
		}

		assertArrayEquals(expecteds, actuals);

	}

	@Test
	void testDrumBeams() {
		TabReader test = new TabReader();
		test.setInput(new File("src/test/resources/drumBeamsTest.txt"));
		test.convertTabs();
		test.makeDrumNotes();
		boolean[] actuals = new boolean[4];
		boolean[] expecteds = new boolean[4];
		Arrays.fill(expecteds, true);
		int i = 0;

		for (Measure m : test.getMeasures()) {
			boolean correctBeamNumber = false;
			if (m.getNotes().get(0).type.equals("quarter") && !(m.getNotes().get(0).beam1))
				correctBeamNumber = true;
			else if (m.getNotes().get(0).type.equals("eighth") && m.getNotes().get(0).beam1)
				correctBeamNumber = true;
			else if (m.getNotes().get(0).type.equals("16th") && m.getNotes().get(0).beam2)
				correctBeamNumber = true;
			else if (m.getNotes().get(0).type.equals("32nd") && m.getNotes().get(0).beam3)
				correctBeamNumber = true;
			
			actuals[i] = correctBeamNumber;
			i++;
		}

		assertArrayEquals(expecteds, actuals);
	}
	
	@Test
	void testDrumGhost() {
		Note note = new Note("SN", "g", 0);
		assertTrue(note.ghost);
	}
	
	@Test
	void testDrumRoll() {
		Note note = new Note("SN", "b", 0);
		assertTrue(note.roll);
	}
	
	@Test
	void testDrumFlam() {
		Note note = new Note("SN", "f", 0);
		assertTrue(note.flam);
	}
	
	@Test
	void testDrumDrag() {
		Note note = new Note("SN", "d", 0);
		assertTrue(note.drag);
	}
	
	@Test
	void testHiHatRoll() {
		Note note = new Note("HH", "B", 0);
		assertTrue(note.roll);
	}
	
	@Test
	void testDrumAccent() {
		Note note = new Note("SN", "O", 0);
		assertTrue(note.accent);
	}
	
}
