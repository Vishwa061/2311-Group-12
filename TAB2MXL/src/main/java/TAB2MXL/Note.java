package TAB2MXL;

import java.util.HashMap;
import java.util.Map;

public class Note implements Comparable<Note> {
	private static final String[] ALL_NOTES = { "C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B" };
	public static final Map<String, Integer> ALL_NOTES_MAP = initAllNotesMap();
	private static final Map<Integer, Integer> GUITAR_OCTAVES = initGuitarOctaves();
	public Pitch pitch;
	public int duration;
	public String type;
	public int charIndex;
	public boolean slurStart;
	public boolean slurStop;
	public boolean slideStart;
	public boolean slideStop;
	public boolean pullStart;
	public boolean pullStop;
	public boolean hammerStart;
	public boolean hammerStop;
	public int stringNo;
	public int fret;
	public boolean dot;
	public boolean chord;
	public boolean bendStart;
	public boolean bendStop;
	public boolean reverseStart;
	public boolean reverseStop;
	public int bendAlter;

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
		this.pitch = createPitch(stringNumber, stringTuning.toUpperCase().replaceAll("\\s", ""), fret);
		this.charIndex = charIndex;
		this.stringNo = stringNumber;
		this.fret = fret;
	}

	@Override
	public String toString() {

		String toMXL = "\t<note>\n";

		if (chord) {
			toMXL += "\t\t<chord/>\n";
		}

		toMXL += this.pitch + "\t\t<duration>" + this.duration + "</duration>\n" + "\t\t<type>" + this.type
				+ "</type>\n";

		if (dot) {
			toMXL += "\t\t<dot/>\n";
		}

		toMXL += "\t\t<stem>down</stem>\n" + "\t\t<notations>\n";
		
		if (bendStart) {
			toMXL += "\t\t\t<articulations>\n" + "\t\t\t\t<staccato/>\n" + "\t\t\t</articulations>\n";
		}
		
		toMXL += "\t\t\t<technical>\n";

		if (hammerStart || hammerStop || pullStart || pullStop || bendStart) {
			if (hammerStart) {
				toMXL += "\t\t\t\t<hammer-on type=\"start\">H</hammer-on>\n";
			}
			if (hammerStop) {
				toMXL += "\t\t\t\t<hammer-on type=\"stop\"/>\n";
			}
			if (pullStart) {
				toMXL += "\t\t\t\t<pull-off type=\"start\">P</pull-off>\n";
			}
			if (pullStop) {
				toMXL += "\t\t\t\t<pull-off type=\"stop\"/>\n";
			}
			if (bendStart) {
				toMXL += "\t\t\t\t<bend>\n" + "\t\t\t\t\t<bend-alter>" + bendAlter + "</bend-alter>\n" + "\t\t\t\t</bend>\n";
			}
		}

		toMXL += "\t\t\t\t<string>" + this.stringNo + "</string>\n" + "\t\t\t\t<fret>" + this.fret + "</fret>\n"
				+ "\t\t\t</technical>\n";

		if (slurStart || slideStart || slurStop || slideStop || bendStart || bendStop || reverseStart || reverseStop) {
			if (slurStart)
				toMXL += "\t\t\t<slur type=\"start\"/>\n";
			if (slideStart)
				toMXL += "\t\t\t<slide type=\"start\"/>\n";
			if (slurStop)
				toMXL += "\t\t\t<slur type=\"stop\"/>\n";
			if (slideStop)
				toMXL += "\t\t\t<slide type=\"stop\"/>\n";
		}

		toMXL += "\t\t</notations>\n" + "\t</note>";

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
