package TAB2MXL;

public class Note implements Comparable<Note> {
	public Pitch pitch;
	public Unpitch unpitch;
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
	public boolean bend;
	public boolean release;
	public int bendAlter;
	public boolean grace;

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
	 * @param scoreInstrument - (ignore case except t) B/BD, S/SN/SD, ST/HT/T1/T,
	 *                        MT/LT/T2/t, FT/T3, H/HH, HF, C/CR/CC, R/RD/RC
	 * @param drumsetNote     - O, f, d, b, x, X, o
	 * @param charIndex
	 */
	public Note(String scoreInstrument, String drumsetNote, int charIndex) {
		this.unpitch = new Unpitch(scoreInstrument, drumsetNote);
		this.charIndex = charIndex;
	}

	@Override
	public String toString() {

		String toMXL = "\t<note>\n";

		if (chord) {
			toMXL += "\t\t<chord/>\n";
		}
		
		if (grace) {
			toMXL += "\t\t<grace/>\n";
		}

		toMXL += this.pitch + "\t\t<duration>" + this.duration + "</duration>\n" + "\t\t<type>" + this.type
				+ "</type>\n";

		if (dot) {
			toMXL += "\t\t<dot/>\n";
		}

		toMXL += "\t\t<stem>down</stem>\n" + "\t\t<notations>\n" + "\t\t\t<technical>\n";

		if (hammerStart || hammerStop || pullStart || pullStop || bend || release) {
			if (hammerStop) 
				toMXL += "\t\t\t\t<hammer-on type=\"stop\"/>\n";	
			if (hammerStart) 
				toMXL += "\t\t\t\t<hammer-on type=\"start\">H</hammer-on>\n";				
			if (pullStop) 
				toMXL += "\t\t\t\t<pull-off type=\"stop\"/>\n";
			if (pullStart) 
				toMXL += "\t\t\t\t<pull-off type=\"start\">P</pull-off>\n";
			if (bend)
				toMXL += "\t\t\t\t<bend>\n" + "\t\t\t\t\t<bend-alter>" + bendAlter + "</bend-alter>\n"
						+ "\t\t\t\t</bend>\n";
			if (release)
				toMXL += "\t\t\t\t<bend>\n" + "\t\t\t\t\t<bend-alter>" + bendAlter + "</bend-alter>\n"
						+ "\t\t\t\t\t<release/>\n" + "\t\t\t\t</bend>\n";
		}

		toMXL += "\t\t\t\t<string>" + this.stringNo + "</string>\n" + "\t\t\t\t<fret>" + this.fret + "</fret>\n"
				+ "\t\t\t</technical>\n";

		if (slurStart || slideStart || slurStop || slideStop) {
			if (slurStop)
				toMXL += "\t\t\t<slur type=\"stop\"/>\n";
			if (slurStart)
				toMXL += "\t\t\t<slur type=\"start\"/>\n";
			if (slideStop)
				toMXL += "\t\t\t<slide type=\"stop\"/>\n";
			if (slideStart)
				toMXL += "\t\t\t<slide type=\"start\"/>\n";
		}

		toMXL += "\t\t</notations>\n" + "\t</note>";

		return toMXL;

	}

	public Pitch getPitch() {
		return this.pitch;
	}

	public Unpitch getUnpitch() {
		return this.unpitch;
	}

	@Override
	public int compareTo(Note object1) {
		return (this.charIndex - object1.charIndex);
	}

}
