package TAB2MXL;
import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

public class TestGuitarString {
	GuitarString[] guitar;

	@Before
	public void setUp() {
		guitar = new GuitarString[6];
		guitar[0] = new GuitarString();
		guitar[1] = new GuitarString();
		guitar[2] = new GuitarString();
		guitar[3] = new GuitarString();
		guitar[4] = new GuitarString();
		guitar[5] = new GuitarString();
	}

	//	public void standardTuning() { //EADGBE
	//		guitar[0] = new GuitarString(1, "E");
	//		guitar[1] = new GuitarString(2, "B");
	//		guitar[2] = new GuitarString(3, "G");
	//		guitar[3] = new GuitarString(4, "D");
	//		guitar[4] = new GuitarString(5, "A");
	//		guitar[5] = new GuitarString(6, "E");
	//	}
	//	
	//	public void dropDTuning() { //DADGBE
	//		guitar[0] = new GuitarString(1, "E");
	//		guitar[1] = new GuitarString(2, "B");
	//		guitar[2] = new GuitarString(3, "G");
	//		guitar[3] = new GuitarString(4, "D");
	//		guitar[4] = new GuitarString(5, "A");
	//		guitar[5] = new GuitarString(6, "D");
	//	}

	@Test
	public void testStandardTuning_OpenStrings() {
		//		standardTuning();
		ArrayList<Pitch> expected = new ArrayList<Pitch>();
		expected.add(new Pitch("E", 0, 4));
		expected.add(new Pitch("B", 0, 3));
		expected.add(new Pitch("G", 0, 3));
		expected.add(new Pitch("D", 0, 3));
		expected.add(new Pitch("A", 0, 2));
		expected.add(new Pitch("E", 0, 2));

		for (int i = 0; i < guitar.length; i++) {
			GuitarString string = guitar[i];
			Pitch p = string.createPitch(i+1, expected.get(i).getStep(), 0);

			assertEquals(expected.get(i).getStep(), p.getStep());
			assertEquals(expected.get(i).getAlter(), p.getAlter());
			assertEquals(expected.get(i).getOctave(), p.getOctave());
		}
	}

	//	@Test
	//	public void testStandardTuning_AllSharps() {
	////		standardTuning();
	//		ArrayList<Pitch> expected = new ArrayList<Pitch>();
	//		expected.add(new Pitch("D", 1, 5));
	//		expected.add(new Pitch("A", 1, 4));
	//		expected.add(new Pitch("F", 1, 4));
	//		expected.add(new Pitch("C", 1, 4));
	//		expected.add(new Pitch("G", 1, 3));
	//		expected.add(new Pitch("D", 1, 3));
	//		
	//		for (int i = 0; i < guitar.length; i++) {
	//			GuitarString string = guitar[i];
	//			Pitch p = string.createPitch(11); // in standard tuning, fret 11 has all sharps
	//			
	//			assertEquals(expected.get(i).getStep(), p.getStep());
	//			assertEquals(expected.get(i).getAlter(), p.getAlter());
	//			assertEquals(expected.get(i).getOctave(), p.getOctave());
	//		}
	//	}
	//	
	//	@Test
	//	public void testDropDTuning_OpenStrings() {
	//		dropDTuning();
	//		ArrayList<Pitch> expected = new ArrayList<Pitch>();
	//		expected.add(new Pitch("E", 0, 4));
	//		expected.add(new Pitch("B", 0, 3));
	//		expected.add(new Pitch("G", 0, 3));
	//		expected.add(new Pitch("D", 0, 3));
	//		expected.add(new Pitch("A", 0, 2));
	//		expected.add(new Pitch("D", 0, 2));
	//		
	//		for (int i = 0; i < guitar.length; i++) {
	//			GuitarString string = guitar[i];
	//			Pitch p = string.createPitch(0);
	//
	//			assertEquals(expected.get(i).getStep(), p.getStep());
	//			assertEquals(expected.get(i).getAlter(), p.getAlter());
	//			assertEquals(expected.get(i).getOctave(), p.getOctave());
	//		}
	//	}
	//	
	//	@Test
	//	public void testDropDTuning_Fret4() {
	//		dropDTuning();
	//		ArrayList<Pitch> expected = new ArrayList<Pitch>();
	//		expected.add(new Pitch("G", 1, 4));
	//		expected.add(new Pitch("D", 1, 4));
	//		expected.add(new Pitch("B", 0, 3));
	//		expected.add(new Pitch("F", 1, 3));
	//		expected.add(new Pitch("C", 1, 3));
	//		expected.add(new Pitch("F", 1, 2));
	//		
	//		for (int i = 0; i < guitar.length; i++) {
	//			GuitarString string = guitar[i];
	//			Pitch p = string.createPitch(4);
	//			
	//			assertEquals(expected.get(i).getStep(), p.getStep());
	//			assertEquals(expected.get(i).getAlter(), p.getAlter());
	//			assertEquals(expected.get(i).getOctave(), p.getOctave());
	//		}
	//	}
}
