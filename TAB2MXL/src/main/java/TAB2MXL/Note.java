package TAB2MXL;

import java.util.HashMap;
import java.util.Map;

public class Note implements Comparable<Note> {
	public Pitch pitch;
	public Unpitched unpitched;
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

	/////////////// DELETE BEFORE PUSHING TO DEVELOP ///////////////
	class Unpitched {public Unpitched(String step, int octave) {}}
	////////////////////////////////////////////////////////////////
	
	private static final Map<String, Integer> DRUM_STEPS = initDrumOctaves();
	private static final Map<String, Integer> DRUM_OCTAVES = initDrumOctaves();
	private static final Map<String, Integer> DRUM_INSTRUMENT_IDS = initDrumOctaves();
	/////////////////////// MOVE TO UNPITCHED //////////////////////
	private static Map<String, Integer> initDrumSteps() {
		Map<String, Integer> drumSteps = new HashMap<String, Integer>();
		
		drumSteps.put("B", null);
		
		drumSteps.put("S", null);
		
		drumSteps.put("ST", null);
		
		drumSteps.put("MT", null);
		
		drumSteps.put("FT", null);
		
		drumSteps.put("H", null);
		
		drumSteps.put("HF", null);
		
		drumSteps.put("C", null);
		
		drumSteps.put("R", null);
		
		return drumSteps;
	}
	
	private static Map<String, Integer> initDrumOctaves() {
		Map<String, Integer> drumOctaves = new HashMap<String, Integer>();
		
		drumOctaves.put("B", null);
		
		drumOctaves.put("S", null);
		
		drumOctaves.put("ST", null);
		
		drumOctaves.put("MT", null);
		
		drumOctaves.put("FT", null);
		
		drumOctaves.put("H", null);
		
		drumOctaves.put("HF", null);
		
		drumOctaves.put("C", null);
		
		drumOctaves.put("R", null);
		
		return drumOctaves;
	}
	////////////////////////////////////////////////////////////////

	/**
	 * Creates a guitar note
	 * 
	 * @param stringNumber a number from 1 to 6, 1 being the thinnest string and 6
	 *                     the thickest
	 * @param stringTuning the tuning note on the left of the tabs (C, C#, D, D#, E,
	 *                     F, F#, G, G#, A, A#, B);
	 * @param fret
	 * @param charIndex
	 */
	public Note(int stringNumber, String stringTuning, int fret, int charIndex) {
		this.pitch = new Pitch(stringNumber, stringTuning, fret);
		this.charIndex = charIndex;
		this.stringNo = stringNumber;
		this.fret = fret;
	}

	/**
	 * Creates a drum note 
	 * 
	 * @param scoreInstrument - (ignore case except t) B/BD, S/SD, ST/HT/T1/T, MT/LT/T2/t, FT/T3, H/HH, HF, C/CR/CC, R/RD
	 * @param drumNote - O, f, d, b, x, X, o
	 * @param charIndex
	 */
	public Note(String scoreInstrument, String drumNote, int charIndex) {
		this.unpitched = createUnpitched(scoreInstrument, drumNote, charIndex);
		this.charIndex = charIndex;
	}
	
	private Unpitched createUnpitched(String scoreInstrument, String drumNote, int charIndex) {
		String step = "";
		int octave = 0;
		
		return new Unpitched(step, octave);
	}

	@Override
	public String toString() {

		String toMXL = "\t<note>\n";

		if (chord) {
			toMXL += "\t\t<chord/>\n";
		}

		toMXL += TabReader.instrument.equals("Drumset") ? this.unpitched : this.pitch;
		
		toMXL += "\t\t<duration>" + this.duration + "</duration>\n" + "\t\t<type>" + this.type
				+ "</type>\n";

		if (dot) {
			toMXL += "<dot/>\n";
		}

		toMXL += "\t\t<stem>down</stem>\n" + "\t\t<notations>\n" + "\t\t\t<technical>\n";

		if (hammerStart || hammerStop || pullStart || pullStop) {
			if(hammerStart) {
				toMXL += "\t\t\t\t<hammer-on type=\"start\">H</hammer-on>\n";
			}
			if(hammerStop) {
				toMXL += "\t\t\t\t<hammer-on type=\"stop\"/>\n";
			}
			if(pullStart) {
				toMXL += "\t\t\t\t<pull-off type=\"start\">P</pull-off>\n";
			}
			if(pullStop) {
				toMXL += "\t\t\t\t<pull-off type=\"stop\"/>\n";
			}
		}

		toMXL += "\t\t\t\t<string>" + this.stringNo + "</string>\n" + "\t\t\t\t<fret>" + this.fret + "</fret>\n"
				+ "\t\t\t</technical>\n";

		if (slurStart || slideStart || slurStop || slideStop) {
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
	
	public Unpitched getUnpitched() {
		return this.unpitched;
	}

	@Override
	public int compareTo(Note object1) {
		return (this.charIndex - object1.charIndex);
	}

}
