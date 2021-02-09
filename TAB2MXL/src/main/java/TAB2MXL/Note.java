package TAB2MXL;

import java.util.HashMap;
import java.util.Map;

public class Note implements Comparable<Note> {
	private static final String[] ALL_NOTES = { "C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B" };
	private static final Map<String, Integer> ALL_NOTES_MAP = initAllNotesMap();
	private static final Map<Integer, Integer> GUITAR_OCTAVES = initGuitarOctaves();
	public Pitch pitch;
	public int duration;
	public String type;
	public int charIndex;
	public boolean slurStart;
	public boolean slurStop;
	public boolean tieStart;
	public boolean tieStop;
	public boolean slideStart;
	public boolean slideStop;

	/**
	 * Creates a Single Note
	 * 
	 * @param stringNumber a number from 1 to 6, 1 being the thinnest string and 6
	 *                     the thickest
	 * @param stringTuning the tuning note on the left of the tabs (C, C#, D, D#, E,
	 *                     F, F#, G, G#, A, A#, B);
	 * @param fret
	 * @param charIndex
	 */
	Note(int stringNumber, String stringTuning, int fret, int charIndex) {
		this.pitch = createPitch(stringNumber, stringTuning.toUpperCase(), fret);
		this.charIndex = charIndex;
	}

	@Override
	public String toString() {
		
		String toMXL = "";
		
		if(slurStart || tieStart || slideStart || slurStop || tieStop || slideStop) {
			if(slurStart)
				toMXL = "\t<note>\n" + this.pitch + "\t\t<duration> method not complete </duration>\n"
						+ "\t\t<type> method not complete </type>\n" + "\t\t<staff>1</staff>\n" + "\t\t<notations>\n" + "\t\t\t<slur type=\"start\"/>\n" 
						+ "\t\t</notations>\n" + "\t</note>";
			if(tieStart)
				toMXL = "\t<note>\n" + this.pitch + "\t\t<duration> method not complete </duration>\n"
						+ "\t\t<type> method not complete </type>\n" + "\t\t<staff>1</staff>\n" + "\t\t<notations>\n" + "\t\t\t<tie type=\"start\"/>\n" 
						+ "\t\t</notations>\n" + "\t</note>";
			if(slideStart)
				toMXL = "\t<note>\n" + this.pitch + "\t\t<duration> method not complete </duration>\n"
						+ "\t\t<type> method not complete </type>\n" + "\t\t<staff>1</staff>\n" + "\t\t<notations>\n" + "\t\t\t<slide type=\"start\"/>\n" 
						+ "\t\t</notations>\n" + "\t</note>";
			if(slurStop)
				toMXL = "\t<note>\n" + this.pitch + "\t\t<duration> method not complete </duration>\n"
						+ "\t\t<type> method not complete </type>\n" + "\t\t<staff>1</staff>\n" + "\t\t<notations>\n" + "\t\t\t<slur type=\"stop\"/>\n" 
						+ "\t\t</notations>\n" + "\t</note>";
			if(tieStop)
				toMXL = "\t<note>\n" + this.pitch + "\t\t<duration> method not complete </duration>\n"
						+ "\t\t<type> method not complete </type>\n" + "\t\t<staff>1</staff>\n" + "\t\t<notations>\n" + "\t\t\t<tie type=\"stop\"/>\n" 
						+ "\t\t</notations>\n" + "\t</note>";
			if(slideStop)
				toMXL = "\t<note>\n" + this.pitch + "\t\t<duration> method not complete </duration>\n"
						+ "\t\t<type> method not complete </type>\n" + "\t\t<staff>1</staff>\n" + "\t\t<notations>\n" + "\t\t\t<slide type=\"stop\"/>\n" 
						+ "\t\t</notations>\n" + "\t</note>";
		}

		
		else {
			toMXL = "\t<note>\n" + this.pitch + "\t\t<duration> method not complete </duration>\n"
				+ "\t\t<type> method not complete </type>\n" + "\t</note>";
		}

		return toMXL;
		
	}

	public Pitch getPitch() {
		return this.pitch;
	}

	/**
	 * Creates and returns a Pitch object which has the step, alter, and octave
	 * 
	 * @param stringNumber a number from 1 to 6, 1 being the thinnest string and 6
	 *                     the thickest
	 * @param stringTuning the tuning note on the left of the tabs (C, C#, D, D#, E,
	 *                     F, F#, G, G#, A, A#, B);
	 * @param fret
	 * @return a Pitch object
	 */
	private Pitch createPitch(int stringNumber, String stringTuning, int fret) {
		int tuningValue = ALL_NOTES_MAP.get(stringTuning);
		int noteIndex = (tuningValue + fret) % ALL_NOTES.length;
		String note = ALL_NOTES[noteIndex];

		String step = Character.toString(note.charAt(0));
		int alter = note.length() - 1;
		int octave = GUITAR_OCTAVES.get(stringNumber) + (tuningValue + fret) / ALL_NOTES.length;

		return new Pitch(step, alter, octave);
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

	private static Map<String, Integer> initAllNotesMap() {
		Map<String, Integer> allNotesMap = new HashMap<String, Integer>();
		allNotesMap.put("C", 0);
		allNotesMap.put("C#", 1);
		allNotesMap.put("D", 2);
		allNotesMap.put("D#", 3);
		allNotesMap.put("E", 4);
		allNotesMap.put("F", 5);
		allNotesMap.put("F#", 6);
		allNotesMap.put("G", 7);
		allNotesMap.put("G#", 8);
		allNotesMap.put("A", 9);
		allNotesMap.put("A#", 10);
		allNotesMap.put("B", 11);

		return allNotesMap;
	}
	
	@Override
	public int compareTo(Note object1) {
		return (this.charIndex - object1.charIndex);
	}
	

}
