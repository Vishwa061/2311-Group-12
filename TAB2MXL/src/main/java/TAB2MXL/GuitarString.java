package TAB2MXL;

import java.util.HashMap;
import java.util.Map;

public class GuitarString {
	private static final String[] ALL_NOTES = {"C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B"};
	private static final Map<Integer, Integer> OPEN_GUITAR_OCTAVES = initGuitarOctaves();
	private int tuningValue;
	private int stringNumber;

	/**
	 * A guitar string that can return Pitches given a fret
	 * 
	 * @param stringNumber a number from 1 to 6, 1 being the thinnest string and 6 the thickest
	 * @param stringTuning the tuning note on the left of the tabs (C, C#, D, D#, E, F, F#, G, G#, A, A#, B);
	 */
	public GuitarString(int stringNumber, String stringTuning) {
		this.stringNumber = stringNumber;
		for (int i = 0; i < ALL_NOTES.length; i++) {
			if (stringTuning.equals(ALL_NOTES[i])) {
				tuningValue = i;
				break;
			}
		}
	}
	
	/**
	 * Creates and returns a Pitch object which has the step, alter, and octave
	 * 
	 * @param fret
	 * @return a Pitch object
	 */
	public Pitch createPitch(int fret) {
		int noteIndex = (tuningValue + fret) % ALL_NOTES.length;
		String note = ALL_NOTES[noteIndex];
		
		String step = Character.toString(note.charAt(0));
		int alter = note.length() - 1;
		int octave = OPEN_GUITAR_OCTAVES.get(stringNumber) + (tuningValue + fret) / ALL_NOTES.length;
		
		return new Pitch(step, alter, octave);
	}

	public int getStringNumber() {
		return stringNumber;
	}
	
	private static Map<Integer, Integer> initGuitarOctaves() {
		Map<Integer, Integer> guitarOctaves = new HashMap<Integer, Integer>();
		guitarOctaves.put(1, 4);
		guitarOctaves.put(2, 3);
		guitarOctaves.put(3, 3);
		guitarOctaves.put(4, 3);
		guitarOctaves.put(5, 2);
		guitarOctaves.put(6, 2);
		
		return guitarOctaves;
	}
	
}